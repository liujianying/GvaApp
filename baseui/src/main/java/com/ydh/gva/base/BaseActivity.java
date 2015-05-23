package com.ydh.gva.base;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by liujianying on 15/4/24.
 */
public class BaseActivity extends AppActivity {

    protected TextView toolbarTitle;
    protected Toolbar toolbar;
    protected Context mContext;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);


        mContext = this;
//        toolbarInit();
    }

    /**
     * @初始化Toolbar
     */
    private void toolbarInit() {
        View v = $(R.id.id_toolbar);
        if (v != null) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            toolbarTitle = $(v, R.id.toolbar_title);
            if (toolbarTitle != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }


}
