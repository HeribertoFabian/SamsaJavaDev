# Configuración Básica

Este ejemplo demuestra una configuración básica de Kafka con un productor y un consumidor.

## Archivos Involucrados

- `Consumidor.java`
- `Productor.java`

## Pasos para Ejecutar

1. Asegúrese de que Zookeeper y Kafka estén en ejecución.
2. Crear un topic para este ejemplo:

    ```bash
    ./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic topic-test
    ```

3. Ejecutar el productor:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.Productor"
    ```

4. Ejecutar el consumidor:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.Consumidor"
    ```

## Descripción

- **Productor**: Envía mensajes al topic `topic-test`.
- **Consumidor**: Lee mensajes del topic `topic-test`.