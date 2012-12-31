package badstudent.mappings.云南省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.云南省.昆明市.昆明市Mappings;

public class 云南省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("昆明市",new 昆明市Mappings());


    }

}
