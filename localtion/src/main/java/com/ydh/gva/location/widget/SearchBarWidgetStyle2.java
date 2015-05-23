package com.ydh.gva.location.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ydh.gva.localtion.R;


public class SearchBarWidgetStyle2 extends LinearLayout {

	private LinearLayout ll_search_bg;
	private ImageView mSearchRightImageView;
	private ImageView iv_line;
	private ImageView clearinput;
	public  EditText mSearchEditText;
	private InputMethodManager imm;
	private OnSearchListener mOnSearchListener = null;
	private final int DEFAULTWIDTH=90;



	public interface OnSearchListener {
		public void onSearchChange(String search);
		public void onSearchCancel();
	}

	public SearchBarWidgetStyle2(Context context) {
		super(context);
		viewInit(context);
		
		
	}

	public SearchBarWidgetStyle2(Context context, AttributeSet attrs) {
		super(context, attrs);
		viewInit(context);
	}
	

	/*** 界面初始界面 **/
	private void viewInit(Context context) {
		inflate(context, R.layout.bar_search_layout_two, this);
//		mSearchCancelLayout = (LinearLayout) findViewById(R.id.search_cancel_layout);
		clearinput = (ImageView) findViewById(R.id.btn_clear_input);
		mSearchRightImageView = (ImageView) findViewById(R.id.search_right);
		iv_line = (ImageView) findViewById(R.id.iv_line);
		mSearchEditText = (EditText) findViewById(R.id.search_text);
		ll_search_bg = (LinearLayout) findViewById(R.id.ll_search_bg);

		clearinput.setVisibility(View.GONE);
		
		imm = (InputMethodManager)mSearchEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mSearchEditText, 0);
		
		logicInit();
	}

	public enum SearchViewType{
		/** 陌生人*/
		NormalStyle,
		NewCloseStyle,

	}

	SearchViewType currentType=SearchViewType.NormalStyle;
	/**
	 * 设置搜索新样式
	 * @param viewType
	 */
	public void setSearchInputStyle(SearchViewType viewType){
		currentType=viewType;
		switch (viewType){
			case NewCloseStyle:
				ll_search_bg.setBackgroundColor(0x00000000);
				mSearchEditText.clearComposingText();
				mSearchEditText.setPadding(0,0,0,0);
				mSearchEditText.setCompoundDrawables(null, null, null, null);
				mSearchEditText.clearComposingText();
				clearinput.setBackgroundResource(R.mipmap.icon_search_delete);
				clearinput.setVisibility(VISIBLE);
				iv_line.setVisibility(VISIBLE);
				break;
		}
	}

	/*** 逻辑初始面 **/
	private void logicInit() {
		if(null!=clearinput){
			clearinput.setOnClickListener(mSearchCancelClickListener);
		}

		if (mSearchEditText != null) {
			mSearchEditText.setOnTouchListener(mSearchEditTextOnClickListener);
			mSearchEditText.addTextChangedListener(mSearchTextWatcher);
		}
		setTextEditable(false);
	}

	/** 取消键点击事件处理 **/
	private OnClickListener mSearchCancelClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id=v.getId();
            if(id==R.id.btn_clear_input){
            	mSearchEditText.setText("");
				if( null != mOnSearchListener)
					mOnSearchListener.onSearchCancel();
            }else{
//            	if (mSearchCancelLayout != null	&& mSearchCancelLayout.getVisibility() == View.VISIBLE) {
//            		setSearchBarState(LAYOUT_STATE_VIEW);
//            	}
            	if(imm!=null&&mSearchEditText.getWindowToken()!=null)
					imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				if( null != mOnSearchListener)
            	 	mOnSearchListener.onSearchCancel();
            }
		}
	};

	/** EditText Touch事件处理 **/
	private OnTouchListener mSearchEditTextOnClickListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
