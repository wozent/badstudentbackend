package badstudent.mappings.内蒙古自治区;

import badstudent.mappings.MappingBase;
import badstudent.mappings.内蒙古自治区.包头市.包头市Mappings;
import badstudent.mappings.内蒙古自治区.呼和浩特市.呼和浩特市Mappings;
import badstudent.mappings.内蒙古自治区.临河市.临河市Mappings;
import badstudent.mappings.内蒙古自治区.通辽市.通辽市Mappings;

public class 内蒙古自治区Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("包头市",new 包头市Mappings());
        subAreaMappings.put("呼和浩特市",new 呼和浩特市Mappings());
        subAreaMappings.put("临河市",new 临河市Mappings());
        subAreaMappings.put("通辽市",new 通辽市Mappings());


    }

}