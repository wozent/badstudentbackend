package badstudent.database;

import java.util.Set;

import badstudent.common.Constants;
import redis.clients.jedis.Jedis;

public class DaoBasic {
    private static Jedis jedis = new Jedis("localhost");

    public static Long generateId(){
        return jedis.incr(Constants.key_idGenerator);
    }
    
    public static Set<String> getWholeDatabase(){
        return jedis.keys("*");  
    }

    public static void clearDatabase(){
        jedis.flushAll();
    }
}
