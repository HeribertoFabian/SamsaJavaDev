package com.example.fash.trackergps.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.example.fash.trackergps.VehicleGPSData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TruckCoordinatesSerializer implements Serializer<VehicleGPSData> {

	@Override
	public byte[] serialize(String topic, VehicleGPSData data) {
		byte[] retorno = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			retorno = mapper.writeValueAsString(data).getBytes();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
