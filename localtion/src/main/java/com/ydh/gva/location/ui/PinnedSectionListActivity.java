package com.ydh.gva.location.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ydh.gva.base.SwipeActivity;
import com.ydh.gva.localtion.R;
import com.ydh.gva.location.adapter.CitySimpleAdatpter;
import com.ydh.gva.location.config.LocationModeConfig;
import com.ydh.gva.location.core.CharacterParser;
import com.ydh.gva.location.core.CityEntity;
import com.ydh.gva.location.localtion.AddressDBManager;
import com.ydh.gva.location.localtion.AddressHelper;
import com.ydh.gva.location.localtion.CommonStringUtils;
import com.ydh.gva.location.localtion.LocationControler;
import com.ydh.gva.location.localtion.LocationInfoBean;
import com.ydh.gva.location.localtion.UserCityCacher;
import com.ydh.gva.location.widget.PinnedSectionListView;
import com.ydh.gva.location.widget.SearchBarWidgetStyle2;
import com.ydh.gva.location.widget.SideBarCityView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import gva.ydh.com.loadview.LoadView;
import gva.ydh.com.util.AsyncTaskUtils;
import gva.ydh.com.util.Callable;
import gva.ydh.com.util.Callback;
import gva.ydh.com.util.InputMethodUtil;
import gva.ydh.com.util.SafetyUitl;


public class PinnedSectionListActivity extends SwipeActivity implements OnClickListener {

