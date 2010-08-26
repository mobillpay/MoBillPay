package com.payment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.ListActivity;
import android.content.Intent;
import android.content.Context;

import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class GetVenues extends ListActivity{
	
	ArrayList<String> venueStringArray = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.getvenues);

	  this.getNearbyVenues();
;

	  setListAdapter(new ArrayAdapter<String>(this,
	          R.layout.getvenuestextview, venueStringArray));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {

	    	ProjectVariables.clickedString = ((TextView) view).getText().toString();
	    	Intent myIntent = new Intent();
	    	myIntent.setClassName("com.payment", "com.payment.ShowMap");
	    	myIntent.putExtra("com.payment.GetVenues", "Hello, World!"); 
	    	startActivity(myIntent);    

	    }
	  });
	  
	    Button button = (Button)findViewById(R.id.btnHome);
	    
	    // Register the onClick listener with the implementation above
	    button.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
	    	Intent myIntent = new Intent();
	    	myIntent.setClassName("com.payment", "com.payment.PayProject");
	    	myIntent.putExtra("com.payment.GetVenues", "Hello, World!"); // key/value pair, where key needs current package prefix.
	    	startActivity(myIntent);    

	    }
	    });

	}


	public void getNearbyVenues()
	{
		 try
		 {
			 ArrayList<VenueDetail> venueDetailArray = new ArrayList<VenueDetail>();
			 URL url = new URL("http://api.foursquare.com/v1/venues?geolat="+ ProjectVariables.getLatitude() + "&geolong=" + ProjectVariables.getLongitude() + "&q=" + ProjectVariables.getSearchFor());
			 HttpURLConnection connection = null;
			 InputStream is = null;

			 connection = (HttpURLConnection) url.openConnection();
			 connection.setRequestMethod("GET");
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
			 
			 
			 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			 DocumentBuilder db = dbf.newDocumentBuilder();
			 InputSource inStream = new InputSource();
			 inStream.setCharacterStream(new StringReader(myString));
			 Document doc = db.parse(inStream);
			 
            Element root = doc.getDocumentElement();
            NodeList items = root.getElementsByTagName("venue");
            for (int i=0;i<items.getLength();i++){
                Node item = items.item(i);
                NodeList properties = item.getChildNodes();
                VenueDetail venueDetail = new VenueDetail();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    String value = property.getFirstChild().getNodeValue();
                    if (name.equalsIgnoreCase("name")){
                    	venueDetail.setName(value);
                    } else if (name.equalsIgnoreCase("address")){
                    	venueDetail.setAddress(value);
                    } else if (name.equalsIgnoreCase("city")){
                    	venueDetail.setCity(value);
                    } else if (name.equalsIgnoreCase("zip")){
                    	venueDetail.setZip(value);
                    } else if (name.equalsIgnoreCase("state")){
                    	venueDetail.setState(value);
                    } else if (name.equalsIgnoreCase("distance")){
                    	venueDetail.setDistance(venueDetail.roundTwoDecimals(Integer.parseInt(value)*0.000621371192));
                    } 
                    else if (name.equalsIgnoreCase("id")){
                    	venueDetail.setId(value);
                    } 
	                    
                }
                venueDetailArray.add(venueDetail);
            }
            Collections.sort(venueDetailArray, new Comparator<VenueDetail>()
            {
            	 
                public int compare(VenueDetail v1, VenueDetail v2) {
                    int i = 0;
                    if ((v1.getDistance()) == (v2.getDistance()) )
                    {
                    	i = 0;
                    }
                    else if ((v1.getDistance()) > (v2.getDistance()) )
                    		{
                    	i = 1;
                    		}
                    else { 
                    	i = -1;
                    }
                  
                   return i;
                }
     
            });

        	for (int i = 0; i < venueDetailArray.size();i++)
        	{
                String venueStringLine2;
                VenueDetail venueDetail = (VenueDetail) venueDetailArray.get(i);
                String venueStringLine1 = venueDetail.getName();
                if ( i % 3 == 0 )
                {
                	venueStringLine2 = venueDetail.toStringValue() + "\n" + "Accepts Paypal";
                }
                else
                {
                	venueStringLine2 = venueDetail.toStringValue();
                }

                venueStringArray.add(venueStringLine1 + " \n" + venueStringLine2);

             
			 }
		 }
			 catch(Exception exception)
			 {
				 exception.printStackTrace();
				 Context context = getApplicationContext();
			    	CharSequence text = "Cannot find venues. Please check your \n " +
	        		"network connection or try another venue.";
		            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		            toast.show();
			 }

	}
	
}
