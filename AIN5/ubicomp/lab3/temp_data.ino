#include <WiFiNINA.h>
#include <Arduino_LSM6DSOX.h>

void setup() {
  Serial.begin(9600);
  while (!Serial);
  if (!IMU.begin()) {
	Serial.println("Failed to initialize IMU!");
	while (1);
  }
}

void loop() {
  int temp = 0;
  IMU.readTemperature(temp);
  Serial.println(temp);
  delay(1000);
}
