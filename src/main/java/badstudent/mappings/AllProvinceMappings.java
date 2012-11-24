package badstudent.mappings;

import java.util.HashMap;

import badstudent.mappings.上海.ShanghaiProvinceMappings;
import badstudent.mappings.北京.BeijingProvinceMappings;
import badstudent.mappings.江苏省.JiangsuProvinceMappings;

public class AllProvinceMappings {

    public static final String beijing_province = "北京";
    public static final String shanghai_province = "上海";
    public static final String tianjing_province = "天津";
    public static final String chongqing_province = "重庆";
    public static final String heilongjiang_province = "黑龙江省";
    public static final String jiling_province = "吉林省";
    public static final String liaoning_province = "辽宁省";
    public static final String shandong_province = "山东省";
    public static final String shanxi_tone1_province = "山西省";
    public static final String shanxi_tone3_province = "陕西省";
    public static final String hebei_province = "河北省";
    public static final String henan_province = "河南省";
    public static final String hubei_province = "湖北省";
    public static final String hunan_province = "湖南省";
    public static final String hainan_province = "海南省";
    public static final String jiangsu_province = "江苏省";
    public static final String jiangxi_province = "江西省";
    public static final String guangdong_province = "广东省";
    public static final String guangxi_province = "广西省";
    public static final String yunnan_province = "云南省";
    public static final String guizhou_province = "贵州省";
    public static final String sichuan_province = "四川省";
    public static final String neimenggu_province = "内蒙古省";
    public static final String ningxia_province = "宁夏省";
    public static final String gansu_province = "甘肃省";
    public static final String qinghai_province = "青海省";
    public static final String xizang_province = "西藏省";
    public static final String xingjiang_province = "新疆省";
    public static final String anhui_province = "安徽省";
    public static final String zhejiang_province = "浙江省";
    public static final String fujian_province = "福建省";

    public static final String[] Allprovince = { beijing_province, shanghai_province, tianjing_province, chongqing_province, heilongjiang_province, jiling_province, liaoning_province,
            shandong_province, shanxi_tone1_province, shanxi_tone3_province, hebei_province, hubei_province, hunan_province, hainan_province, jiangsu_province, jiangxi_province, guangdong_province,
            guangxi_province, yunnan_province, guizhou_province, sichuan_province, neimenggu_province, ningxia_province, gansu_province, qinghai_province, xizang_province, xingjiang_province,
            anhui_province, zhejiang_province, fujian_province };

    public static final HashMap<String, MappingBase> provinceToCityMappings = new HashMap<String, MappingBase>() {
        private static final long serialVersionUID = 1L;
        {
            put(beijing_province, new BeijingProvinceMappings());
            put(shanghai_province,new ShanghaiProvinceMappings());
            put(jiangsu_province,new JiangsuProvinceMappings());
        }
    };

}
