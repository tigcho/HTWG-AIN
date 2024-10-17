#include <Arduino_LSM6DSOX.h>
#include <WiFiNINA.h>

void setup() {
  pinMode(LEDR, OUTPUT);
  pinMode(LEDG, OUTPUT);
  pinMode(LEDB, OUTPUT);
  if (!IMU.begin()) {
    Serial.println("Failed to initialize IMU!");
    while (1);
  }
}

void loop() {
  if (IMU.temperatureAvailable())
  {
    int temperature_deg = 0;
    IMU.readTemperature(temperature_deg);

    Serial.print("LSM6DSOX Temperature = ");
    Serial.print(temperature_deg);
    Serial.println(" °C");

    if (temperature_deg > 32) {
      digitalWrite(LEDR, HIGH);
    } if (temperature_deg < 25) {
      digitalWrite(LEDB, HIGH);
    } if (temperature_deg >= 20 && temperature_deg <= 36) {
      digitalWrite(LEDG, HIGH);
    }
  }
  delay(500);
  digitalWrite(LEDR, LOW);
  digitalWrite(LEDB, LOW);
  digitalWrite(LEDG, LOW); 
}