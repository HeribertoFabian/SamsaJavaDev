package com.fash.example.kafkav.secondexample.customserializers;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderDeserializer implements Deserializer<Order> {

	@Override
	public Order deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		Order order = null;
		try {
			order = mapper.readValue(data, Order.class);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return order;
	}

}
