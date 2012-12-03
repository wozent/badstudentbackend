package badstudent.mappings.江苏省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.江苏省.南京市.南京市Mappings;

public class 江苏省Mappings extends MappingBase {
    private final String 常州市 = "常州市";
    private final String 淮安市 = "淮安市";
    private final String 连云港市 = "连云港市";
    private final String 南通市 = "南通市";
    private final String 宿迁市 = "宿迁市";
    private final String 苏州市 = "苏州市";
    private final String 泰州市 = "泰州市";
    private final String 无锡市 = "无锡市";
    private final String 徐州市 = "徐州市";
    private final String 盐城市 = "盐城市";
    private final String 扬州市 = "扬州市";
    private final String 镇江市 = "镇江市";


    @Override
    protected void initMappings() {
        subAreaMappings.put("南京市", new 南京市Mappings());
        //TODO:Missing data.Remove constants.
    }
    
    
    
}
