package com.example.fash.trackergps;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.example.fash.trackergps.serializer.TruckCoordinatesDeserializer;

public class TrackerGPSConsumer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
		props.setProperty("value.deserializer", TruckCoordinatesDeserializer.class.getName());
		props.setProperty("group.id", "TrackerGroup");
		
		KafkaConsumer<Integer, VehicleGPSData> kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Collections.singletonList("GPSTracker"));		
		ConsumerRecords<Integer, VehicleGPSData> vehiculos = kafkaConsumer.poll(Duration.ofSeconds(30));
		
		
		for(ConsumerRecord<Integer, VehicleGPSData> vehiculo: vehiculos) {
			System.out.print("ID:" + vehiculo.key());
			System.out.println(" | " + vehiculo.value().toString());
		}
		
		kafkaConsumer.close();
		
		
		
	}
}
