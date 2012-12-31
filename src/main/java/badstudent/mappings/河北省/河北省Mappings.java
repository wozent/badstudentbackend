package badstudent.mappings.河北省;

import badstudent.mappings.MappingBase;
import badstudent.mappings.河北省.保定市.保定市Mappings;
import badstudent.mappings.河北省.邯郸市.邯郸市Mappings;
import badstudent.mappings.河北省.秦皇岛市.秦皇岛市Mappings;
import badstudent.mappings.河北省.石家庄市.石家庄市Mappings;
import badstudent.mappings.河北省.唐山市.唐山市Mappings;

public class 河北省Mappings extends MappingBase {

    @Override
    protected void initMappings() {
        subAreaMappings.put("保定市",new 保定市Mappings());
        subAreaMappings.put("邯郸市",new 邯郸市Mappings());
        subAreaMappings.put("秦皇岛市",new 秦皇岛市Mappings());
        subAreaMappings.put("石家庄市",new 石家庄市Mappings());
        subAreaMappings.put("唐山市",new 唐山市Mappings());


    }

}
