package gva.ydh.com;

import android.content.Context;

/**
 * Created by liujianying on 15/5/18.
 */
public class LocationManager {

    private static LocationManager lManager;
    private Context context;


    public static synchronized LocationManager getInstance(Context context) {
        if (lManager == null) {
            lManager = new LocationManager(context);
        }
        return lManager;
    }


    private LocationManager() {
    }

    private LocationManager(Context context) {
        this.context = context;
    }


}
