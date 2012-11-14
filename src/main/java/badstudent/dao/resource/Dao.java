package badstudent.dao.resource;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.*;

import badstudent.message.*;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * the sample main database access class
 * 
 * @author @Matthew
 */
public class Dao{
	private Jedis jedis = new Jedis("localhost");
	
	//private static Log log = LogFactory.getLog(Dao.class);
	//private static final String TABLE_PREFIX = Dao.class.getName() + "_";

	/*returns a message by its identifier*/
	public Message getMessageById(String id){
		String jsonMessage = jedis.get(id);     //get a serialized message from Redis
		
		//if the message is not null(exist), deserialized into Message object and return
		if (jsonMessage != null){
			Message message = new JSONDeserializer<Message>().deserialize(jsonMessage);
			return message;
		}
		return null;
	}

	/*creates a new single message*/
	public Message createMessage(Message message){
		try {
			JSONSerializer serializer = new JSONSerializer();		//initialize JSONSerializer
			String jsonMessage = serializer.serialize(message);		//use JSONSerializer to serialize the message obj into a JSON string and store it in Redis

			String messageId = message.getId();		//Redis use key-value pairs, here the key is the id of each Message, the value if the JSON String representation of the Message object

			jedis.set(messageId, jsonMessage);		//store the key-value pair into Redis

		} catch (Exception e) {
			System.out.println(e.getStackTrace());	//catch JSON exception and print stack trace
		}

		return message;
	}

	/*updates a message*/
	public Message updateMessage(Message message){

		String messageId = message.getId(); 

		//check if message exists
		Message oldMessage = getMessageById(messageId);
		
		//if not null(exist), update it
		if (oldMessage != null){
			JSONSerializer serializer = new JSONSerializer();
			String jsonMessage = serializer.serialize(message);
			//jedis.del(); //this deletes the original, be careful, there should be some sort of update function...too lazy to look it up tonight
			jedis.del(messageId);
			jedis.set(message.getId(), jsonMessage);
			
		}
		else{
			System.out.println("message not found");
		}

		return message;
	}

	/*deletes a single message*/
	public boolean deleteMessage(String messageId) {

		jedis.del(messageId);
		return true;
	}

	/*return all messaes*/
	public List<Message> getMessages() {
		List<Message> messages = new ArrayList<Message>();
		
		//use a Set to store all the keys in Redis
		Set<String> keys = jedis.keys("*");    //O(n), this is actually a bad way but it works just fine
		for (String key : keys) {
			//for each key, extract the Message and add it into the messages list
			String jsonMessage = jedis.get(key);
			Message message = new JSONDeserializer<Message>().deserialize(jsonMessage);
			messages.add(message);
		}
		//System.out.println("Dao::getMessages");
		return messages;
	}
	
	//return all the ids with the Message prefix, may be needed later on with different data models
	public Set<String> getIds(){
		return jedis.keys(Message.prefix + "*");  
	}
}