package com.ydh.gva.ui.Fresments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ydh.gva.R;
import com.ydh.gva.ui.Base.BaseFragment;
import com.ydh.gva.ui.Test11;

/**
 * Created by liujianying on 15/5/12.
 */
public class HomePageFragment extends BaseFragment {

    private View contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.home_page_fragment, null);

        $(contentView, R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Test11.class));
            }
        });
        return contentView;
    }





}
