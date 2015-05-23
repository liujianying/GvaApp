package com.ydh.gva.autoview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import gva.ydh.com.util.ActivityUtil;

public class PageIndicatorView extends View {
	private int mCurrentPage = -1;
	private int mTotalPage = 0;
	private int radius  = 6;//圆点的半径
	private int radius_unSelected  = 6;//圆点的半径

	public PageIndicatorView(Context context) {
		super(context);
		radius= ActivityUtil.dp2px(2.5f);
		radius_unSelected=ActivityUtil.dp2px(2.5f);
	}
	
	public PageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		radius=ActivityUtil.dp2px(2.5f);
		radius_unSelected=ActivityUtil.dp2px(2.5f);
	}

	public void setTotalPage(int nPageNum) {
		mTotalPage = nPageNum;
		if (mCurrentPage >= mTotalPage)
			mCurrentPage = mTotalPage - 1;
	}

	public int getCurrentPage() {
		return mCurrentPage;
	}

	public void setCurrentPage(int nPageIndex) {
		if (nPageIndex < 0 || nPageIndex >= mTotalPage)
			return;

		if (mCurrentPage != nPageIndex) {
			mCurrentPage = nPageIndex;
			this.invalidate();
		}
	}
	
	public void setRadius(int radius){
		this.radius = radius;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Rect r = new Rect();
		this.getDrawingRect(r);

		int space = 12;

		int x = (r.width() - (radius * 2 * mTotalPage + space * (mTotalPage - 1))) / 2;
		int y = (r.height() - radius * 2) / 2;
		Paint imagePaint = new Paint();
		for (int i = 0; i < mTotalPage; i++) {
			imagePaint.setAntiAlias(true);
			if (i == mCurrentPage) {
				imagePaint.setColor(Color.WHITE);
				canvas.drawCircle(x + radius, y + radius, radius, imagePaint);
			}else{
				imagePaint.setColor(Color.GRAY);
				canvas.drawCircle(x + radius, y + radius, radius_unSelected, imagePaint);
			}
			x += radius*2 + space;
		}

	}


}
