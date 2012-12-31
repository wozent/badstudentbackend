package badstudent.mappings;

import badstudent.mappings.上海.上海Mappings;
import badstudent.mappings.云南省.云南省Mappings;
import badstudent.mappings.北京.北京Mappings;
import badstudent.mappings.四川省.四川省Mappings;
import badstudent.mappings.天津.天津Mappings;
import badstudent.mappings.宁夏回族自治区.宁夏回族自治区Mappings;
import badstudent.mappings.安徽省.安徽省Mappings;
import badstudent.mappings.山东省.山东省Mappings;
import badstudent.mappings.山西省.山西省Mappings;
import badstudent.mappings.广东省.广东省Mappings;
import badstudent.mappings.广西壮族自治区.广西壮族自治区Mappings;
import badstudent.mappings.新疆维吾尔自治区.新疆维吾尔自治区Mappings;
import badstudent.mappings.江苏省.江苏省Mappings;
import badstudent.mappings.江西省.江西省Mappings;
import badstudent.mappings.河北省.河北省Mappings;
import badstudent.mappings.河南省.河南省Mappings;
import badstudent.mappings.浙江省.浙江省Mappings;
import badstudent.mappings.海南省.海南省Mappings;
import badstudent.mappings.湖北省.湖北省Mappings;
import badstudent.mappings.湖南省.湖南省Mappings;
import badstudent.mappings.甘肃省.甘肃省Mappings;
import badstudent.mappings.福建省.福建省Mappings;
import badstudent.mappings.西藏自治区.西藏自治区Mappings;
import badstudent.mappings.贵州省.贵州省Mappings;
import badstudent.mappings.重庆.重庆Mappings;
import badstudent.mappings.陕西省.陕西省Mappings;
import badstudent.mappings.青海省.青海省Mappings;

public class AllProvinceMappings extends MappingBase {

    private final String 黑龙江省 = "黑龙江省";
    private final String 吉林省 = "吉林省";
    private final String 辽宁省 = "辽宁省";
    private final String 内蒙古省 = "内蒙古省";

    @Override
    protected void initMappings() {
        subAreaMappings.put("上海", new 上海Mappings());
        subAreaMappings.put("北京", new 北京Mappings());
        subAreaMappings.put("天津", new 天津Mappings());
        subAreaMappings.put("江苏省", new 江苏省Mappings());
        subAreaMappings.put("浙江省", new 浙江省Mappings());
        subAreaMappings.put("重庆", new 重庆Mappings());
        subAreaMappings.put("宁夏回族自治区", new 宁夏回族自治区Mappings());
        subAreaMappings.put("安徽省", new 安徽省Mappings());
        subAreaMappings.put("广东省", new 广东省Mappings());
        subAreaMappings.put("新疆维吾尔自治区", new 新疆维吾尔自治区Mappings());
        subAreaMappings.put("甘肃省", new 甘肃省Mappings());
        subAreaMappings.put("青海省", new 青海省Mappings());
        subAreaMappings.put("云南省", new 云南省Mappings());
        subAreaMappings.put("广西壮族自治区", new 广西壮族自治区Mappings());
        subAreaMappings.put("江西省", new 江西省Mappings());
        subAreaMappings.put("海南省", new 海南省Mappings());
        subAreaMappings.put("福建省", new 福建省Mappings());
        subAreaMappings.put("贵州省", new 贵州省Mappings());
        subAreaMappings.put("西藏自治区", new 西藏自治区Mappings());
        subAreaMappings.put("四川省", new 四川省Mappings());
        subAreaMappings.put("山东省", new 山东省Mappings());
        subAreaMappings.put("山西省", new 山西省Mappings());
        subAreaMappings.put("河北省", new 河北省Mappings());
        subAreaMappings.put("河南省", new 河南省Mappings());
        subAreaMappings.put("湖北省", new 湖北省Mappings());
        subAreaMappings.put("湖南省", new 湖南省Mappings());
        subAreaMappings.put("陕西省", new 陕西省Mappings());
        //TODO:Missing Data. remove constants
    }

}
