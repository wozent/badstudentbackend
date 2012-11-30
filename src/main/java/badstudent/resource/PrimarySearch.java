package badstudent.resource;

import java.io.IOException;
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

	private DaoService daoService = new DaoService();

	/*set the response header to allow for CORS*/
	public static Form addHeader(Form responseHeaders){
		if (responseHeaders == null) { 
			responseHeaders = new Form(); 
			responseHeaders.add("Access-Control-Allow-Origin", "*");
			responseHeaders.add("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
			responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
			return responseHeaders;
		}
		return null;
	}
	
	
	@Get
	public Representation searchMessages() {
		//get query parameter _phone _email _qq _selfDefined
		String locationString = getQuery().getValues("location");
		String dateString = getQuery().getValues("date");

		Location location = new Location(locationString);
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyy MM dd").parse(dateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
			System.out.println("PrimarySearch:: @Get date string parse error: dateString: " + dateString);
		};
		
		//main search part, first calls searchByLocation to get location-based messages, then test if each message is on target day
		List<Message> searchResult = daoService.searchByLocation(location, null, null);
		searchResult = daoService.searchByDate(date, searchResult, null);
		
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
			System.out.println(result.getText() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("@Get::resources::primarySearch query parameters: location: " + locationString + " date " + dateString);
		
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		return result;
	}


	
}

