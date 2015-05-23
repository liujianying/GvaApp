package com.ydh.gva.location.popuwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * PopupWindow 的基类
* @ClassName: BasePopupWindow  
* @Description:  
* @author xiezw  
* @date 2013-11-13 上午11:27:47  
*
 */
public abstract class BasePopupWindow extends PopupWindow {

	protected View mContentView;
	protected Context mContext;
	protected onSubmitClickListener mOnSubmitClickListener;

	public BasePopupWindow() {
		super();
	}

//	public BasePopupWindow(Context context, AttributeSet attrs,int defStyleAttr, int defStyleRes) {
//			
//		super(context, attrs, defStyleAttr, defStyleRes);
//	}

	public BasePopupWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BasePopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BasePopupWindow(Context context) {
		super(context);
	}

	public BasePopupWindow(int width, int height) {
		super(width, height);
	}

	public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
			
		super(contentView, width, height, focusable);
	}


	public BasePopupWindow(Context context, View contentView, int width, int height) {
		super(contentView, width, height, true);
		mContentView = contentView;
		mContext = context;
		setBackgroundDrawable(new BitmapDrawable());
		initViews();
		initEvents();
		init();
	}
	
	
//	public BasePopupWindow(Context context, int layout, int width, int height) {
//		super(LayoutInflater.from(context).inflate(layout, null), width, height, true);
//		mContentView = LayoutInflater.from(context).inflate(layout, null);
//		setBackgroundDrawable(new BitmapDrawable());
//		initViews(context);
//		initEvents();
//		init();
//	}
	
	public BasePopupWindow(View contentView) {
		super(contentView);
	}

	public abstract void initViews();

	public abstract void initEvents();

	public abstract void init();

	public View findViewById(int id) {
		return mContentView.findViewById(id);
	}

	/**
	 * 显示在parent的上部并水平居中
	 * 
	 * @param parent
	 */
	public void showViewTopCenter(View parent) {
		showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	public void showViewTopCenter(View parent, int x,int y) {
		showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, x, y);
	}

	/**
	 * 显示在parent的中心
	 * 
	 * @param parent
	 */
	public void showViewCenter(View parent) {
		showAtLocation(parent, Gravity.CENTER, 0, 0);
	}

	/**
	 * 添加确认单击监听
	 * 
	 * @param l
	 */
	public void setOnSubmitClickListener(onSubmitClickListener l) {
		mOnSubmitClickListener = l;
	}

	public interface onSubmitClickListener {
		void onClick();
	}

}
