package com.fash.example.kafkav.avroserializers;

import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fash.example.kafkav.avro.Order;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class GenericOrderProducer {

	public static void main(String[] args) {

		Properties props = new Properties();
		// Mandatory properties
		props.setProperty("bootstrap.servers", "localhost:9092"); // uncomment if you want to run from your machine
//		props.setProperty("bootstrap.servers", "kafka:9092");
		props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
		props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
		props.setProperty("schema.registry.url", "http://localhost:8081"); // uncomment if you want to run from your machine
//		props.setProperty("schema.registry.url", "http://schema-registry:8081");

		KafkaProducer<String, GenericRecord> kafkaProducer = new KafkaProducer<>(props);
		Parser parser = new Schema.Parser();
		Schema schema = parser.parse("{\r\n"
				+ "\"namespace\":\"com.fash.example.kafkav.avro\",\r\n"
				+ "\"type\":\"record\",\r\n"
				+ "\"name\":\"Order\",\r\n"
				+ "\"fields\":[\r\n"
				+ "{\"name\":\"customerName\", \"type\":\"string\"},\r\n"
				+ "{\"name\":\"product\", \"type\":\"string\"},\r\n"
				+ "{\"name\":\"quantity\", \"type\":\"int\"}\r\n"
				+ "]\r\n"
				+ "}");
		
		GenericRecord order = new GenericData.Record(schema);
		order.put("customerName", "Heriberto Fabian Santiago");
		order.put("product", "Iphone");
		order.put("quantity", 150);
		
//		Order order = new Order("Heriberto Fabian", "Iphone", 100);

		ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(
				"OrderAvroGRTopic",order.get("customerName").toString(), order);

		/*
		 * En el siguiente ejemplo se lanza un mensaje y se olvida por que nunca
		 * esperamos una respuesta de vuelta In the next example we fire and forget the
		 * message, because we didn't wait for the response to come back
		 */
		try {
			kafkaProducer.send(record);
			System.out.println("Message Sent successfully");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			kafkaProducer.close();
		}

	}

}
