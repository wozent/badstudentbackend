package badstudent.mappings.河北省.石家庄市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.河北省.石家庄市.石家庄区.石家庄区Mappings;

public class 石家庄市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("石家庄区",new 石家庄区Mappings());

    }

}

