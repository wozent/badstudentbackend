package badstudent.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import badstudent.mappings.AllProvinceMappings;
import badstudent.mappings.MappingBase;
import badstudent.model.Location;
import badstudent.model.Message;
import redis.clients.jedis.Jedis;

public class DaoLocation {
    
    private static Jedis jedis = new Jedis("localhost");
    
    
    public static String[] getAllProvince(){
        return new AllProvinceMappings().getAllSubArea();
    }
    
    private static MappingBase getProvinceMapping(String province){
        MappingBase retVal;
        try{
           retVal = new AllProvinceMappings().getSubAreaMappings(province);
           return retVal;
        }catch(NullPointerException e){
            return null;
        }
    }
    
    public static String[] getAllCity(String province){
        MappingBase provinceMapping = getProvinceMapping(province);
        if(provinceMapping!=null){
            return provinceMapping.getAllSubArea();
        }else{
            return null;
        }
    }
    
    public static String[] getAllRegion(String province,String city){
        MappingBase provinceMapping = getProvinceMapping(province);
        if(provinceMapping!=null){
            MappingBase cityMapping = provinceMapping.getSubAreaMappings(city);
            if(cityMapping!=null){
                return cityMapping.getAllSubArea();
            }else{
                return null;
            }
        }else{
            return null;
        }
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
