package gva.ydh.com.loadview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by liujianying on 15/5/20.
 */
public class LoadView extends FrameLayout{
    private Context context;
    private View view;

    private RelativeLayout rl_loadLayout;
    private ImageView iv_loading;
    private TextView loading_text;

    private LinearLayout ll_responseLayout;
    private ImageView iv_response;
    private TextView tv_response;
    private OnResponseImageClickListener listener;

    private View showView;
    private View customView;
    private boolean isShow = false;



    public enum LoadResponse{
        Success,
        Fail,
        NoNetWork,
        NoData,
        Custom
    }


    public LoadView(Context context) {
        super(context);
        this.context = context;
        initLayout();
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLayout();
    }

    public LoadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initLayout();
    }


    private void initLayout(){
        view = LayoutInflater.from(this.context).inflate(R.layout.weile_loading_layout, null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT, Gravity.CENTER);
        addView(view,lp);

        rl_loadLayout = (RelativeLayout)view.findViewById(R.id.rl_loadLayout);
        iv_loading = (ImageView)view.findViewById(R.id.iv_loading);
        loading_text = (TextView)view.findViewById(R.id.loading_text);

        ll_responseLayout = (LinearLayout)view.findViewById(R.id.ll_responseLayout);
        iv_response = (ImageView)view.findViewById(R.id.iv_response);
        tv_response = (TextView)view.findViewById(R.id.tv_response);

    }

    public void setText(String text){
        if(loading_text != null){
            loading_text.setText(text);
        }
    }

    public void setTextSize(int size){
        if(loading_text != null){
            loading_text.setTextSize(size);
        }
    }

    public void setText(String text,int colorID){
        if(loading_text != null){
            loading_text.setText(text);
            if(colorID != 0){
                loading_text.setTextColor(getResources().getColor(colorID));
            }
        }
    }

    /** 设置加载成功后显示的view */
    public void setLoadSucessView(View view){
        this.showView = view;
    }

    /**
     * 设置加载失败时候显示的动画
     * @param drawableId
     */
    public void setLoadFailViewImage(int drawableId){
        iv_response.setImageResource(drawableId);
    }

    /** 设置加载自定义的加载完成界面  */
    public void setCustomView(View view ){
        customView = view;
        ViewGroup.LayoutParams layoutParams =  new ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        ((FrameLayout)this.view).addView(customView, layoutParams);

        if(listener != null){
            customView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClickListener();
                }
            });
        }
    }


    /**
     * 显示加载动画
     */
    public void show(){
        rl_loadLayout.setVisibility(View.VISIBLE);
        ((AnimationDrawable)iv_loading.getDrawable()).start();

        if(showView != null)
            showView.setVisibility(View.GONE);
        if(customView != null)
            customView.setVisibility(View.GONE);
        ll_responseLayout.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
        isShow = true;
    }




    public void closed(LoadResponse response){
        ((AnimationDrawable)iv_loading.getDrawable()).stop();
        if(response == LoadResponse.Success){
            setVisibility(View.GONE);
            if(showView != null)
                showView.setVisibility(View.VISIBLE);
        }else if(response == LoadResponse.Fail){
            setVisibility(View.VISIBLE);
            tv_response.setText("加载失败,请重试!");
            rl_loadLayout.setVisibility(View.GONE);
            ll_responseLayout.setVisibility(View.VISIBLE);
            if(showView != null)
                showView.setVisibility(View.GONE);
        }else if(response == LoadResponse.NoNetWork){
            setVisibility(View.VISIBLE);
            tv_response.setText("网络异常,请重试!");
            rl_loadLayout.setVisibility(View.GONE);
            ll_responseLayout.setVisibility(View.VISIBLE);
            if(showView != null)
                showView.setVisibility(View.GONE);
        }else if(response == LoadResponse.NoData){
            setVisibility(View.VISIBLE);
            tv_response.setText("亲,没有您想要的数据!");
            rl_loadLayout.setVisibility(View.GONE);
            ll_responseLayout.setVisibility(View.VISIBLE);
            if(showView != null)
                showView.setVisibility(View.GONE);
        }else if(response == LoadResponse.Custom){
            if(customView != null){
                customView.setVisibility(View.VISIBLE);
            }
            if(showView != null)
                showView.setVisibility(View.GONE);
            rl_loadLayout.setVisibility(GONE);
        }
        isShow = false;
    }

    /**是否显示 */
    public boolean isShow(){
        return isShow;
    }

    /**添加加载结果点击事件*/
    public void setOnResponseImageClickListener(OnResponseImageClickListener l){
        this.listener = l;
        if(customView != null){
            customView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClickListener();
                }
            });
        }
        else if(iv_response != null){
            iv_response.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.OnClickListener();
                }
            });
        }

    }

    public interface OnResponseImageClickListener{
        public void OnClickListener();
    }



}
