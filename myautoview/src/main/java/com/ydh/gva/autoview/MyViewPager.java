package com.ydh.gva.autoview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	private PointF mDownP = new PointF();
	private PointF mCurrentP = new PointF();
	private MyAutoView autoView;
	OnSingleTouchListener mOnSingleTouchListener;
	private float min_distance = 30;
	private boolean isVertical = false;
	private boolean isHorizontal = false;
	
	// 滑动距离及坐标  
    private float xDistance, yDistance, xLast, yLast;  

	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return true;
	}
	
	/**
	 * 设置   自动滚动的控件
	 * @param autoView
	 */
	public  void setAutoView(MyAutoView autoView){
		this.autoView = autoView;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		mCurrentP.x = arg0.getX();
		mCurrentP.y = arg0.getY();
		
		if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
			isVertical = false;
			isHorizontal = false;
			xDistance = yDistance = 0f;  
            xLast = arg0.getX();  
            yLast = arg0.getY();  
            
			mDownP.x = arg0.getX();
			mDownP.y = arg0.getY();
			if(autoView != null){
				autoView.stopTimer();
			}
//			getParent().getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
		}

		if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
			 final float curX = arg0.getX();  
             final float curY = arg0.getY();  
               
             xDistance += Math.abs(curX - xLast);  
             yDistance += Math.abs(curY - yLast);  
             xLast = curX;  
             yLast = curY;  
             //linbing 
             //解决viewpager上下滑pulltorefreshscrollview，无法刷新兼容问题
             if(xDistance < yDistance && yDistance > 10){
            	 if(!isHorizontal) {
            		 isVertical = true;
                	 return false;  
            	 }
             }    
			float aMoveX = mDownP.x - mCurrentP.x;
//			getParent().getParent().getParent().getParent()
//			.getParent().requestDisallowInterceptTouchEvent(true);
			if (((getCurrentItem() + 1) == getChildCount() && aMoveX > 10)){
				isHorizontal = true;
				getParent().getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
			}
		}

		if (arg0.getAction() == MotionEvent.ACTION_UP) {
			if(isVertical) {
				return false;
			}
			if(autoView != null){
				autoView.starTimer(autoView.getRepetitionTime());
			}
//			if (mDownP.x == mCurrentP.x && mDownP.y == mCurrentP.y) {
//				onSingleTouch();
//				return true;
//			}
			if (Math.abs(mCurrentP.x - mDownP.x) <= min_distance && Math.abs(mCurrentP.y - mDownP.y) <= min_distance) {
				onSingleTouch();
				return true;
			}
		}

		return super.onTouchEvent(arg0);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	public void onSingleTouch() {
		if (mOnSingleTouchListener != null) {

			mOnSingleTouchListener.onSingleTouch();
		}
	}

	public interface OnSingleTouchListener {
		public void onSingleTouch();
	}

	public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
		this.mOnSingleTouchListener = onSingleTouchListener;
	}
}
