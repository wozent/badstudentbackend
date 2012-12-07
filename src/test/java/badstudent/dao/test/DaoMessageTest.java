package badstudent.dao.test;

import org.junit.Test;

import badstudent.common.Constants;
import badstudent.dbservice.DaoService;
import badstudent.model.Location;
import badstudent.model.Message;

public class DaoMessageTest {
    
    @Test
    public void CreatMessageTest(){
        Location vaildLocation = new Location("江苏省", "南京市", "南京农业大学");
        Location invaildLocation = new Location("江苏省", "南京", "南京农业大学");
        Message msg1 = new Message("user1", "random1", "2013 10 12", "2013 10 12", 60, vaildLocation, Constants.geneder_male,
                "randomContent", "user1@random.com", "2269891234", "1145633458", "@user1", "randomself", 30, Constants.type_ask);
        Message msg2 = new Message("user2", "random2", "2013 10 11", "2013 10 13", 90, invaildLocation, Constants.geneder_female,
                "randomContent", "user2@random.com", "2269891233", "1145633459", "@user2", "randomself", 20, Constants.type_help);
        DaoService.createMessage(msg1);
    }
}
