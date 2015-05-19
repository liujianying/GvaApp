package com.ydh.gva.ui.Base;

import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by liujianying on 15/5/12.
 */
public class BaseFragment extends Fragment {

    public <T extends View> T $(int id) {
        return (T) getActivity().findViewById(id);
    }


    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

}
