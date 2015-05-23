package com.ydh.gva.location.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ydh.gva.localtion.R;
import com.ydh.gva.location.core.CityEntity;
import com.ydh.gva.location.localtion.AddressHelper;
import com.ydh.gva.location.ui.PinnedSectionListActivity;

import java.util.ArrayList;
import java.util.List;

import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 14/10/21.
 */
public class LeShopCityList extends BasePopupWindow implements PopupWindow.OnDismissListener {

    private GridView hot_city_list;
    private RelativeLayout city_hot_grid;
    private RelativeLayout popu_id;
    private TextView city_text;
    private TextView city_name;
    private List<CityEntity> city_lista = new ArrayList<CityEntity>();
    private String city_name_string;
    private int selection = 0;
    private AddressHelper ah;

    public LeShopCityList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LeShopCityList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeShopCityList(Context context) {
        super(context);
    }

    public LeShopCityList(Context context, int width, int height, RelativeLayout popu_id, TextView city_text) {

        super(context, LayoutInflater.from(context).inflate(R.layout.city_list_popuwindow, null), width, height);
        setOnDismissListener(this);
        refreshData(true);
    }

    public void initViewData() {
        city_lista.clear();
    }

    /**
     * 更新数据
     */
    public void refreshData(boolean setFlag) {
        initViewData();

        city_name = (TextView) findViewById(R.id.city_name);

//        sa = new LeShopCityListAdapter(mContext, city_lista, hot_city_list);
//
//        sa.setSelectedPosition(selection);

        if(setFlag) {
            city_text.setText(city_name_string);

            city_name.setText("当前城市:" + city_name_string);
        }

//        hot_city_list.setAdapter(sa); // 与gridview绑定

        hot_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

                  view.setSelected(true);
                  TextView textView = (TextView) view.findViewById(R.id.city_hot_txt);

                  int city_id_int = (Integer) textView.getTag();
                  if(arg2 == 0) {

                      city_name_string = ah.getNameById(city_id_int + "");
                      city_text.setText(city_name_string);
                      city_name.setText("当前城市:" + city_name_string);
                      selection = arg2;

                      popu_id.setVisibility(View.GONE);
                      dismiss();
                  } else {

                      city_text.setText(textView.getText().toString());
                      city_name.setText("当前城市:" + city_name_string + textView.getText().toString());
                      selection = arg2;

                      popu_id.setVisibility(View.GONE);
                      dismiss();
                  }



            }
        });
    }




    @Override
    public void initViews() {

        city_name = (TextView)mContentView.findViewById(R.id.city_name);
        city_hot_grid = (RelativeLayout) mContentView.findViewById(R.id.city_list_rl);
        hot_city_list = (GridView) mContentView.findViewById(R.id.city_hot_grid);
        hot_city_list.setSelector(new ColorDrawable(mContext.getResources().getColor(R.color.transparent)));

    }

    @Override
    public void initEvents() {
        city_hot_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLog.out("切换城市");
                Intent intent = new Intent();
                intent.setClass(mContext, PinnedSectionListActivity.class);
                AnimationUtil.setLayout(R.anim.push_bottom_in_activity, R.anim.push_bottom_out_activity);
                if (android.os.Build.VERSION.SDK_INT <19)intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ((Activity)mContext).startActivityForResult(intent, 0);
                dismiss();
                popu_id.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void init() {

    }


    @Override
    public void showAsDropDown(View anchor) {
        refreshData(false);
        popu_id.setVisibility(View.VISIBLE);
        super.showAsDropDown(anchor);

    }

    @Override
    public void onDismiss() {
        popu_id.setVisibility(View.GONE);
    }
}
