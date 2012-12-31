package badstudent.mappings.贵州省.贵阳市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.贵州省.贵阳市.贵阳区.贵阳区Mappings;
import badstudent.mappings.贵州省.贵阳市.花溪大学城.花溪大学城Mappings;

public class 贵阳市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("贵阳区",new 贵阳区Mappings());
        subAreaMappings.put("花溪大学城",new 花溪大学城Mappings());

    }

}
