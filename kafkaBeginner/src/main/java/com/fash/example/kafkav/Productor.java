package com.fash.example.kafkav;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Productor {

    public static void main(String[] args) {
        // Crear una instancia de Properties para almacenar las configuraciones del productor
        Properties props = new Properties();
        
        // Configurar el serializador para las claves de los mensajes
        // StringSerializer convierte las claves en una cadena de bytes para ser enviada a Kafka
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        // Configurar el serializador para los valores de los mensajes
        // StringSerializer convierte los valores en una cadena de bytes para ser enviada a Kafka
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
//         Configurar los acknowledgements para las solicitudes:
//         "all" significa que el líder y todas las réplicas deben confirmar la recepción del mensaje,
//         proporcionando la mayor durabilidad posible
//        "0": El productor no espera ninguna confirmación.
//        "1": Solo el líder de la partición debe confirmar.
//        "all": El líder y todas las réplicas en sincronización deben confirmar, garantizando la mayor durabilidad.
        props.put("acks", "all");
        
        // Configurar los brokers del clúster de Kafka a los que se conectará el productor
        // Estos brokers se utilizan para descubrir el clúster Kafka
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093, localhost:9094");
        
        // Configurar el número de reintentos en caso de fallos al enviar mensajes
        // Un valor de 0 significa que no se reintentará
        props.put("retries", 0);
        
        // Configurar el tamaño del lote en bytes:
        // La cantidad de datos que se acumularán antes de enviar un lote de mensajes
        // 16384 bytes (16 KB) es un valor común que proporciona un buen equilibrio entre latencia y rendimiento
        props.put("batch.size", 16384);
        
        // Configurar la cantidad total de memoria que el productor puede usar para almacenar
        // los registros que aún no se han enviado
        // 33554432 bytes (32 MB) permite al productor acumular un gran número de mensajes antes de enviar
        props.put("buffer.memory", 33554432);
        


        KafkaProducer<String, String> prod = new KafkaProducer<>(props);
        
        // Definir el topic al que se enviarán los mensajes
        String topic = "topic-test";
        
        // Definir la partición a la que se enviará el mensaje (opcional)
        // Las particiones permiten que los registros de un topic se distribuyan entre múltiples brokers
        int partition = 0;
        
        // Definir la clave del mensaje (opcional)
        // Las claves se utilizan para garantizar que todos los mensajes con la misma clave se envíen a la misma partición
        String key = "testkey";
        
        // Definir el valor del mensaje
        // Este es el contenido del mensaje que se enviará a Kafka
        String value = "testvalue";
        
        // Enviar el mensaje al topic especificado, partición, clave y valor
        // ProducerRecord representa un registro que será enviado a Kafka
//        1a FORMA
//         en el siguiente ejemplo se configura el metodo send para aceptar la perdida de mensajes
//         pero no es recomendado en aplicaciones productivas
//        prod.send(new ProducerRecord<>(topic, partition, key, value));
        
//        2A FORMA
//        para realizar un envio sincrono apropiado para datos criticos o transacciones se usa un get del resultado
//        El cual espera la confirmacion del broker, en este caso la perdida de mensajes no es aceptable
//        try {
//			prod.send(new ProducerRecord<>(topic, partition, key, value)).get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
//        3A FORMA
        final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
        prod.send(record, new Callback() {
			
			@Override
			public void onCompletion(RecordMetadata metadata, Exception e) {
				if(e != null) {
					System.out.println("Send failed for record");
				}
				
			}
		});
        
        
        
        
        // Cerrar el productor para liberar los recursos
        // Es importante cerrar el productor para asegurarse de que todos los mensajes en el buffer se envíen
        prod.close();
    }
}



/*

0	No se espera confirmación del broker. Riesgo de pérdida de mensajes.									Baja				Muy baja
1	El líder confirma la recepción del mensaje. Equilibrio entre durabilidad y latencia.					Media				Media
all	El líder y todas las réplicas en sincronización confirman la recepción del mensaje. Alta durabilidad.	Alta	Alta

*/