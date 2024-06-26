package com.example.fash.trackergps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleGPSData {

	@JsonProperty("ID")
	private String ID;
	
	@JsonProperty("Coordinates")
	private String coordinates;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public String toString() {
		return "VehicleGPSData [ID=" + ID + ", coordinates=" + coordinates + "]";
	}
	
	
}
