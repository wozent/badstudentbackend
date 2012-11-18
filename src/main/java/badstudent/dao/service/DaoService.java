package badstudent.dao.service;

import java.util.List;
import java.util.Set;

import flexjson.JSONDeserializer;

import badstudent.model.*;

import badstudent.dao.resource.*;

public class DaoService{
	
	//private static Log log = LogFactory.getLog(ScheduleResource.class);
	private Dao dao;
	
	public DaoService(){
		this.dao = new Dao();
	}
	
	/*checks if the id exist in Redis*/
	public boolean checkExistance(String id){
		if (dao.getMessageById(id) == null){
			return false;
		}
		return true;
	}
	
	
	public Message getMessageById(String id){
		return dao.getMessageById(id);
	}
	
	/*create non-existing messages, return null if the message was already in the database(same Id), return the message entity if the message was not in existence and now created*/
	public Message createMessage(Message message){
		if (checkExistance(message.getId()) ){
			System.out.println("@createMessage::***warning***id does exist, doing nothing");
			return null;
		}
		else{
			System.out.println("@createSchedule::id does not exist, creating message with id: " + message.getId());
			dao.createMessage(message);
			return dao.createMessage(message);
		}
	}
	
	/*update message*/
	public Message updateMessage(Message message, String id){
		if (checkExistance(id) ){
			System.out.println("@updateMessage::id exist, updating message with id: " + id);
			dao.updateMessage(message);
			return message;
		}
		else{
			System.out.println("@updateSchedule::***warning***id does not exist, creating new message with id: " + id);
			dao.createMessage(message);
			return null;
		}
		
	}
	
	/*delete existing messages, return true if the message existed and now deleted, return false if the message was not found*/
	public boolean deleteMessage(String id){
		if (checkExistance(id) ){
			System.out.println("@deleteMessage::id exist, deleting message with id: " + id);
			dao.deleteMessage(id);
			return true;
		}
		else{
			System.out.println("@deleteMessage::***warning***id does not exist, doing nothing");
			return false;
		}
	}
	
	
	public List<Message> getMessages(){
		return dao.getMessages();
	}
	
	public Set<String> getIds(){
		return dao.getIds();
	}
	
	public List<Message> GeneralGontactInfoSearch(String contactInfoPiece){
		Set<String> keys = dao.getIds();
		List<Message> matchedMessages;
		for (String key : keys) {
			if (key.indexOf(key.substring(0, 3), 7) != -1 || key.indexOf(key.substring(key.length()-3)) != -1){
				Message testMessage = dao.getMessageById(key);
				
			}
		}
		
		return null;
	}
	
	
}
