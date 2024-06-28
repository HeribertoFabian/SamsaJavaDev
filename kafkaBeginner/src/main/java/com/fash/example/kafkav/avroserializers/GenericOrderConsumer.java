package com.fash.example.kafkav.avroserializers;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fash.example.kafkav.avro.Order;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

public class GenericOrderConsumer {
	public static void main(String[] args) {

		Properties props = new Properties();
		// Mandatory properties
		props.setProperty("bootstrap.servers", "localhost:9092");  // uncomment if you want to run from your machine
//		props.setProperty("bootstrap.servers", "kafka:9092");
		props.setProperty("key.deserializer", KafkaAvroDeserializer.class.getName());
		props.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
		props.setProperty("group.id", "OrderAvroGroup");
		props.setProperty("schema.registry.url", "http://localhost:8081"); // uncomment if you want to run from your machine
//		props.setProperty("schema.registry.url", "http://schema-registry:8081");

		KafkaConsumer<String, GenericRecord> kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Collections.singletonList("OrderAvroGRTopic"));

		try {
//			while (true) {
				ConsumerRecords<String, GenericRecord> records = kafkaConsumer.poll(Duration.ofSeconds(60));

				for (ConsumerRecord<String, GenericRecord> record : records) {
					String customerName = record.key();
					GenericRecord current_order = record.value();
					System.out.println("Customer name: " + customerName);
					System.out.println("Product: " + current_order.get("product"));
					System.out.println("Quantity: " + current_order.get("quantity"));
				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			kafkaConsumer.close();
		}
	}
	
}
