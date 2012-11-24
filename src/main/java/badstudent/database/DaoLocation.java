package badstudent.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import badstudent.mappings.AllProvinceMappings;
import badstudent.model.Location;
import badstudent.model.Message;
import redis.clients.jedis.Jedis;

public class DaoLocation {
    
    private static Jedis jedis = new Jedis("localhost");
    
    
    public static String[] getAllProvince(){
        return AllProvinceMappings.Allprovince;
    }
    
    public static String[] getAllCity(String province){
        return AllProvinceMappings.provinceToCityMappings.get(province).getAllSubArea();
    }
    
    public static String[] getAllRegion(String province,String city){
        return AllProvinceMappings.provinceToCityMappings.get(province).getSubAreaMappings(city).getAllSubArea();
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
