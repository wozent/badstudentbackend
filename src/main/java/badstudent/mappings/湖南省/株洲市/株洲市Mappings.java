package badstudent.mappings.湖南省.株洲市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.湖南省.株洲市.株洲区.株洲区Mappings;

public class 株洲市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("株洲区",new 株洲区Mappings());

    }

}
