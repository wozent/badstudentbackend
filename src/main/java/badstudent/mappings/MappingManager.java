package badstudent.mappings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MappingManager {
    
    private static MappingBase getProvinceMappings(String province){
        return (new AllProvinceMappings()).getSubAreaMappings(province);
    }
    
    private static MappingBase getCityMappings(String province,String city){
        MappingBase provinceMappings = getProvinceMappings(province);
        if(provinceMappings!=null){
            return provinceMappings.getSubAreaMappings(city);
        }else{
            return null;
        }
    }
    
    public static MappingBase getRegionMappings(String province,String city,String region){
        MappingBase cityMappings = getCityMappings(province, city);
        if(cityMappings!=null){
            return cityMappings.getSubAreaMappings(region);
        }else{
            return null;
        }
    }
    
    public static String[] getAllProvince(){
        return (new AllProvinceMappings()).getAllSubArea();
    }
    
    public static String[] getAllCity(String province){
        return getProvinceMappings(province).getAllSubArea();
    }
    
    
    /*
    public static String[] getAllRegion(String province,String city){
        MappingBase cityMappings = getCityMappings(province, city);
        if(cityMappings!=null){
            return cityMappings.getAllSubArea();
        }else{
            return null;
        }
    }*/
    
    public static List<String> getAllSchools(String province,String city){
        MappingBase cityMapping = getCityMappings(province, city);
        if(cityMapping==null) return null;
        ArrayList<String> retVal = new ArrayList<String>();
        for(String region: cityMapping.getAllSubArea()){
            MappingBase regionMapping = cityMapping.getSubAreaMappings(region);
            if(regionMapping==null) continue;
            Collections.addAll(retVal, regionMapping.getAllSubArea());
        }
        return retVal;
    }
    
}
