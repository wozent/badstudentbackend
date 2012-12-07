package badstudent.dbservice;

import java.util.*;

import badstudent.mappings.MappingBase;
import badstudent.mappings.MappingManager;
import badstudent.model.*;
import badstudent.common.*;
import badstudent.database.*;

public class DaoService{

    //private static Log log = LogFactory.getLog(ScheduleResource.class);

    /*checks if the id exist in Redis*/
    public static boolean checkExistance(String id){
        if (DaoMessage.getMessageById(id) == null){
            return false;
        }
        return true;
    }


    public static Message getMessageById(String id){
        return DaoMessage.getMessageById(id);
    }
    
    public static List<Message> getRecents(){
    	return DaoMessage.getRecents();
    }

    /*create non-existing messages, return null if the message was already in the database(same Id), return the message entity if the message was not in existence and now created*/
    public static Message createMessage(Message message){
        if(!message.isMessageVaild()){
            return null;
        }
        if (checkExistance(message.getId()) ){
            Common.d("@createMessage::***warning***id does exist, doing nothing");
            return null;
        }
        if(DaoLocation.checkExistance(message)){
            Common.d("@createMessage::***warning***id does exist in location, doing nothing");
            return null;
        }
        Common.d("@createSchedule::id does not exist, creating message with id: " + message.getId());
        DaoLocation.addMessageToSchool(message);
        DaoMessage.addMessageToDatabase(message);
        return message;
    }

    /*update message*/
    public static Message updateMessage(Message newMessage, String oldMessageId){
        if(!newMessage.isMessageVaild()){
            Common.d("@updateMessage:: New Message is not valid");
        }
        if(checkExistance(oldMessageId) && DaoLocation.checkExistance(getMessageById(oldMessageId))){
            Common.d("@updateMessage::id exist, updating message with id: " + oldMessageId);
            return DaoMessage.updateMessageInDatabase(newMessage);
        }else{
            Common.d("@updateSchedule::***warning***id does not exist, abort.");
            return null;
        }
    }

    /*delete existing messages, return true if the message existed and now deleted, return false if the message was not found*/
    public static boolean deleteMessage(String id){
        if (checkExistance(id) && DaoLocation.checkExistance(getMessageById(id))){
            Common.d("@deleteMessage::id exist, deleting message with id: " + id);
            DaoLocation.deleteMessageFromSchool(DaoMessage.getMessageById(id));
            DaoMessage.deleteMessageFromDatabase(id);
            return true;
        }
        else{
            Common.d("@deleteMessage::***warning***id does not exist, doing nothing");
            return false;
        }
    }


    public static List<Message> getAllMessages(){
        return DaoMessage.getAllMessagesInDatabase();
    }

    public static Set<String> getIds(){
        return DaoMessage.getAllMessageIdInDatabase();
    }

    public static Set<String> getPartialIds(String targetPattern){
        return DaoMessage.getPartialIds(Constants.key_message_prefix + "*" + targetPattern + "*");
    }

    public static Set<String> getEverything(){
        return DaoBasic.getWholeDatabase();  
    }