    private PinnedSectionListView pListView;
    private CitySimpleAdatpter adapter;
    private List<CityEntity> city_list = new ArrayList<CityEntity>();
    private SideBarCityView sideBar;
    private TextView dialog;
    private View hot_city;
    private GridView hot_city_list;
    private SearchBarWidgetStyle2 search_edit;
    private ImageButton btn_back_city;
    private final int locationSuccess = 1;
    private final int locationError = 2;
    private LinearLayout located_progess;
    private LinearLayout area_loc_layout;
    private TextView city_loc_txt;
    private Boolean locationFlag = false;
    private Boolean FilterFlag = false;
    private List<CityEntity> filterDateList;
    private InputMethodManager manager;
    private LoadView loadView;
    int region_id;
    private LocationControler.OnLocationResultCallBack locationControler;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.city_list);
        new AddressDBManager(this);

        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        ((TextView) $(R.id.title_id)).setText("城市选择");
        $(R.id.btn_back).setOnClickListener(this);
        $(R.id.btn_back).setVisibility(View.VISIBLE);
        loadView = (LoadView) findViewById(R.id.loadDataView);
        loadView.setLoadSucessView(null);
        loadView.show();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        sideBar = (SideBarCityView) findViewById(R.id.sidrbar);
        sideBar.falg = false;
        dialog = (TextView) findViewById(R.id.dialog);
        btn_back_city = (ImageButton) findViewById(R.id.btn_back);
        located_progess = (LinearLayout) findViewById(R.id.located_progess);
        area_loc_layout = (LinearLayout) findViewById(R.id.area_loc_layout);
        city_loc_txt = (TextView) findViewById(R.id.city_loc_txt);
        pListView = (PinnedSectionListView) findViewById(R.id.pinned_list);
        pListView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if ((getCurrentFocus() != null) && (getCurrentFocus().getWindowToken() != null)) {
                        manager.hideSoftInputFromWindow(getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
        search_edit = (SearchBarWidgetStyle2) findViewById(R.id.search_edit);
        adapter = new CitySimpleAdatpter(this, city_list);
        initializeHeaderAndFooter();
        pListView.setAdapter(adapter);
        btn_back_city.setOnClickListener(this);
        btn_back_city.setClickable(false);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBarCityView.OnTouchingLetterChangedListener() {

            public void onTouchingLetterChanged(String s) {

                // 该字母首次出现的位置
                if ("热".equals(s)) {
                    pListView.setSelection(0);
                    return;
                }

                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    position += 1;
                    pListView.setSelection(position);
                    return;
                }

            }
        });

        search_edit.mSearchEditText.setHint(R.string.search_hint);

        // 根据输入框输入值的改变来过滤搜索
        search_edit.mSearchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表

                if (TextUtils.isEmpty(s.toString())) {
                    hot_city_list.setVisibility(View.VISIBLE);
                } else {
                    hot_city_list.setVisibility(View.GONE);
                }
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pListView.setOnItemClickListener(new OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                CityEntity cy;
                if (!FilterFlag) {
                    if (city_list == null || city_list.size() == 0
                            || city_list.size() <= (arg2 - 1)) {
                        return;
                    }
                    cy = city_list.get(arg2 - 1);
                } else {
                    if (filterDateList == null || filterDateList.size() == 0
                            || filterDateList.size() <= (arg2 - 1)) {
                        return;
                    }
                    cy = filterDateList.get(arg2 - 1);
                }

                backCityid(cy.getRegion_name(), cy.getRegion_cd());
            }
        });


        area_loc_layout.setFocusable(false);
        area_loc_layout.setOnClickListener(this);
        ininDb();
    }


    public void ininDb() {

        locationControler = new LocationControler.OnLocationResultCallBack() {
            @Override
            public void OnLocationResultSuccess(LocationInfoBean locationInfoBean) {
                area_loc_layout.setFocusable(true);
                located_progess.setVisibility(View.GONE);
                region_id = SafetyUitl.tryInt(locationInfoBean.getAdCode());
                city_loc_txt.setText(locationInfoBean.getCityName());
                area_loc_layout.setClickable(true);
                locationFlag = true;
            }

            @Override
            public void OnLocationResultFailure(LocationInfoBean locationInfoBean) {
                located_progess.setVisibility(View.GONE);
                city_loc_txt.setText("定位失败");
            }
        };
        initLocation();
        AsyncTaskUtils.doAsync(null, new Callable<Object>() {
            @Override
            public AddressHelper call() throws Exception {

                AddressHelper ah = new AddressHelper(LocationModeConfig.Instance().context);
                List<CityEntity> city_lista = ah.getCityInfo();
                Collections.sort(city_lista, comparator);
                int size = city_lista.size();
                int mark = 0;
                for (int i = 0; i < size; i++) {
                    CityEntity cy = city_lista.get(i);
                    CityEntity ct = new CityEntity();
                    if (cy.fristLetter != mark) {
                        mark = cy.fristLetter;
                        ct.pinying = cy.pinying;
                        ct.fristLetter = cy.fristLetter;
                        ct.setRegion_name(cy.getRegion_name());
                        ct.setRegion_cd(cy.getRegion_cd());
                        ct.type = 1;
                        city_list.add(ct);
                    }
                    city_list.add(cy);
                }
                city_lista.clear();
                return null;
            }
        }, new Callback<Object>() {
            @Override
            public void onCallback(Object pCallbackValue) {
                refreshUi();
            }
        });
    }

    private void refreshUi() {
        adapter.notifyDataSetChanged();
        loadView.closed(LoadView.LoadResponse.Success);
        btn_back_city.setClickable(true);
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {

        FilterFlag = true;
        filterDateList = new ArrayList<CityEntity>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = city_list;
        } else {
            filterDateList.clear();
            for (CityEntity sortModel : city_list) {
                String name = sortModel.getRegion_name();

                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {

                    filterDateList.add(sortModel);

                }
            }
        }

        // 根据a-z进行排序
        // Collections.sort(filterDateList, pinyinComparator);
        Collections.sort(filterDateList, comparator);
        adapter.updateListView(filterDateList);
    }

    private void initializeHeaderAndFooter() {
        pListView.setAdapter(null);
        hot_city = View.inflate(this, R.layout.areas_header, null);
        pListView.addHeaderView(hot_city);
        hot_city_list = (GridView) hot_city.findViewById(R.id.city_hot_grid);
        hot_city_list.setSelector(new ColorDrawable(getResources().getColor(R.color.transparent)));
        final String[] hotcity = {"北京市", "上海市", "厦门市", "成都市", "天津市", "杭州市", "重庆市", "郑州市"};

        ArrayList<HashMap<String, String>> hotcitylist = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < hotcity.length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("hotcity", hotcity[i]);
            hotcitylist.add(map);
        }

        SimpleAdapter sa = new SimpleAdapter(this, // 上下文环境
                hotcitylist, // 数据源
                R.layout.areas_hot_item, // 内容布局
                new String[]{"hotcity"}, // 数据源的arrayName
                new int[]{R.id.city_hot_txt} // 装载数据的控件
        );
        hot_city_list.setAdapter(sa); // 与gridview绑定

        hot_city_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                for (int i = 0, size = city_list.size(); i < size; i++) {
                    CityEntity cl = city_list.get(i);
                    if (cl.getRegion_name().contains(hotcity[arg2])) {
                        region_id = cl.getRegion_cd();
                        break;
                    }


                    if ("全国".equals(hotcity[arg2])) {
                        region_id = 1;
                        break;
                    }
                }

                backCityid(hotcity[arg2], region_id);
            }
        });

    }


    /**
     * a-z排序
     */
    Comparator comparator = new Comparator<CityEntity>() {

        public int compare(CityEntity lhs, CityEntity rhs) {
            String a = lhs.pinying.substring(0, 1);
            String b = rhs.pinying.substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }

        }
    };

    @Override
    public void onClick(View id) {


        if (id.getId() == R.id.btn_back) {
            boolean fromLogo = getIntent().getBooleanExtra("fromLogo", false);
            if (fromLogo) {
                return;
            }
            InputMethodUtil.hiddenKeyboard(PinnedSectionListActivity.this);
            finishPinnedSectionListActivity(null, null);
            return;
        }

        if (id.getId() == R.id.area_loc_layout) {
            if (locationFlag) {
                backCityid(city_loc_txt.getText() + "", region_id);
            } else {
                initLocation();
            }
            return;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果需要用户设置城市，则不让返回
            boolean fromLogo = getIntent().getBooleanExtra("fromLogo", false);
            if (fromLogo) {
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param city    城市名
     * @param city_id id
     */
    private void backCityid(String city, int city_id) {
        Bundle bundle = new Bundle();
        bundle.putString("city", city);
        bundle.putInt("city_id", city_id);
        setResult(1, this.getIntent().putExtras(bundle));
        finishPinnedSectionListActivity(city, city_id + "");
    }


    private void initLocation() {

        located_progess.setVisibility(View.VISIBLE);
        city_loc_txt.setText("");


        LocationControler.getInstance().requestLocationImediately(locationControler);
    }

    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if ((getCurrentFocus() != null) && (getCurrentFocus().getWindowToken() != null)) {
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        LocationControler.getInstance().disableMyLocation();
        super.onDestroy();
    }


    public void finishPinnedSectionListActivity(String cityName, String cityId) {
        //更新缓存的城市信息
        if (!CommonStringUtils.isBlank(cityId) && !CommonStringUtils.isBlank(cityName)) {
            UserCityCacher.getCityCacher().setCityInfo(getApplicationContext(), cityId, cityName);
            UserCityCacher.getCityCacher().setRegionInfo(getApplicationContext(), null, null);
        }
        boolean fromLogo = getIntent().getBooleanExtra("fromLogo", false);
        if (fromLogo) {
            //当定位失败，从引导页进来，则返回到主页
            Bundle bundle = getIntent().getExtras();
//    		PageTool.gotoMainTabPage(PinnedSectionListActivity.this,true,bundle);
        }
        PinnedSectionListActivity.this.finish();
        overridePendingTransition(0, R.anim.push_bottom_out_activity);
    }


}
