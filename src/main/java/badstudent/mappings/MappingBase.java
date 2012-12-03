package badstudent.mappings;

import java.util.HashMap;
import java.util.Set;

public abstract class MappingBase {
    protected HashMap<String, MappingBase> subAreaMappings = new HashMap<String, MappingBase>();
    protected Set<String> getAllSubArea(){
        return subAreaMappings.keySet();
    }
    protected MappingBase getSubAreaMappings(String id){
        return subAreaMappings.get(id);
    }
    protected MappingBase() {
        initMappings();
    }
    protected String getAreaName(){
        String className = getClass().getSimpleName();
        return className.substring(0,className.length()-8);
    }
    protected abstract void initMappings();
}
