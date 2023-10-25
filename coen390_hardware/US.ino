

// defines pins numbers
const int trigPin = 9;
const int echoPin = 10;
// defines variables
long duration;
int distance;

bool calibrationMode = false;
bool occupied = false;

int refValue = 0;
int smallestCar = 80;


void setup() {
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input
  Serial.begin(9600); // Starts the serial communication
}

// calibrates refValue
void() calibrate{
	
	int upperBound = 390;
	int lowerBound = 200;
	//stores obtained variables
	int arr[15] = {};
	int var = 0;
	int n_values = 0;
	// repeats measurments 15 times
	for(int i =0; i <15;i++){
		var = getMeasurement();
		if( var >= lowerBound && var <=upperBound){
			arr[i] = var;
			n_values++;
		}
		delayMicroseconds(1000);
	}
	
	int average = 0;
	// gets average from array
	for(int i =0; i<15;i++){
		average += arr[i];
	}
	average = average/n_values;
	refValue = average;
	
}

// gets measurments from sensor
int getMeasurement(){
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


void loop() {
	
	if(calibrateMode == true && refValue == 0){
		calibrate();
	}
	distance = getMeasurement();
	
	if(refValue-distance <= refValue-smallestCar){
		occupied = true;
	}
	else{
		occupied = false;
	}
	
	// ping server
	
	
	// 5 seconds delay
	delayMicroseconds(5000);
 

}