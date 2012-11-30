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
    protected abstract void initMappings();
}
