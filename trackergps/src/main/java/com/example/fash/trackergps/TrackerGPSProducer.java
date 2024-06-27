package com.example.fash.trackergps;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderProducer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);
		
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = JsonReaderProducer.class.getResourceAsStream("/gps_tracking_data_with_strings.json");
		
		try {
			List<VehicleGPSData> vehiculos = mapper.readValue(stream, new TypeReference<List<VehicleGPSData>>() {});
			for(VehicleGPSData vehiculo: vehiculos) {
				ProducerRecord<String, String> record = new ProducerRecord<String, String>("GPSTracker", vehiculo.getID(), vehiculo.getCoordinates());
				kafkaProducer.send(record, new TrackerGPSKafkaCallback());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			kafkaProducer.close();
		}
		
		
		
	}
}
