package badstudent.mappings.山西省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.山西省.大同市.大同市Mappings;
import badstudent.mappings.山西省.临汾市.临汾市Mappings;
import badstudent.mappings.山西省.太谷县.太谷县Mappings;
import badstudent.mappings.山西省.太原市.太原市Mappings;

public class 山西省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("大同市",new 大同市Mappings());
        subAreaMappings.put("临汾市",new 临汾市Mappings());
        subAreaMappings.put("太谷县",new 太谷县Mappings());
        subAreaMappings.put("太原市",new 太原市Mappings());


    }

}
