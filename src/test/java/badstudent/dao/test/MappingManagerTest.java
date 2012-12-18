package badstudent.dao.test;

import org.junit.Test;

import badstudent.common.Common;
import badstudent.mappings.MappingManager;

public class MappingManagerTest {
    
    @Test
    public void normalFunctionTest(){
        for(String val : MappingManager.getAllSchools("江苏省","南京市")){
            Common.d_Chinese(val);
        }
    }

}
