package badstudent.mappings.贵州省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.贵州省.贵阳市.贵阳市Mappings;

public class 贵州省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("贵阳市",new 贵阳市Mappings());


    }

}
