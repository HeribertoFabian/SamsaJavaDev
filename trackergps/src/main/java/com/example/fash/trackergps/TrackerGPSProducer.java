package com.example.fash.trackergps;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.example.fash.trackergps.serializer.TruckCoordinatesSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrackerGPSProducer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		props.setProperty("value.serializer", TruckCoordinatesSerializer.class.getName());
		
		KafkaProducer<Integer, VehicleGPSData> kafkaProducer = new KafkaProducer<>(props);
		
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = TrackerGPSProducer.class.getResourceAsStream("/gps_tracking_data.json");
		
		try {
			List<VehicleGPSData> vehiculos = mapper.readValue(stream, new TypeReference<List<VehicleGPSData>>() {});
			for(VehicleGPSData vehiculo: vehiculos) {
				ProducerRecord<Integer, VehicleGPSData> record = new ProducerRecord<Integer, VehicleGPSData>("GPSTracker", vehiculo.getID(), vehiculo);
				kafkaProducer.send(record, new TrackerGPSKafkaCallback());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			kafkaProducer.close();
		}		
	}
}
