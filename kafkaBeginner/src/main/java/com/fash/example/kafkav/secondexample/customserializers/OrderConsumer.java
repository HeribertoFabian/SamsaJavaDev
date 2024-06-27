package com.fash.example.kafkav.secondexample.customserializers;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class OrderConsumer {
	public static void main(String[] args) {
		
		Properties props = new Properties();
		// Mandatory properties
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.deserializer", StringDeserializer.class.getName());
		props.setProperty("value.deserializer", OrderDeserializer.class.getName());
		props.setProperty("group.id", "OrderCSGroup");
		
		KafkaConsumer<String,Order> kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Collections.singletonList("OrderCSTopic"));		
		ConsumerRecords<String, Order> records = kafkaConsumer.poll(Duration.ofSeconds(20));
		
		
		for(ConsumerRecord<String, Order> record: records) {
			String customerName = record.key();
			Order current_order = record.value();
			System.out.println("Customer name: " + customerName);
			System.out.println("Product: "+ current_order.getProduct());
			System.out.println("Quantity: " + current_order.getQuantity());
		}
		kafkaConsumer.close();
	}
}
