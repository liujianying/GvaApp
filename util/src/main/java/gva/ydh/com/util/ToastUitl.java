package gva.ydh.com.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * @防止Toast多次点击 一直显示问题
 * Created by liujianying on 14-9-12.
 */
public class ToastUitl {

    private static int r_string;
    private static String ToastContent = "";
    private static Toast mToast = null;



    public static void showToast(final Context context,final String text) {


        try {
            if(!TextUtils.isEmpty(text)) {
                if (mToast == null) {
                    ToastUitl.ToastContent = text;
                    mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {

                    mToast.setText(text);
                    if ((ToastContent != null) && (!ToastContent.equals(text))) {
                        ToastUitl.ToastContent = text;
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    }
                }
                mToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(final Context context, final int text) {
        try {
                if (mToast == null) {
                    ToastUitl.r_string = text;
                    mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {

                    mToast.setText(text);
                    if (text != r_string) {
                        ToastUitl.r_string = text;
                        mToast.setDuration(Toast.LENGTH_SHORT);
                }
            }
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



//        private static Handler handler = new Handler(Looper.getMainLooper());
//
//        private static Toast toast = null;
//
//        private static Object synObj = new Object();
//
//        public static void showToast(final Context act, final String msg) {
//            showMessage(act, msg, Toast.LENGTH_SHORT);
//        }
//
//        public static void showToast(final Context act, final int msg) {
//            showMessage(act, msg, Toast.LENGTH_SHORT);
//        }
//
//        public static void showMessage(final Context act, final String msg,
//                                       final int len) {
//            new Thread(new Runnable() {
//                public void run() {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            synchronized (synObj) {
//                                if (toast != null) {
//                                    toast.cancel();
//                                    toast.setText(msg);
//                                    toast.setDuration(len);
//                                } else {
//                                    toast = Toast.makeText(act, msg, len);
//                                }
//                                toast.show();
//                            }
//                        }
//                    });
//                }
//            }).start();
//        }
//
//
//        public static void showMessage(final Context act, final int msg,
//                                       final int len) {
//            new Thread(new Runnable() {
//                public void run() {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            synchronized (synObj) {
//                                if (toast != null) {
//                                    toast.cancel();
//                                    toast.setText(msg);
//                                    toast.setDuration(len);
//                                } else {
//                                    toast = Toast.makeText(act, msg, len);
//                                }
//                                toast.show();
//                            }
//                        }
//                    });
//                }
//            }).start();
//        }

}
