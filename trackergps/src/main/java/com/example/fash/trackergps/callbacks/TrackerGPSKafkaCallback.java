package com.example.fash.trackergps.callbacks;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TrackerGPSKafkaCallback implements Callback {

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if(exception != null) {
			exception.printStackTrace();
		}
		else {
			System.out.println("SUCCESSFUL[ partition: " +metadata.partition()+", timestamp:" + metadata.timestamp()+"]");
		}

	}

}
