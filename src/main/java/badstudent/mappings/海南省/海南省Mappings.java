package badstudent.mappings.海南省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.海南省.海口市.海口市Mappings;

public class 海南省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("海口市",new 海口市Mappings());


    }

}
