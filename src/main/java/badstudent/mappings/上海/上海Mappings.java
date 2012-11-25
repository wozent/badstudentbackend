package badstudent.mappings.上海;


import badstudent.mappings.MappingBase;

public class 上海Mappings implements MappingBase{
    private final String 上海市 = "上海市";
    
    private final String[] AllCity = {上海市};

    public String[] getAllSubArea() {
        return AllCity;
    }

    public MappingBase getSubAreaMappings(String id) {
        //TODO:unimplemented
        return null;
    }


}
