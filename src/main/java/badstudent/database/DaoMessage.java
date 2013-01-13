package badstudent.database;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.*;

import badstudent.common.Common;
import badstudent.common.Constants;
import badstudent.model.*;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class DaoMessage{
	private static Jedis jedis = new Jedis("localhost");
	
	/*returns a message by its identifier*/
	public static Message getMessageById(String id){
		String jsonMessage = jedis.get(id);
		Message message = null;
		if (jsonMessage != null){
			message = new JSONDeserializer<Message>().deserialize(jsonMessage);
		}
        return message;
	}
	
	public static List<Message> getRecents(){
		//return the recentMessage list
		List<String> messageStrings = jedis.lrange(Constants.key_recents, 0, Constants.max_recents);
		List<Message> messages = new ArrayList<Message>();
		for (int i = 0; i < messageStrings.size(); i++){
			messages.add(new JSONDeserializer<Message>().deserialize(messageStrings.get(i)));
		}
		return messages;
	}
	
	public static List<String> getInfoChanged(){
		//return the list of id of contact-info-updated messages
		long listSize = jedis.llen(Constants.key_infoChanged);
		List<String> infoChangedIds = jedis.lrange(Constants.key_infoChanged, 0, listSize - 1);
		return infoChangedIds;
	}
	
	public static boolean addInfoChanged(String id){
		List<String> ids = getInfoChanged();
		boolean found = false;
		int i = 0;
		while (i < ids.size() && found == false){
			if (ids.get(i).compareTo(id) == 0){
				ids.remove(i);
				found = true;
			}
			i++;
		}
		if (!found){
			jedis.lpush(Constants.key_infoChanged, id);
		}
		
		return true;
	}
	
	public static long deleteInfoChanged(String id){
		List<String> ids = getInfoChanged();
		boolean found = false;
		int i = 0;
		while (i < ids.size() && found == false){
			if (ids.get(i).compareTo(id) == 0){
				ids.remove(i);
				found = true;
			}
			i++;
		}
		long result =  jedis.lrem(Constants.key_infoChanged, 0, "");//remove all elements
		for (i = 0; i < jedis.llen(Constants.key_infoChanged); i++){
			jedis.lpop(Constants.key_infoChanged);
		}
		for (i = 0; i < ids.size(); i++){
			jedis.lpush(Constants.key_infoChanged, ids.get(i));
		}
		
		return result;
	}
	
	public static boolean addRecents(String jsonMessage){
		try {
			//push this message to the top of the recentMessage list
			jedis.lpush(Constants.key_recents, jsonMessage);
			//if the recentMessage reaches its maximum length, pop off its last element
			if (jedis.llen(Constants.key_recents) > Constants.max_recents){
				jedis.rpop(Constants.key_recents);
			}
			Common.d("Dao::addRecents, add message : " + jsonMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/*double check why lrm did not work, current way too inefficient*/
	public static long updateRecents(String id, String newJsonMessage){
		List<Message> messages = getRecents();
		boolean found = false;
		int i = 0;
		while (i < messages.size() && found == false){
			if (messages.get(i).getId().compareTo(id) == 0){
				messages.remove(i);
				found = true;
			}
			i++;
		}
		long result =  jedis.lrem(Constants.key_recents, 0, "");//remove all elements
		for (i = 0; i < Constants.max_recents; i++){
			jedis.lpop(Constants.key_recents);
		}
		for (i = 0; i < messages.size(); i++){
			String jsonMessage = new JSONSerializer().serialize(messages.get(i));
			jedis.lpush(Constants.key_recents, jsonMessage);
		}
		jedis.lpush(Constants.key_recents, newJsonMessage);
		return result;
	}
	
	/*double check why lrm did not work, current way too inefficient*/
	public static long deleteRecents(String id){
		List<Message> messages = getRecents();
		boolean found = false;
		int i = 0;
		while (i < messages.size() && found == false){
			if (messages.get(i).getId().compareTo(id) == 0){
				messages.remove(i);
				found = true;
			}
			i++;
		}
		long result =  jedis.lrem(Constants.key_recents, 0, "");//remove all elements
		for (i = 0; i < Constants.max_recents; i++){
			jedis.lpop(Constants.key_recents);
		}
		for (i = 0; i < messages.size(); i++){
			String jsonMessage = new JSONSerializer().serialize(messages.get(i));
			jedis.lpush(Constants.key_recents, jsonMessage);
		}
		return result;
		/*Common.d("deleteRecents::trying to delete recents message with id: " + id);
		String jsonMessage = jedis.get(id);
		return jedis.lrem(Constants.key_recents, 1, jsonMessage);*/
	}

	public static Message addMessageToDatabase(Message message){
		try {
			String jsonMessage = new JSONSerializer().serialize(message);
			String messageId = message.getId();
			jedis.set(messageId, jsonMessage);
			addRecents(jsonMessage);
			Common.d("Dao::createMessage, creating message with message id: " + messageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	public static Message updateMessageInDatabase(Message message){
		String messageId = message.getId(); 
		Message oldMessage = getMessageById(messageId);
		if (oldMessage != null){
			String jsonMessage = new JSONSerializer().serialize(message);
			updateRecents(messageId, jsonMessage);
			if (Common.infoHasChanged(message, oldMessage)){
				addInfoChanged(messageId);
			}
			jedis.set(message.getId(), jsonMessage);
		}
		else{
			Common.d("message not found");
		}

		return message;
	}

	public static boolean deleteMessageFromDatabase(String messageId) {
		long result = deleteRecents(messageId);
		long secondResult = deleteInfoChanged(messageId);
		Common.d("deleteMessage::delete result: " + result + " " + secondResult);
	    return 1==jedis.del(messageId);
	}

	public static List<Message> getAllMessagesInDatabase() {
		List<Message> messages = new ArrayList<Message>();
		Set<String> keys = jedis.keys(Constants.key_message_prefix+"*");
		for (String key : keys) {
			String jsonMessage = jedis.get(key);
			Message message = new JSONDeserializer<Message>().deserialize(jsonMessage);
			messages.add(message);
		}
		return messages;
	}
	
	//return all the ids with the Message prefix, may be needed later on with different data models
	public static Set<String> getAllMessageIdInDatabase(){
		return jedis.keys(Constants.key_message_prefix + "*");  
	}
	
	
	public static Set<String> getPartialIds(String targetPattern){
		return jedis.keys(Constants.key_message_prefix + "*" + targetPattern + "*");
	}

}