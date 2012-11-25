package badstudent.mappings.江苏省.南京市.玄武区;

import badstudent.mappings.MappingBase;

public class 玄武区Mappings implements MappingBase {
    
    private final String 东南大学 = "东南大学";
    private final String 南京林业大学 = "南京林业大学";
    private final String 南京财经大学紫金校区 = "南京财经大学紫金校区";
    private final String 南京师范大学紫金校区 = "南京师范大学紫金校区";
    private final String 南京农业大学 = "南京农业大学";
    
    private final String[] AllSchool = {东南大学,南京林业大学,南京财经大学紫金校区,南京师范大学紫金校区,南京农业大学};

    public String[] getAllSubArea() {
        return AllSchool;
    }

    public MappingBase getSubAreaMappings(String id) {
        return null;
    }

}
