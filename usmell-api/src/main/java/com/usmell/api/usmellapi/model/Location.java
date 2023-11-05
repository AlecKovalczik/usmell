package com.usmell.api.usmellapi.model;

import java.util.HashMap;
import com.google.maps.GeolocationApi;
import com.google.maps.GeolocationApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.GeolocationPayload;
import com.google.maps.model.GeolocationResult;
import com.google.maps.model.GeolocationPayload.GeolocationPayloadBuilder;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApiRequest;
public class Location {
    private int locationID;
    private String name; 
    private HashMap<Smell, Integer> currentSmells; 
    private double [] coordinates; 
    private GeoApiContext context = new GeoApiContext.Builder(new GaeRequestHandler.Builder())
    .apiKey("AIzaSyBB9XG4ZzujuwkH54S4AJyN-QBHRRNEfOM")
    .build();
    public Location(String name){
        // generate the coordinates using google maps API 
        
        this.name = name; 
        currentSmells = new HashMap<Smell, Integer> (); 
        // create payload 
        //GeolocationPayload payload = new GeolocationPayload(); 
        GeolocationPayloadBuilder balls = new GeolocationPayloadBuilder(); 
        GeolocationPayload payload = balls.createGeolocationPayload(); 
        PendingResult<GeolocationResult> pendingRes = GeolocationApi.geolocate(context, payload )
        // get coordinates from getting the location 
    }

    public Location(){
        currentSmells = new HashMap<Smell, Integer>(); 
    }

}
