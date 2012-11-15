package badstudent.resource;

import java.io.IOException;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.data.Form;
import org.restlet.data.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import badstudent.message.*;
import badstudent.dao.service.*;

public class MessageResource extends ServerResource{

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

		System.out.println("@Post::receive jsonMessage: " +  jsonMessage.toString());

		Message message = null;
		try {
			message = new Message(jsonMessage.getString("userName"), jsonMessage.getString("password"),jsonMessage.getString("dateString"),jsonMessage.getString("location"),jsonMessage.getInt("gender"),jsonMessage.getString("title"),jsonMessage.getString("content") );
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message;
	}

	@Get
	public Representation getCurrentMessages() {
		//get query parameter _startTime
		String startTimeString = getQuery().getValues("startTime");
		
		
		JSONArray jsonArray = new JSONArray();
		
		/** change this once the daoService is fully implemented
		 * JSONArray jsonArray = new JSONArray(daoService.getCurrentMessages());
		 */
		
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
		
		System.out.println("@Get::resources:getCurrentMessages");
		
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
		/**change this once the daoService is fully implemented
		*daoService.createMessage(message);
		*/
		setStatus(Status.SUCCESS_OK);

		System.out.println("@Post::resources::createMessage: available: " + message.getUserName() + message.getPassword());

		JSONObject newJsonMessage = new JSONObject(message);
		System.out.println("@Post::resources::createMessage: newJsonMessage" + newJsonMessage.toString());
		result = new JsonRepresentation(newJsonMessage);


		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		return result;
	}

	
	
	//needed here since backbone will try to send OPTIONS before PPOST
	@Options
	public Representation takeOptions(Representation entity) {
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 

		//send anything back will be fine, browser only expects a response
		Message message = new Message("options");
		Representation result = new JsonRepresentation(message);

		return result;
	}


}
