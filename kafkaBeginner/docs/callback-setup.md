# Configuración con CallBack

Este ejemplo muestra cómo utilizar callbacks con un productor de Kafka.

## Archivos Involucrados

- `OrderCallBack.java`
- `OrderConsumer.java`
- `OrderProducer.java`

## Pasos para Ejecutar

1. Asegúrese de que Zookeeper y Kafka estén en ejecución.
2. Crear un topic para este ejemplo:

    ```bash
    ./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic OrderTopic
    ```

3. Ejecutar el productor:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.OrderProducer"
    ```

4. Ejecutar el consumidor:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.OrderConsumer"
    ```

## Descripción

- **OrderProducer**: Envía mensajes al topic `OrderTopic` utilizando un callback para manejar la confirmación del envío.
- **OrderConsumer**: Lee mensajes del topic `OrderTopic`.
