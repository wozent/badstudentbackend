package badstudent.dao.test;

import java.util.ArrayList;

import org.junit.Test;

import badstudent.common.Common;
import badstudent.database.DaoLocation;
import badstudent.dbservice.DaoService;
import badstudent.mappings.MappingManager;
import badstudent.model.Location;
import badstudent.model.Message;

public class DaoLocationTest {
    
    @Test
    public void test(){
        for(String bla : MappingManager.getAllSchools("江苏省", "南京市")){
            Common.d_Chinese(bla);
        }
        for(int a=0;a<1000;a++){
            Common.d(""+a+MappingManager.isLocationVaild(new Location("江苏省", "南京市", "玄武区", "东南大学")));
        }
        DaoService d = new DaoService();
        d.createMessage(new Message("1","bla", "bla", "1994-02-12", new Location("江苏省", "南京市", "玄武区", "东南大学"), true, "", "", "", "", "", "", 1, 1));
        //d.createMessage(new Message("22","blaa", "bla", "1994-02-12", new Location("江苏省", "南京市", "玄武区", "东大学"), true, "", "", "", "", "", "", 1, 1));
        ArrayList<Message> n = new ArrayList<Message>();
        n.add(d.getMessageById("1"));
        Common.d(d.searchByLocation(new Location("江苏省", "南京市", "玄武区", "东南大学"), null, n).get(0).getUserName());
    }

}
