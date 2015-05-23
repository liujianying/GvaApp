package com.ydh.gva.location.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ydh.gva.localtion.R;


public class SideBarCityView extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字母
	public String[] b = { "热","A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#" };

//	private Bitmap search_ico_a;
//	private Bitmap search_ico_b;
	private int choose = -1;// 选中
	private Paint paint = new Paint();
	private boolean onTouchFlag = false;
	private TextView mTextDialog;
	public  boolean falg = true;
	
	
	
	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}


	public SideBarCityView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public SideBarCityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SideBarCityView(Context context) {
		super(context);
		init(context);
	}

	
	private void init(Context context){
//		search_ico_a = BitmapFactory.decodeResource(context.getResources(), R.drawable.search_icon_pressed);
//		search_ico_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.search_icon);
	}
	/**
	 * 重写这个方法
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		
		float singleHeight = height / (b.length);// 获取每一个字母的高度
		
		if(falg)singleHeight = height/(b.length - 1) - 0.6f;
		
		int i = 0;
		if(falg) i = 1;
		for (   ; i < b.length; i++) {
			if(onTouchFlag)
				paint.setColor(Color.parseColor("#ffffff"));
			else
				paint.setColor(Color.parseColor("#626262"));	
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.textSizeContentSmall));
			// 选中的状态
			if (i  == choose) {
				paint.setColor(Color.parseColor("#000000"));
			}
			
			paint.setFakeBoldText(true);
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * (i) + singleHeight;
			if(falg)yPos = singleHeight * (i - 1) + singleHeight;;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}
		
//		if(choose == 0){
//			canvas.drawBitmap(search_ico_a, width / 2 - search_ico_a.getWidth() / 2, singleHeight / 2, null);
//		}else{
//			canvas.drawBitmap(search_ico_b, width / 2 - search_ico_b.getWidth() / 2, singleHeight / 2, null);
//		}
		
//		if(choose == 0){
//			
//		}else{
//			
//		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		int c = (int) (y / getHeight() * (b.length));// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
		if(falg) c = (int) (y / getHeight() * (b.length - 1));

		switch (action) {
		case MotionEvent.ACTION_UP:
			
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			onTouchFlag = false;
			break;

		default:
			onTouchFlag = true;
			setBackgroundResource(R.drawable.sidebar_background);
			if (oldChoose != c) {
				
				
				if ((c >= 0) && (c < b.length)) {
					
					if(c == 0){
						
						if(falg)return true;
						
						if (listener != null) {
							listener.onTouchingLetterChanged("热");
						}
						
						if (mTextDialog != null) {
							mTextDialog.setText("热");
							mTextDialog.setVisibility(View.VISIBLE);
						}
					}else{
						
						
						if (listener != null) {
							listener.onTouchingLetterChanged(b[c]);
						}
						if (mTextDialog != null) {
							mTextDialog.setText(b[c]);
							mTextDialog.setVisibility(View.VISIBLE);
						}
					}
					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 向外公开的方法
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 * 
	 * @author coder
	 * 
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}