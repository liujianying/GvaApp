package com.ydh.gva.ui.Fresments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.common.internal.Lists;
import com.ydh.gva.R;
import com.ydh.gva.autoview.MyAutoView;
import com.ydh.gva.base.BaseFragment;
import com.ydh.gva.core.BannerEntity;
import com.ydh.gva.core.BisEntity;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.location.localtion.UserCityCacher;
import com.ydh.gva.location.ui.PinnedSectionListActivity;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.Test11;
import com.ydh.gva.ui.activitys.HomeCfg;
import com.ydh.gva.util.net.url.HttpRequestUrl;
import com.ydh.gva.util.net.volley.FastjsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.ToastUitl;

/**
 * Created by liujianying on 15/5/12.
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {

    private final static int CITY_CODE = 1;
    private View contentView;
    private TextView city_text;
    private TextView city_name;
    private int region_cd = 0;
    private List<BisEntity> bisEntity;
    private List<BannerEntity> bannerEntity;
    private List<HomeCfg> homeCfgs;
    private MyAutoView homePage_autoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.home_page_fragment, null);
        startActivityForResult(new Intent(getActivity(), PinnedSectionListActivity.class), 0);
        city_text = $(contentView, R.id.city_text);
        city_name = $(contentView, R.id.city_name);
        homePage_autoView = $(contentView, R.id.homePage_autoView);
        $(contentView, R.id.rl_city).setOnClickListener(this);
        $(contentView, R.id.button2).setOnClickListener(this);
        getHomeConfig();
        return contentView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

       	/* 用户选择城市的回调 */
        Bundle bundle = data.getExtras();
        if (CITY_CODE == resultCode && bundle != null) {
            String cityName = bundle.getString("city");
            city_text.setText(cityName);
            int r_cd = bundle.getInt("city_id");

            if (region_cd != r_cd) {
                region_cd = r_cd;
                getCityData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @获取城市列表
     */
    private void getCityData() {

        String region_cd_string = ((region_cd / 100)) * 100 + "";
        FastjsonRequest stringRequest = new FastjsonRequest<>(Request.Method.POST, HttpRequestUrl.Instance().bisList(region_cd_string),
                YDHData.class, new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                bisEntity = JSON.parseArray(response.getData(), BisEntity.class);
                refreshUi();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }

    /**
     * @获取首页广告
     */
    private void getBannerList() {
        FastjsonRequest stringRequest = new FastjsonRequest<>(Request.Method.POST, HttpRequestUrl.Instance().banner(bisEntity.get(0).getBusinessId()),
                YDHData.class, new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                bannerEntity = JSON.parseArray(response.getData(), BannerEntity.class);
                setBannerUi();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }

    /**
     * @获取首页配置
     */
    private void getHomeConfig() {
        FastjsonRequest stringRequest = new FastjsonRequest<>(Request.Method.POST, HttpRequestUrl.Instance().homeCfg(),
                YDHData.class, new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                homeCfgs = JSON.parseArray(response.getData(), HomeCfg.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }




    private void setBannerUi() {
        List<String> list = new ArrayList<>();
        for (BannerEntity bEntity : bannerEntity) {
            list.add(bEntity.getMoreImg());
        }
        homePage_autoView.setDataForUrl(list,
                com.ydh.gva.autoview.R.mipmap.bg_leshop_home_ad);
    }

    public void refreshUi() {
        if (bisEntity == null && bisEntity.size() == 0) {
            ToastUitl.showToast(mContext, "暂无该城市信息");
            return;
        }
        city_name.setText(bisEntity.get(0).getBisName());
        getBannerList();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_city:
                startActivityForResult(new Intent(getActivity(), PinnedSectionListActivity.class), 0);
                break;
            case R.id.button2:
                getActivity().startActivity(new Intent(getActivity(), Test11.class));

                break;
        }
    }
}
