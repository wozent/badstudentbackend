package badstudent.dao.test;

import static org.junit.Assert.*;

import badstudent.Common.Common;
import badstudent.Common.Constants;
import badstudent.dao.resource.*;
import badstudent.model.*;

import java.util.*;

import redis.clients.jedis.*;

import org.junit.Test;

public class DaoTest {
	
	Dao dao = new Dao();
	Jedis jedis = new Jedis("localhost");
	
	@Test
	public void initDaoTest(){
		Location locationUW = new Location("Ontario", "Waterloo", "UniversityofWaterloo");
	    Message msgUW = new Message("Simon","lol",20121221,locationUW,true,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","SimonJiang", 19.99, 1);
	    dao.createMessage(msgUW);
	    Location locationUL = new Location("Ontario", "Waterloo", "UniversityofLarier");
	    Message msgUL = new Message("Simon","lol",20121222,locationUL,true,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","SimonJiang", 19.99, 0);
	    dao.createMessage(msgUL);
	    Location locationUT = new Location("Ontario", "Waterloo", "UniversityofLarier");
	    Message msgUT = new Message("Simon","lol",20121223,locationUT,true,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","SimonJiang", 19.99, -1);
	    dao.createMessage(msgUT);

	    assertTrue(Integer.parseInt(jedis.get(Constants.idGenerator)) == 3);
	    
	    Message returnMsgUW = dao.getMessageById(msgUW.getId());
	    Message returnMsgUT = dao.getMessageById(msgUT.getId());
	    Message returnMsgUL = dao.getMessageById(msgUL.getId());
	    System.out.println("daoTest::initDaoTest() -> initial msgUW location " + msgUW.getLocation());
	    System.out.println("daoTest::initDaoTest() -> retuerned msgUW location " + returnMsgUW.getLocation());
	    assertTrue(returnMsgUW.getLocation().compareTo(msgUW.getLocation()) == 0);
	    assertTrue(returnMsgUT.getLocation().compareTo(msgUT.getLocation()) == 0);
	    assertTrue(returnMsgUL.getLocation().compareTo(msgUL.getLocation()) == 0);
	    
	    dao.deleteMessage(msgUT.getId());
	    dao.deleteMessage(msgUW.getId());
	    dao.deleteMessage(msgUL.getId());
	    assertTrue(Integer.parseInt(jedis.get(Constants.idGenerator)) == 3);
	    
	    List<Message> msgs = dao.getAllMessages();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
		
		jedis.del(Constants.idGenerator);
		assertNull(jedis.get(Constants.idGenerator));
	    assertTrue(dao.getEverything().size() == 0);
	}
	
	@Test
	public void createMessageTest() {
		Message msg = new Message("Matthew");
		
		dao.createMessage(msg);
		String msgId = msg.getId();
		assertNotNull(dao.getMessageById(msgId));
		
		dao.deleteMessage(msgId);
		assertNull(dao.getMessageById(msgId));
		
		assertNull(jedis.get(msgId));
		
		List<Message> msgs = dao.getAllMessages();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void getMessageByIdentifierTest() {
		Message message = new Message("Michael");
		dao.createMessage(message);
		
		Message returned = dao.getMessageById(message.getId());
		
		assertNotNull(returned);
		assertTrue(returned.getId().compareTo(message.getId()) == 0);
		
		jedis.del(message.getId());
		Message newReturned = dao.getMessageById(message.getId());
		
		assertNull(newReturned);
		List<Message> messages = dao.getAllMessages();
		assertTrue(messages.size() == 0);			//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void updateMessageTest() {
		
		//here "test" is the original roomNumber
		Message message = new Message("MichaelMatthew");
		dao.createMessage(message);

		message.setUserName("jUnit");
		dao.updateMessage(message);
		
		Message returned = dao.getMessageById(message.getId());
		
		assertTrue(returned.getUserName().compareTo("jUnit") == 0);  //check if updated
		
		jedis.del(message.getId());
		assertNull(dao.getMessageById(message.getId()));   //check if new message has been deleted
		
		List<Message> messages = dao.getAllMessages();
		assertTrue(messages.size() == 0);
	}
	
	@Test
	public void deleteMessageTest() {
		Message message = new Message("admin");
		
		dao.createMessage(message);
		
		String messageIdentifier = message.getId();
		
		assertNotNull(jedis.get(messageIdentifier));
		
		Message returned = dao.getMessageById(message.getId());
		assertTrue(returned.getId().compareTo(message.getId()) ==0);	//make sure the message is in the database
		
		dao.deleteMessage(message.getId());
		assertNull(dao.getMessageById(messageIdentifier));		//make sure the message now is being deleted
		
		List<Message> messages = dao.getAllMessages();
		assertTrue(messages.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	@Test
	public void getMessagesTest() {
		Message messageAlpha = new Message("SunnyMichael");
		Message messageBeta = new Message("SunnyMatthew");
		Message messageSigma = new Message("SunnyEmma");
		
		dao.createMessage(messageAlpha);
		dao.createMessage(messageBeta);
		dao.createMessage(messageSigma);
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagealpha" + jedis.get(messageAlpha.getId()) );
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagebeta" + jedis.get(messageBeta.getId()) );
		Common.d( "daoTest::getCurrentMessagesTest -> retuerned messagesigma" + jedis.get(messageSigma.getId()) );
		
		
		List<Message> messages = dao.getAllMessages();
		Common.d( "daoTest::getMessagesTest -> retuerned messages size" + messages.size() );
		assertTrue(messages.size() == 3);
		
		dao.deleteMessage(messageAlpha.getId());      //redis duplicate keys problem
		dao.deleteMessage(messageBeta.getId());
		dao.deleteMessage(messageSigma.getId());
		
		messages = dao.getAllMessages();
		assertTrue(messages.size() == 0);
	}
	
}
