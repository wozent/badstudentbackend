package badstudent.mappings;

import java.util.HashMap;

import badstudent.mappings.上海.上海Mappings;
import badstudent.mappings.北京.北京Mappings;
import badstudent.mappings.江苏省.江苏省Mappings;

public class AllProvinceMappings implements MappingBase {

    public final String 北京 = "北京";
    public final String 上海 = "上海";
    public final String 天津 = "天津";
    public final String 重庆 = "重庆";
    public final String 黑龙江省 = "黑龙江省";
    public final String 吉林省 = "吉林省";
    public final String 辽宁省 = "辽宁省";
    public final String 山东省 = "山东省";
    public final String 山西省 = "山西省";
    public final String 陕西省 = "陕西省";
    public final String 河北省 = "河北省";
    public final String 河南省 = "河南省";
    public final String 湖北省 = "湖北省";
    public final String 湖南省 = "湖南省";
    public final String 海南省 = "海南省";
    public final String 江苏省 = "江苏省";
    public final String 江西省 = "江西省";
    public final String 广东省 = "广东省";
    public final String 广西省 = "广西省";
    public final String 云南省 = "云南省";
    public final String 贵州省 = "贵州省";
    public final String 四川省 = "四川省";
    public final String 内蒙古省 = "内蒙古省";
    public final String 宁夏省 = "宁夏省";
    public final String 甘肃省 = "甘肃省";
    public final String 青海省 = "青海省";
    public final String 西藏省 = "西藏省";
    public final String 新疆省 = "新疆省";
    public final String 安徽省 = "安徽省";
    public final String 浙江省 = "浙江省";
    public final String 福建省 = "福建省";

    private final String[] Allprovince = { 北京, 上海, 天津, 重庆, 黑龙江省, 吉林省, 辽宁省, 山东省, 山西省, 陕西省, 河北省, 湖北省, 湖南省, 海南省, 江苏省, 江西省, 广东省, 广西省, 云南省, 贵州省, 四川省, 内蒙古省, 宁夏省, 甘肃省, 青海省, 西藏省, 新疆省, 安徽省, 浙江省, 福建省 };

    private final HashMap<String, MappingBase> provinceToCityMappings = new HashMap<String, MappingBase>() {
        private static final long serialVersionUID = 1L;
        {
            put(北京, new 北京Mappings());
            put(上海, new 上海Mappings());
            put(江苏省, new 江苏省Mappings());
        }
    };

    public String[] getAllSubArea() {
        return Allprovince;
    }

    public MappingBase getSubAreaMappings(String id) {
        return provinceToCityMappings.get(id);
    }

}
