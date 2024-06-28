# Configuración de un partitioner personalizado

Por defecto, Kafka busca la partición especificada en los registros; si no la encuentra, usa la clave del mensaje para calcular un hash y asignarlo a una partición. En este ejemplo, aprenderás a crear tu propio particionador personalizado y a asignar registros a la partición que definas.

También aprenderás algunas configuraciones avanzadas para tu productor que influirán en su funcionamiento y en la reacción de los brokers de Kafka.

## Archivos Involucrados

- `PACKAGE: com.fash.example.kafkav.secondexample.customserializers.partitioner`
- `OrderConsumer.java`
- `OrderProducer.java`
- `VIPPartitioner.java`

## Pasos para Ejecutar

1. Asegúrese de que Zookeeper y Kafka estén en ejecución.
2. Crear un topic para este ejemplo:

    ```bash
    ./bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 10 --topic OrderPartitionedTopic
    ```

3. Ejecutar el productor desde tu IDE o bien desde la terminal:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.customserializers.partitioner.OrderProducer"
    ```

4. Ejecutar el productor desde tu IDE o bien desde la terminal:

    ```bash
    mvn exec:java -Dexec.mainClass="com.fash.example.kafkav.secondexample.customserializers.partitioner.OrderConsumer"
    ```

## Descripción

- **OrderProducer**: Envía mensajes al topic `OrderPartitionedTopic` utilizando un serializador personalizado.
- **OrderConsumer**: Lee mensajes del topic `OrderPartitionedTopic` utilizando un deserializador personalizado.
- **VIPPartitioner**: implementa la interfaz `Partitioner` de Kafka y permite personalizar la lógica de asignación de particiones para mensajes específicos. 

```java
	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.availablePartitionsForTopic(topic);
		if(((String)key).equals("Heriberto5")) {
		return 5;
		}
		return Math.abs(Utils.murmur2(keyBytes)%partitions.size()-1);
	}
```

Esta clase es un ejemplo de cómo puedes crear un particionador personalizado en Kafka que asigna ciertos mensajes a una partición específica y distribuye otros mensajes de manera uniforme entre las particiones disponibles.

  - Obtiene la lista de particiones disponibles para un tema específico.
  - Si la clave del mensaje es "Heriberto5", asigna el mensaje a la partición 5.
  - Para cualquier otra clave, utiliza el algoritmo de hash `murmur2` para calcular una partición de manera uniforme.


## Configuración del Productor en Kafka

Al configurar un productor en Kafka, es importante definir varias propiedades que afectarán su comportamiento y rendimiento. La clase `ProducerConfig` proporciona constantes para todas las configuraciones posibles, lo que ayuda a evitar errores tipográficos y facilita la lectura del código. A continuación se describen algunas de las propiedades más importantes configuradas en nuestro ejemplo:

- **`ProducerConfig.ACKS_CONFIG`**: Define el nivel de reconocimiento que el productor requiere del broker. El valor `"1"` significa que el líder de la partición debe confirmar que ha recibido el registro.

- **`ProducerConfig.BUFFER_MEMORY_CONFIG`**: Establece la cantidad total de memoria que el productor puede usar para almacenar los registros a la espera de ser enviados al servidor. En este caso, se ha configurado a `"343434334"` bytes.

- **`ProducerConfig.COMPRESSION_TYPE_CONFIG`**: Especifica el tipo de compresión que se usará para los registros. El valor `"gzip"` indica que se utilizará la compresión GZIP, lo que puede reducir el tamaño de los datos enviados y mejorar el rendimiento de la red.

- **`ProducerConfig.RETRIES_CONFIG`**: Indica el número de intentos de reenvío que se realizarán en caso de fallo. El valor `"2"` significa que el productor intentará reenviar el registro hasta dos veces antes de fallar.

- **`ProducerConfig.RETRY_BACKOFF_MS_CONFIG`**: Establece el tiempo de espera entre reintentos en milisegundos. Con un valor de `"400"`, el productor esperará 400 ms antes de intentar reenviar un registro fallido.

- **`ProducerConfig.BATCH_SIZE_CONFIG`**: Define el tamaño máximo de un lote de registros. El valor `"10243434343"` bytes significa que el productor intentará enviar registros en lotes de hasta ese tamaño para mejorar el rendimiento mediante la reducción del número de solicitudes.

- **`ProducerConfig.LINGER_MS_CONFIG`**: Establece el tiempo máximo que el productor esperará para acumular un lote de registros antes de enviarlo al broker. Con un valor de `"500"` ms, el productor esperará hasta medio segundo para acumular un lote de registros.

- **`ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG`**: Define el tiempo máximo que el productor esperará una respuesta del broker antes de considerar la solicitud como fallida. El valor `"200"` ms significa que el productor esperará hasta 200 ms antes de fallar una solicitud.

### Beneficios de Usar `ProducerConfig`

Usar la clase `ProducerConfig` en lugar de cadenas literales tiene varias ventajas:

1. **Evita Errores Tipográficos**: Al usar constantes definidas, reduces la posibilidad de cometer errores tipográficos que pueden ser difíciles de depurar.
2. **Legibilidad**: Las constantes proporcionadas por `ProducerConfig` son descriptivas y facilitan la comprensión del código.
3. **Mantenibilidad**: Si Kafka cambia el nombre de alguna propiedad en el futuro, solo necesitarás actualizar la constante en `ProducerConfig` en lugar de buscar y reemplazar todas las ocurrencias en tu código.

Al configurar tu productor con estas propiedades y usando `ProducerConfig`, puedes optimizar su comportamiento y rendimiento mientras mantienes tu código limpio y fácil de mantener.
