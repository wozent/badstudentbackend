package badstudent.mappings;

import java.util.HashMap;

import badstudent.mappings.上海.ShanghaiProvinceMappings;
import badstudent.mappings.北京.BeijingProvinceMappings;
import badstudent.mappings.江苏省.JiangsuProvinceMappings;

public class AllProvinceMappings implements MappingBase {

    public final String beijing_province = "北京";
    public final String shanghai_province = "上海";
    public final String tianjing_province = "天津";
    public final String chongqing_province = "重庆";
    public final String heilongjiang_province = "黑龙江省";
    public final String jiling_province = "吉林省";
    public final String liaoning_province = "辽宁省";
    public final String shandong_province = "山东省";
    public final String shanxi_tone1_province = "山西省";
    public final String shanxi_tone3_province = "陕西省";
    public final String hebei_province = "河北省";
    public final String henan_province = "河南省";
    public final String hubei_province = "湖北省";
    public final String hunan_province = "湖南省";
    public final String hainan_province = "海南省";
    public final String jiangsu_province = "江苏省";
    public final String jiangxi_province = "江西省";
    public final String guangdong_province = "广东省";
    public final String guangxi_province = "广西省";
    public final String yunnan_province = "云南省";
    public final String guizhou_province = "贵州省";
    public final String sichuan_province = "四川省";
    public final String neimenggu_province = "内蒙古省";
    public final String ningxia_province = "宁夏省";
    public final String gansu_province = "甘肃省";
    public final String qinghai_province = "青海省";
    public final String xizang_province = "西藏省";
    public final String xingjiang_province = "新疆省";
    public final String anhui_province = "安徽省";
    public final String zhejiang_province = "浙江省";
    public final String fujian_province = "福建省";

    private final String[] Allprovince = { beijing_province, shanghai_province, tianjing_province, chongqing_province, heilongjiang_province, jiling_province, liaoning_province,
            shandong_province, shanxi_tone1_province, shanxi_tone3_province, hebei_province, hubei_province, hunan_province, hainan_province, jiangsu_province, jiangxi_province, guangdong_province,
            guangxi_province, yunnan_province, guizhou_province, sichuan_province, neimenggu_province, ningxia_province, gansu_province, qinghai_province, xizang_province, xingjiang_province,
            anhui_province, zhejiang_province, fujian_province };

    private final HashMap<String, MappingBase> provinceToCityMappings = new HashMap<String, MappingBase>() {
        private static final long serialVersionUID = 1L;
        {
            put(beijing_province, new BeijingProvinceMappings());
            put(shanghai_province,new ShanghaiProvinceMappings());
            put(jiangsu_province,new JiangsuProvinceMappings());
        }
    };

    public String[] getAllSubArea() {
        return Allprovince;
    }

    public MappingBase getSubAreaMappings(String id) {
        return provinceToCityMappings.get(id);
    }

}
