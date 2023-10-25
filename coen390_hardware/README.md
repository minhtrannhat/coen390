# COEN390 Hardware

- Place for Arduino/Espressif ESP32 files and build instructions.
- Also put CAD files and other design files here.

## Setup

### General

- Install Arduino IDE
- Install ESP32 board information in Boards Manager
- `File` -> `coen390_hardware` to open this Arduino project


### Hardware

The Ultra Sound sensor has four pins that need to be connected to the board `GND`,`Echo`,`Trig`,`Vcc`. 
- The `GND` is for the ground and needs to be connected to the same port on the board. A `black` cable should be used.
- The `Vcc` is the voltage and should be connected to the voltage port on the board indicated either by `Vcc` or `5V` / `3.3V`. It is recommended to use the `5V` option if it is available. A `red` or `white` cable should be used here.
- For the `Echo`,`Trig` , any of the numbered pins need to be used, however make sure that the number of the pins is the same in the code. Use different cables to differentiate. 
Example:

 ```
  // trig pin names
  const int trigPin = 12;
  const int echoPin = 14;

```

In the code the pin 12 on the board is used for the Trig, and pin 14 for the Echo.

For more information go to this [page](https://howtomechatronics.com/tutorials/arduino/ultrasonic-sensor-hc-sr04/).

### Code

When starting the program wait a few seconds for the device to adjust its reference value ( no more than 5). 

Then you can move the sensor and compare the results. The `refValue` returns the obtained reference value during calibration,  the `distance` variable from the terminal returs the currently obtained value,the final value returns 1 or 0 ( true or false) for if the spot is taken of free. 
