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
	
	public static Long generateId(){
	    return jedis.incr(Constants.key_idGenerator);
	}
	
	/*returns a message by its identifier*/
	public Message getMessageById(String id){
		String jsonMessage = jedis.get(id);
		Message message = null;
		if (jsonMessage != null){
			message = new JSONDeserializer<Message>().deserialize(jsonMessage);
		}
        return message;
	}

	public Message addMessageToDatabase(Message message){
		try {
			String jsonMessage = new JSONSerializer().serialize(message);
			String messageId = message.getId();
			jedis.set(messageId, jsonMessage);
			Common.d("Dao::createMessage, creating message with message id: " + messageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	public Message updateMessageInDatabase(Message message){
		String messageId = message.getId(); 
		Message oldMessage = getMessageById(messageId);
		if (oldMessage != null){
			String jsonMessage = new JSONSerializer().serialize(message);
			jedis.set(message.getId(), jsonMessage);
		}
		else{
			Common.d("message not found");
		}

		return message;
	}

	public boolean deleteMessageFromDatabase(String messageId) {
	    return 1==jedis.del(messageId);
	}

	public List<Message> getAllMessagesInDatabase() {
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
	public Set<String> getAllMessageIdInDatabase(){
		return jedis.keys(Constants.key_message_prefix + "*");  
	}
	
	
	public Set<String> getPartialIds(String targetPattern){
		return jedis.keys(Constants.key_message_prefix + "*" + targetPattern + "*");
	}
	
	public Set<String> getEverything(){
		return jedis.keys("*");  
	}
	
	public static void clearDatabase(){
	    jedis.flushAll();
	}
}