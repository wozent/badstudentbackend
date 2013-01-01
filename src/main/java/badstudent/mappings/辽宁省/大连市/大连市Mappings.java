package badstudent.mappings.辽宁省.大连市;

import badstudent.mappings.MappingBase;
import badstudent.mappings.辽宁省.大连市.大连区.大连区Mappings;
import badstudent.mappings.辽宁省.大连市.黑石礁凌水桥地区.黑石礁凌水桥地区Mappings;
import badstudent.mappings.辽宁省.大连市.西南路马兰广场.西南路马兰广场Mappings;
import badstudent.mappings.辽宁省.大连市.旅顺南路大学城.旅顺南路大学城Mappings;

public class 大连市Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("大连区",new 大连区Mappings());
        subAreaMappings.put("黑石礁凌水桥地区",new 黑石礁凌水桥地区Mappings());
        subAreaMappings.put("西南路马兰广场地区",new 西南路马兰广场Mappings());
        subAreaMappings.put("旅顺南路大学城",new 旅顺南路大学城Mappings());

    }

}
