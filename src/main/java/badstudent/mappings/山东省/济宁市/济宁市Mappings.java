package badstudent.mappings.山东省.济宁市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.山东省.济宁市.济宁区.济宁区Mappings;

public class 济宁市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("济宁区",new 济宁区Mappings());

    }

}
