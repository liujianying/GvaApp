package gva.ydh.com.ui;

import android.app.Activity;
import android.os.Bundle;

import gva.ydh.com.localtion.LocationControlers;

/**
 * Created by liujianying on 15/5/18.
 */
public class MainLocation extends Activity {

    LocationControlers lc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_location);

        lc = LocationControlers.getInstance(this);
        lc.startLocation();
    }


    @Override
    protected void onPause() {
        super.onPause();
        lc.stopLocation();
    }
}
