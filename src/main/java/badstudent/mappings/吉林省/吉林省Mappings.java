package badstudent.mappings.吉林省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.吉林省.吉林市.吉林市Mappings;
import badstudent.mappings.吉林省.四平市.四平市Mappings;
import badstudent.mappings.吉林省.延吉市.延吉市Mappings;
import badstudent.mappings.吉林省.长春市.长春市Mappings;

public class 吉林省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("吉林市",new 吉林市Mappings());
        subAreaMappings.put("四平市",new 四平市Mappings());
        subAreaMappings.put("延吉市",new 延吉市Mappings());
        subAreaMappings.put("长春市",new 长春市Mappings());


    }

}