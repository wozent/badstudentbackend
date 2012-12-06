package badstudent.dao.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import badstudent.common.Common;
import badstudent.mappings.MappingManager;

public class MappingManagerTest {
    
    @Test
    public void normalFunctionTest(){
        //MappingManager.getAllProvince();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        Date d = null;
        try {
            d = sdf.parse("2012 12 6");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Common.d(""+Common.getCurrentDate().toString());
        Common.d(""+d.equals(Common.getCurrentDate()));
    }

}