//			if (mSearchCancelLayout != null
//					&& mSearchCancelLayout.getVisibility() != View.VISIBLE) {
//				setSearchBarState(LAYOUT_STATE_EDIT);
//			}
			return false;
		}
	};

	/** 搜索条文字变化监听器 ***/
	private TextWatcher mSearchTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,int count) {

			String filter = s.toString();
			if(filter != null && filter.length() > 0){
				clearinput.setVisibility(View.VISIBLE);
			}else{
				if(currentType == SearchViewType.NewCloseStyle){

				}else{
					clearinput.setVisibility(View.GONE);
				}
			}
			if (mOnSearchListener != null) {
				mOnSearchListener.onSearchChange(filter);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	/***
	 * 设置搜索框是否可以编辑
	 * 
	 * @param isEditable
	 */
	private void setTextEditable(boolean isEditable) {
		if (isEditable) {
//			mSearchEditText.setFocusableInTouchMode(true);
//			mSearchEditText.setFocusable(true);
//			mSearchEditText.requestFocus();
		} else {
//			mSearchEditText.clearFocus();
//			mSearchEditText.setFocusable(false);
		}
	}

	public static final int LAYOUT_STATE_VIEW = 1;
	public static final int LAYOUT_STATE_EDIT = 2;
	public static final int LAYOUT_STATE_EDIT_NO_CANCEL = 3;

	/**
	 * 设置搜索条的状态
	 * 
	 * @param state
	 */
	public void setSearchBarState(int state) {
//		int width = mSearchCancelLayout.getWidth();
//		if(width==0){
//			mSearchCancelLayout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//			width = mSearchCancelLayout.getMeasuredWidth();
//			if(width == 0)width = DEFAULTWIDTH;
//		}
		switch (state) {
		case LAYOUT_STATE_VIEW:
//			if(mSearchCancelLayout.getVisibility()==View.GONE){
//				return;
//			}
			mSearchEditText.setText("");
//			mSearchCancelLayout.startAnimation(getButtonTranslateAnimation(false, width));
//			mSearchRightImageView.startAnimation(getImageTranslateAnimation(false, width));
			setTextEditable(false);
//			mSearchCancelLayout.setVisibility(View.GONE);
			break;
		case LAYOUT_STATE_EDIT:
//			if(mSearchCancelLayout.getVisibility()==View.VISIBLE){
//				return;
//			}
//			mSearchCancelLayout.startAnimation(getButtonTranslateAnimation(true, width));
//			mSearchRightImageView.startAnimation(getImageTranslateAnimation(true, width));
			setTextEditable(true);
//			mSearchCancelLayout.setVisibility(View.VISIBLE);
			break;
		case LAYOUT_STATE_EDIT_NO_CANCEL:
			setTextEditable(true);
//			mSearchCancelLayout.setVisibility(View.GONE);
//			mSearchCancelLayout=null;
		    break;
		default:
			break;
		}
	}
	
	public void setMSearchEditTextHint(String hintText){
		
		if( null != mSearchEditText){
			mSearchEditText.setHint(hintText);
		}
	}
	
	
	public void hideInputStatus(){
		
		if(imm != null && mSearchEditText.getWindowToken() != null){
			imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);	
		}
	}

	/*** 取消按�位移�� **/
	private TranslateAnimation getButtonTranslateAnimation(boolean in,
			int distance) {
		TranslateAnimation animation = null;
		if (in) {
			animation = new TranslateAnimation(distance, 0, 0, 0);
		} else {
			animation = new TranslateAnimation(0, distance, 0, 0);
		}
		animation.setDuration(300);
		animation.setFillAfter(true);
		return animation;
	}

	/*** 背景存图位移动画 **/
	private TranslateAnimation getImageTranslateAnimation(boolean in,int distance) {
		TranslateAnimation animation = null;
		if (in) {
			animation = new TranslateAnimation(0, -distance, 0, 0);
		} else {
			animation = new TranslateAnimation(-distance, 0, 0, 0);
		}
		animation.setDuration(300);
		animation.setFillAfter(true);
		return animation;
	}

	public void setOnSearchListener(OnSearchListener listener) {
		if (listener != null) {
			mOnSearchListener = listener;
		}
	}
	
	public void clearEditText(){
		if(mSearchEditText!=null){
			mSearchEditText.setText("");
		}
//		if(mSearchCancelLayout.getVisibility()!=View.GONE){
//			mSearchCancelLayout.setVisibility(View.GONE);
//		}
	}
	
	/**
	 * 获取输入的内容
	 * @Title: getInputContent 
	 * @Description: 获取输入的内容
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public String getInputContent(){
		return mSearchEditText.getText().toString();
	}
	
}
