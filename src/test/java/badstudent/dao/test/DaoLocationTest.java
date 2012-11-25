package badstudent.dao.test;

import org.junit.Test;

import badstudent.common.Common;
import badstudent.database.DaoLocation;
import badstudent.mappings.MappingManager;

public class DaoLocationTest {
    
    @Test
    public void test(){
        for(String bla : MappingManager.getAllSchools("江苏省", "南京市")){
            Common.d_Chinese(bla);
        }
    }

}
