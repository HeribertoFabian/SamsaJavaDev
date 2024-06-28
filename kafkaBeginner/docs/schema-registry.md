# Configuración usando schema-registry y avro

En este ejemplo veremos como ejecutar la aplicacion conectandonos a Docker en el cual tendremos configurada la plataforma de confluent, por lo cual es importante haber leido la documentación sobre configuracion de Docker
**Confluent Platform en Docker**: [docs/confluent-platform-settings.md](docs/confluent-platform-settings.md)


## Archivos Involucrados

- `../src/main/java/com/fash/example/kafkav/avroserializers/OrderConsumer.java`
- `../src/main/java/com/fash/example/kafkav/avroserializers/OrderProducer.java`
- `../src/main/java/com/fash/example/kafkav/avro/Order.java`
- `../src/main/resources/order.avsc`

## Pasos para Ejecutar

1. Asegúrese de que Zookeeper y Kafka estén en ejecución. 

    **Confluent Platform en Docker**: [docs/confluent-platform-settings.md](docs/confluent-platform-settings.md) 

2. Crear un topic para este ejemplo:

```bash
docker ps
```
Busca la entrada correspondiente al contenedor de kafka, por ejemplo: confluentinc/cp-kafka:7.3.0

Crea un topic de kafka con el comando kafka-topics dentro de ese contenedor, supongamos que el nombre del contenedor es **confluent-platform_kafka_1 kafka-topics** 

```bash
docker exec -it confluent-platform_kafka_1 kafka-topics --create --topic OrderAvroTopic --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1
```

3. Ejecutar el consumidor:

Puedes ejecutar desde tu IDE o bien desde la terminal

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.customserializers.OrderConsumer"
    ```

4. Ejecutar el productor:

Puedes ejecutar desde tu IDE o bien desde la terminal

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.customserializers.OrderProducer"
    ```



## Descripción

- **OrderProducer**: Envía mensajes al topic `OrderAvroTopic` utilizando un serializador KafkaAvroSerializer.
- **OrderConsumer**: Lee mensajes del topic `OrderAvroTopic` utilizando un deserializador KafkaAvroDeserializer.


## ISSUES

Si por alguna razón no puedes conectar tu aplicativo al kafka que está corriendo en docker, entonces tendras que seguir los pasos del archivo
**Run Project Inside Kafka Image**: [docs/run-project-inside-kafka-image.md](docs/run-project-inside-kafka-image.md) 
