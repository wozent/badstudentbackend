package badstudent.mappings.湖南省.长沙市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.湖南省.长沙市.长沙区.长沙区Mappings;

public class 长沙市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("长沙区",new 长沙区Mappings());

    }

}
