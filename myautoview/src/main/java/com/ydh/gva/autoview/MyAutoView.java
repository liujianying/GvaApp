package com.ydh.gva.autoview;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.ydh.gva.base.widget.WLImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 自动滚动控件
 * 调用时需要   setDataForUrl()方法，传入要加载的数据
 * 当调用的Activity 结束或暂停时，需要调用   stopTimer() 来结束定时器
 * @author Administrator
 *
 */
public class  MyAutoView extends FrameLayout implements OnPageChangeListener{
	private MyViewPager viewPager;
	public PageIndicatorView pageView;
	private View view;
	private Context context;
	private ArrayList<String>  images_list;
	private int currentPage = 0;
	private Handler handler;
	private long time;
	private boolean isAutoRun;
	private OnImageViewClickListener listener;
	private MyPagerAdapter mPagerAdapter;
	private Timer timer;
	private final long Default_Time = 6000;
	private boolean timerIsRun;

	private int defaultBg = R.mipmap.bg_leshop_home_ad;

	public MyAutoView(Context context) {
		super(context);
		this.context = context;
		initLayout();
	}

	public MyAutoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initLayout();
	}

	public MyAutoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initLayout();
	}


	public void initLayout(){
		view =  LayoutInflater.from(this.context).inflate(R.layout.viewfillper_layout, null);
		this.addView(view);
		viewPager = (MyViewPager)view.findViewById(R.id.viewpager);
		ViewPagerScroller scroller = new ViewPagerScroller(context);
		scroller.setScrollDuration(2000);
		scroller.initViewPagerScroll(viewPager);
		viewPager.setAutoView(this);
		viewPager.setOnSingleTouchListener(new MyViewPager.OnSingleTouchListener() {

			@Override
			public void onSingleTouch() {
				if(listener != null)
					listener.OnItemClickListener(currentPage % images_list.size());
			}
		});
		pageView = (PageIndicatorView)view.findViewById(R.id.pageindex);
		images_list = new ArrayList<String>();
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				viewPager.setCurrentItem(currentPage);
				super.handleMessage(msg);
			}
		};
		isAutoRun = false;
		timerIsRun = false;
		time = Default_Time;
		timer = new Timer();
	}

	public Integer getCurrentPage(){
		return currentPage;
	}

	public void setCurrentPage(int currentPage){
		viewPager.setCurrentItem(currentPage);
		this.currentPage = currentPage;
	}
	/**
	 * 设置自动滚动控件的数据
	 * @param urls
	 */
	public void setDataForUrl(List<String> urls){
		if( null == urls){
			return;
		}
		images_list.clear();
		images_list.addAll(urls);
		creatViewPager();
	}



	/**
	 * 设置自动滚动控件的数据
	 * @param urls
	 * @param defaultBg 默认背景
	 */
	public void setDataForUrl(List<String> urls,int defaultBg){
		this.defaultBg = defaultBg;
		setDataForUrl(urls);
	}


	private void creatViewPager(){
		currentPage = 0;
		if(images_list.size() > 1){
			currentPage += 1000 * images_list.size();
		}

		pageView.setTotalPage(images_list.size());
		pageView.setCurrentPage(0);
		mPagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setCurrentItem(currentPage);
		viewPager.setOnPageChangeListener(this);

		if(getTimerIsRun()){
			stopTimer();
		}

		if(images_list.size() > 1 && isAutoRun){
			starTimer(time);
		}
	}


	class MyPagerAdapter extends PagerAdapter{
		List<WLImageView> mViewCache = new ArrayList<WLImageView>();

		@Override
		public int getCount() {
			if(images_list.size() > 1){
				return Integer.MAX_VALUE;
			}
			return images_list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object object) {
			return arg0 == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			if (object != null && object instanceof WLImageView) {
				WLImageView imageView = (WLImageView) object;
				container.removeView(imageView);
				mViewCache.add(imageView);
			}
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			WLImageView iv;
			if (mViewCache.size() > 0) {
				iv = mViewCache.remove(0);
			} else {
				iv = new WLImageView(context);
				iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				GenericDraweeHierarchy hierarchy = iv.getHierarchy();
				hierarchy.setPlaceholderImage(defaultBg);
				hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
				iv.setHierarchy(hierarchy);

			}

			iv.setImageURI(images_list.get(position % images_list.size()));
//			ImageLoaderUtil.loadImageWithodFade(images_list.get(position % images_list.size()), iv, defaultBg);

			iv.setTag(position);
			try {
				container.addView(iv);
			}catch (Exception e) {

			}

			return iv;
		}

	}

//    private boolean isAddView(ViewGroup container, View view) {
//        for(int i = 0; i < container.getChildCount(); i++) {
//            if(container.getChildAt(i) == view) {
//                MyToast.showToast(context, "已发目标");
//                return true;
//            }
//
//        }
//
//        return false;
//    }


	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		pageView.setCurrentPage(arg0 % images_list.size());
		currentPage = arg0;
	}

	/**
	 * @category 设置自动滚动的时间间隔
	 * @param time   时间
	 */
	public void setAutoRollTime(long time){
		if(time > 0 ){
			isAutoRun = true;
			this.time = time;
		}else{
			isAutoRun = false;
			this.time = Default_Time;
		}
	}

	public interface OnImageViewClickListener{
		public void OnItemClickListener(int position);
	}

	public void setOnImageClickListener(OnImageViewClickListener listener){
		this.listener = listener;
	}

	/**
	 * 开始 定时器
	 */
	public void starTimer(long time){
		if(!timerIsRun) {
			if(images_list != null && images_list.size() > 0) {
				timerIsRun = true;
				if (timer == null) {
					timer = new Timer();
				}
				if (time == -1) {
					time = this.time;
				}
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						currentPage++;
						if (currentPage > mPagerAdapter.getCount()) {
							currentPage = 0;
						}
						handler.sendEmptyMessage(0);
					}
				}, 5000, time);
			}
		}
	}

	public void starTimer(){
		starTimer(-1);
	}

	/**
	 * 停止定时器
	 */
	public void stopTimer(){
		timerIsRun = false;
		if(timer == null){
			return;
		}
		timer.cancel();
		timer = null;
	}


	/**
	 * 获取循环时间
	 * @return
	 */
	public long getRepetitionTime(){
		return time;
	}


	/**
	 * 获取定时器是否在运行
	 * @return
	 */
	public boolean getTimerIsRun(){
		return timerIsRun;
	}


	public WLImageView getImageViewByPosition(int position){
		WLImageView iv = (WLImageView)viewPager.findViewWithTag(position);
		if(iv != null){
			return  iv;
		}
		return  null;
//		if(imageView_list != null)
//			return imageView_list.get(position % imageView_list.size());
//		return  null;
	}


	/**
	 * 隐藏底部指示条
	 * linbing
	 */
	public void hideIndicatorView() {
		if(pageView != null) {
			pageView.setVisibility(View.INVISIBLE);
		}
	}
}
