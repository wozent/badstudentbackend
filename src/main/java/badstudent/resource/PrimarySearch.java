package badstudent.resource;

import java.io.IOException;
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
		String phone = getQuery().getValues("phone");
		String email = getQuery().getValues("email");
		String qq = getQuery().getValues("qq");
		String selfDefined = getQuery().getValues("selfDefined");
		String queryType = getQuery().getValues("type");
		try{
			int type = Integer.parseInt(queryType);
		}
		catch (NumberFormatException e){
			Common.d("MessageResource::@GET  NumberFormatException with queryType: " + queryType);
			e.printStackTrace();
		}
		
		List<Message> merge = daoService.multipeSearch(phone, email, qq, selfDefined);	
		
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
			System.out.println(result.getText() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("@Get::resources:searchMessages: query parameters: phone " + phone + " email " + email + " qq " + qq + " selfDefined" + selfDefined);
		
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		return result;
	}


	
}

