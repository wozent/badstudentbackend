package badstudent.mappings.香港.香港特别行政区;

import badstudent.mappings.MappingBase;
import badstudent.mappings.香港.香港特别行政区.香港区.香港区Mappings;

public class 香港特别行政区Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("香港区",new 香港区Mappings());


    }

}
