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
	public Message getMessageById(String id){
		String jsonMessage = jedis.get(id);
		Message message = null;
		if (jsonMessage != null){
			message = new JSONDeserializer<Message>().deserialize(jsonMessage);
		}
        return message;
	}
	
	public List<Message> getRecents(){
		//return the recentMessage list
		List<String> messageStrings = jedis.lrange(Constants.key_recents, 0, Constants.max_recents);
		List<Message> messages = new ArrayList<Message>();
		for (int i = 0; i < messageStrings.size(); i++){
			messages.add(new JSONDeserializer<Message>().deserialize(messageStrings.get(i)));
		}
		return messages;
	}
	
	public boolean addRecents(String jsonMessage){
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

	public Message addMessageToDatabase(Message message){
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

}