package com.payment;

import java.text.DecimalFormat;

public class VenueDetail {
	
	public String name;
	public String city;
	public String state;
	public String zip;
	public double distance;
	public String id;
	public String address;
	
	
	public  String getId() {
		return id;
	}
	public  void setId(String id) {
		this.id = id;
	}
	public  String getCity() {
		return city;
	}
	public  void setCity(String city) {
		this.city = city;
	}
	public  String getState() {
		return state;
	}
	public  void setState(String state) {
		this.state = state;
	}
	public  String getZip() {
		return zip;
	}
	public  void setZip(String zip) {
		this.zip = zip;
	}
	public  double getDistance() {
		return distance;
	}
	public  void setDistance(double distance) {
		this.distance = distance;
	}
	
	public  String getName() {
		return name;
	}
	public  void setName(String name) {
		this.name = name;
	}
	public  String getAddress() {
		return address;
	}
	public  void setAddress(String address) {
		this.address = address;
	}

	public  String toStringValue() {
		return "Address: " + getAddress() + " , " + getCity() +  " , " + getState()  + "  " +  getZip() + 
		"\nDistance: " + getDistance() + " miles" +
		"\nId: " + getId();
	}
	public  double roundTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
	return Double.valueOf(twoDForm.format(d));
}

}
