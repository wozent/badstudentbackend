package badstudent.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mappings.provinceMappings;


import badstudent.model.Location;
import badstudent.model.Message;
import redis.clients.jedis.Jedis;

public class DaoLocation {
    
    private static Jedis jedis = new Jedis("localhost");
    
    
    public static String[] getAllProvince(){
        return provinceMappings.Allprovince;
    }
    
    public static Set<String> getAllCity(String province){
        return jedis.smembers(province);
    }
    
    public static Set<String> getAllSchool(String city){
        Set<String> regions = jedis.smembers(city);
        //Set<String> schools = ;
        for(String region : regions){
            //schools.addAll(jedis.smembers(region));
        }
        return jedis.smembers(city);
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
