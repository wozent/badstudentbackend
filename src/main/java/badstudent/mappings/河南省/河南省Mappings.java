package badstudent.mappings.河南省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.河南省.焦作市.焦作市Mappings;
import badstudent.mappings.河南省.开封市.开封市Mappings;
import badstudent.mappings.河南省.洛阳市.洛阳市Mappings;
import badstudent.mappings.河南省.新乡市.新乡市Mappings;
import badstudent.mappings.河南省.郑州市.郑州市Mappings;

public class 河南省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("焦作市",new 焦作市Mappings());
        subAreaMappings.put("开封市",new 开封市Mappings());
        subAreaMappings.put("洛阳市",new 洛阳市Mappings());
        subAreaMappings.put("新乡市",new 新乡市Mappings());
        subAreaMappings.put("郑州市",new 郑州市Mappings());


    }

}
