package badstudent.mappings.北京;


import badstudent.mappings.MappingBase;

public class 北京Mappings implements MappingBase {
    private final String 北京市 = "北京市";
    
    private final String[] AllCity = {北京市};
    
    public String[] getAllSubArea(){
        return AllCity;
    }

    public MappingBase getSubAreaMappings(String id) {
        //TODO:unimplemented
        return null;
    }
            
}
