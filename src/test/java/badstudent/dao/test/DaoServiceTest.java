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

    Jedis jedis = new Jedis("localhost", 6379);
    
    @Test
    public void initDaoTest(){
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUW = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
        DaoService.createMessage(msgUW);
        Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUL = new Message("Simon","lol","2012 12 22","2012 12 22",30,locationUL,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 0);
        DaoService.createMessage(msgUL);
        Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUT = new Message("Simon","lol","2012 12 23","2012 12 23",30,locationUT,2,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, -1);
        DaoService.createMessage(msgUT);

        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);

        Message returnMsgUW = DaoService.getMessageById(msgUW.getId());
        Message returnMsgUT = DaoService.getMessageById(msgUT.getId());
        Message returnMsgUL = DaoService.getMessageById(msgUL.getId());
        Common.d("daoServiceTest::initDaoTest() -> initial msgUW location " + msgUW.getLocation());
        Common.d("daoServiceTest::initDaoTest() -> retuerned msgUW location " + returnMsgUW.getLocation());
        assertTrue(returnMsgUW.getLocation().toString().compareTo(msgUW.getLocation().toString()) == 0);
        assertTrue(returnMsgUT.getLocation().toString().compareTo(msgUT.getLocation().toString()) == 0);
        assertTrue(returnMsgUL.getLocation().toString().compareTo(msgUL.getLocation().toString()) == 0);

        DaoService.deleteMessage(msgUT.getId());
        DaoService.deleteMessage(msgUW.getId());
        DaoService.deleteMessage(msgUL.getId());
        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 3);

        List<Message> msgs = DaoService.getAllMessages();
        assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations

        jedis.del(Constants.key_idGenerator);
        assertNull(jedis.get(Constants.key_idGenerator));
        jedis.del(Constants.key_recents);
        assertTrue(DaoService.getEverything().size() == 0);
    }

    @Test
    public void checkExistenceTest(){
        Message msg = new Message("IAmInHere");
        DaoService.createMessage(msg);
        assertFalse(DaoService.checkExistance(msg.getId()));

        DaoService.deleteMessage(msg.getId());
        assertFalse(DaoService.checkExistance(msg.getId()));

        List<Message> msgs = DaoService.getAllMessages();
        assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
    }

    @Test
    public void createMessageTest() {
        Message msg = new Message("Matthew");

        DaoService.createMessage(msg);
        String msgId = msg.getId();
        assertNull(DaoService.getMessageById(msgId));

        DaoService.deleteMessage(msgId);
        assertNull(DaoService.getMessageById(msgId));

        assertNull(jedis.get(msgId));

        List<Message> msgs = DaoService.getAllMessages();
        assertTrue(msgs.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
    }


    @Test
    public void getMessageByIdentifierTest() {
        Message message = new Message("Michael");
        DaoService.createMessage(message);

        Message returned = DaoService.getMessageById(message.getId());

        assertNull(returned);
        //assertTrue(returned.getId().compareTo(message.getId()) == 0);

        DaoService.deleteMessage(message.getId());
        Message newReturned = DaoService.getMessageById(message.getId());

        assertNull(newReturned);
        List<Message> messages = DaoService.getAllMessages();
        assertTrue(messages.size() == 0);			//make sure the database is totally cleared, no memory has occurred in the above operations
    }


    @Test
    public void updateMessageTest() {

        //here "test" is the original roomNumber
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message message = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
        DaoService.createMessage(message);

        message.setUserName("jUnit");
        DaoService.updateMessage(message, message.getId());

        Message returned = DaoService.getMessageById(message.getId());

        assertTrue(returned.getUserName().compareTo("jUnit") == 0);  //check if updated

        DaoService.deleteMessage(message.getId());
        assertNull(DaoService.getMessageById(message.getId()));   //check if new message has been deleted

        List<Message> messages = DaoService.getAllMessages();
        assertTrue(messages.size() == 0);
    }

    @Test
    public void deleteMessageTest() {
        Message message = new Message("admin");

        DaoService.createMessage(message);

        String messageIdentifier = message.getId();

        assertNull(jedis.get(messageIdentifier));

        Message returned = DaoService.getMessageById(message.getId());
        //assertTrue(returned.getId().compareTo(message.getId()) ==0);	//make sure the message is in the database

        DaoService.deleteMessage(message.getId());
        assertNull(DaoService.getMessageById(messageIdentifier));		//make sure the message now is being deleted

        List<Message> messages = DaoService.getAllMessages();
        assertTrue(messages.size() == 0);				//make sure the database is totally cleared, no memory has occurred in the above operations
    }

    @Test
    public void getMessagesTest() {
        Message messageAlpha = new Message("SunnyMichael");
        Message messageBeta = new Message("SunnyMatthew");
        Message messageSigma = new Message("SunnyEmma");

        DaoService.createMessage(messageAlpha);
        DaoService.createMessage(messageBeta);
        DaoService.createMessage(messageSigma);
        Common.d( "daoServiceTest::getCurrentMessagesTest -> retuerned messagealpha" + jedis.get(messageAlpha.getId()) );
        Common.d( "daoServiceTest::getCurrentMessagesTest -> retuerned messagebeta" + jedis.get(messageBeta.getId()) );
        Common.d( "daoServiceTest::getCurrentMessagesTest -> retuerned messagesigma" + jedis.get(messageSigma.getId()) );


        List<Message> messages = DaoService.getAllMessages();
        Common.d( "daoServiceTest::getMessagesTest -> retuerned messages size" + messages.size() );
        assertTrue(messages.size() == 0);

        DaoService.deleteMessage(messageAlpha.getId());      //redis duplicate keys problem
        DaoService.deleteMessage(messageBeta.getId());
        DaoService.deleteMessage(messageSigma.getId());

        messages = DaoService.getAllMessages();
        assertTrue(messages.size() == 0);
    }

    @Test
    public void SearchMessageTest(){
        jedis.del(Constants.key_idGenerator);
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUW = new Message("Simon","lol","2012 12 21","2012 12 21",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","test","SimonJiang", 19.99, 1);
        DaoService.createMessage(msgUW);
        Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUL = new Message("Simon","lol","2012 12 22","2012 12 22",30,locationUL,0,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456788","twit","test", 19.99, 0);
        DaoService.createMessage(msgUL);
        Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUT = new Message("Simon","lol","2012 12 23","2012 12 23",30,locationUT,2,"looking for girlfriend","mic@uwaterloo.ca",
                "516xxxxxx","123456789","twit","SimonJian", 19.99, -1);
        DaoService.createMessage(msgUT);

        Message msgLucas = new Message("Lucas","lol","2012 12 23","2012 12 23",30,locationUW,1,"looking for girlfriend","qqb@me.com",
                "226xxxxxx","987654321","twitter","LucasWang", 19.99, -1);
        DaoService.createMessage(msgLucas);
        
        assertTrue(DaoService.generalGontactInfoSearch("test").size() == 2);
        assertTrue(DaoService.generalGontactInfoSearch("SimonJiang").size() == 1);
        assertTrue(DaoService.generalGontactInfoSearch("516xxxxxx").size() == 1);
        assertTrue(DaoService.generalGontactInfoSearch("BullShit").size() == 0);
        
        assertTrue(DaoService.emailInfoSearch("simon@uwaterloo.ca").size() == 2);
        assertTrue(DaoService.emailInfoSearch("mic@uwaterloo.ca").size() == 1);
        assertTrue(DaoService.emailInfoSearch("12").size() == 0);
        
        assertTrue(DaoService.phoneInfoSearch("519xxxxxx").size() == 2);
        assertTrue(DaoService.phoneInfoSearch("516xxxxxx").size() == 1);
        assertTrue(DaoService.phoneInfoSearch(null).size() == 0);
        
        assertTrue(DaoService.qqInfoSearch("123456789").size() == 2);
        assertTrue(DaoService.qqInfoSearch("123456788").size() == 1);
        assertTrue(DaoService.qqInfoSearch("BullShit").size() == 0);
        
        assertTrue(DaoService.twitterInfoSearch("test").size() == 1);
        assertTrue(DaoService.twitterInfoSearch("twitter").size() == 1);
        assertTrue(DaoService.twitterInfoSearch("twit").size() == 2);
        
        assertTrue(DaoService.selfDefinedInfoSearch("SimonJiang").size() == 1);
        assertTrue(DaoService.selfDefinedInfoSearch("test").size() == 1);
        assertTrue(DaoService.selfDefinedInfoSearch("SimonJian").size() == 1);
        assertTrue(DaoService.selfDefinedInfoSearch("BullShit").size() == 0);
        
        //2*519xxxxxx, 1* mic@uwaterloo,ca, 1*twitter
        assertTrue(DaoService.multipeSearch("519xxxxxx", "mic@uwaterloo.ca", "", "twitter", "Bla").size() == 4);
        
        DaoService.deleteMessage(msgUT.getId());
        DaoService.deleteMessage(msgUW.getId());
        DaoService.deleteMessage(msgUL.getId());
        DaoService.deleteMessage(msgLucas.getId());
        assertTrue(Integer.parseInt(jedis.get(Constants.key_idGenerator)) == 4);

        List<Message> msgs = DaoService.getAllMessages();
        assertTrue(msgs.size() == 0);               //make sure the database is totally cleared, no memory has occurred in the above operations

        jedis.del(Constants.key_idGenerator);
        assertNull(jedis.get(Constants.key_idGenerator));
        jedis.del(Constants.key_recents);
        assertTrue(DaoService.getEverything().size() == 0);
    }
    
    @Test
    public void sortMessageTest(){
        Location locationUW = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUW = new Message("Simon","lol","6353 26 73","6353 26 73",30,locationUW,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456789","twit","SimonJiang", 19.99, 1);
        DaoService.createMessage(msgUW);
        Location locationUL = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUL = new Message("Simon","lol","2353 26 73","2353 26 73",30,locationUL,1,"looking for girlfriend","simon@uwaterloo.ca",
                "519xxxxxx","123456788","twit","SimonJiang", 19.99, 0);
        DaoService.createMessage(msgUL);
        Location locationUT = new Location("江苏省", "南京市", "南京农业大学");
        Message msgUT = new Message("Simon","lol","3353 26 73","3353 26 73",30,locationUT,1,"looking for girlfriend","mic@uwaterloo.ca",
                "516xxxxxx","123456789","twit","SimonJian", 19.99, -1);
        DaoService.createMessage(msgUT);
        DaoService.sortMessageByEndDate(DaoService.getAllMessages());
        DaoService.clearDatabase();
        
    }
    
}
