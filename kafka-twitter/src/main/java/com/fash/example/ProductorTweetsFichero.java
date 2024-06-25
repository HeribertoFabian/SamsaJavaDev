package com.fash.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class ProductorTweetsFichero {
 
   public final static String TOPIC_NAME = "rawtweets";
   public static ObjectMapper objectMapper = new ObjectMapper();
 
   public static void main (String[] args){
 
       Properties props = new Properties();
       props.put("acks", "1");
       props.put("retries", 3);
       props.put("batch.size", 16384);
       props.put("buffer.memory", 33554432);
       props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
 
    // Crear una instancia de KafkaProducer con las propiedades especificadas (props)
       final KafkaProducer<String, String> prod = new KafkaProducer<>(props);

       try (BufferedReader br = new BufferedReader(new InputStreamReader(
               ProductorTweetsFichero.class.getResourceAsStream("/tweets.txt")))) {		//El uso de try-with-resources garantiza que el BufferedReader se cierre automáticamente.
           String line;
           // Leer el archivo línea por línea
           while ((line = br.readLine()) != null) {
               JsonNode root;
               try {
                   // Convertir la línea leída en un objeto JSON
                   root = objectMapper.readTree(line);
                   // Extraer el nodo "hashtags" del objeto JSON
                   JsonNode hashtagsNode = root.path("entities").path("hashtags");
                   // Verificar si el nodo "hashtags" no está vacío
                   if (!hashtagsNode.toString().equals("")) {
                       // Convertir todo el objeto JSON en una cadena para el valor del mensaje
                       String value = root.toString();
                       // Extraer el campo "lang" del objeto JSON para usarlo como clave del mensaje
                       String lang = root.path("lang").toString();
                       // Enviar el mensaje al topic especificado en ProductorTweetsFichero.TOPIC_NAME
                       // La clave del mensaje es "lang" y el valor es el JSON completo
                       prod.send(new ProducerRecord<>(ProductorTweetsFichero.TOPIC_NAME, lang, value));
                   }
               } catch (Exception e) {
                   // Manejar y mostrar cualquier excepción que ocurra al procesar la línea
                   e.printStackTrace();
               }
           }
       } catch (IOException e) {
           // Manejar y lanzar una excepción en caso de error al leer el archivo
           throw new RuntimeException(e);
       }

   }
}
