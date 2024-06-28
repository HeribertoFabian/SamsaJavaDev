package com.fash.example.kafkav.secondexample;

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
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		props.put("retries", 0);

		KafkaProducer<String, Integer> kafkaProducer = new KafkaProducer<String, Integer>(props);
		ProducerRecord<String, Integer> record = new ProducerRecord<String, Integer>("OrderTopic", "Mac Book Pro", 101);
		
		/*
		 * En el siguiente ejemplo se lanza un mensaje y se olvida por que nunca esperamos una respuesta de vuelta
		 * In the next example we fire and forget the message, because we didn't wait for the response to come back 
		 */
//		try {
//			kafkaProducer.send(record);
//			System.out.println("Message Sent successfully");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			kafkaProducer.close();
//		}
		
		/*
		 * En el siguiente ejemplo se hace un envio sincrono el cual lanza una peticion y espera un objeto Future 
		 */
//		try {
//			RecordMetadata recordMetadata = kafkaProducer.send(record).get();
//			System.out.println(recordMetadata.partition());
//			System.out.println(recordMetadata.timestamp());
//			System.out.println("Message Sent successfully");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			kafkaProducer.close();
//		}
//		
		/*
		 * En el siguiente ejemplo se hace un envio asincrono el cual lanza una peticion 
		 */
		try {
			kafkaProducer.send(record, new OrderCallBack());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			kafkaProducer.close();
		}
		
	}
}
