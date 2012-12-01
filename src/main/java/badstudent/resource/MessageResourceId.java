package badstudent.resource;

import java.io.IOException;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.data.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import badstudent.common.Common;
import badstudent.dbservice.*;
import badstudent.model.*;
import badstudent.mappings.*;

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

        Common.d("@PutWithID::receive jsonMessage: " +  jsonMessage.toString());

        Message message = null;
        try {
            message = new Message(jsonMessage.getString("id"),jsonMessage.getString("userName"), jsonMessage.getString("password"),
                    jsonMessage.getString("date"),new Location(jsonMessage.getString("location")),
                    jsonMessage.getBoolean("isMale"),jsonMessage.getString("content"),
                    jsonMessage.getString("email"),jsonMessage.getString("phone"),
                    jsonMessage.getString("qq"),jsonMessage.getString("twitter"),jsonMessage.getString("selfDefined"),
                    jsonMessage.getDouble("price"),jsonMessage.getInt("type"), jsonMessage.getInt("authCode"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }
    
    //checking both password and authCode
    //authCode must not equal to initial authCode -1
    public boolean authMatch(Message receivedMessage, Message dbMessage){
    	boolean authCodeMatch = (receivedMessage.getAuthCode() == dbMessage.getAuthCode()) && (receivedMessage.getAuthCode() != -1);
    	boolean passwordMatch = (receivedMessage.getPassword().compareTo(dbMessage.getPassword()) == 0);
    	return  (authCodeMatch == passwordMatch);
    }

    @Get 
    public Representation getCurrentMessagesById() {
        String id = (String)this.getRequestAttributes().get("id");
        Common.d(id);
        JSONObject jsonObject = null;
        try {
        	Common.d(daoService.getMessageById(id).toString());
            jsonObject = new JSONObject(daoService.getMessageById(id));
            jsonObject.remove("messageIdentifier");

        } catch (Exception e1) {
            e1.printStackTrace();
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

    //if authentication passed, local model should have the correct password field, thus checking both password and authCode here, please note under other situations password on the front end would be goofypassword
    //authCode must not equal to initial authCode -1
    @Put 
    public Representation updateMessage(Representation entity) {
        String id = (String)this.getRequestAttributes().get("id");
        Representation result = null;
        Message message = parseJSON(entity);
        
        if (message != null && authMatch(message, daoService.getMessageById(id))){
            //auth-POST session ends, restore authCode to -1
        	message.restoreAuthCode();
        	//if available, update the message, before the password is changed to the goofy password
            daoService.updateMessage(message, id);
            
            //goofy password sent to front end instead of real password
            message.setPassword(Message.goofyPasswordTrickHackers);
            JSONObject newJsonMessage = new JSONObject(message);
            Common.d("@Put::resources::updateMessage: newJsonMessage" + newJsonMessage.toString());
            result = new JsonRepresentation(newJsonMessage);
            setStatus(Status.SUCCESS_OK);
        }
        else{
        	setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
        

        /*set the response header*/
        Form responseHeaders = MessageResource.addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
        if (responseHeaders != null){
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
        }  

        return result;
    }
    
    //now front end sending delete must expose authCode as a parameter, must not equal to initial authCode -1
    @Delete
    public Representation deleteMessage() {
    	//get query parameter _authCode
    	String authCodeString = getQuery().getValues("authCode");
        String id = (String)this.getRequestAttributes().get("id");
        
        try{
        	 int authCode = Integer.parseInt(authCodeString);
        	 Message message = daoService.getMessageById(id);
        	 //check for authCode
             if (message.getAuthCode() == authCode && authCode != -1){
            	 boolean exist = daoService.deleteMessage(id);
                 
                 if (!exist){
                     setStatus(Status.CLIENT_ERROR_CONFLICT );
                 }
                 else{
                     setStatus(Status.SUCCESS_OK);
                 }
                 Common.d("@Delete with id: " + id);
             }
             else{
            	 setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
             }
             
        }
        catch(NumberFormatException e){
        	e.printStackTrace();
        	setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch(NullPointerException e){
        	e.printStackTrace();
        	setStatus(Status.CLIENT_ERROR_CONFLICT );
        }
        

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
