package badstudent.mappings.安徽省.淮南市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.安徽省.淮南市.淮南区.淮南区Mappings;

public class 淮南市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("淮南区",new 淮南区Mappings());

    }

}

