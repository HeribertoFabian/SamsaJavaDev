package com.example.fash.trackergps;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.example.fash.trackergps.model.VehicleGPSData;
import com.fash.example.kafkav.avro.TruckCoordinates;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

public class AvroSerializerTrackerGPSConsumer {

	public static void main(String[] args) {
		
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");// uncomment if you want to run from your machine
//		props.setProperty("bootstrap.servers", "kafka:9092");
		props.setProperty("key.deserializer", KafkaAvroDeserializer.class.getName());
		props.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
		props.setProperty("group.id", "TrackerGroup");
		props.setProperty("schema.registry.url", "http://localhost:8081"); // uncomment if you want to run from your machine
//		props.setProperty("schema.registry.url", "http://schema-registry:8081");
		props.setProperty("specific.avro.reader", "true");
		
		
		KafkaConsumer<Integer, TruckCoordinates> kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Collections.singletonList("GPSTracker"));		
		ConsumerRecords<Integer, TruckCoordinates> vehiculos = kafkaConsumer.poll(Duration.ofSeconds(30));
		
		
		for(ConsumerRecord<Integer, TruckCoordinates> vehiculo: vehiculos) {
			System.out.print("ID:" + vehiculo.key());
			System.out.println(" ( " + vehiculo.value().getLatitude() + ", " + vehiculo.value().getLongitude() + ")");
		}
		
		kafkaConsumer.close();
		
		
		
	}
}
