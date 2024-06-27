

- [1. Introduccion](#1-introduccion)
- [Kafka Beginner Project](#kafka-beginner-project)
  - [Contenido](#contenido)
  - [Requisitos](#requisitos)
  - [Comandos Esenciales](#comandos-esenciales)
    - [1. Levantar Zookeeper](#1-levantar-zookeeper)
    - [2. Levantar los brokers de kafka](#2-levantar-los-brokers-de-kafka)
    - [3. Crear un topic](#3-crear-un-topic)



# 1. Introduccion

# Kafka Beginner Project

Este proyecto contiene ejemplos de configuración y uso de Apache Kafka, incluyendo configuraciones básicas, uso de callbacks, y serializadores/deserializadores personalizados.

## Contenido

- **Configuración Básica**: [docs/basic-setup.md](docs/basic-setup.md)
- **Configuración con CallBack**: [docs/callback-setup.md](docs/callback-setup.md)
- **Configuración con Custom Serializer/Deserializer**: [docs/custom-serializer-setup.md](docs/custom-serializer-setup.md)

## Requisitos

- Apache Kafka
- Java Development Kit (JDK)
- Maven

## Comandos Esenciales

### 1. Levantar Zookeeper

```bash
./bin/windows/zookeeper-server-start.bat ./config/zookeeper.properties
```

### 2. Levantar los brokers de kafka

```bash
./bin/windows/kafka-server-start.bat ./config/server.properties
```

### 3. Crear un topic

**importante** 
Dentro de la documentacion de cada proyecto de ejemplo viene el topic que debe ser creado, el siguiente comando solo es para fines ilustrativos

```bash
./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic <TopicName>
```

