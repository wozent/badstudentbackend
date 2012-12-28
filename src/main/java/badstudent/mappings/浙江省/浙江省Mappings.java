package badstudent.mappings.浙江省;

import badstudent.mappings.MappingBase;
import badstudent.mappings. 浙江省.杭州市.杭州市Mappings;
import badstudent.mappings. 浙江省.金华市.金华市Mappings;
import badstudent.mappings. 浙江省.宁波市.宁波市Mappings;
import badstudent.mappings. 浙江省.温州市.温州市Mappings;

public class 浙江省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("杭州市",new 杭州市Mappings());
        subAreaMappings.put("金华市",new 金华市Mappings());
        subAreaMappings.put("宁波市",new 宁波市Mappings());
        subAreaMappings.put("温州市",new 温州市Mappings());


    }

}
