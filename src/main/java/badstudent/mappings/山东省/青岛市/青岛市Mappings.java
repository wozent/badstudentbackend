package badstudent.mappings.山东省.青岛市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.山东省.青岛市.青岛区.青岛区Mappings;

public class 青岛市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("青岛区",new 青岛区Mappings());

    }

}
