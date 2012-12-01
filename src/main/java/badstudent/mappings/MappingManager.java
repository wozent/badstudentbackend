package badstudent.mappings;

import java.util.HashSet;
import java.util.Set;

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
        if (cityMapping == null)
            return null;
        Set<String> retVal = new HashSet<String>();
        for (String region : cityMapping.getAllSubArea()) {
            MappingBase regionMapping = cityMapping.getSubAreaMappings(region);
            if (regionMapping == null)
                continue;
            retVal.addAll(regionMapping.getAllSubArea());
        }
        return retVal;
    }

    public static boolean isLocationVaild(Location location) {
        String province = location.getProvince();
        String city = location.getCity();
        String region = location.getRegion();
        String school = location.getSchool();
        if (getProvinceMappings(province)!=null) {
            if (getCityMappings(province, city)!=null) {
                if (getRegionMappings(province, city, region)!=null) {
                    if (getAllSchools(province, city, region).contains(school)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
