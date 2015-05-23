package com.ydh.gva.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ydh.gva.R;
import com.ydh.gva.base.BaseActivity;
import com.ydh.gva.config.Session;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.util.net.volley.VolleyError;

import gva.ydh.com.util.ToastUitl;

/**
 * Created by liujianying on 15/5/18.
 */
public class LogoActivity extends BaseActivity {

    private SimpleDraweeView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo);
        logo = $(R.id.logo);
//        button = $(R.id.button2);
//        logo.setImageURI(Uri.parse("http://h.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=a28b6779d1c8a786be7f42085739e505/adaf2edda3cc7cd90a84f35a3901213fb90e918c.jpg"));

        ImageView m = new ImageView(this);
        m.setScaleType(ImageView.ScaleType.CENTER);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSession();
//            }
//        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSession();
            }
        }, 100);

    }


    public void getSession() {
        Session.requestSession(new Session.SessionInterfaec() {
            @Override
            public void onSessionSuccess(YDHData response) {
                startMainActivity();
//                startActivity(new Intent(mContext, PinnedSectionListActivity.class));
                finish();
            }

            @Override
            public void onSessionFailure(VolleyError error) {
                startMainActivity();
                finish();
                ToastUitl.showToast(mContext, "Session 获取失败");
            }
        });
    }


    private void startMainActivity() {

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("isStartPintActivity", true);
        startActivity(intent);
    }


}
