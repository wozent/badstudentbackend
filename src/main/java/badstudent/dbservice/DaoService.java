package badstudent.dbservice;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import java.util.Set;
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
            return dao.createMessage(message);
        }
    }

    /*update message*/
    public Message updateMessage(Message message, String id){
        if (checkExistance(id) ){
            System.out.println("@updateMessage::id exist, updating message with id: " + id);
            return dao.updateMessage(message);
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


    public List<Message> getAllMessages(){
        return dao.getAllMessages();
    }

    public Set<String> getIds(){
        return dao.getAllIds();
    }

    public Set<String> getPartialIds(String targetPattern){
        return dao.getPartialIds(Constants.message_prefix + "*" + targetPattern + "*");
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
                if (testMessage.getPhone().equalsIgnoreCase(contactInfoPiece) || testMessage.getEmail().equalsIgnoreCase(contactInfoPiece) || testMessage.getQq().equalsIgnoreCase(contactInfoPiece) ||  testMessage.getSelfDefined().equalsIgnoreCase(contactInfoPiece)){
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

    public List<Message> multipeSearch(String phone, String email, String qq, String selfDefined){
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

    public List<Message> sortAllMessageByDate(){
        List<Message> allMessages = this.getAllMessages();
        for(Message msg : allMessages){
            Common.d("Before sort:"+msg.getDate());
        }
        for(int b=1;b<allMessages.size();b++){
            for(int a=0;a<allMessages.size()-b;a++){
                Message msg1 = allMessages.get(a);
                Message msg2 = allMessages.get(a+1);
                if(Common.isMessageBefore(msg1,msg2)){
                    allMessages.set(a, msg2);
                    allMessages.set(a+1, msg1);
                }
            }
        }
        for(Message msg : allMessages){
            Common.d("After sort:"+msg.getDate());
        }
        //TODO:add sort
        return allMessages;
    }
    
    public void clearDatabase(){
        DaoMessage.clearDatabase();
    }

}
