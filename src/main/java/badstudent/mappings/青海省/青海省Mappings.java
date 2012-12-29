package badstudent.mappings.青海省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.青海省.西宁市.西宁市Mappings;

public class 青海省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("西宁市",new 西宁市Mappings());


    }

}
