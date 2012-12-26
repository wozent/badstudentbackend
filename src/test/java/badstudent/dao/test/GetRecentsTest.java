package badstudent.dao.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import badstudent.common.Common;
import badstudent.common.Constants;
import badstudent.database.DaoBasic;
import badstudent.dbservice.DaoService;
import badstudent.model.Location;
import badstudent.model.Message;

public class GetRecentsTest {
    
    @Test
    public void CreatMessageTest(){
        DaoBasic.clearDatabase();
        Location vaildLocation = new Location("江苏省 南京市 南京农业大学");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");  
        String today = formatter.format(new Date());
        Common.d(today);
        Message msg1 = new Message("user1", "random1", today, today, 60, vaildLocation, Constants.geneder_male,
                "randomContent", "user1@random.com", "2269891234", "1145633458", "@user1", "randomself", 30, Constants.type_ask);
        Message msg2 = new Message("user2", "random2", today, today, 90, vaildLocation, Constants.geneder_female,
                "randomContent", "user2@random.com", "2269891233", "1145633459", "@user2", "randomself", 20, Constants.type_help);
        DaoService.createMessage(msg1);
        DaoService.createMessage(msg2);
        
        List<Message> searchResult = DaoService.getRecents();
        assertTrue(searchResult.size() == 2);
        DaoBasic.clearDatabase();
    }
}
