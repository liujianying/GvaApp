package gva.ydh.com.util;

/**
 * @安全的数值类型转换
 * @author liujianying
 */
public class SafetyUitl {

    /**
     * 安全int类型转换 如果解析失败返回默认值0
     * @param str
     * @return
     */
    public static int tryInt(String str) {

        try {

            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param str
     * @param tryDefaultValue 类型转换异常  设默认值
     * @return
     */
    public static int tryInt(String str, int tryDefaultValue) {

        try {

            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tryDefaultValue;
    }

    /**
     *
     * @param str
     * @return
     */
    public static Integer tryInteger(String str) {

        try {

            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     *
     * @param str
     * @param tryDefaultValue
     * @return
     */
    public static Integer tryInteger(String str , Integer tryDefaultValue) {

        try {

            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tryDefaultValue;
    }

    /**
     *
     * @param str
     * @return
     */
    public static double trydouble(String str) {

        try {

            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    /**
     *
     * @param str
     * @param tryDefaultValue
     * @return
     */
    public static double trydouble(String str, double tryDefaultValue) {

        try {

            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tryDefaultValue;
    }

    /**
     *
     * @param str
     * @return
     */
    public static long trylong(String str) {

        try {

            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }
    /**
     *
     * @param str
     * @param tryDefaultValue
     * @return
     */
    public static long trylong(String str, long tryDefaultValue) {

        try {

            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tryDefaultValue;
    }
    /**
     *
     * @param str
     * @return
     */
    public static Long tryLong(String str) {

        try {

            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }
    /**
     *
     * @param str
     * @param tryDefaultValue
     * @return
     */
    public static Long tryLong(String str, long tryDefaultValue) {

        try {

            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tryDefaultValue;
    }

    public static Double addDouble(String str, String str2) {
        return trydouble(str)+trydouble(str2);
    }


}
