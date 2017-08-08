
/*
* Getting Started example sketch for nRF24L01+ radios
* This is a very basic example of how to send data from one node to another
* Updated: Dec 2014 by TMRh20
*/

#include <SPI.h>
#include "RF24.h"
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
/****************** User Config ***************************/
/***      Set this radio as radio number 0 or 1         ***/
bool radioNumber = 0;
String lastMsg = "";
/* Hardware configuration: Set up nRF24L01 radio on SPI bus plus pins 7 & 8 */
RF24 radio(D0, D1);
/**********************************************************/
const char *ssid = "NODE";
const char *password = "";
ESP8266WebServer server(80);

unsigned long previousMillis = 0;
boolean ledStatus = false;

#define INTERVAL 500
#define PAYLOAD_SIZE 32

byte addresses[][6] = {"1Node", "2Node"};

void setup()
{
	pinMode(D4, OUTPUT);
	Serial.begin(115200);
	// Serial.println(F("RF24/examples/GettingStarted"));
	Serial.println("\n\nConfiguring access point...");
	// You can remove the password parameter if you want the AP to be open.
	WiFi.softAP(ssid, password);
	IPAddress myIP = WiFi.softAPIP();
	Serial.print("AP IP address: ");
	Serial.println(myIP);
	server.on("/", handleRoot);
	server.on("/req", handleRequestMsg);
	server.begin();
	Serial.println("HTTP server started");

	radio.begin();
	// Set the PA Level low to prevent power supply related issues since this is a
	// getting_started sketch, and the likelihood of close proximity of the devices. RF24_PA_MAX is default.
	//radio.setPALevel(RF24_PA_LOW);

	// Open a writing and reading pipe on each radio, with opposite addresses
	if (radioNumber)
	{
		radio.openWritingPipe(addresses[1]);
		radio.openReadingPipe(1, addresses[0]);
	}
	else
	{
		radio.openWritingPipe(addresses[0]);
		radio.openReadingPipe(1, addresses[1]);
	}
	Serial.print("Radio Conectado: ");
	Serial.println(radio.isChipConnected() ? "SIM" : "NAO");
	// Start the radio listening for data
	radio.setPayloadSize(PAYLOAD_SIZE);
	radio.startListening();
}

void loop()
{
	char msgBytes[PAYLOAD_SIZE] = "";
	if (radio.available())
	{
		radio.read(msgBytes, PAYLOAD_SIZE); // Get the payload
		if (strlen(msgBytes) > 0)
		{
			msgBytes[strlen(msgBytes)] = '\0';
			lastMsg.concat(msgBytes);
			Serial.println(msgBytes);
		}
	}
	if (Serial.available() > 0)
	{
		Serial.readBytesUntil('\r', msgBytes, PAYLOAD_SIZE);
		sendRadioMsg(msgBytes);
	}

	unsigned long currentMillis = millis();
	if (currentMillis - previousMillis >= INTERVAL)
	{
		//ledStatus = !ledStatus;
		digitalWrite(D4, HIGH);
		previousMillis = currentMillis;
	}
	server.handleClient();
}

/* Just a little test message.  Go to http://192.168.4.1 in a web browser
 * connected to this access point to see it.
 */
void handleRoot()
{
	String msg = server.arg("msg");
	//String page = "<html><head><title>Chat arduino</title></head><body><form method='GET'><input type='text'name='msg'id ='msg'/><input type='submit'name='send'id='send'/></form></body></html> ";
	//server.send(200, "text/html", page);
	//char msgBytes[32] = "";
	if (msg.length() > 0)
	{
		char *msgBytes = (char *)malloc(sizeof(char) * (msg.length() + 1));
		msg.toCharArray(msgBytes, msg.length() + 1);
		int writeDataSize = 0;
		int msgSize = strlen(msgBytes);
		do
		{
			sendRadioMsg(msgBytes + writeDataSize);
			writeDataSize += PAYLOAD_SIZE;
		} while (writeDataSize < msgSize);
		free(msgBytes);
	}
	else
	{
		server.send(200, "", "");
	}
}

void handleReceivedMsg()
{
}

void handleRequestMsg()
{
	if (!lastMsg.equals(""))
	{
		server.send(200, "text/html", lastMsg);
		lastMsg = "";
	}
	else
	{
		server.send(200, "", "");
	}
}

void sendRadioMsg(char *msg)
{
	radio.stopListening(); // First, stop listening so we can talk
	msg[strlen(msg)] = '\0';
	if (!radio.write(msg, strlen(msg)))
	{
		Serial.println("Failed");
		server.send(200, "text/html", "fail");
	}
	else
	{
		server.send(200, "", "");
	}
	delay(10);
	radio.startListening(); // Now, continue listening
}