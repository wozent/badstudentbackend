package badstudent.mappings.安徽省.合肥市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.安徽省.合肥市.合肥大学城.合肥大学城Mappings;
import badstudent.mappings.安徽省.合肥市.合肥区.合肥区Mappings;

public class 合肥市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("合肥区",new 合肥区Mappings());
        subAreaMappings.put("合肥大学城",new 合肥大学城Mappings());

    }

}
