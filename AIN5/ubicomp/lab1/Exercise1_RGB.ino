#include <WiFiNINA.h>

const int delayTime = 500; // 0.5 seconds

void setup() {
  pinMode(LEDR, OUTPUT);
  pinMode(LEDG, OUTPUT);
  pinMode(LEDB, OUTPUT);
}

void loop() {
  blink_red();
  delay(delayTime);

  blink_blue();
  delay(delayTime);

  blink_green();
  delay(delayTime);
}

void blink_red() {
  digitalWrite(LEDR, HIGH);
  digitalWrite(LEDB, LOW);
  digitalWrite(LEDG, LOW);
}

void blink_blue() {   
  digitalWrite(LEDR, LOW);
  digitalWrite(LEDB, HIGH);
  digitalWrite(LEDG, LOW);
}

void blink_green() {
  digitalWrite(LEDR, LOW);
  digitalWrite(LEDB, LOW);
  digitalWrite(LEDG, HIGH);
}