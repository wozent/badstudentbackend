package badstudent.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



import badstudent.mappings.AllProvinceMappings;
import badstudent.mappings.MappingBase;
import badstudent.mappings.MappingManager;
import badstudent.model.Location;
import badstudent.model.Message;
import redis.clients.jedis.Jedis;

public class DaoLocation {
    
    private static Jedis jedis = new Jedis("localhost");
    
    public static Set<String> getMessageIdBySchool(String school){
        return jedis.smembers(school);
    }
    
    public static void addMessageToSchool(Message msg){
        String messageId = msg.getId();
        String school = msg.getLocation().getSchool();
        jedis.sadd(school, messageId);
    }
    
    public static Set<String> getMessageIdByRegion(String province, String city, String region){
        Set<String> schoolSet = new HashSet<String>();
        if(MappingManager.getRegionMappings(province, city, region)==null) return null;
        for(String school :MappingManager.getRegionMappings(province, city, region).getAllSubArea()){
            schoolSet.addAll(jedis.smembers(school));
        }
        return schoolSet;
    }
    
    public static void addNewMessage(Message msg){
        Location location = msg.getLocation();
        String id = msg.getId();
        //TODO: add a message to DB
    }
    
    public static void deleteMessage(Message msg){
        Location location = msg.getLocation();
        String id = msg.getId();
        //TODO: delete a message from DB        
    }
    
    public static List<Message> SearchForMessage(Location location){
        ArrayList<Message> messageList = new ArrayList<Message>();
        //TODO: add search Funcation
        
        return messageList;
    }
    
    //TODO: add location database support

}
