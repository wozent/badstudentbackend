package badstudent.mappings.江苏省.南京市;

import badstudent.mappings.MappingBase;


public class nanjingCityMappings implements MappingBase {
    private final String 玄武区 = "玄武区";
    private final String 白下区 = "白下区";
    private final String 秦淮区 = "秦淮区";
    private final String 建邺区 = "建邺区";
    private final String 下关区 = "下关区";
    private final String 鼓楼区 = "鼓楼区";
    private final String 雨花台区 = "雨花台区";
    private final String 栖霞区 = "栖霞区";
    private final String 江宁区 = "江宁区";
    private final String 浦口区 = "浦口区";
    private final String 六合区 = "六合区";
    private final String 溧水县 = "溧水县";
    private final String 高淳县 = "高淳县";

    private final String[] AllRegion = {玄武区,白下区,秦淮区,建邺区,下关区,鼓楼区,雨花台区,栖霞区,江宁区,浦口区,六合区,溧水县,高淳县};

    public String[] getAllSubArea() {
        return AllRegion;
    }

    public MappingBase getSubAreaMappings(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
