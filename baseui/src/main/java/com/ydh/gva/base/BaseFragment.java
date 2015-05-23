package com.ydh.gva.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liujianying on 15/5/12.
 */
public class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    protected Context mContext;

    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
