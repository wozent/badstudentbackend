package badstudent.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import badstudent.common.Common;
import badstudent.common.Constants;
import badstudent.dbservice.*;
import badstudent.model.Location;
import badstudent.model.Message;

public class DaoServiceTest {

    DaoService daoService = new DaoService();
    Jedis jedis = new Jedis("localhost", 6379);
    
    @Test
    public void initDaoTest(){
        Location locationUW = new Location("Ontario", "Waterloo", "UniversityofWaterloo");
        Message msgUW = new Message("Simon","lol","2012 12 21",locationUW,true,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","SimonJiang", 19.99, 1);
        daoService.createMessage(msgUW);
        Location locationUL = new Location("Ontario", "Waterloo", "UniversityofLarier");
        Message msgUL = new Message("Simon","lol","2012 12 22",locationUL,true,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","SimonJiang", 19.99, 0);
        daoService.createMessage(msgUL);
        Location locationUT = new Location("Ontario", "Waterloo", "UniversityofLarier");
        Message msgUT = new Message("Simon","lol","2012 12 23",locationUT,true,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","SimonJiang", 19.99, -1);
        daoService.createMessage(msgUT);

        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);

        Message returnMsgUW = daoService.getMessageById(msgUW.getId());
        Message returnMsgUT = daoService.getMessageById(msgUT.getId());
        Message returnMsgUL = daoService.getMessageById(msgUL.getId());
        System.out.println("daoServiceTest::initDaoTest() -> initial msgUW location " + msgUW.getLocation());
        System.out.println("daoServiceTest::initDaoTest() -> retuerned msgUW location " + returnMsgUW.getLocation());
        assertTrue(returnMsgUW.getLocation().toString().compareTo(msgUW.getLocation().toString()) == 0);
        assertTrue(returnMsgUT.getLocation().toString().compareTo(msgUT.getLocation().toString()) == 0);
        assertTrue(returnMsgUL.getLocation().toString().compareTo(msgUL.getLocation().toString()) == 0);

        daoService.deleteMessage(msgUT.getId());
        daoService.deleteMessage(msgUW.getId());
        daoService.deleteMessage(msgUL.getId());
        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);

        List<Message> msgs = daoService.getAllMessages();
        assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations

        jedis.del(Constants.key_idGenerator);
        assertNull(jedis.get(Constants.key_idGenerator));
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

    @Test
    public void SearchMessageTest(){
        Location locationUW = new Location("Ontario", "Waterloo", "UniversityofWaterloo");
        Message msgUW = new Message("Simon","lol","2012 12 21",locationUW,true,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","SimonJiang", 19.99, 1);
        daoService.createMessage(msgUW);
        Location locationUL = new Location("Ontario", "Waterloo", "UniversityofLarier");
        Message msgUL = new Message("Simon","lol","2012 12 22",locationUL,true,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456788","SimonJiang", 19.99, 0);
        daoService.createMessage(msgUL);
        Location locationUT = new Location("Ontario", "Waterloo", "UniversityofLarier");
        Message msgUT = new Message("Simon","lol","2012 12 23",locationUT,true,"looking for girlfriend","mic@uwaterloo.ca",
                "516xxxxxx","123456789","SimonJian", 19.99, -1);
        daoService.createMessage(msgUT);
        
        assertTrue(daoService.generalGontactInfoSearch("SimonJiang").size() == 2);
        assertTrue(daoService.generalGontactInfoSearch("516xxxxxx").size() == 1);
        assertTrue(daoService.generalGontactInfoSearch("BullShit").size() == 0);
        
        assertTrue(daoService.emailInfoSearch("simon@uwaterloo.ca").size() == 2);
        assertTrue(daoService.emailInfoSearch("mic@uwaterloo.ca").size() == 1);
        assertTrue(daoService.emailInfoSearch("12").size() == 0);
        
        assertTrue(daoService.phoneInfoSearch("519xxxxxx").size() == 2);
        assertTrue(daoService.phoneInfoSearch("516xxxxxx").size() == 1);
        assertTrue(daoService.phoneInfoSearch(null).size() == 0);
        
        assertTrue(daoService.qqInfoSearch("123456789").size() == 2);
        assertTrue(daoService.qqInfoSearch("123456788").size() == 1);
        assertTrue(daoService.qqInfoSearch("BullShit").size() == 0);
        
        assertTrue(daoService.selfDefinedInfoSearch("SimonJiang").size() == 2);
        assertTrue(daoService.selfDefinedInfoSearch("SimonJian").size() == 1);
        assertTrue(daoService.selfDefinedInfoSearch("BullShit").size() == 0);
        
        assertTrue(daoService.multipeSearch("519xxxxxx", "mic@uwaterloo.ca", "", "Bla").size() == 3);
                
        daoService.deleteMessage(msgUT.getId());
        daoService.deleteMessage(msgUW.getId());
        daoService.deleteMessage(msgUL.getId());
        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);

        List<Message> msgs = daoService.getAllMessages();
        assertTrue(msgs.size() == 0);               //make sure the database is totally cleared, no memory has occurred in the above operations

        jedis.del(Constants.key_idGenerator);
        assertNull(jedis.get(Constants.key_idGenerator));
        assertTrue(daoService.getEverything().size() == 0);
    }
    
    @Test
    public void sortMessageTest(){
        Location locationUW = new Location("Ontario", "Waterloo", "UniversityofWaterloo");
        Message msgUW = new Message("Simon","lol","6353 26 73",locationUW,true,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","SimonJiang", 19.99, 1);
        daoService.createMessage(msgUW);
        Location locationUL = new Location("Ontario", "Waterloo", "UniversityofLarier");
        Message msgUL = new Message("Simon","lol","1353 26 73",locationUL,true,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456788","SimonJiang", 19.99, 0);
        daoService.createMessage(msgUL);
        Location locationUT = new Location("Ontario", "Waterloo", "UniversityofLarier");
        Message msgUT = new Message("Simon","lol","3353 26 73",locationUT,true,"looking for girlfriend","mic@uwaterloo.ca",
                "516xxxxxx","123456789","SimonJian", 19.99, -1);
        daoService.createMessage(msgUT);
        daoService.sortMessageByDate(daoService.getAllMessages());
        daoService.clearDatabase();
        
    }
}
