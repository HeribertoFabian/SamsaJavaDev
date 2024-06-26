package com.fash.example.kafkav.secondexample;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderCallBack implements Callback {

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {

		if (exception != null) {
			exception.printStackTrace();
		} else {
			System.out.println(metadata.partition());
			System.out.println(metadata.timestamp());
			System.out.println("Message Sent successfully");
		}

	}

}
