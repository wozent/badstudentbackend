package badstudent.mappings.湖北省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.湖北省.荆州市.荆州市Mappings;
import badstudent.mappings.湖北省.宜昌市.宜昌市Mappings;
import badstudent.mappings.湖北省.武汉市.武汉市Mappings;

public class 湖北省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("荆州市",new 荆州市Mappings());
        subAreaMappings.put("宜昌市",new 宜昌市Mappings());
        subAreaMappings.put("武汉市",new 武汉市Mappings());


    }

}
