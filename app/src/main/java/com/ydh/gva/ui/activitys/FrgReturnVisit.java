package com.ydh.gva.ui.activitys;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ydh.gva.R;
import com.ydh.gva.ui.Base.BaseFragment;
import com.ydh.gva.ui.Test11;

import java.lang.reflect.Field;

/**
 * <dl>
 * <dt>FrgReference.java</dt>
 * <dd>Description:患教---随访---微访</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company:</dd>
 * <dd>CreateDate: 2014-11-18 上午10:19:23</dd>
 * </dl>
 * 
 * @author lihs
 */
public class FrgReturnVisit extends BaseFragment {

	private static final String TAG = FrgReturnVisit.class.getName();

	private View view;
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.main1, null);

		view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getActivity(), Test11.class));
			}
		});
		return view;
	}



}
