package gva.ydh.com.util;
/**
 * @
 * @微乐统一Log 工具类
 */
public class AppLog {

    public final static String Tag = "WL";

    public static boolean isLog = true;

    /**
     * @param Tag
     * @param errorString
     */
    public static void E(String Tag, String errorString) {

        if (isLog)
            android.util.Log.e(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void E(String errorString) {

        if (isLog)
            android.util.Log.e(Tag, errorString);
    }

    /**
     * @param Tag
     * @param errorString
     */
    public static void e(String Tag, String errorString) {

        if (isLog)
            android.util.Log.e(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void e(String errorString) {

        if (isLog)
            android.util.Log.e(Tag, errorString);
    }


    /**
     * @param Tag
     * @param errorString
     */
    public static void I(String Tag, String errorString) {

        if (isLog)
            android.util.Log.i(Tag, errorString);
    }



    /**
     * @param errorString
     */
    public static void I(String errorString) {

        if (isLog)
            android.util.Log.i(Tag, errorString);
    }

    /**
     * @param Tag
     * @param errorString
     */
    public static void i(String Tag, String errorString) {

        if (isLog)
            android.util.Log.i(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void i(String errorString) {

        if (isLog)
            android.util.Log.i(Tag, errorString);
    }

    /**
     * @param Tag
     * @param errorString
     */
    public static void D(String Tag, String errorString) {

        if (isLog)
            android.util.Log.d(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void D(String errorString) {

        if (isLog)
            android.util.Log.d(Tag, errorString);
    }

    /**
     * @param Tag
     * @param errorString
     */
    public static void d(String Tag, String errorString) {

        if (isLog)
            android.util.Log.d(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void d(String errorString) {

        if (isLog)
            android.util.Log.d(Tag, errorString);
    }


    /**
     * @param Tag
     * @param errorString
     */
    public static void W(String Tag, String errorString) {

        if (isLog)
            android.util.Log.w(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void W(String errorString) {

        if (isLog)
            android.util.Log.w(Tag, errorString);
    }


    /**
     * @param Tag
     * @param errorString
     */
    public static void w(String Tag, String errorString) {

        if (isLog)
            android.util.Log.w(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void w(String errorString) {

        if (isLog)
            android.util.Log.w(Tag, errorString);
    }


    /**
     * @param Tag
     * @param errorString
     */
    public static void V(String Tag, String errorString) {

        if (isLog)
            android.util.Log.v(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void V(String errorString) {

        if (isLog)
            android.util.Log.v(Tag, errorString);
    }

    /**
     * @param Tag
     * @param errorString
     */
    public static void v(String Tag, String errorString) {

        if (isLog)
            android.util.Log.v(Tag, errorString);
    }

    /**
     * @param errorString
     */
    public static void v(String errorString) {

        if (isLog)
            android.util.Log.v(Tag, errorString);
    }


    /**
     * @param string
     */
    public static void out(String string) {
        if (isLog)
            System.out.println(string);
    }

    /**
     * @param object
     */
    public static void out(Object object) {

        if (isLog)
            System.out.println(object);
    }


}
