# Configuración con Custom Serializer/Deserializer

Este ejemplo muestra cómo utilizar serializadores y deserializadores personalizados con Kafka.

## Archivos Involucrados

- `Order.java`
- `OrderConsumer.java`
- `OrderDeserializer.java`
- `OrderProducer.java`
- `OrderSerializer.java`

## Pasos para Ejecutar

1. Asegúrese de que Zookeeper y Kafka estén en ejecución.
2. Crear un topic para este ejemplo:

    ```bash
    ./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic OrderCSTopic
    ```

3. Ejecutar el productor:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.customserializers.OrderProducer"
    ```

4. Ejecutar el consumidor:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.customserializers.OrderConsumer"
    ```

## Descripción

- **OrderProducer**: Envía mensajes al topic `OrderCSTopic` utilizando un serializador personalizado.
- **OrderConsumer**: Lee mensajes del topic `OrderCSTopic` utilizando un deserializador personalizado.
