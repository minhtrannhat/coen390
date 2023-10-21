# COEN390 Hardware

- Place for Arduino/Espressif ESP32 files and build instructions.
- Also put CAD files and other design files here.

## Setup

### General

- Install Arduino IDE
- Install ESP32 board information in Boards Manager
- `File` -> `coen390_hardware` to open this Arduino project

### Firebase

- Go to `Sketch` -> `Include library` -> `Manage libraries` and find the ESP32 Firebase client by mobizt, click install
- Create a Firebase project

  - Set up anon authentication
  - Create a test real-time firestore
  - Copy the Firestore URL
  - Copy the Firebase Web API
  - Create a file called `conf.h` in this directory (same folder with the `coen390_hardware.ino` file)
  - Paste this in, change the strings accordingly

  ```
    #ifndef CONF_H
    #define CONF_H

    #define WIFI_SSID ""     // your Wifi name
    #define WIFI_PASSWORD "" // your Wifi password

    #define API_KEY ""      // Firebase web API key
    #define DATABASE_URL "" // Real-time Firestore URL

    #endif
  ```

  - Build & upload the sketch to ESP32

## Troubleshooting

### Guru Meditation Error: Core 0 panic'ed (IllegalInstruction). Exception was unhandled

- A flash reset may be needed, install `esptool` via `pip` and run `python3 -m esptool --chip esp32 erase_flash` to erase flash.
- Upload the sketch again.
