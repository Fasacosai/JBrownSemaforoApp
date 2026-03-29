int ledVerde = 7;
int ledAmarillo = 6;
int ledRojo = 5;

void setup() {
  Serial.begin(9600);
  
  pinMode(ledVerde, OUTPUT);
  pinMode(ledAmarillo, OUTPUT);
  pinMode(ledRojo, OUTPUT);

  Serial.println("Conectado!");
  Serial.println("0=Apagar todo");
  Serial.println("1=Encender verde");
  Serial.println("2=Apagar verde");
  Serial.println("3=Encender amarillo");
  Serial.println("4=Apagar amarillo");
  Serial.println("5=Encender rojo");
  Serial.println("6=Apagar rojo");
}

void loop() {
  if (Serial.available()) {
    char dato = Serial.read();

    switch (dato) {
      case '0':
        digitalWrite(ledVerde, LOW);
        digitalWrite(ledAmarillo, LOW);
        digitalWrite(ledRojo, LOW);
        Serial.println("Todos apagados");
        break;

      case '1':
        digitalWrite(ledVerde, HIGH);
        Serial.println("Verde encendido");
        break;

      case '2':
        digitalWrite(ledVerde, LOW);
        Serial.println("Verde apagado");
        break;

      case '3':
        digitalWrite(ledAmarillo, HIGH);
        Serial.println("Amarillo encendido");
        break;

      case '4':
        digitalWrite(ledAmarillo, LOW);
        Serial.println("Amarillo apagado");
        break;

      case '5':
        digitalWrite(ledRojo, HIGH);
        Serial.println("Rojo encendido");
        break;

      case '6':
        digitalWrite(ledRojo, LOW);
        Serial.println("Rojo apagado");
        break;
    }
  }
}