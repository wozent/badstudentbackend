package badstudent.mappings.山西省.大同市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.山西省.大同市.大同区.大同区Mappings;

public class 大同市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("大同区",new 大同区Mappings());

    }

}
