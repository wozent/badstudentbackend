package badstudent.dao.test;

import org.junit.Test;

import badstudent.common.Common;
import badstudent.database.DaoLocation;
import badstudent.dbservice.DaoService;
import badstudent.mappings.MappingManager;
import badstudent.model.Location;

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
        
        d.searchByLocation(new Location("江苏省", "南京市", "玄武区", "东南大学"), null, null);
        
    }

}
