package badstudent.mappings.山东省.聊城市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.山东省.聊城市.聊城区.聊城区Mappings;

public class 聊城市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("聊城区",new 聊城区Mappings());

    }

}
