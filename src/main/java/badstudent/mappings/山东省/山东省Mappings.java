package badstudent.mappings.山东省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.山东省.济南市.济南市Mappings;
import badstudent.mappings.山东省.济宁市.济宁市Mappings;
import badstudent.mappings.山东省.聊城市.聊城市Mappings;
import badstudent.mappings.山东省.青岛市.青岛市Mappings;
import badstudent.mappings.山东省.泰安市.泰安市Mappings;
import badstudent.mappings.山东省.烟台市.烟台市Mappings;
import badstudent.mappings.山东省.淄博市.淄博市Mappings;

public class 山东省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("济南市",new 济南市Mappings());
        subAreaMappings.put("济宁市",new 济宁市Mappings());
        subAreaMappings.put("聊城市",new 聊城市Mappings());
        subAreaMappings.put("青岛市",new 青岛市Mappings());
        subAreaMappings.put("泰安市",new 泰安市Mappings());
        subAreaMappings.put("烟台市",new 烟台市Mappings());
        subAreaMappings.put("淄博市",new 淄博市Mappings());


    }

}