package com.example.fash.trackergps.serializer;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.example.fash.trackergps.model.VehicleGPSData;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TruckCoordinatesDeserializer implements Deserializer<VehicleGPSData> {

	@Override
	public VehicleGPSData deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		VehicleGPSData retorno = null;
		try {
			retorno = mapper.readValue(data, VehicleGPSData.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
