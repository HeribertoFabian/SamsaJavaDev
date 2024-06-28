package com.fash.example.kafkav.secondexample.customserializers.partitioner;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fash.example.kafkav.secondexample.customserializers.Order;

public class OrderProducer {

	/*	Se requiere la creacion de un topic de preferencia con 10 particiones para ver la asignacion de la particion
	 *  kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 10 --topic OrderPartitionedTopic
	 *  kafka-topics --describe --bootstrap-server localhost:9092 --topic OrderPartitionedTopic
	 */

	public static void main(String[] args) {

		Properties props = new Properties();
		// Mandatory properties
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.fash.example.kafkav.secondexample.customserializers.OrderSerializer");
		props.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, VIPPartitioner.class.getName());
		
		props.setProperty(ProducerConfig.ACKS_CONFIG, "1");		
		props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, "343434334");
		props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
		props.setProperty(ProducerConfig.RETRIES_CONFIG, "2");
		props.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "400");
		props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "10243434343");
		props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "500");
		props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "200");
		

		KafkaProducer<String, Order> kafkaProducer = new KafkaProducer<String, Order>(props);
		Order order = new Order();
//		order.setCustomer("Heriberto Fabian");
		order.setCustomer("Heriberto5");
		order.setProduct("Iphone");
		order.setQuantity(100);
		ProducerRecord<String, Order> record = new ProducerRecord<String,Order>("OrderPartitionedTopic", order.getCustomer(), order );
		
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
