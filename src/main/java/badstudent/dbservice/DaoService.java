package badstudent.dbservice;

import java.util.*;
import badstudent.model.*;
import badstudent.common.*;
import badstudent.database.*;

public class DaoService{

    //private static Log log = LogFactory.getLog(ScheduleResource.class);
    private DaoMessage dao;

    public DaoService(){
        this.dao = new DaoMessage();
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
            return dao.addMessageToDatabase(message);
        }
    }

    /*update message*/
    public Message updateMessage(Message message, String id){
        if (checkExistance(id) ){
            System.out.println("@updateMessage::id exist, updating message with id: " + id);
            return dao.updateMessageInDatabase(message);
        }
        else{
            System.out.println("@updateSchedule::***warning***id does not exist, creating new message with id: " + id);
            dao.addMessageToDatabase(message);
            return null;
        }

    }

    /*delete existing messages, return true if the message existed and now deleted, return false if the message was not found*/
    public boolean deleteMessage(String id){
        if (checkExistance(id) ){
            System.out.println("@deleteMessage::id exist, deleting message with id: " + id);
            dao.deleteMessageFromDatabase(id);
            return true;
        }
        else{
            System.out.println("@deleteMessage::***warning***id does not exist, doing nothing");
            return false;
        }
    }


    public List<Message> getAllMessages(){
        return dao.getAllMessagesInDatabase();
    }

    public Set<String> getIds(){
        return dao.getAllMessageIdInDatabase();
    }

    public Set<String> getPartialIds(String targetPattern){
        return dao.getPartialIds(Constants.key_message_prefix + "*" + targetPattern + "*");
    }

    public Set<String> getEverything(){
        return dao.getEverything();  
    }

    public List<Message> generalGontactInfoSearch(String contactInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(contactInfoPiece == null || contactInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = dao.getPartialIds(contactInfoPiece.substring(0, 3));
        keys.addAll(dao.getPartialIds(contactInfoPiece.substring(contactInfoPiece.length()-3)));
        for (String key : keys) {
            if (key.indexOf(contactInfoPiece.substring(0, 3), 7) != -1 || key.indexOf(contactInfoPiece.substring(contactInfoPiece.length()-3),7) != -1){
                Message testMessage = dao.getMessageById(key);
                if (testMessage.getPhone().equalsIgnoreCase(contactInfoPiece) || testMessage.getEmail().equalsIgnoreCase(contactInfoPiece) || testMessage.getQq().equalsIgnoreCase(contactInfoPiece) || testMessage.getTwitter().equalsIgnoreCase(contactInfoPiece)|| testMessage.getSelfDefined().equalsIgnoreCase(contactInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }


    public List<Message> emailInfoSearch(String emailInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(emailInfoPiece == null || emailInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = dao.getPartialIds(emailInfoPiece.substring(0,3));
        for (String key : keys) {
            if (key.indexOf(emailInfoPiece.substring(0,3), 7) != -1){
                Message testMessage = dao.getMessageById(key);
                if (testMessage.getEmail().equalsIgnoreCase(emailInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }

    public List<Message> phoneInfoSearch(String phoneInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(phoneInfoPiece == null || phoneInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = dao.getPartialIds(phoneInfoPiece.substring(phoneInfoPiece.length()-3));
        for (String key : keys) {
            if (key.indexOf(phoneInfoPiece.substring(phoneInfoPiece.length()-3), 7) != -1){
                Message testMessage = dao.getMessageById(key);
                if (testMessage.getPhone().equalsIgnoreCase(phoneInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }

    public List<Message> qqInfoSearch(String qqInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(qqInfoPiece == null || qqInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = dao.getPartialIds(qqInfoPiece.substring(qqInfoPiece.length() -3));
        for (String key : keys) {
            if (key.indexOf(qqInfoPiece.substring(qqInfoPiece.length() -3), 7) != -1){
                Message testMessage = dao.getMessageById(key);
                if (testMessage.getQq().equalsIgnoreCase(qqInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }
    
    public List<Message> twitterInfoSearch(String twitterInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(twitterInfoPiece == null || twitterInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = dao.getPartialIds(twitterInfoPiece.substring(twitterInfoPiece.length() -3));
        for (String key : keys) {
            if (key.indexOf(twitterInfoPiece.substring(twitterInfoPiece.length() -3), 7) != -1){
                Message testMessage = dao.getMessageById(key);
                if (testMessage.getTwitter().equalsIgnoreCase(twitterInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }
    

    public List<Message> selfDefinedInfoSearch(String selfDefinedInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(selfDefinedInfoPiece == null || selfDefinedInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = dao.getPartialIds(selfDefinedInfoPiece.substring(selfDefinedInfoPiece.length() -3));
        for (String key : keys) {
            if (key.indexOf(selfDefinedInfoPiece.substring(selfDefinedInfoPiece.length() -3), 7) != -1){
                Message testMessage = dao.getMessageById(key);
                if (testMessage.getSelfDefined().equalsIgnoreCase(selfDefinedInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }

    public List<Message> multipeSearch(String phone, String email, String qq, String twitter ,String selfDefined){
        List<Message> merge = new ArrayList<Message>();

        //adding each of the search results into the merge List
        if(phone.compareTo("") != 0){
            List<Message> searchByPhone = phoneInfoSearch(phone);
            for (int i = 0; i < searchByPhone.size(); i++){
                Message temp = searchByPhone.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (email.compareTo("") != 0){
            List<Message> searchByEmail = emailInfoSearch(email);
            for (int i = 0; i < searchByEmail.size(); i++){
                Message temp = searchByEmail.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (qq.compareTo("") != 0){
            List<Message> searchByQq = qqInfoSearch(qq);
            for (int i = 0; i < searchByQq.size(); i++){
                Message temp = searchByQq.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (twitter.compareTo("") != 0){
            List<Message> searchBytwitter = twitterInfoSearch(twitter);
            for (int i = 0; i < searchBytwitter.size(); i++){
                Message temp = searchBytwitter.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (selfDefined.compareTo("") != 0){
            List<Message> searchBySelfDefined = selfDefinedInfoSearch(selfDefined);
            for (int i = 0; i < searchBySelfDefined.size(); i++){
                Message temp = searchBySelfDefined.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }	

        return merge;
    }

    public List<Message> sortMessageByDate(List<Message> allMessages){
        for(Message msg : allMessages){
            Common.d("Before sort:"+msg.getDate());
        }
        for(int b=1;b<allMessages.size();b++){
            for(int a=0;a<allMessages.size()-b;a++){
                Message msg1 = allMessages.get(a);
                Message msg2 = allMessages.get(a+1);
                if(msg1.getDate().before(msg2.getDate())){
                    allMessages.set(a, msg2);
                    allMessages.set(a+1, msg1);
                }
            }
        }
        for(Message msg : allMessages){
            Common.d("After sort:"+msg.getDate());
        }
        return allMessages;
    }
    
    public void clearDatabase(){
        DaoMessage.clearDatabase();
    }
    
    
    /**
     * @param location  	target location to be searched upon
     * @param within    	search results only within these messages	if null then search all
     * @param without		search results excluding these messages		if null then return full result
     * @return list of messages corresponding to target location, contained in within, not contained in without
     */
    public List<Message> searchByLocation(Location location, List<Message> within, List<Message> without){
    	List<Message> searchResult = new ArrayList<Message>();
    	
    	//@Michael this part requires some mapping, you'd be more familiar
    	
    	if (within == null){
    		//TODO
    		//map along with location, return all the messages under this location, if any provinces/cities/regions/universities not found, return null
    		
    	}
    	//if within is not null
    	else{
    		//store messages from within on target location into searchResult
    		for (int i = 0; i < within.size(); i++){
    			if (within.get(i).sameLocation(location)){
    				searchResult.add(within.get(i));
    			}
    		}
    	}
    	if (without != null){
    		boolean changed = searchResult.removeAll(without);
    		if (changed){
    			Common.d("DaoService:: searchByLocation: searchResult contains coliding messages, all messages in both result and without are wipped from result");
    		}
    		else{
    			Common.d("DaoService:: searchByLocation: no message collision detected, searchResult remain same");
    		}
    	}
    	return searchResult;
    }
    
    
    /**
     * @param date		target date to be searched upon
     * @param within	search results only within these messages   if null then search all (should never allow)
     * @param without	search results excluding these messages		if null then return full result
     * @return list of messages corresponding to target date, contained in within, not contained in without
     */
    public List<Message> searchByDate(Date date, List<Message> within, List<Message> without){
    	List<Message> searchResult = new ArrayList<Message>();
    	if (within == null){
    		//store all messages on target date into searchResult
    		List<Message> allMessages = dao.getAllMessagesInDatabase();
    		for (int i = 0; i < allMessages.size(); i++){
    			if (allMessages.get(i).sameDay(date)){
    				searchResult.add(allMessages.get(i));
    			}
    		}
    	}
    	//if within is not full
    	else{
    		//store messages from within on target date into searchResult
    		for (int i = 0; i < within.size(); i++){
    			if (within.get(i).sameDay(date)){
    				searchResult.add(within.get(i));
    			}
    		}
    	}
    	// eliminate all the messages contained in without
    	if (without != null){
    		boolean changed = searchResult.removeAll(without);
    		if (changed){
    			Common.d("DaoService:: searchByDate: searchResult contains coliding messages, all messages in both result and without are wipped from result");
    		}
    		else{
    			Common.d("DaoService:: searchByDate: no message collision detected, search remain same");
    		}
    	}
    	return searchResult;
    }
    
    

}
