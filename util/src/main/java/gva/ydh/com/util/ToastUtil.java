package gva.ydh.com.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by liujianying on 15/5/18.
 */
public class ToastUtil {

    private static String ToastContent = "";
    private static Toast mToast = null;

    public static void show(Context context, String text) {
        try {
            if (!TextUtils.isEmpty(text)) {
                if (mToast == null) {
                    ToastUtil.ToastContent = text;
                    mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {

                    mToast.setText(text);
                    if ((ToastContent != null) && (!ToastContent.equals(text))) {
                        ToastUtil.ToastContent = text;
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    }
                }
                mToast.show();
            }
        } catch (Exception e) {

        }
    }

}
