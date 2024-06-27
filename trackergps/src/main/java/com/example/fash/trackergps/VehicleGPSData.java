package com.example.fash.trackergps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleGPSData {

	@JsonProperty("ID")
	private int ID;
	
	@JsonProperty("Latitude")
	private double latitude;
	
	@JsonProperty("Longitude")
	private double longitude;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "VehicleGPSData [ID=" + ID + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}


	
}
