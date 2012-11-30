package badstudent.mappings;

import java.util.HashMap;
import java.util.Set;

public abstract class MappingBase {
    protected HashMap<String, MappingBase> subAreaMappings = new HashMap<String, MappingBase>();
    public Set<String> getAllSubArea(){
        return subAreaMappings.keySet();
    }
    public MappingBase getSubAreaMappings(String id){
        return subAreaMappings.get(id);
    }
    public MappingBase() {
        initMappings();
    }
    protected abstract void initMappings();
}
