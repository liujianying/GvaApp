package com.ydh.gva.location.popuwindow;

public class AnimationUtil {
	 
    /**
     * anim中的布局ID
     */
    public static int ANIM_IN = 0;
    /**
     * anim中的布局ID
     */
    public static int ANIM_OUT = 0;
 
    /**
     * 通过动画xml文件的id设置需要使用的动画布局文件
     * 
     * @param layoutIn
     * @param layoutOut
     */
    public static void setLayout(int layoutIn, int layoutOut) {
        ANIM_IN = layoutIn;
        ANIM_OUT = layoutOut;
    }
 
    /**
     * 设置id为0
     */
    public static void clear() {
        ANIM_IN = 0;
        ANIM_OUT = 0;
    }
    
    
//    Intent intent = new Intent(InformActivity.this, InformItemActivity.class);
//    AnimationUtil.setLayout(R.anim.zoom_enter,R.anim.zoom_exit); 
//    startActivity(intent);
    
    
//    @Override
//    protected void onPause() {
//        if (AnimationUtil.ANIM_IN != 0 && AnimationUtil.ANIM_OUT != 0) {
//            super.overridePendingTransition(AnimationUtil.ANIM_IN,
//                    AnimationUtil.ANIM_OUT);
//            AnimationUtil.clear();
//        }
//        super.onPause();
//    }
}