package badstudent.mappings.辽宁省.锦州市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.辽宁省.锦州市.锦州区.锦州区Mappings;

public class 锦州市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("锦州区",new 锦州区Mappings());

    }

}
