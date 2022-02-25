#include <Servo.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27,16,2); 

// 0,1 == transfer pins
const int actuatorLiftPower = 2;
const int moveLiftUpPin     = 3;
const int servoPin          = 4;
const int topLimitPin       = 5;
const int bottomLimitPin    = 6;

const int greenLedPin       = 7;
const int yellowLedPin      = 8;
const int redLedPin         = 9;
const int smallRedLedPin    = 10;
const int smallGreenLedPin  = 11;
const int waitingLedPin     = 12;

/* --------------------------------- */
const int   MAXDIPCOUNT =     15;
const int   SECOND =          1000;
const long  MINUTE =          60L * SECOND;
const long  HOUR =            60 * MINUTE;
const int   LIDMAXPOSITION =  150;
const int   IMMERSIONTIME =   15 * SECOND;
const long  DRYING_TIME =     2 * HOUR;
/* --------------------------------- */

int dipCounter = 0;
boolean debugFlag = true;
Servo lidServo;

void setup() {
  Serial.begin(9600);
  lidServo.attach(servoPin, 484, 2500);
  moveLid(0);
  setPinModes();
  stop();
  initLcd();
  debug("vaappuhissi v.36");
  wait(2000);
}

void moveLid(int pos) {
  int current = lidServo.read();
  while (current > pos) {
    lidServo.write(--current);
    wait(100);
  }
  while (current < pos) {
    lidServo.write(++current);
    wait(100);
  }
  wait(30);
}

void setPinModes() {
  pinMode(moveLiftUpPin, OUTPUT);
  pinMode(actuatorLiftPower, OUTPUT);
  pinMode(topLimitPin, INPUT_PULLUP);
  pinMode(bottomLimitPin, INPUT_PULLUP);
  pinMode(greenLedPin, OUTPUT);
  pinMode(yellowLedPin, OUTPUT);
  pinMode(redLedPin, OUTPUT);
  pinMode(smallRedLedPin, OUTPUT);
  pinMode(smallGreenLedPin, OUTPUT);
  pinMode(waitingLedPin, OUTPUT);
}

void stop() {
  debug("stopping");
  setPin(moveLiftUpPin);
  setPin(actuatorLiftPower);
  wait(500);
  blinkLed();
}

void initLcd() {
  lcd.init();
  lcd.clear();
  lcd.setCursor(1,0);
  lcd.backlight();
}

void debug(String s) {
  lcd.clear();
  if (s.length() > 16) {
    lcd.print(s.substring(0, 16));
    lcd.setCursor(0, 1);
    lcd.print(s.substring(16));
  } else {
    lcd.print(s);
  }
  Serial.println(s);
}

void debug(int i) {
  Serial.println(i);
}

void wait(long timeInMs) {
  delay(timeInMs);
}

void loop() {
  openLid();
  goDownUntilLimit();
  wait(IMMERSIONTIME);
  goUpUntilLimit();
  dipCounter++;
  closeLid();
  waitUntilDryOrFinishRun();
}

void openLid() {
  debug("opening lid.");
  moveLid(LIDMAXPOSITION);
}

void goDownUntilLimit() {
  int tmp = 0;
  while (!isBottomLimit()) {
    moveDown();
    wait(200);
    if (++tmp > 1000) {
      handleError();
    }
  }
  stop();
}

boolean isBottomLimit() {
  return readPin(bottomLimitPin);
}

boolean readPin(int pin) {
  return digitalRead(pin) > 0;
}

void moveDown() {
  setPin(moveLiftUpPin);
  clearPin(actuatorLiftPower);// PUOLALAINEN KÄÄNTEISLOGIIKKA RELAY-MODULISSA.
  debug("moving down");
}

void handleError() {
  stop();
  debug("VIRHE XXXXXXXXXXXXXXXXXXXXXXX");
  lcd.clear();
  lcd.print("  ------- VIRHE ------- "); 
  while(true){
    wait(900);
    toggleLeds();
  }
}

void goUpUntilLimit() {
  int tmp = 0;
  while (!isTopLimit()) {
    moveUp();
    wait(200);
    if (++tmp > 1000) {
      handleError();
    }
  }
  stop();
  while (isTopLimit()) {
    moveDown();
    wait(20);
  }
  stop();
}

boolean isTopLimit() {
  return readPin(topLimitPin);
}

void moveUp() {
  clearPin(actuatorLiftPower);// PUOLALAINEN KÄÄNTEISLOGIIKKA RELAY-MODULISSA.
  clearPin(moveLiftUpPin);
  debug("moving up");
}

void toggleLeds(){
  toggleLed(greenLedPin);
  toggleLed(yellowLedPin);
  toggleLed(redLedPin);
}

void closeLid() {
  debug("closing lid");
  moveLid(LIDMAXPOSITION);
}

void waitUntilDryOrFinishRun() {
  if (dipCounter < MAXDIPCOUNT) {
    waitWithLedBlinking();
  } else {
    printEndStatus();
    lcd.setCursor(0, 1);
    while(true) {
      lcd.print("."); 
      wait(30 * MINUTE);
    }
  } 
}

void waitWithLedBlinking() {
  int seconds = DRYING_TIME / 1000;
  lcd.clear();  
  while (seconds > 0) {
    delay(1000);
    seconds--;
    printStatus(seconds);
    toggleLed(waitingLedPin);
  }
}

void printEndStatus(){
  lcd.clear();
  lcd.print("done "); 
  lcd.print(dipCounter);  
  lcd.print(" dips."); 
}

void clearLeds() {
  clearPin(greenLedPin);
  clearPin(yellowLedPin);
  clearPin(redLedPin);
}

void blinkLed() {
  blinkLed(smallRedLedPin, SECOND);
}

void blinkLed(const int pin, const int time){
  setPin(pin);
  wait(time);
  clearPin(pin);
}

void debug(long l) {
  Serial.println(l);
}

void printStatus(int seconds){
  lcd.setCursor(0, 0);
  lcd.print("waiting " + (String) seconds + " s  ");
  lcd.setCursor(0, 1);
  lcd.print("dip (" + (String) dipCounter + "/10)  ");
}

void toggleLed(int pin) {
  if (readPin(pin)) {
    clearPin(pin);
  } else {
    setPin(pin);
  }
}

void setPin(int pin) {
  digitalWrite(pin, HIGH);
}

void clearPin(int pin) {
  digitalWrite(pin, LOW);
}
