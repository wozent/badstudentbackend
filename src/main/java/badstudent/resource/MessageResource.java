package badstudent.resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import badstudent.mappings.*;

public class MessageResource extends ServerResource{

	/*set the response header to allow for CORS*/
	public static Form addHeader(Form responseHeaders){
		if (responseHeaders == null) { 
			responseHeaders = new Form(); 
			responseHeaders.add("Access-Control-Allow-Origin", "*");
			responseHeaders.add("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
			responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
			responseHeaders.add("Access-Control-Allow-Headers", "authCode");
			responseHeaders.add("Access-Control-Allow-Headers", "origin, x-requested-with, content-type, authCode");
			return responseHeaders;
		}
		return null;
	}

	//passes received json into message
	//note that this parseJSON assumes the message does not exist yet, it is different fron the parsejson in MessageIdResources
	private Message parseJSON(Representation entity){
		JSONObject jsonMessage = null;
		try {
			jsonMessage = (new JsonRepresentation(entity)).getJsonObject();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Common.d("@Post::receive jsonMessage: " +  jsonMessage.toString());

		Message message = null;
		try {
			message = new Message(jsonMessage.getString("userName"), jsonMessage.getString("password"),
			        jsonMessage.getString("startDate"),jsonMessage.getString("endDate"),
			        jsonMessage.getInt("courseLengthInMinutes"), new Location(jsonMessage.getString("location")),
			        jsonMessage.getInt("gender"),jsonMessage.getString("content"),
			        jsonMessage.getString("email"),jsonMessage.getString("phone"),
			        jsonMessage.getString("qq"),jsonMessage.getString("twitter"),jsonMessage.getString("selfDefined"),
			        jsonMessage.getDouble("price"),jsonMessage.getInt("type"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		  catch (NullPointerException e){
			  e.printStackTrace();
			  Common.d("likely invalid location string format");
		}

		return message;
	}
	
	/*genearal GET handlder, can be used to testing Server and DB
	@Get
	public Representation getCurrentMessages() {
		
		JSONArray jsonArray = new JSONArray(daoService.getMessages());
		
		
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
		
		Common.d("@Get::resources:getCurrentMessages");
		
		//set the response header
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		return result;
	}
	*/
	
	@Get
	public Representation searchMessages() {
		//get query parameter _phone _email _qq _selfDefined
		String phone = "";
		String email = "";
		String qq = "";
		String twitter = "";
		String selfDefined = "";
		
		try {
			phone = java.net.URLDecoder.decode(getQuery().getValues("phone"),"utf-8");
			email = java.net.URLDecoder.decode(getQuery().getValues("email"),"utf-8");
			qq = java.net.URLDecoder.decode(getQuery().getValues("qq"),"utf-8");
			twitter = java.net.URLDecoder.decode(getQuery().getValues("twitter"),"utf-8");
			selfDefined = java.net.URLDecoder.decode(getQuery().getValues("selfDefined"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Message> merge = DaoService.multipeSearch(phone, email, qq, twitter, selfDefined);	
		
		/*clear the content and password upon returning search results*/
		for (int i = 0; i < merge.size(); i++){
			merge.get(i).setContent("");
			merge.get(i).setPassword(Message.goofyPasswordTrickHackers);
		}
		
		JSONArray jsonArray = new JSONArray(merge);
		
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
		Common.d("@Get::resources:searchMessages: query parameters: phone " + phone + " email " + email + " qq " + qq +  "twitter " + twitter + " selfDefined" + selfDefined);
		
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		return result;
	}

	

	@Post
	public Representation createMessage(Representation entity) {

		Message message = parseJSON(entity);
		Representation result =  null;
		//if available, add the message
		if (message != null){
			DaoService.createMessage(message);
			

			Common.d("@Post::resources::createMessage: available: " + message.getUserName() + message.getPassword());
			//password will not be exposed to the front end
			message.setPassword(Message.goofyPasswordTrickHackers);
			JSONObject newJsonMessage = new JSONObject(message);
			Common.d("@Post::resources::createMessage: newJsonMessage" + newJsonMessage.toString());
			result = new JsonRepresentation(newJsonMessage);

			setStatus(Status.SUCCESS_OK);
		}
		
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		return result;
	}

	
	
	//needed here since backbone will try to send OPTIONS before POST
	@Options
	public Representation takeOptions(Representation entity) {
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 

		//send anything back will be fine, browser only expects a response
		Message message = new Message();
		Representation result = new JsonRepresentation(message);

		return null;
	}


}
