package com.fash.example.kafkav.secondexample.customserializers.partitioner;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fash.example.kafkav.secondexample.customserializers.Order;
import com.fash.example.kafkav.secondexample.customserializers.OrderDeserializer;

public class OrderConsumer {
	public static void main(String[] args) {
		
		Properties props = new Properties();
		// Mandatory properties
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class.getName());
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "OrderPartitionedGroup");
		
		KafkaConsumer<String,Order> kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Collections.singletonList("OrderPartitionedTopic"));		
		ConsumerRecords<String, Order> records = kafkaConsumer.poll(Duration.ofSeconds(60));
		
		
		for(ConsumerRecord<String, Order> record: records) {
			String customerName = record.key();
			Order current_order = record.value();
			System.out.println("Customer name: " + customerName);
			System.out.println("Product: "+ current_order.getProduct());
			System.out.println("Quantity: " + current_order.getQuantity());
			System.out.println("Partition: " + record.partition());
		}
		kafkaConsumer.close();
	}
}
