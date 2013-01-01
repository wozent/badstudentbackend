package badstudent.mappings.黑龙江省.鸡西市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.黑龙江省.鸡西市.鸡西区.鸡西区Mappings;

public class 鸡西市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("鸡西区",new 鸡西区Mappings());

    }

}
