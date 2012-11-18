package badstudent.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import badstudent.Common.Common;
import badstudent.Common.Constants;
import badstudent.dao.service.*;
import badstudent.model.Location;
import badstudent.model.Message;

public class DaoServiceTest {

	DaoService daoService = new DaoService();
	Jedis jedis = new Jedis("localhost");
	
	@Test
	public void initDaoTest(){
		Location locationUW = new Location("Ontario", "Waterloo", "UniversityofWaterloo");
	    Message msgUW = new Message("Simon","lol",20121221,locationUW,true,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","SimonJiang", 19.99, 1);
	    daoService.createMessage(msgUW);
	    Location locationUL = new Location("Ontario", "Waterloo", "UniversityofLarier");
	    Message msgUL = new Message("Simon","lol",201212212,locationUL,true,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","SimonJiang", 19.99, 0);
	    daoService.createMessage(msgUL);
	    Location locationUT = new Location("Ontario", "Waterloo", "UniversityofLarier");
	    Message msgUT = new Message("Simon","lol",20121223,locationUT,true,"looking for girlfriend","simon@uwaterloo.ca",
	            "519xxxxxx","123456789","SimonJiang", 19.99, -1);
	    daoService.createMessage(msgUT);

	    assertTrue(Integer.parseInt(jedis.get(Constants.idGenerator)) == 3);
	    
	    Message returnMsgUW = daoService.getMessageById(msgUW.getId());
	    Message returnMsgUT = daoService.getMessageById(msgUT.getId());
	    Message returnMsgUL = daoService.getMessageById(msgUL.getId());
	    System.out.println("daoServiceTest::initDaoTest() -> initial msgUW location " + msgUW.getLocation());
	    System.out.println("daoServiceTest::initDaoTest() -> retuerned msgUW location " + returnMsgUW.getLocation());
	    assertTrue(returnMsgUW.getLocation().compareTo(msgUW.getLocation()) == 0);
	    assertTrue(returnMsgUT.getLocation().compareTo(msgUT.getLocation()) == 0);
	    assertTrue(returnMsgUL.getLocation().compareTo(msgUL.getLocation()) == 0);
	    
	    daoService.deleteMessage(msgUT.getId());
	    daoService.deleteMessage(msgUW.getId());
	    daoService.deleteMessage(msgUL.getId());
	    assertTrue(Integer.parseInt(jedis.get(Constants.idGenerator)) == 3);
	    
	    List<Message> msgs = daoService.getAllMessages();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
		
		jedis.del(Constants.idGenerator);
		assertNull(jedis.get(Constants.idGenerator));
	    assertTrue(daoService.getEverything().size() == 0);
	}
	
	@Test
	public void checkExistenceTest(){
		Message msg = new Message("IAmInHere");
		daoService.createMessage(msg);
		assertTrue(daoService.checkExistance(msg.getId()));
		
		daoService.deleteMessage(msg.getId());
		assertFalse(daoService.checkExistance(msg.getId()));
		
		List<Message> msgs = daoService.getAllMessages();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	@Test
	public void createMessageTest() {
		Message msg = new Message("Matthew");
		
		daoService.createMessage(msg);
		String msgId = msg.getId();
		assertNotNull(daoService.getMessageById(msgId));
		
		daoService.deleteMessage(msgId);
		assertNull(daoService.getMessageById(msgId));
		
		assertNull(jedis.get(msgId));
		
		List<Message> msgs = daoService.getAllMessages();
		assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void getMessageByIdentifierTest() {
		Message message = new Message("Michael");
		daoService.createMessage(message);
		
		Message returned = daoService.getMessageById(message.getId());
		
		assertNotNull(returned);
		assertTrue(returned.getId().compareTo(message.getId()) == 0);
		
		jedis.del(message.getId());
		Message newReturned = daoService.getMessageById(message.getId());
		
		assertNull(newReturned);
		List<Message> messages = daoService.getAllMessages();
		assertTrue(messages.size() == 0);			//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	
	@Test
	public void updateMessageTest() {
		
		//here "test" is the original roomNumber
		Message message = new Message("MichaelMatthew");
		daoService.createMessage(message);

		message.setUserName("jUnit");
		daoService.updateMessage(message, message.getId());
		
		Message returned = daoService.getMessageById(message.getId());
		
		assertTrue(returned.getUserName().compareTo("jUnit") == 0);  //check if updated
		
		jedis.del(message.getId());
		assertNull(daoService.getMessageById(message.getId()));   //check if new message has been deleted
		
		List<Message> messages = daoService.getAllMessages();
		assertTrue(messages.size() == 0);
	}
	
	@Test
	public void deleteMessageTest() {
		Message message = new Message("admin");
		
		daoService.createMessage(message);
		
		String messageIdentifier = message.getId();
		
		assertNotNull(jedis.get(messageIdentifier));
		
		Message returned = daoService.getMessageById(message.getId());
		assertTrue(returned.getId().compareTo(message.getId()) ==0);	//make sure the message is in the database
		
		daoService.deleteMessage(message.getId());
		assertNull(daoService.getMessageById(messageIdentifier));		//make sure the message now is being deleted
		
		List<Message> messages = daoService.getAllMessages();
		assertTrue(messages.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
	}
	
	@Test
	public void getMessagesTest() {
		Message messageAlpha = new Message("SunnyMichael");
		Message messageBeta = new Message("SunnyMatthew");
		Message messageSigma = new Message("SunnyEmma");
		
		daoService.createMessage(messageAlpha);
		daoService.createMessage(messageBeta);
		daoService.createMessage(messageSigma);
		Common.d( "daoServiceTest::getCurrentMessagesTest -> retuerned messagealpha" + jedis.get(messageAlpha.getId()) );
		Common.d( "daoServiceTest::getCurrentMessagesTest -> retuerned messagebeta" + jedis.get(messageBeta.getId()) );
		Common.d( "daoServiceTest::getCurrentMessagesTest -> retuerned messagesigma" + jedis.get(messageSigma.getId()) );
		
		
		List<Message> messages = daoService.getAllMessages();
		Common.d( "daoServiceTest::getMessagesTest -> retuerned messages size" + messages.size() );
		assertTrue(messages.size() == 3);
		
		daoService.deleteMessage(messageAlpha.getId());      //redis duplicate keys problem
		daoService.deleteMessage(messageBeta.getId());
		daoService.deleteMessage(messageSigma.getId());
		
		messages = daoService.getAllMessages();
		assertTrue(messages.size() == 0);
	}
}
