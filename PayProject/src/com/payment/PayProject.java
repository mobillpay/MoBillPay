package com.payment;

import java.util.List;

import com.google.android.maps.MapActivity;

import android.content.Intent;
import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class PayProject extends MapActivity {
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	Geocoder myLocation;;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    myLocation = new Geocoder(this);
	    Button button = (Button)findViewById(R.id.buttonGO);
	    
	    // Register the onClick listener with the implementation above
	    button.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		    // Perform action on click
		    EditText address = (EditText) findViewById(R.id.txtAddress);
		    EditText searchFor = (EditText) findViewById(R.id.txtSearchFor);
		    ProjectVariables.setAddress(address.getText().toString());
		    ProjectVariables.setSearchFor(searchFor.getText().toString());

		    //Get latitude and longitude
		    try
		    {
				 List<Address> addressList =  myLocation.getFromLocationName(ProjectVariables.getAddress(), 5);
				 if (addressList == null || addressList.size() == 0 )
				 {
		    		 throw new Exception();		
				 }
				 Address addressObj = (Address) addressList.get(0);
				 double latitude = addressObj.getLatitude();
				 double longitude = addressObj.getLongitude();
				 ProjectVariables.setLatitude(latitude);
				 ProjectVariables.setLongitude(longitude);
		    
			    //Start new Intent for listing the venues
		    	Intent myIntent = new Intent();
		    	myIntent.setClassName("com.payment", "com.payment.GetVenues");
		    	myIntent.putExtra("com.payment.PayProject", "Hello, World!"); // key/value pair, where key needs current package prefix.
		    	startActivity(myIntent);   
		    }
		    catch(Exception ex)
		    {
		    	ex.printStackTrace();
		        Context context = getApplicationContext();

		    	CharSequence text = "Cannot find venues. Please check your \n " +
        		"network connection or try another venue.";
	            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
	            toast.show();
		    }
	} 
	});
	}
	
	
	
}

        