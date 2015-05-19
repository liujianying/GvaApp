package com.ydh.gva.ui.activitys;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.ydh.gva.R;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;
import com.ydh.gva.util.net.volley.toolbox.StringRequest;
import com.ydh.gva.ui.Base.BaseActivity;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.Fresments.HomePageFragment;
import com.ydh.gva.ui.Fresments.MallPageFragment;
import com.ydh.gva.ui.Fresments.MorePageFragment;
import com.ydh.gva.ui.Fresments.PersonalCenterPageFragment;
import com.ydh.gva.util.system.ActivityUtil;

/**
 * Created by liujianying on 15/5/12.
 */
public class HomepageActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    protected RadioButton rd_1, rd_3, rd_4, rd_0;
    private FragmentTabHost tabHost;
    public static final String KEY_TAB = "tab";
    public static final String TAB_MAIN = "tab_main"; //主页
    public static final String TAB_SHOP = "tab_shop"; //乐商主页
    public static final String TAB_CARD_PACK = "tab_cardpack"; //卡包主页
    public static final String TAB_MORE = "tab_more"; //更多


    public int clickButtonId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        tabHost = $(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.getTabWidget().setVisibility(View.GONE);	//隐藏系统的TabWidget



        tabHost.addTab(tabHost.newTabSpec(TAB_MAIN).setIndicator("首页"),
                HomePageFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec(TAB_SHOP).setIndicator("乐商"),
                MallPageFragment.class, null);


        tabHost.addTab(tabHost.newTabSpec(TAB_CARD_PACK).setIndicator("卡包"),
                MorePageFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec(TAB_MORE).setIndicator("我的"),
                PersonalCenterPageFragment.class, null);

        tabHost.setCurrentTabByTag(TAB_MAIN);

        rd_0 = $(R.id.radio_button0);
        rd_0.setOnCheckedChangeListener(this);
        rd_1 = $(R.id.radio_button1);
        rd_1.setOnCheckedChangeListener(this);
        rd_3 = $(R.id.radio_button3);
        rd_3.setOnCheckedChangeListener(this);
        rd_4 = $(R.id.radio_button4);
        rd_4.setOnCheckedChangeListener(this);
        rd_0.setChecked(true);
        onCheckedChanged();


        setRadioButtonDrawable(rd_0, R.drawable.tab_button_home);
        setRadioButtonDrawable(rd_1, R.drawable.tab_button_shop);
        setRadioButtonDrawable(rd_3, R.drawable.tab_button_cardpack);
        setRadioButtonDrawable(rd_4, R.drawable.tab_button_more);


        StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        GvaApp.requestQueue.add(stringRequest);

    }

    private void setRadioButtonDrawable(RadioButton radioButton, int drawable) {
        Drawable myImage = ResourcesCompat.getDrawable(getResources(), drawable, null);
        myImage.setBounds(0, 0, ActivityUtil.dp2px(30), ActivityUtil.dp2px(30));
        radioButton.setCompoundDrawables(null, myImage, null, null);
    }


    public void onCheckedChanged() {

        FragmentManager fm = getSupportFragmentManager();
        HomePageFragment index = (HomePageFragment) fm.findFragmentByTag(TAB_MAIN);
        MallPageFragment monitor = (MallPageFragment) fm.findFragmentByTag(TAB_SHOP);
        MorePageFragment expert = (MorePageFragment) fm.findFragmentByTag(TAB_CARD_PACK);
        PersonalCenterPageFragment query = (PersonalCenterPageFragment) fm.findFragmentByTag(TAB_MORE);

        FragmentTransaction ft = fm.beginTransaction();

        if (index != null)
            ft.detach(index);
        if (monitor != null)
            ft.detach(monitor);
        if (expert != null)
            ft.detach(expert);
        if (query != null)
            ft.detach(query);

    }



    private void selectDefaultTab() {
        String tab = getIntent().getStringExtra(KEY_TAB);
        if (TAB_MAIN.equals(tab)) {
            rd_0.performClick();
        } else if (TAB_SHOP.equals(tab)) {
            rd_1.performClick();
        } else if (TAB_CARD_PACK.equals(tab)) {
            rd_3.performClick();
        } else if (TAB_MORE.equals(tab)) {
            rd_4.performClick();
        }
    }






    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        selectDefaultTab();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            rd_0.setTextColor(getResources().getColor(R.color.lightgray));
            rd_1.setTextColor(getResources().getColor(R.color.lightgray));
            rd_3.setTextColor(getResources().getColor(R.color.lightgray));
            rd_4.setTextColor(getResources().getColor(R.color.lightgray));
            switch (buttonView.getId()) {
                case R.id.radio_button0:
                    rd_0.setTextColor(getResources().getColor(R.color.title_bg));
                    tabHost.setCurrentTabByTag("tab_main");
                    break;
                case R.id.radio_button1:
                    rd_1.setTextColor(getResources().getColor(R.color.title_bg));
                    tabHost.setCurrentTabByTag("tab_shop");
                    break;
                case R.id.radio_button3:
                    clickButtonId = R.id.radio_button3;
                    break;
                case R.id.radio_button4:
                    rd_4.setTextColor(getResources().getColor(R.color.title_bg));
                    tabHost.setCurrentTabByTag("tab_more");
                    break;
                default:
                    break;
            }

        }

    }




    @Override
    public void onBackPressed() {
    }



    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
