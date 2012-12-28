package badstudent.mappings;

import badstudent.mappings.上海.上海Mappings;
import badstudent.mappings.北京.北京Mappings;
import badstudent.mappings.天津.天津Mappings;
import badstudent.mappings.江苏省.江苏省Mappings;
import badstudent.mappings.浙江省.浙江省Mappings;
import badstudent.mappings.重庆.重庆Mappings;

public class AllProvinceMappings extends MappingBase {

    private final String 黑龙江省 = "黑龙江省";
    private final String 吉林省 = "吉林省";
    private final String 辽宁省 = "辽宁省";
    private final String 山东省 = "山东省";
    private final String 山西省 = "山西省";
    private final String 陕西省 = "陕西省";
    private final String 河北省 = "河北省";
    private final String 河南省 = "河南省";
    private final String 湖北省 = "湖北省";
    private final String 湖南省 = "湖南省";
    private final String 海南省 = "海南省";
    private final String 江西省 = "江西省";
    private final String 广东省 = "广东省";
    private final String 广西省 = "广西省";
    private final String 云南省 = "云南省";
    private final String 贵州省 = "贵州省";
    private final String 四川省 = "四川省";
    private final String 内蒙古省 = "内蒙古省";
    private final String 宁夏省 = "宁夏省";
    private final String 甘肃省 = "甘肃省";
    private final String 青海省 = "青海省";
    private final String 西藏省 = "西藏省";
    private final String 新疆省 = "新疆省";
    private final String 安徽省 = "安徽省";
    private final String 福建省 = "福建省";

    @Override
    protected void initMappings() {
        subAreaMappings.put("上海", new 上海Mappings());
        subAreaMappings.put("北京", new 北京Mappings());
        subAreaMappings.put("天津", new 天津Mappings());
        subAreaMappings.put("江苏省", new 江苏省Mappings());
        subAreaMappings.put("浙江省", new 浙江省Mappings());
        subAreaMappings.put("重庆", new 重庆Mappings());

        //TODO:Missing Data. remove constants
    }

}
