package badstudent.mappings.辽宁省.抚顺市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.辽宁省.抚顺市.抚顺区.抚顺区Mappings;

public class 抚顺市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("抚顺区",new 抚顺区Mappings());

    }

}
