package com.fash.example.kafkav;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumidor {

    public static void main(String[] args) {
        // Crear una instancia de Properties para almacenar las configuraciones del consumidor
        Properties props = new Properties();
        
        // Configurar el deserializador para las claves de los mensajes
        // StringDeserializer convierte las claves de bytes en cadenas
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        
        // Configurar el deserializador para los valores de los mensajes
        // StringDeserializer convierte los valores de bytes en cadenas
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        
        // Configurar los brokers del clúster de Kafka a los que se conectará el consumidor
        // Estos brokers se utilizan para descubrir el clúster Kafka
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        
        // Configurar el identificador del grupo de consumidores al que pertenece este consumidor
        // Los consumidores con el mismo group.id comparten la carga de lectura de los mensajes
        props.put("group.id", "grupo1");
        
        // Habilitar el auto commit de los desplazamientos (offsets)
        // Esto significa que los desplazamientos se confirmarán automáticamente
        props.put("enable.auto.commit", "true");
        
        // Configurar el intervalo en milisegundos para el auto commit de los desplazamientos
        // Cada 1000 ms (1 segundo), los desplazamientos se confirmarán automáticamente
        props.put("auto.commit.interval.ms", "1000");
        
        // Configurar el tamaño mínimo en bytes que debe tener una recuperación (fetch) de mensajes
        // No se devolverán menos de 1 byte
        props.put("fetch.min.bytes", "1");
        
        // Configurar el tiempo máximo en milisegundos que el servidor esperará para obtener fetch.min.bytes de mensajes antes de devolver los datos
        // En este caso, 500 ms
        props.put("fetch.max.wait.ms", "500");
        
        // Configurar el tamaño máximo en bytes por partición que el consumidor recuperará en una sola solicitud de fetch
        // 1048576 bytes (1 MB)
        props.put("max.partition.fetch.bytes", "1048576");
        
        // Configurar el tiempo máximo en milisegundos que el consumidor esperará para recibir un latido (heartbeat) antes de considerarlo inactivo
        // 10000 ms (10 segundos)
        props.put("session.timeout.ms", "10000");
        
        // Crear una instancia de KafkaConsumer con las configuraciones especificadas
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        
        try {
            // Suscribirse al topic "topic-test"
            // Esto hace que el consumidor comience a recibir mensajes de este topic
            consumer.subscribe(Collections.singletonList("topic-test"));
            
            // Bucle infinito para seguir leyendo los mensajes
            while (true) {
                // Recuperar los registros de los mensajes del topic especificado
                // El método poll espera durante el tiempo especificado para recibir mensajes (10 segundos en este caso)
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
                
                // Procesar cada registro recibido
                for (ConsumerRecord<String, String> record : records) {
                    // Imprimir el topic del mensaje
                    System.out.print("Topic: " + record.topic() + ", ");
                    
                    // Imprimir la partición del mensaje
                    System.out.print("Partition: " + record.partition() + ", ");
                    
                    // Imprimir la clave del mensaje
                    System.out.print("Key: " + record.key() + ", ");
                    
                    // Imprimir el valor del mensaje
                    System.out.println("Value: " + record.value() + ", ");
                }
            }
        } catch (Exception e) {
            // Capturar y mostrar cualquier excepción que ocurra durante la ejecución
            e.printStackTrace();
        } finally {
            // Cerrar el consumidor para liberar los recursos
            consumer.close();
        }
    }
}
