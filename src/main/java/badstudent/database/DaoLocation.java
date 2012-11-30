package badstudent.database;

import java.util.HashSet;
import java.util.Set;
import badstudent.mappings.MappingManager;
import badstudent.model.Message;
import redis.clients.jedis.Jedis;

public class DaoLocation {
    
    private static Jedis jedis = new Jedis("localhost");
    
    public static Set<String> getMessageIdBySchool(String school){
        return jedis.smembers(school);
    }
    
    public static Set<String> getMessageIdByRegion(String province, String city, String region){
        Set<String> schools = MappingManager.getAllSchools(province, city, region);
        Set<String> retVal = new HashSet<String>();
        for(String school : schools){
            retVal.addAll(jedis.smembers(school));
        }
        return null;
    }
    
    public static void addMessageToSchool(Message msg){
        String messageId = msg.getId();
        String school = msg.getLocation().getSchool();
        jedis.sadd(school, messageId);
    }
    
    public static boolean deleteMessageFromSchool(Message msg){
        String messageId = msg.getId();
        String school = msg.getLocation().getSchool();
        return jedis.srem(school, messageId).equals(1);
    }

}
