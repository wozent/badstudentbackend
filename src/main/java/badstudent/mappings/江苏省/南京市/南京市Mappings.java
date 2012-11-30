package badstudent.mappings.江苏省.南京市;

import java.util.HashMap;

import badstudent.mappings.MappingBase;
import badstudent.mappings.江苏省.南京市.玄武区.玄武区Mappings;

public class 南京市Mappings extends MappingBase {
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


    @Override
    protected void initMappings() {
        subAreaMappings.put("玄武区",new 玄武区Mappings());
        //TODO:Missing data.Remove constants.
    }

}
