package badstudent.mappings.江苏省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.江苏省.常州市.常州市Mappings;
import badstudent.mappings.江苏省.南京市.南京市Mappings;
import badstudent.mappings.江苏省.南通市.南通市Mappings;
import badstudent.mappings.江苏省.苏州市.苏州市Mappings;
import badstudent.mappings.江苏省.无锡市.无锡市Mappings;
import badstudent.mappings.江苏省.徐州市.徐州市Mappings;
import badstudent.mappings.江苏省.扬州市.扬州市Mappings;
import badstudent.mappings.江苏省.镇江市.镇江市Mappings;

public class 江苏省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("常州市",new 常州市Mappings());
        subAreaMappings.put("南京市",new 南京市Mappings());
        subAreaMappings.put("南通市",new 南通市Mappings());
        subAreaMappings.put("苏州市",new 苏州市Mappings());
        subAreaMappings.put("无锡市",new 无锡市Mappings());
        subAreaMappings.put("徐州市",new 徐州市Mappings());
        subAreaMappings.put("扬州市",new 扬州市Mappings());
        subAreaMappings.put("镇江市",new 镇江市Mappings());


    }

}
