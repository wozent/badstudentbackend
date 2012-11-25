package badstudent.mappings.江苏省;

import java.util.HashMap;

import badstudent.mappings.MappingBase;
import badstudent.mappings.江苏省.南京市.南京市Mappings;

public class 江苏省Mappings implements MappingBase {
    private final String 南京市 = "南京市";
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

    private final String[] AllCity = { 南京市, 常州市, 淮安市, 连云港市, 南通市, 宿迁市, 苏州市, 泰州市, 无锡市, 徐州市, 盐城市, 扬州市, 镇江市 };

    private final HashMap<String, MappingBase> CityToRegionMappings = new HashMap<String, MappingBase>() {
        private static final long serialVersionUID = 1L;
        {
            put(南京市, new 南京市Mappings());
        }
    };
    
    public String[] getAllSubArea(){
        return AllCity;
    }

    public MappingBase getSubAreaMappings(String id) {
        return CityToRegionMappings.get(id);
    }
    
}
