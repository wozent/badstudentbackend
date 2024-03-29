package badstudent.mappings;

import java.util.HashSet;
import java.util.Set;

import badstudent.common.Common;
import badstudent.model.Location;

public class MappingManager {

    private static MappingBase getProvinceMappings(String province) {
        return (new AllProvinceMappings()).getSubAreaMappings(province);
    }

    private static MappingBase getCityMappings(String province, String city) {
        MappingBase provinceMappings = getProvinceMappings(province);
        if (provinceMappings != null) {
            return provinceMappings.getSubAreaMappings(city);
        } else {
            return null;
        }
    }

    private static MappingBase getRegionMappings(String province, String city, String region) {
        MappingBase cityMappings = getCityMappings(province, city);
        if (cityMappings != null) {
            return cityMappings.getSubAreaMappings(region);
        } else {
            return null;
        }
    }

    public static Set<String> getAllProvince() {
        return (new AllProvinceMappings()).getAllSubArea();
    }

    public static Set<String> getAllCity(String province) {
        MappingBase provinceMappings = getProvinceMappings(province);
        if (provinceMappings != null) {
            return provinceMappings.getAllSubArea();
        } else {
            return null;
        }
    }

    public static Set<String> getAllRegion(String province, String city) {
        MappingBase cityMappings = getCityMappings(province, city);
        if (cityMappings != null) {
            return cityMappings.getAllSubArea();
        } else {
            return null;
        }
    }

    // Searching purpose
    public static Set<String> getAllSchools(String province, String city, String region) {
        MappingBase regionMappings = getRegionMappings(province, city, region);
        if (regionMappings != null) {
            return regionMappings.getAllSubArea();
        } else {
            return null;
        }
    }

    // populating purpose
    public static Set<String> getAllSchools(String province, String city) {
        MappingBase cityMapping = getCityMappings(province, city);
        if (cityMapping == null){
            return null;
        }
        Set<String> retVal = new HashSet<String>();
        for (String region : cityMapping.getAllSubArea()) {
            MappingBase regionMapping = cityMapping.getSubAreaMappings(region);
            if (regionMapping != null){
                retVal.addAll(regionMapping.getAllSubArea());
            }
        }
        return retVal;
    }

    public static boolean isLocationVaild(Location location) {
        String province = location.getProvince();
        String city = location.getCity();
        String school = location.getSchool();
        
        MappingBase provinceMappings = getProvinceMappings(province);
        if(provinceMappings!=null){
            MappingBase cityMappings = getCityMappings(province, city);
            if(cityMappings!=null){
                for(String region : getAllRegion(province, city)){
                    MappingBase regionMappings = getRegionMappings(province, city, region);
                    if(regionMappings.getAllSubArea().contains(school)){
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    public static String determineRegionFromLocation(Location location){
        if(location.getRegion()!="NULL"){
            Common.d("Location has already got region which is " + location.getRegion());
            return location.getRegion();
        }
        if(!isLocationVaild(location)){
            Common.d("Location is not vaild.");
            Common.d(""+location.getSchool());
            Common.d(""+location.getProvince());
            Common.d(""+location.getCity());
            return null;
        }
        for(String region : getAllRegion(location.getProvince(), location.getCity())){
            MappingBase regionMappings = getRegionMappings(location.getProvince(), location.getCity(), region);
            if(regionMappings.getAllSubArea().contains(location.getSchool())){
                return regionMappings.getAreaName();
            }
        }
        Common.d("Can not determine location.");
        return location.getRegion();
    }

}
