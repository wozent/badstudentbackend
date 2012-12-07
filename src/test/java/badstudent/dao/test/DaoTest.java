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
	
	DaoMessage dao = new DaoMessage();
	Jedis jedis = new Jedis("localhost");
	
	@Test
	public void initDaoTest(){
		Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
	    Message msgUW = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
	    dao.addMessageToDatabase(msgUW);
	    Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
	    Message msgUL = new Message("Simon","lol","2012 12 22","2012 12 22",30,locationUL,0,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","twit","SimonJiang", 19.99, 0);
	    dao.addMessageToDatabase(msgUL);
	    Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
	    Message msgUT = new Message("Simon","lol","2012 12 23","2012 12 23",30,locationUT,2,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","twit","SimonJiang", 19.99, -1);
	    dao.addMessageToDatabase(msgUT);

	    assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);
	    
	    Message returnMsgUW = dao.getMessageById(msgUW.getId());
	    Message returnMsgUT = dao.getMessageById(msgUT.getId());
	    Message returnMsgUL = dao.getMessageById(msgUL.getId());
	    System.out.println("daoTest::initDaoTest() -> initial msgUW location " + msgUW.getLocation());
	    System.out.println("daoTest::initDaoTest() -> retuerned msgUW location " + returnMsgUW.getLocation());
	    assertTrue(returnMsgUW.getLocation().toString().compareTo(msgUW.getLocation().toString()) == 0);
	    assertTrue(returnMsgUT.getLocation().toString().compareTo(msgUT.getLocation().toString()) == 0);
	    assertTrue(returnMsgUL.getLocation().toString().compareTo(msgUL.getLocation().toString()) == 0);
	    
	    dao.deleteMessageFromDatabase(msgUT.getId());
	    dao.deleteMessageFromDatabase(msgUW.getId());
	    dao.deleteMessageFromDatabase(msgUL.getId());
	    assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);
	    
	    List<Message> msgs = dao.getAllMessagesInDatabase();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
		
		jedis.del(Constants.key_idGenerator);
		jedis.del(Constants.key_recents);
		assertNull(jedis.get(Constants.key_idGenerator));
	    assertTrue(dao.getEverything().size() == 0);
	}
	
	@Test
	public void createMessageTest() {
		Message msg = new Message("Matthew");
		
		dao.addMessageToDatabase(msg);
		String msgId = msg.getId();
		assertNotNull(dao.getMessageById(msgId));
		
		dao.deleteMessageFromDatabase(msgId);
		assertNull(dao.getMessageById(msgId));
		
		assertNull(jedis.get(msgId));
		
		List<Message> msgs = dao.getAllMessagesInDatabase();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void getMessageByIdentifierTest() {
		Message message = new Message("Michael");
		dao.addMessageToDatabase(message);
		
		Message returned = dao.getMessageById(message.getId());
		
		assertNotNull(returned);
		assertTrue(returned.getId().compareTo(message.getId()) == 0);
		
		jedis.del(message.getId());
		Message newReturned = dao.getMessageById(message.getId());
		
		assertNull(newReturned);
		List<Message> messages = dao.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);			//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void updateMessageTest() {
		
		//here "test" is the original roomNumber
		Message message = new Message("MichaelMatthew");
		dao.addMessageToDatabase(message);

		message.setUserName("jUnit");
		dao.updateMessageInDatabase(message);
		
		Message returned = dao.getMessageById(message.getId());
		
		assertTrue(returned.getUserName().compareTo("jUnit") == 0);  //check if updated
		
		jedis.del(message.getId());
		assertNull(dao.getMessageById(message.getId()));   //check if new message has been deleted
		
		List<Message> messages = dao.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);
	}
	
	@Test
	public void deleteMessageTest() {
		Message message = new Message("admin");
		
		dao.addMessageToDatabase(message);
		
		String messageIdentifier = message.getId();
		
		assertNotNull(jedis.get(messageIdentifier));
		
		Message returned = dao.getMessageById(message.getId());
		assertTrue(returned.getId().compareTo(message.getId()) ==0);	//make sure the message is in the database
		
		dao.deleteMessageFromDatabase(message.getId());
		assertNull(dao.getMessageById(messageIdentifier));		//make sure the message now is being deleted
		
		List<Message> messages = dao.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	@Test
	public void getMessagesTest() {
		Message messageAlpha = new Message("SunnyMichael");
		Message messageBeta = new Message("SunnyMatthew");
		Message messageSigma = new Message("SunnyEmma");
		
		dao.addMessageToDatabase(messageAlpha);
		dao.addMessageToDatabase(messageBeta);
		dao.addMessageToDatabase(messageSigma);
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagealpha" + jedis.get(messageAlpha.getId()) );
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagebeta" + jedis.get(messageBeta.getId()) );
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagesigma" + jedis.get(messageSigma.getId()) );
		
		
		List<Message> messages = dao.getAllMessagesInDatabase();
		Common.d( "daoTest::getMessagesTest -> retuerned messages size" + messages.size() );
		assertTrue(messages.size() == 3);
		
		dao.deleteMessageFromDatabase(messageAlpha.getId());      //redis duplicate keys problem
		dao.deleteMessageFromDatabase(messageBeta.getId());
		dao.deleteMessageFromDatabase(messageSigma.getId());
		
		messages = dao.getAllMessagesInDatabase();
		assertTrue(messages.size() == 0);
		jedis.flushDB();
	}
	
}
