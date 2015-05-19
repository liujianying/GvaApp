package com.ydh.gva.ui.Base;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ydh.gva.R;

import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 15/4/24.
 */
public class BaseActivity extends AppActivity {

    protected final String Tag = this.getClass().getSimpleName();
    protected TextView toolbarTitle;
    protected Toolbar toolbar;
    protected Context mContext;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbarInit();
    }

    /**
     * @初始化Toolbar
     */
    private void toolbarInit() {
        this.mContext = this;
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


    @Override
    protected void onStart() {
        super.onStart();
        AppLog.D(Tag, "---------onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.D(Tag, "---------onResume ");
    }

    @Override
    protected void onStop() {
        super.onResume();
        AppLog.D(Tag, "---------onStop ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppLog.D(Tag, "---------onPause ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppLog.D(Tag, "---------onRestart ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLog.D(Tag, "---------onDestroy ");
    }


}
