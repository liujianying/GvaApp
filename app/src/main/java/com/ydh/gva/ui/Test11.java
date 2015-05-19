package com.ydh.gva.ui;

import android.os.Bundle;
import android.widget.Button;

import com.ydh.gva.R;
import com.ydh.gva.ui.Base.SwipeActivity;

/**
 * Created by liujianying on 15/5/9.
 */
public class Test11 extends SwipeActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        button= (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TestAction testAction = new TestAction();
//                testAction.setStr("click");
////                BusProvider.getInstance().post(testAction);
//
//                EventBus.getDefault().post(testAction);
//            }
//        });
//
//
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Test test = new Test();
//                test.setTt("测试测试");
////                BusProvider.getInstance().post(test);
//                EventBus.getDefault().post(test);
//            }
//        });
    }
}
