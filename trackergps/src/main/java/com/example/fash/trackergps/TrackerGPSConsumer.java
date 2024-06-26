package com.example.fash.trackergps;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TrackerGPSConsumer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("group.id", "TrackerGroup");
		
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Collections.singletonList("GPSTracker"));		
		ConsumerRecords<String, String> vehiculos = kafkaConsumer.poll(Duration.ofSeconds(30));
		
		
		for(ConsumerRecord<String, String> vehiculo: vehiculos) {
			System.out.print("Vehiculo: [ ID:" + vehiculo.key());
			System.out.println(", Coordenadas:" + vehiculo.value()+ "]");
		}
		
		kafkaConsumer.close();
		
		
		
	}
}
