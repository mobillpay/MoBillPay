package com.payment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Intent;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ShowMap extends MapActivity{
	
	private MapView myMap;
	String clickedname;
	GeoPoint p;
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	Geocoder myLocation;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.showmap);

	    Button button = (Button)findViewById(R.id.btnHome);
	    
	    // Register the onClick listener with the implementation above
	    button.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
	    	Intent myIntent = new Intent();
	    	myIntent.setClassName("com.payment", "com.payment.PayProject");
	    	myIntent.putExtra("com.payment.ShowMap", "Hello, World!"); // key/value pair, where key needs current package prefix.
	    	startActivity(myIntent);    

	    }
	    });
	    
	    Button buttonBack = (Button)findViewById(R.id.btnBack);
	    
	    // Register the onClick listener with the implementation above
	    buttonBack.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
	    	Intent myIntent = new Intent();
	    	myIntent.setClassName("com.payment", "com.payment.GetVenues");
	    	myIntent.putExtra("com.payment.ShowMap", "Hello, World!"); // key/value pair, where key needs current package prefix.
	    	startActivity(myIntent);    

	    }
	    });
	    
	    Button buttonPay = (Button)findViewById(R.id.buttonPay);
	    
	    // Register the onClick listener with the implementation above
	    buttonPay.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
	    	try
	    	{
	    		Intent myIntent = new Intent(Intent.ACTION_VIEW, 
	    				Uri.parse("http://10.0.2.2:8080/APClient/APClientServlet"));
		    	startActivity(myIntent);  
	    		//startActivityForResult
			 /*URL url = new URL("http://10.0.2.2:8080/APClient/APClientServlet");
			 HttpURLConnection connection = null;
			 InputStream is = null;

			 connection = (HttpURLConnection) url.openConnection();
			 connection.setRequestMethod("POST");
			 connection.connect();
			 is = connection.getInputStream();
			 
			 StringBuilder sb = new StringBuilder();
			 String line;
			 if (is != null) {

				 try {
					 BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					 while ((line = reader.readLine()) != null) {
						 	sb.append(line).append("\n");
				         }
				 } finally {
					 is.close();
				 }
			 }
			 String myString = sb.toString();
			 Context context = getApplicationContext();
	         Toast toast = Toast.makeText(context, myString, Toast.LENGTH_LONG);
	         toast.show();
	         ((Button)findViewById(R.id.buttonPay)).setEnabled(false);*/
	    	}
	    	catch (Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	    }
	    });
	    
	    Button buttonCheckIn = (Button)findViewById(R.id.buttonCheckIn);
	    
	    // Register the onClick listener with the implementation above
	    buttonCheckIn.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
			 Context context = getApplicationContext();
	         Toast toast = Toast.makeText(context, "Checked into " + clickedname, Toast.LENGTH_LONG);
	         toast.show();
	         ((Button)findViewById(R.id.buttonCheckIn)).setEnabled(false);

	    }
	    });

	    myLocation = new Geocoder(this);

	    TextView tv = (TextView) findViewById(R.id.AddressCheckIn);
	    tv.setText( ProjectVariables.getClickedString());
	    if (!(ProjectVariables.clickedString).contains("Paypal"))
	    {
	        Button myButton = (Button) findViewById(R.id.buttonPay);
	        myButton.setVisibility(4);
	    }
	    parseClickedString();
	    try
	    {
			 List<Address> addressList =  myLocation.getFromLocationName(ProjectVariables.getClickedAddress(), 5);
			 if (addressList == null || addressList.size() == 0 )
			 {
	    		 throw new Exception();		
			 }
			 Address addressObj = (Address) addressList.get(0);
			 double latitude = addressObj.getLatitude();
			 double longitude = addressObj.getLongitude();
	
			myMap = (MapView) findViewById(R.id.simpleGM_map); // Get map from XML
			navigateToLocation((latitude * 1000000), (longitude * 1000000),
					myMap); // display the found address
	    }
	    catch( Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	}
	
	
	/**
	 * Navigates a given MapView to the specified Longitude and Latitude
	 * 
	 * @param latitude
	 * @param longitude
	 * @param mv
	 */
	public  void navigateToLocation(double latitude, double longitude,
			MapView mv) {
	    p = new GeoPoint((int) latitude, (int) longitude); // new
		// GeoPoint
		mv.setBuiltInZoomControls(true);

		MapController mc = mv.getController();
		mc.setZoom(16); // zoom
		mc.setCenter(p);
		
		mv.setSatellite(false); // display only "normal" mapview
		
        //---Add a location marker---
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mv.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay); 

		
	}
	
	private void parseClickedString()
	{
		String tokenString;
		String parsedTokenString;
		StringTokenizer st = new StringTokenizer(ProjectVariables.clickedString, "\n");
		clickedname = st.nextToken();
		 st = new StringTokenizer(ProjectVariables.clickedString, "\n");
		StringTokenizer parsedst;

		while (st.hasMoreTokens()) {
			  tokenString = st.nextToken();
			  parsedst = new StringTokenizer(tokenString, ":");
			  while (parsedst.hasMoreTokens())
			  {
				  parsedTokenString = parsedst.nextToken();
				  if (("Address").equals(parsedTokenString.trim()) )
				  {
					  ProjectVariables.setClickedAddress(parsedst.nextToken());
				  }

				  if ( ("ID").equals(parsedTokenString.trim()))
				  {
					  ProjectVariables.setClickedID(parsedst.nextToken());
			  
				  }


			  }
			}

	}
	
	 
    class MapOverlay extends com.google.android.maps.Overlay
    {
        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pushpin2);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
            return true;
        }
    } 

}
