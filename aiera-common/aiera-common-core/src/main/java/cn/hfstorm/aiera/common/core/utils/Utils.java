package cn.hfstorm.aiera.common.core.utils;

/**
 * @author : hmy
 * @date : 2025/2/18
 */
public class Utils {

    /**
     * 字符串转换成布尔型，默认返回false
     *
     * @param strVal
     * @return
     */
    public static boolean str2bool(String strVal) {
        boolean boolVal = false;
        try {
            if (!"".equals(strVal)) {
                boolVal = Boolean.parseBoolean(strVal);
            }
        } catch (Exception e) {
        }
        return boolVal;
    }


    public static String null2String(String s) {
        return s == null ? "" : s;
    }

    public static String null2String(String s, String def) {
        return s == null ? (def == null ? "" : def) : s;

    }

    //s为null或""时候返回默认值
    public static String blank2String(String s, String def) {
        return (s == null || s.equals("")) ? (def == null ? "" : def) : s;
    }

    //s为null或""时候返回0
    public static String blank2Zero(String s) {
        return (s == null || s.equals("")) ? "0" : s;
    }


    public static String exception2String(Throwable e) {
        return e == null ? "" : e.getMessage();
    }
}
