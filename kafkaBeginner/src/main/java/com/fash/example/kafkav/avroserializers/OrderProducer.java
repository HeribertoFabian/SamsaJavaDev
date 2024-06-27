package com.fash.example.kafkav.avroserializers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.fash.example.kafkav.avro.Order;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class OrderProducer {


	public static void main(String[] args) {

		Properties props = new Properties();
		// Mandatory properties
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
		props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
		props.setProperty("schema.registry.url", "http://localhost:8081");

		KafkaProducer<String, Order> kafkaProducer = new KafkaProducer<String, Order>(props);
		Order order = new Order("Heriberto Fabian","Iphone", 100 );

		ProducerRecord<String, Order> record = new ProducerRecord<String,Order>("OrderAvroTopic", order.getCustomerName().toString(), order );
		
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