    public static List<Message> generalGontactInfoSearch(String contactInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(contactInfoPiece == null || contactInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = DaoMessage.getPartialIds(contactInfoPiece.substring(0, 3));
        keys.addAll(DaoMessage.getPartialIds(contactInfoPiece.substring(contactInfoPiece.length()-3)));
        for (String key : keys) {
            if (key.indexOf(contactInfoPiece.substring(0, 3), 7) != -1 || key.indexOf(contactInfoPiece.substring(contactInfoPiece.length()-3),7) != -1){
                Message testMessage = DaoMessage.getMessageById(key);
                if (testMessage.getPhone().equalsIgnoreCase(contactInfoPiece) || testMessage.getEmail().equalsIgnoreCase(contactInfoPiece) || testMessage.getQq().equalsIgnoreCase(contactInfoPiece) || testMessage.getTwitter().equalsIgnoreCase(contactInfoPiece)|| testMessage.getSelfDefined().equalsIgnoreCase(contactInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }


    public static List<Message> emailInfoSearch(String emailInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(emailInfoPiece == null || emailInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = DaoMessage.getPartialIds(emailInfoPiece.substring(0,3));
        for (String key : keys) {
            if (key.indexOf(emailInfoPiece.substring(0,3), 7) != -1){
                Message testMessage = DaoMessage.getMessageById(key);
                if (testMessage.getEmail().equalsIgnoreCase(emailInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }

    public static List<Message> phoneInfoSearch(String phoneInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(phoneInfoPiece == null || phoneInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = DaoMessage.getPartialIds(phoneInfoPiece.substring(phoneInfoPiece.length()-3));
        for (String key : keys) {
            if (key.indexOf(phoneInfoPiece.substring(phoneInfoPiece.length()-3), 7) != -1){
                Message testMessage = DaoMessage.getMessageById(key);
                if (testMessage.getPhone().equalsIgnoreCase(phoneInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }

    public static List<Message> qqInfoSearch(String qqInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(qqInfoPiece == null || qqInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = DaoMessage.getPartialIds(qqInfoPiece.substring(qqInfoPiece.length() -3));
        for (String key : keys) {
            if (key.indexOf(qqInfoPiece.substring(qqInfoPiece.length() -3), 7) != -1){
                Message testMessage = DaoMessage.getMessageById(key);
                if (testMessage.getQq().equalsIgnoreCase(qqInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }

    public static List<Message> twitterInfoSearch(String twitterInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(twitterInfoPiece == null || twitterInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = DaoMessage.getPartialIds(twitterInfoPiece.substring(twitterInfoPiece.length() -3));
        for (String key : keys) {
            if (key.indexOf(twitterInfoPiece.substring(twitterInfoPiece.length() -3), 7) != -1){
                Message testMessage = DaoMessage.getMessageById(key);
                if (testMessage.getTwitter().equalsIgnoreCase(twitterInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }


    public static List<Message> selfDefinedInfoSearch(String selfDefinedInfoPiece){
        List<Message> matchedMessages = new ArrayList<Message>();
        if(selfDefinedInfoPiece == null || selfDefinedInfoPiece.length()<3){
            return matchedMessages;
        }
        Set<String> keys = DaoMessage.getPartialIds(selfDefinedInfoPiece.substring(selfDefinedInfoPiece.length() -3));
        for (String key : keys) {
            if (key.indexOf(selfDefinedInfoPiece.substring(selfDefinedInfoPiece.length() -3), 7) != -1){
                Message testMessage = DaoMessage.getMessageById(key);
                if (testMessage.getSelfDefined().equalsIgnoreCase(selfDefinedInfoPiece)){
                    matchedMessages.add(testMessage);
                }
            }
        }

        return matchedMessages;
    }

    public static List<Message> multipeSearch(String phone, String email, String qq, String twitter ,String selfDefined){
        List<Message> merge = new ArrayList<Message>();

        //adding each of the search results into the merge List
        if(phone != null && phone.compareTo("") != 0){
            List<Message> searchByPhone = phoneInfoSearch(phone);
            for (int i = 0; i < searchByPhone.size(); i++){
                Message temp = searchByPhone.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (email != null && email.compareTo("") != 0){
            List<Message> searchByEmail = emailInfoSearch(email);
            for (int i = 0; i < searchByEmail.size(); i++){
                Message temp = searchByEmail.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (qq != null && qq.compareTo("") != 0){
            List<Message> searchByQq = qqInfoSearch(qq);
            for (int i = 0; i < searchByQq.size(); i++){
                Message temp = searchByQq.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (twitter != null && twitter.compareTo("") != 0){
            List<Message> searchBytwitter = twitterInfoSearch(twitter);
            for (int i = 0; i < searchBytwitter.size(); i++){
                Message temp = searchBytwitter.get(i);
                if (!merge.contains(temp)){
                    merge.add(temp);
                }
            }
        }
        if (selfDefined != null && selfDefined.compareTo("") != 0){
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

    public static List<Message> sortMessageByEndDate(List<Message> allMessages){
        for(Message msg : allMessages){
            Common.d("Before sort:"+msg.getEndDate());
        }
        for(int b=1;b<allMessages.size();b++){
            for(int a=0;a<allMessages.size()-b;a++){
                Message msg1 = allMessages.get(a);
                Message msg2 = allMessages.get(a+1);
                if(msg1.getEndDate().before(msg2.getEndDate())){
                    allMessages.set(a, msg2);
                    allMessages.set(a+1, msg1);
                }
            }
        }
        for(Message msg : allMessages){
            Common.d("After sort:"+msg.getEndDate());
        }
        return allMessages;
    }

    public static void clearDatabase(){
        DaoBasic.clearDatabase();
    }


    /**
     * @param location  	target location to be searched upon
     * @param within    	search results only within these messages	if null then search all
     * @param without		search results excluding these messages		if null then return full result
     * @return list of messages corresponding to target location, contained in within, not contained in without
     */
    public static List<Message> searchByLocation(Location location, List<Message> within, List<Message> without){
    	List<Message> searchResult = new ArrayList<Message>();
        if(!MappingManager.isLocationVaild(location)){
            return null;
        }
        Set<String> messageIds = DaoLocation.getMessageIdBySchool(location.getSchool());
        if(within==null){
            within = new ArrayList<Message>();
            for(String id : messageIds){
                within.add(DaoMessage.getMessageById(id));
            }
        }
        else{
        	for (int i = 0; i < within.size(); i++){
                if (within.get(i).getLocation().sameLocation(location)){
                    searchResult.add(within.get(i));
                }
            }
        }
        if(without!=null){
            within.removeAll(without);
        }
        return within;
    }
    
    public static List<Message> searchByType(int type, List<Message> within, List<Message> without){
    	List<Message> searchResult = new ArrayList<Message>();
    	if (within == null){
    		Set<String> keys = getPartialIds("-" + Integer.toString(type));
    		for (String key : keys){
    			Message message = getMessageById(key);
    			if (type != -1 && message.getType() == type){
    				searchResult.add(message);
    			}
    		}
    	}
    	else{
    		for (int i = 0; i < within.size(); i++){
    			if (type != -1 && within.get(i).getType() == type){
    				searchResult.add(within.get(i));
    			}
    		}
    	}
    	// eliminate all the messages contained in without
        if (without != null){
            boolean changed = searchResult.removeAll(without);
            if (changed){
                Common.d("DaoService:: searchByType: searchResult contains coliding messages, all messages in both result and without are wipped from result");
            }
            else{
                Common.d("DaoService:: searchByType: no message collision detected, search remain same");
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
    public static List<Message> searchByDate(Date date, List<Message> within, List<Message> without){
        List<Message> searchResult = new ArrayList<Message>();
        if (within == null){
            //store all messages on target date into searchResult
            List<Message> allMessages = DaoMessage.getAllMessagesInDatabase();
            for (int i = 0; i < allMessages.size(); i++){
                if (allMessages.get(i).isDayInRange(date)){
                    searchResult.add(allMessages.get(i));
                }
            }
        }
        //if within is not full
        else{
            //store messages from within on target date into searchResult
            for (int i = 0; i < within.size(); i++){
                if (within.get(i).isDayInRange(date)){
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
