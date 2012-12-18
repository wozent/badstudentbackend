package badstudent.resource;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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


public class AuthResource extends ServerResource{

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
	
	public static int authCodeGenerator(){
		int min = 0;
		int max = 100000000;
		//generating random numbers from 0 to 100,000,000
		int ranNum = min + (int)(Math.random() * ((max - min) + 1));
		return ranNum;
	}
	
	
	@Get
	public Representation authentication() {
		//get query parameter _password
		String password = getQuery().getValues("password");
		String id = getQuery().getValues("id");
		JSONObject jsonObject = null;
		
		Message message = DaoService.getMessageById(id);
		
		if (message != null){
			try {
	        	Common.d(message.toString());
	        	
	        	if (message.getPassword().compareTo(password) == 0){
	        		//set an authentication code, also update data model in DB
	        		message.setAuthCode(authCodeGenerator());
	        		
	        		DaoService.updateMessage(message, id);
	        		jsonObject = new JSONObject(message);
	            	jsonObject.remove("messageIdentifier");
	        	}
	        	else{
	        		Common.d("AuthResource:: authentication: received password: " + password + " expected password: " + message.toString());
	        		setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
	        	}

	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
		}
		

        /*set the response header*/
        Form responseHeaders = MessageResource.addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
        if (responseHeaders != null){
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
        } 

        Representation result = new JsonRepresentation(jsonObject);
        try {
            Common.d(result.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
	}


	
}

