# Kafka Twitter Project

Este proyecto configura un entorno de Apache Kafka para procesar tweets. A continuación se detallan los pasos necesarios para levantar Zookeeper, los brokers de Kafka y crear un topic.

## Requisitos

- Apache Kafka
- Java Development Kit (JDK)

## Pasos para arrancar el proyecto

### 1. Levantar Zookeeper

Para iniciar Zookeeper, ejecute el siguiente comando en su terminal:

```bash
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```
### 2. Levantar los brokers de Kafka

Para iniciar los brokers de Kafka, ejecute el siguiente comando en su terminal:

```bash
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

### 3. Crear el topic

Para crear un topic en Kafka, ejecute el siguiente comando en su terminal:

```bash
.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic rawtweets
```

### NOTAS ADICIONALES

- segúrese de que los archivos de configuración zookeeper.properties y server.properties estén correctamente configurados y ubicados en el directorio config.
- El topic rawtweets se crea con un factor de replicación de 1 y 2 particiones. Ajuste estos valores según sus necesidades y configuración del clúster de Kafka.