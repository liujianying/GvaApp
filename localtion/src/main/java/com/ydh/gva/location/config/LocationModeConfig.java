package com.ydh.gva.location.config;

import android.content.Context;

/**
 * Created by liujianying on 15/5/20.
 */
public class LocationModeConfig {

    public Context context;
    private static LocationModeConfig location;

    private LocationModeConfig() {}


    public static LocationModeConfig Instance() {

        if (null == location) {
            synchronized (LocationModeConfig.class) {
                if (null == location) {
                    location = new LocationModeConfig();
                }
            }
        }

        return location;
    }


    public void initialize(Context context) {
        this.context = context;
    }
}
