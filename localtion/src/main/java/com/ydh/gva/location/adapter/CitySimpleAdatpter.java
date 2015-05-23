package com.ydh.gva.location.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ydh.gva.localtion.R;
import com.ydh.gva.location.core.CityEntity;
import com.ydh.gva.location.widget.PinnedSectionListView;

import java.util.List;

public class CitySimpleAdatpter extends BaseAdapter implements
		PinnedSectionListView.PinnedSectionListAdapter {

	private Context context;
	private List<CityEntity> list;

	public CitySimpleAdatpter(Context context, List<CityEntity> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CityEntity item = list.get(position);
		ViewHoldera  ch1 = null;
		ViewHolderb  ch2 = null;

		if(convertView == null){
			if (item.type != 1) {
				ch1= new ViewHoldera();
				convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
				ch1.titile = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(ch1);
			} else {
				ch2 = new ViewHolderb();
				convertView = LayoutInflater.from(context).inflate(R.layout.city_group_item, parent, false);
				ch2.cotent =  (TextView)convertView.findViewById(R.id.city_group_name);
				convertView.setTag(ch2);
				
			}
		}else{
			if(item.type != 1){
				ch1 = (ViewHoldera) convertView.getTag();
			}else{
				ch2 = (ViewHolderb) convertView.getTag();
			}
		}

		if(item.type != 1){
			ch1.titile.setTag("" + position);
			ch1.titile.setText(item.getRegion_name());
		}else{
	
			ch2.cotent.setText(item.fristLetter + "");
		}
		
		return convertView;
	}


	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return list.get(position).type;
	}

	public boolean isItemViewTypePinned(int viewType) {
		return viewType == 1;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		
		for (int i = 0, j = getCount(); i < j; i++) {
			if (list.get(i).fristLetter == section) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<CityEntity> list) {

		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	
	private class ViewHoldera { 
		
		public TextView titile;
	}
	
	private class ViewHolderb { 
		
		public TextView  cotent;
	}
}
