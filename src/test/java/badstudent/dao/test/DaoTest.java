package badstudent.dao.test;

import static org.junit.Assert.*;

import badstudent.common.Common;
import badstudent.common.Constants;
import badstudent.database.*;
import badstudent.model.*;

import java.util.*;

import redis.clients.jedis.*;

import org.junit.Test;

public class DaoTest {
	
	Jedis jedis = new Jedis("localhost");
	
	@Test
	public void initDaoTest(){
		Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
	    Message msgUW = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
	    DaoMessage.addMessageToDatabase(msgUW);
	    Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
	    Message msgUL = new Message("Simon","lol","2012 12 22","2012 12 22",30,locationUL,0,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","twit","SimonJiang", 19.99, 0);
	    DaoMessage.addMessageToDatabase(msgUL);
	    Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
	    Message msgUT = new Message("Simon","lol","2012 12 23","2012 12 23",30,locationUT,2,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","twit","SimonJiang", 19.99, -1);
	    DaoMessage.addMessageToDatabase(msgUT);

	    assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);
	    
	    Message returnMsgUW = DaoMessage.getMessageById(msgUW.getId());
	    Message returnMsgUT = DaoMessage.getMessageById(msgUT.getId());
	    Message returnMsgUL = DaoMessage.getMessageById(msgUL.getId());
	    Common.d("daoTest::initDaoTest() -> initial msgUW location " + msgUW.getLocation());
	    Common.d("daoTest::initDaoTest() -> retuerned msgUW location " + returnMsgUW.getLocation());
	    assertTrue(returnMsgUW.getLocation().toString().compareTo(msgUW.getLocation().toString()) == 0);
	    assertTrue(returnMsgUT.getLocation().toString().compareTo(msgUT.getLocation().toString()) == 0);
	    assertTrue(returnMsgUL.getLocation().toString().compareTo(msgUL.getLocation().toString()) == 0);
	    
	    DaoMessage.deleteMessageFromDatabase(msgUT.getId());
	    DaoMessage.deleteMessageFromDatabase(msgUW.getId());
	    DaoMessage.deleteMessageFromDatabase(msgUL.getId());
	    assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);
	    
	    List<Message> msgs = DaoMessage.getAllMessagesInDatabase();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
		
		jedis.del(Constants.key_idGenerator);
		jedis.del(Constants.key_recents);
		assertNull(jedis.get(Constants.key_idGenerator));
	    assertTrue(DaoBasic.getWholeDatabase().size() == 0);
	}
	
	@Test
	public void createMessageTest() {
		Message msg = new Message("Matthew");
		
		DaoMessage.addMessageToDatabase(msg);
		String msgId = msg.getId();
		assertNotNull(DaoMessage.getMessageById(msgId));
		
		DaoMessage.deleteMessageFromDatabase(msgId);
		assertNull(DaoMessage.getMessageById(msgId));
		
		assertNull(jedis.get(msgId));
		
		List<Message> msgs = DaoMessage.getAllMessagesInDatabase();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void getMessageByIdentifierTest() {
		Message message = new Message("Michael");
		DaoMessage.addMessageToDatabase(message);
		
		Message returned = DaoMessage.getMessageById(message.getId());
		
		assertNotNull(returned);
		assertTrue(returned.getId().compareTo(message.getId()) == 0);
		
		jedis.del(message.getId());
		Message newReturned = DaoMessage.getMessageById(message.getId());
		
		assertNull(newReturned);
		List<Message> messages = DaoMessage.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);			//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void updateMessageTest() {
		
		//here "test" is the original roomNumber
		Message message = new Message("MichaelMatthew");
		DaoMessage.addMessageToDatabase(message);

		message.setUserName("jUnit");
		DaoMessage.updateMessageInDatabase(message);
		
		Message returned = DaoMessage.getMessageById(message.getId());
		
		assertTrue(returned.getUserName().compareTo("jUnit") == 0);  //check if updated
		
		jedis.del(message.getId());
		assertNull(DaoMessage.getMessageById(message.getId()));   //check if new message has been deleted
		
		List<Message> messages = DaoMessage.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);
	}
	
	@Test
	public void deleteMessageTest() {
		Message message = new Message("admin");
		
		DaoMessage.addMessageToDatabase(message);
		
		String messageIdentifier = message.getId();
		
		assertNotNull(jedis.get(messageIdentifier));
		
		Message returned = DaoMessage.getMessageById(message.getId());
		assertTrue(returned.getId().compareTo(message.getId()) ==0);	//make sure the message is in the database
		
		DaoMessage.deleteMessageFromDatabase(message.getId());
		assertNull(DaoMessage.getMessageById(messageIdentifier));		//make sure the message now is being deleted
		
		List<Message> messages = DaoMessage.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	@Test
	public void getMessagesTest() {
		Message messageAlpha = new Message("SunnyMichael");
		Message messageBeta = new Message("SunnyMatthew");
		Message messageSigma = new Message("SunnyEmma");
		
		DaoMessage.addMessageToDatabase(messageAlpha);
		DaoMessage.addMessageToDatabase(messageBeta);
		DaoMessage.addMessageToDatabase(messageSigma);
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagealpha" + jedis.get(messageAlpha.getId()) );
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagebeta" + jedis.get(messageBeta.getId()) );
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagesigma" + jedis.get(messageSigma.getId()) );
		
		
		List<Message> messages = DaoMessage.getAllMessagesInDatabase();
		Common.d( "daoTest::getMessagesTest -> retuerned messages size" + messages.size() );
		assertTrue(messages.size() == 3);
		
		DaoMessage.deleteMessageFromDatabase(messageAlpha.getId());      //redis duplicate keys problem
		DaoMessage.deleteMessageFromDatabase(messageBeta.getId());
		DaoMessage.deleteMessageFromDatabase(messageSigma.getId());
		
		messages = DaoMessage.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);
		jedis.flushDB();
	}
	
}
