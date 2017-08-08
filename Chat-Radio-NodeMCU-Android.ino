

#include <SPI.h>
#include "RF24.h"
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>

String lastMsg = "";
/* Configuração da conexão com o rádio: CE =  D0 , CS = D1 */
RF24 radio(D0, D1);
//Quantidade de bytes a ser transmitido por vez
//OBS: 32 é o máximo permitido pelo módulo NRF24L01
#define PAYLOAD_SIZE 32
byte addresses[][6] = {"RAD_0", "RAD_1"};
/******************************************************

MUITA ANTENÇÃO NO TRECHO DE CÓDIGO ABAIXO!!!

******************************************************/
#define RADIO_0 0
#define RADIO_1 1
//Especificação de qual dos rádios membros será utilizado
//Mudar o radioNumber, não permitirndo que o número utilizado
//para cada radio seja o mesmo.
//Atualmente, o código dá suporte à comunicação entre dois(2) rádios
bool radioNumber = RADIO_1;

/* Configuração da conexão com a NodeMCU
*O nome da REDE é o mesmo que número do radio(radioNumber) utilizado
*/
const char *ssid = "RADIO_1"; //RADIO_0 ou RADIO_1
const char *password = "";
ESP8266WebServer server(80);

unsigned long previousMillis = 0;
int ledStatus = HIGH;
#define INTERVAL 100

void setup()
{
	pinMode(D4, OUTPUT);
	Serial.begin(115200);

	//Configuração do ponto de acesso wifi
	Serial.println("\n\nConfigurando ponto de acesso");
	WiFi.softAP(ssid, password);
	IPAddress myIP = WiFi.softAPIP();
	Serial.print("Endereco de IP: ");
	Serial.println(myIP);

	//Configurção das rotas
	server.on("/", handleRoot);
	server.on("/req", handleRequestMsg);

	server.begin();
	Serial.println("Servidor iniciado!");

	//Inicialização do rárdio
	radio.begin();
	//Abre o fluxo de entrada e saída de acordo com o radioNumber escolhido
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
	radio.setPayloadSize(PAYLOAD_SIZE);
	//Coloca o rádio em modo ouvinte
	radio.startListening();
}

void loop()
{
	server.handleClient();
	char msgBytes[PAYLOAD_SIZE] = "";
	if (radio.available())
	{
		radio.read(msgBytes, PAYLOAD_SIZE);
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
		ledStatus = !ledStatus;
		digitalWrite(D4, ledStatus);
		previousMillis = currentMillis;
	}
}

void handleRoot()
{
	String msg = server.arg("msg");
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
	radio.startListening(); // Now, continue listening
}