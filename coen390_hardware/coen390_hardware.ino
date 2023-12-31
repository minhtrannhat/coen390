//======================================== Including the libraries.
#if defined(ESP32)
#include <WiFi.h>
#elif defined(ESP8266)
#include <ESP8266WiFi.h>
#endif
#include <Firebase_ESP_Client.h>

#include "conf.h"
//========================================

//Provide the token generation process info.
#include "addons/TokenHelper.h"

//Provide the RTDB payload printing info and other helper functions.
#include "addons/RTDBHelper.h"

// Defines the Digital Pin of the "On Board LED".
#define On_Board_LED 2

// Define Firebase Data object.
FirebaseData fbdo;

// Define firebase authentication.
FirebaseAuth auth;

// Definee firebase configuration.
FirebaseConfig config;

//======================================== Millis variable to send/store data to firebase database.
unsigned long sendDataPrevMillis = 0;
const long sendDataIntervalMillis = 5000;  //--> Sends/stores data to firebase database every 5 seconds.
//========================================

// Boolean variable for sign in status.
bool signupOK = false;

// defines pins numbers
const int trigPin = 12;
const int echoPin = 14;

// defines variables
long duration;
int distance;

bool calibrationMode = false;
bool spotStatusCurrent = false;
bool spotStatusOld = false;

int refValue = 0;
int smallestCar = 80;
String fullPath;

// calibrates refValue
void calibrate() {

  int upperBound = 390;
  int lowerBound = 200;
  //stores obtained variables
  int arr[15] = {};
  int var = 0;
  int n_values = 0;
  // repeats measurments 15 times
  for (int i = 0; i < 15; i++) {
    var = getMeasurement();
    if (var >= lowerBound && var <= upperBound) {
      arr[i] = var;
      n_values++;
    }
    delayMicroseconds(1000);
  }

  int average = 0;
  // gets average from array
  for (int i = 0; i < 15; i++) {
    average += arr[i];
  }

  // checks if average value will be 0 and sets a default average
  if (average <= 0) {
    average = 200;
  } else {
    average = average / n_values;
  }

  refValue = average;
}

// gets measurments from sensor
int getMeasurement() {
  // Clears the trigPin
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);
  // Calculating the distance
  return duration * 0.034 / 2;
}


void setup() {
  Serial.begin(115200);
  Serial.println();

  pinMode(trigPin, OUTPUT);  // Sets the trigPin as an Output
  pinMode(echoPin, INPUT);   // Sets the echoPin as an Input

  //---------------------------------------- The process of connecting the WiFi on the ESP32 to the WiFi Router/Hotspot.
  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.println("---------------Connection");
  Serial.print("Connecting to : ");
  Serial.println(WIFI_SSID);

  while (WiFi.status() != WL_CONNECTED) {
    Serial.println("connecting...");
  }
  digitalWrite(On_Board_LED, LOW);
  Serial.println();
  Serial.print("Successfully connected to : ");
  Serial.println(WIFI_SSID);

  Serial.println("---------------");

  // Assign the api key (required).
  config.api_key = API_KEY;

  // Assign the RTDB URL (required).
  config.database_url = DATABASE_URL;

  // Sign up.
  Serial.println();
  Serial.println("---------------Sign up");
  Serial.print("Sign up new user... ");
  if (Firebase.signUp(&config, &auth, "", "")) {
    Serial.println("ok");
    signupOK = true;
  } else {
    Serial.printf("%s\n", config.signer.signupError.message.c_str());
  }
  Serial.println("---------------");

  // Assign the callback function for the long running token generation task.
  config.token_status_callback = tokenStatusCallback;  //--> see addons/TokenHelper.h

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);

  if (calibrationMode == true || refValue == 0) {
    calibrate();
    Serial.println("Finished Calibration.");
  }

  fullPath = String(FB_PATH) + String(SPOT);
}


void loop() {
  if (Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > sendDataIntervalMillis || sendDataPrevMillis == 0)) {
    sendDataPrevMillis = millis();

    distance = getMeasurement();

    if (refValue - distance <= refValue - smallestCar) {
      spotStatusCurrent = false;
    } else {
      spotStatusCurrent = true;
    }
    //---------------------------------------- The process of sending/storing data to the firebase database.
    Serial.println();
    Serial.println("---------------Store Data");

    Serial.println("Current Distance:");
    Serial.println(distance);
    Serial.println("Current Status:");
    Serial.println(spotStatusCurrent);

    // only upload data if it changes
    if (spotStatusCurrent != spotStatusOld) {

      if (Firebase.RTDB.setBool(&fbdo, F(fullPath.c_str()), spotStatusCurrent)) {
        Serial.println("PASSED");
        Serial.println("PATH: " + fbdo.dataPath());
        Serial.println("TYPE: " + fbdo.dataType());
      } else {
        Serial.println("FAILED");
        Serial.println("REASON: " + fbdo.errorReason());
      }

      spotStatusOld = spotStatusCurrent;
    }

    Serial.println();
    Serial.println("RefValue: ");
    Serial.println(refValue);
    Serial.println("---------------");
    //----------------------------------------
  }
}
