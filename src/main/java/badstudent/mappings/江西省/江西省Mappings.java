package badstudent.mappings.江西省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.江西省.抚州市.抚州市Mappings;
import badstudent.mappings.江西省.赣州市.赣州市Mappings;
import badstudent.mappings.江西省.吉安市.吉安市Mappings;
import badstudent.mappings.江西省.南昌市.南昌市Mappings;

public class 江西省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("抚州市",new 抚州市Mappings());
        subAreaMappings.put("赣州市",new 赣州市Mappings());
        subAreaMappings.put("吉安市",new 吉安市Mappings());
        subAreaMappings.put("南昌市",new 南昌市Mappings());


    }

}
