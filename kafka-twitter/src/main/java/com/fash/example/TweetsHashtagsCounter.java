package com.fash.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
 
import java.util.Collections;
import java.util.Properties;
 
public class TweetsHashtagsCounter {
 
   public static ObjectMapper objectMapper = new ObjectMapper();
   public final static String TOPIC_NAME = "rawtweets";
 
   public static void main(String[] args) {
 
       Properties props = new Properties();
       props.put(StreamsConfig.APPLICATION_ID_CONFIG, "tweethashtagscounterapp");
       props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
       props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
 
       final StreamsBuilder builder = new StreamsBuilder();
       final KStream<String, String> tweets = builder.stream(TOPIC_NAME);
 
       tweets.flatMapValues(value -> Collections.singletonList(getHashtags(value)))
               .groupBy((key, value) -> value)
               .count()
               .toStream()
               .print(Printed.toSysOut());
 
       Topology topology = builder.build();
       final KafkaStreams streams = new KafkaStreams(topology, props);
       streams.start();
 
   }
 
   public static String getHashtags(String input) {
	    JsonNode root;
	    try {
	        // Parsear la cadena de entrada (input) en un objeto JSON usando ObjectMapper
	        root = objectMapper.readTree(input);
	        
	        // Navegar por el objeto JSON para obtener el nodo "hashtags" dentro de "entities"
	        JsonNode hashtagsNode = root.path("entities").path("hashtags");
	        
	        // Verificar si el nodo "hashtags" no está vacío (no es un array vacío)
	        if (!hashtagsNode.toString().equals("[]")) {
	            // Devolver el valor del primer hashtag encontrado
	            return hashtagsNode.get(0).path("tag").asText();
	        }
	    } catch (Exception e) {
	        // Manejar cualquier excepción que ocurra durante el procesamiento del JSON
	        e.printStackTrace();
	    }
	    // Devolver una cadena vacía si no se encontraron hashtags o si ocurrió una excepción
	    return "";
	}

}