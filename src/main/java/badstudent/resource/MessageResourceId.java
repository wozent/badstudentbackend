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
                    jsonMessage.getLong("date"),new Location(jsonMessage.getString("location")),
                    jsonMessage.getBoolean("isMale"),jsonMessage.getString("content"),
                    jsonMessage.getString("email"),jsonMessage.getString("phone"),
                    jsonMessage.getString("qq"),jsonMessage.getString("selfDefined"),
                    jsonMessage.getDouble("price"),jsonMessage.getInt("type"));
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
        Common.d(id);
        Common.d(daoService.getMessageById(id).toString());
        JSONObject jsonObject = null;
        try {
        	Common.d(daoService.getMessageById(id).toString());
            jsonObject = new JSONObject(daoService.getMessageById(id));

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        jsonObject.remove("messageIdentifier");


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


    @Put 
    public Representation updateMessage(Representation entity) {
        String id = (String)this.getRequestAttributes().get("id");

        Message message = parseJSON(entity);
        Representation result = null;
        //if available, update the message

        
        daoService.updateMessage(message, id);
         

        setStatus(Status.SUCCESS_OK);
        JSONObject newJsonMessage = new JSONObject(message);
        Common.d("@Put::resources::updateMessage: newJsonMessage" + newJsonMessage.toString());
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

        boolean exist = daoService.deleteMessage(id);
        

        if (exist){
            setStatus(Status.CLIENT_ERROR_CONFLICT );
        }
        else{
            setStatus(Status.SUCCESS_OK);
        }
        Common.d("@Delete with id: " + id);

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
