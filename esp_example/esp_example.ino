#include <ESP8266WiFi.h>                                // Подключаем библиотеку ESP8266WiFi
#include <Wire.h>                                       // Подключаем библиотеку Wire
#include <Adafruit_BME280.h>                            // Подключаем библиотеку Adafruit_BME280
#include <Adafruit_Sensor.h>                            // Подключаем библиотеку Adafruit_Sensor
#include <ArduinoJson.h>
#include <ESP8266HTTPClient.h>
 
Adafruit_BME280 bme;                                    // Установка связи по интерфейсу I2C
 
const char* ssid = "TestNetIoT";         // Название Вашей WiFi сети
const char* password = "TestNetIoT";     // Пароль от Вашей WiFi сети
const char* postUrl = "http://192.168.1.119:8080/api/device/v1/addMeasure"; //URL отправки показаний
const char* apiKey = "335cd88f-a0d5-4772-97b3-3bf067e2a4b3"; //API ключ
const int interval = 1; // интервал отправки показаний (минуты)


void setup() {
  Serial.begin(115200);                                 // Скорость передачи 115200
  bool status;
  Serial.print("\n\n\n\n\n");                                                 
  if (!bme.begin(0x76)) {                               // Проверка инициализации датчика
    Serial.println("Could not find a valid BME280 sensor, check wiring!"); // Печать, об ошибки инициализации.
    while (1);                                          // Зацикливаем
  }
 
  Serial.print("Connecting to ");                       // Отправка в Serial port 
  Serial.println(ssid);                                 // Отправка в Serial port 
  WiFi.begin(ssid, password);                           // Подключение к WiFi Сети
  while (WiFi.status() != WL_CONNECTED) {               // Проверка подключения к WiFi сети
    delay(500);                                         // Пауза
    Serial.print(".");                                  // Отправка в Serial port 
  }
 
  Serial.println("");                                   // Отправка в Serial port 
  Serial.println("WiFi connected.");                    // Отправка в Serial port 
  Serial.println("IP address: ");                       // Отправка в Serial port 
  Serial.println(WiFi.localIP());                       // Отправка в Serial port                                  
}


String genJson(float temp,float press, float humid){
  String output;
  StaticJsonDocument<512> doc;
  doc["key"] = apiKey;
  JsonArray measure = doc.createNestedArray("measure");
  JsonObject measure_0 = measure.createNestedObject();
  measure_0["variable"] = "temp";
  measure_0["data"] = temp;
  JsonObject measure_1 = measure.createNestedObject();
  measure_1["variable"] = "press";
  measure_1["data"] = press;
  JsonObject measure_2 = measure.createNestedObject();
  measure_2["variable"] = "humid";
  measure_2["data"] = humid;
  serializeJson(doc, output);
  return output;
}
void loop(){
  sendMeasure();
  delay(1000*60*interval);
}

void sendMeasure(){
  HTTPClient http;
  if (WiFi.status() == WL_CONNECTED) {
    //Specify request destination
    http.begin(postUrl);
    http.addHeader("Content-Type", "application/json");
    //Send the request
    String json = genJson(bme.readTemperature(), (bme.readPressure() / 100.0F)*0.75F, bme.readHumidity());
    Serial.println(json);
    int httpCode = http.POST(json);
    //Check the returning code
    if (httpCode > 0) { 
      //Get the request response payload
      String payload = http.getString();
      //Print the response payload
      Serial.print(httpCode);
      Serial.print(" ");
      Serial.println(payload);
    }
    else{
      Serial.print("Fail ");
      Serial.println(httpCode);
    }
    //Close connection
    http.end();
  }
}
