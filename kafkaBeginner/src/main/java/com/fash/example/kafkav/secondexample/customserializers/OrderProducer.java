package com.fash.example.kafkav.secondexample.customserializers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderProducer {


	public static void main(String[] args) {

		Properties props = new Properties();
		// Mandatory properties
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "com.fash.example.kafkav.secondexample.customserializers.OrderSerializer");

		KafkaProducer<String, Order> kafkaProducer = new KafkaProducer<String, Order>(props);
		Order order = new Order();
		order.setCustomer("Heriberto Fabian");
		order.setProduct("Iphone");
		order.setQuantity(100);
		ProducerRecord<String, Order> record = new ProducerRecord<String,Order>("OrderCSTopic", order.getCustomer(), order );
		
		/*
		 * En el siguiente ejemplo se lanza un mensaje y se olvida por que nunca esperamos una respuesta de vuelta
		 * In the next example we fire and forget the message, because we didn't wait for the response to come back 
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
