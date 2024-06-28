package com.example.fash.trackergps;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.example.fash.trackergps.callbacks.TrackerGPSKafkaCallback;
import com.example.fash.trackergps.model.VehicleGPSData;
import com.fash.example.kafkav.avro.TruckCoordinates;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class AvroSerializerTrackerGPSProducer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092"); // uncomment if you want to run from your machine
//		props.setProperty("bootstrap.servers", "kafka:9092");
		props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
		props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
		props.setProperty("schema.registry.url", "http://localhost:8081"); // uncomment if you want to run from your machine
//		props.setProperty("schema.registry.url", "http://schema-registry:8081");
		
		KafkaProducer<Integer, TruckCoordinates> kafkaProducer = new KafkaProducer<>(props);
		
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = AvroSerializerTrackerGPSProducer.class.getResourceAsStream("/gps_tracking_data.json");
		
		try {
			List<VehicleGPSData> vehiculos = mapper.readValue(stream, new TypeReference<List<VehicleGPSData>>() {});
			for(VehicleGPSData vehiculo: vehiculos) {
				TruckCoordinates truckCoordinates = convertToTruckCoordinates(vehiculo);
				ProducerRecord<Integer, TruckCoordinates> record = new ProducerRecord<>("GPSTracker", truckCoordinates.getID(), truckCoordinates);
				kafkaProducer.send(record, new TrackerGPSKafkaCallback());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			kafkaProducer.close();
		}		
	}
	
	
    public static TruckCoordinates convertToTruckCoordinates(VehicleGPSData vehicleGPSData) {
        TruckCoordinates truckCoordinates = new TruckCoordinates();
        truckCoordinates.setID(vehicleGPSData.getID());
        truckCoordinates.setLatitude(String.valueOf(vehicleGPSData.getLatitude()));
        truckCoordinates.setLongitude(String.valueOf(vehicleGPSData.getLongitude()));
        return truckCoordinates;
    }
    
    
    
}
