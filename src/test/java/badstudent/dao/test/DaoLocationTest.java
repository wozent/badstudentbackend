package badstudent.dao.test;

import org.junit.Test;

import badstudent.common.Common;
import badstudent.database.DaoLocation;

public class DaoLocationTest {
    
    @Test
    public void test(){
        Common.d_Chinese(DaoLocation.getAllCity(DaoLocation.getAllProvince()[4])[0]);
        Common.d_Chinese(DaoLocation.getAllProvince()[15]);
    }

}
