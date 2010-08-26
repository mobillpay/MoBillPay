package com.payment;

public class ProjectVariables {
	public static String clickedString;
	public static String address;
	public static String searchFor;
	public static double clickedLatitude;
	public static double clickedLongitude;
	public static double latitude;
	public static double longitude;
	public static String clickedAddress;
	public static String clickedID;
	public static boolean clickedAcceptPaypal;
	
	public static boolean isClickedAcceptPaypal() {
		return clickedAcceptPaypal;
	}
	public static void setClickedAcceptPaypal(boolean clickedAcceptPaypal) {
		ProjectVariables.clickedAcceptPaypal = clickedAcceptPaypal;
	}
	public static String getClickedID() {
		return clickedID;
	}
	public static void setClickedID(String clickedID) {
		ProjectVariables.clickedID = clickedID;
	}
	public static String getClickedAddress() {
		return clickedAddress;
	}
	public static void setClickedAddress(String clickedAddress) {
		ProjectVariables.clickedAddress = clickedAddress;
	}
	public static double getClickedLatitude() {
		return clickedLatitude;
	}
	public static void setClickedLatitude(double clickedLatitude) {
		ProjectVariables.clickedLatitude = clickedLatitude;
	}
	public static double getClickedLongitude() {
		return clickedLongitude;
	}
	public static void setClickedLongitude(double clickedLongitude) {
		ProjectVariables.clickedLongitude = clickedLongitude;
	}
	public static String getClickedString() {
		return clickedString;
	}
	public static void setClickedString(String clickedString) {
		ProjectVariables.clickedString = clickedString;
	}
	
	public static String getAddress() {
		return address;
	}
	
	public static void setAddress(String address) {
		ProjectVariables.address = address;
	}
	
	public static String getSearchFor() {
		return searchFor;
	}
	
	public static void setSearchFor(String searchFor) {
		ProjectVariables.searchFor = searchFor;
	}
	public static double getLatitude() {
		return latitude;
	}
	public static void setLatitude(double latitude) {
		ProjectVariables.latitude = latitude;
	}
	public static double getLongitude() {
		return longitude;
	}
	public static void setLongitude(double longitude) {
		ProjectVariables.longitude = longitude;
	}

}
