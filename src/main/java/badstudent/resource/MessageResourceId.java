package badstudent.resource;

import java.io.IOException;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.data.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import badstudent.Common.NLog;
import badstudent.dao.service.*;
import badstudent.model.*;

public class MessageResourceId extends ServerResource{

	DaoService daoService = new DaoService();

	//this parseJSON parses received json into messages
	//it assumes that an id is present
	private Message parseJSON(Representation entity){
		JSONObject jsonMessage = null;
		try {
			jsonMessage = (new JsonRepresentation(entity)).getJsonObject();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		NLog.d("@PutWithID::receive jsonMessage: " +  jsonMessage.toString());

		Message message = null;
		try {
			message = new Message(jsonMessage.getString("id"), jsonMessage.getString("userName"), jsonMessage.getString("password"),jsonMessage.getString("dateString"),new location(jsonMessage.getString("location")),jsonMessage.getBoolean("isMale"),jsonMessage.getString("title"),jsonMessage.getString("content"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message;
	}

	@Get 
	public Representation getCurrentMessagesById() {
		String id = (String)this.getRequestAttributes().get("id");

		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray();

			/**
			 * jsonArray = new JSONArray(daoService.getMessageById(id));
			 */

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try{
			for (int i = 0; i < jsonArray.length(); i++){
				jsonArray.getJSONObject(i).remove("messageIdentifier");
			}
		}
		catch (JSONException e){
			e.printStackTrace();
		}


		/*set the response header*/
		Form responseHeaders = MessageResource.addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 

		Representation result = new JsonRepresentation(jsonArray);
		try {
            NLog.d(result.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
	}


	@Put 
	public Representation updateMessage(Representation entity) {
		String id = (String)this.getRequestAttributes().get("id");

		Message message = parseJSON(entity);
		Representation result = null;
		//if available, update the message
		
		/**
		*daoService.updateMessage(message, id);
		*/
		
		setStatus(Status.SUCCESS_OK);
		JSONObject newJsonMessage = new JSONObject(message);
		NLog.d("@Put::resources::updateMessage: newJsonMessage" + newJsonMessage.toString());
		result = new JsonRepresentation(newJsonMessage);


		/*set the response header*/
		Form responseHeaders = MessageResource.addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		}  

		return result;
	}

	@Delete
	public Representation deleteMessage() {
		String id = (String)this.getRequestAttributes().get("id");
		
		boolean exist = true;
		/**
		*boolean exist = daoService.deleteMessage(id);
		*/
		
		if (!exist){
			setStatus(Status.CLIENT_ERROR_CONFLICT );
		}
		else{
			setStatus(Status.SUCCESS_OK);
		}
		NLog.d("@Delete with id: " + id);

		/*set the response header*/
		Form responseHeaders = MessageResource.addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 

		return null;
	}



	//needed here since backbone will try to send OPTIONS to /id before PUT or DELETE
	@Options
	public Representation takeOptions(Representation entity) {
		/*set the response header*/
		Form responseHeaders = MessageResource.addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 

		//send anything back will be fine, browser just expects a response
		Message message = new Message("options");
		Representation result = new JsonRepresentation(message);
		return result;
	}

}
