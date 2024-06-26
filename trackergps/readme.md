# Kafka GPS Tracker Project

Este proyecto configura un entorno de Apache Kafka para procesar datos de seguimiento GPS de vehículos. A continuación se detallan los pasos necesarios para levantar Zookeeper, los brokers de Kafka, crear un topic y leer datos desde un archivo JSON ubicado en el directorio `resources`.

## Requisitos

- Apache Kafka
- Java Development Kit (JDK)
- Maven

## Pasos para arrancar el proyecto

### 1. Levantar Zookeeper

Para iniciar Zookeeper, ejecute el siguiente comando en su terminal:

```bash
./bin/windows/zookeeper-server-start.bat ./config/zookeeper.properties
```

### 2. Levantar los Brokers de Kafka

Para iniciar los brokers de Kafka, ejecute el siguiente comando en su terminal:

```bash
./bin/windows/kafka-server-start.bat ./config/server.properties
```


### 3. Crear el topic

Para crear un topic en Kafka, ejecute el siguiente comando en su terminal:

```bash
./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic GPSTracker
```

### 4. Ejecuta el consumidor

Ahora puedes ejecutar el consumidor, el cual tendra una duration de 30 antes de cerrarse

### 5. Ejecuta el Productor

Ahora ejecuta el productor en otra ventana