package badstudent.resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.data.Form;
import org.restlet.data.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import badstudent.common.Common;
import badstudent.dbservice.*;
import badstudent.model.*;


public class PrimarySearch extends ServerResource{

	/*set the response header to allow for CORS*/
	public static Form addHeader(Form responseHeaders){
		if (responseHeaders == null) { 
			responseHeaders = new Form(); 
			responseHeaders.add("Access-Control-Allow-Origin", "*");
			responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS");
			responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
			return responseHeaders;
		}
		return null;
	}
	
	
	@Get
	public Representation searchMessages() {
		//get query parameter _location _date
		String locationString = getQuery().getValues("location");
		String dateString = getQuery().getValues("date");
		String typeString = getQuery().getValues("type");
		Common.d("PrimarySearch::receving GET request location: " + locationString);
		
		/*chinese characters in query parameter*/
		try {
			locationString = java.net.URLDecoder.decode(getQuery().getValues("location"),"utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Common.d("PrimarySearch::receving GET request location: " + locationString);
		
		int type = -1;
		Location location = null;
		
		try{
			location = new Location(locationString);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			Common.d("PrimarySearch:: @Get invalid Location String format, received locationString: " + locationString);
		}

		try{
			type = Integer.parseInt(typeString);
		}
		catch (Exception e){
			e.printStackTrace();
			Common.d("PrimarySearch:: @Get invalid type format, received type: " + type);
		}
		
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyy MM dd").parse(dateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
			Common.d("PrimarySearch:: @Get date string parse error: dateString: " + dateString);
		};
		
		//main search part, first calls searchByLocation to get location-based messages, then test if each message matches target type and if it is on target day
		List<Message> searchResult = DaoService.searchByLocation(location, null, null);
		searchResult = DaoService.searchByType(type, searchResult, null);
		searchResult = DaoService.searchByDate(date, searchResult, null);
		
		/*clear the content upon returning search results*/
		for (int i = 0; i < searchResult.size(); i++){
			searchResult.get(i).setContent("");
			searchResult.get(i).setPassword(Message.goofyPasswordTrickHackers);
		}
		
		JSONArray jsonArray = new JSONArray(searchResult);
		
		try{
			for (int i = 0; i < jsonArray.length(); i++){
				jsonArray.getJSONObject(i).remove("messageIdentifier");
			}
		}
		catch (JSONException e){
			e.printStackTrace();
		}
		
		Representation result = new JsonRepresentation(jsonArray);
		//set status
		setStatus(Status.SUCCESS_OK);
		
		try {
			Common.d(result.getText() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		Common.d("@Get::resources::primarySearch query parameters: location: " + locationString + " date " + dateString);
		
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		return result;
	}


	
}

