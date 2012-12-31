package badstudent.mappings.湖南省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.湖南省.衡阳市.衡阳市Mappings;
import badstudent.mappings.湖南省.吉首市.吉首市Mappings;
import badstudent.mappings.湖南省.湘潭市.湘潭市Mappings;
import badstudent.mappings.湖南省.长沙市.长沙市Mappings;
import badstudent.mappings.湖南省.株洲市.株洲市Mappings;

public class 湖南省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("衡阳市",new 衡阳市Mappings());
        subAreaMappings.put("吉首市",new 吉首市Mappings());
        subAreaMappings.put("湘潭市",new 湘潭市Mappings());
        subAreaMappings.put("长沙市",new 长沙市Mappings());
        subAreaMappings.put("株洲市",new 株洲市Mappings());


    }

}
