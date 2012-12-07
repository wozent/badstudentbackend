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
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUW = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
        daoService.createMessage(msgUW);
        Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUL = new Message("Simon","lol","2012 12 22","2012 12 22",30,locationUL,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 0);
        daoService.createMessage(msgUL);
        Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUT = new Message("Simon","lol","2012 12 23","2012 12 23",30,locationUT,2,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, -1);
        daoService.createMessage(msgUT);

        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);

        Message returnMsgUW = daoService.getMessageById(msgUW.getId());
        Message returnMsgUT = daoService.getMessageById(msgUT.getId());
        Message returnMsgUL = daoService.getMessageById(msgUL.getId());
        Common.d("daoServiceTest::initDaoTest() -> initial msgUW location " + msgUW.getLocation());
        Common.d("daoServiceTest::initDaoTest() -> retuerned msgUW location " + returnMsgUW.getLocation());
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
        jedis.del(Constants.key_recents);
        assertTrue(daoService.getEverything().size() == 0);
    }

    @Test
    public void checkExistenceTest(){
        Message msg = new Message("IAmInHere");
        daoService.createMessage(msg);
        assertFalse(daoService.checkExistance(msg.getId()));

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
        assertNull(daoService.getMessageById(msgId));

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

        assertNull(returned);
        //assertTrue(returned.getId().compareTo(message.getId()) == 0);

        daoService.deleteMessage(message.getId());
        Message newReturned = daoService.getMessageById(message.getId());

        assertNull(newReturned);
        List<Message> messages = daoService.getAllMessages();
        assertTrue(messages.size() == 0);			//make sure the database is totally cleared, no memory has occurred in the above operations
    }


    @Test
    public void updateMessageTest() {

        //here "test" is the original roomNumber
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message message = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
        daoService.createMessage(message);

        message.setUserName("jUnit");
        daoService.updateMessage(message, message.getId());

        Message returned = daoService.getMessageById(message.getId());

        assertTrue(returned.getUserName().compareTo("jUnit") == 0);  //check if updated

        daoService.deleteMessage(message.getId());
        assertNull(daoService.getMessageById(message.getId()));   //check if new message has been deleted

        List<Message> messages = daoService.getAllMessages();
        assertTrue(messages.size() == 0);
    }

    @Test
    public void deleteMessageTest() {
        Message message = new Message("admin");

        daoService.createMessage(message);

        String messageIdentifier = message.getId();

        assertNull(jedis.get(messageIdentifier));

        Message returned = daoService.getMessageById(message.getId());
        //assertTrue(returned.getId().compareTo(message.getId()) ==0);	//make sure the message is in the database

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
        assertTrue(messages.size() == 0);

        daoService.deleteMessage(messageAlpha.getId());      //redis duplicate keys problem
        daoService.deleteMessage(messageBeta.getId());
        daoService.deleteMessage(messageSigma.getId());

        messages = daoService.getAllMessages();
        assertTrue(messages.size() == 0);
    }

    @Test
    public void SearchMessageTest(){
        jedis.del(Constants.key_idGenerator);
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUW = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","test","SimonJiang", 19.99, 1);
        daoService.createMessage(msgUW);
        Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUL = new Message("Simon","lol","2012 12 22","2012 12 22",30,locationUL,0,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456788","twit","test", 19.99, 0);
        daoService.createMessage(msgUL);
        Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUT = new Message("Simon","lol","2012 12 23","2012 12 23",30,locationUT,2,"looking for girlfriend","mic@uwaterloo.ca",
                "516xxxxxx","123456789","twit","SimonJian", 19.99, -1);
        daoService.createMessage(msgUT);

        Message msgLucas = new Message("Lucas","lol","2012 12 23","2012 12 23",30,locationUW,1,"looking for girlfriend","qqb@me.com",
                "226xxxxxx","987654321","twitter","LucasWang", 19.99, -1);
        daoService.createMessage(msgLucas);
        
        assertTrue(daoService.generalGontactInfoSearch("test").size() == 2);
        assertTrue(daoService.generalGontactInfoSearch("SimonJiang").size() == 1);
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
        
        assertTrue(daoService.twitterInfoSearch("test").size() == 1);
        assertTrue(daoService.twitterInfoSearch("twitter").size() == 1);
        assertTrue(daoService.twitterInfoSearch("twit").size() == 2);
        
        assertTrue(daoService.selfDefinedInfoSearch("SimonJiang").size() == 1);
        assertTrue(daoService.selfDefinedInfoSearch("test").size() == 1);
        assertTrue(daoService.selfDefinedInfoSearch("SimonJian").size() == 1);
        assertTrue(daoService.selfDefinedInfoSearch("BullShit").size() == 0);
        
        //2*519xxxxxx, 1* mic@uwaterloo,ca, 1*twitter
        assertTrue(daoService.multipeSearch("519xxxxxx", "mic@uwaterloo.ca", "", "twitter", "Bla").size() == 4);
        
        daoService.deleteMessage(msgUT.getId());
        daoService.deleteMessage(msgUW.getId());
        daoService.deleteMessage(msgUL.getId());
        daoService.deleteMessage(msgLucas.getId());
        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 4);

        List<Message> msgs = daoService.getAllMessages();
        assertTrue(msgs.size() == 0);               //make sure the database is totally cleared, no memory has occurred in the above operations

        jedis.del(Constants.key_idGenerator);
        assertNull(jedis.get(Constants.key_idGenerator));
        jedis.del(Constants.key_recents);
        assertTrue(daoService.getEverything().size() == 0);
    }
    
    @Test
    public void sortMessageTest(){
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUW = new Message("Simon","lol","6353 26 73","6353 26 73",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
        daoService.createMessage(msgUW);
        Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUL = new Message("Simon","lol","2353 26 73","2353 26 73",30,locationUL,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456788","twit","SimonJiang", 19.99, 0);
        daoService.createMessage(msgUL);
        Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUT = new Message("Simon","lol","3353 26 73","3353 26 73",30,locationUT,1,"looking for girlfriend","mic@uwaterloo.ca",
                "516xxxxxx","123456789","twit","SimonJian", 19.99, -1);
        daoService.createMessage(msgUT);
        daoService.sortMessageByEndDate(daoService.getAllMessages());
        daoService.clearDatabase();
        
    }
    
}
