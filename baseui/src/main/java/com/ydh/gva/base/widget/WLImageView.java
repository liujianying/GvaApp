package com.ydh.gva.base.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

/**
 * Created by liujianying on 15/4/27.
 */
public class WLImageView extends SimpleDraweeView {



    public WLImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public WLImageView(Context context) {
        super(context);
        init();
    }

    public WLImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WLImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
    }


    public WLImageView setPlaceholderImage(Drawable drawable) {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        hierarchy.setPlaceholderImage(drawable);
        setHierarchy(hierarchy);
        return this;
    }

    public WLImageView setPlaceholderImage(int resourcesId) {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        hierarchy.setPlaceholderImage(resourcesId);
        setHierarchy(hierarchy);
        return this;
    }


    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    public void setImageURI(String uriString) {
        Uri uri = Uri.parse(uriString);
        super.setImageURI(uri);
    }



    public void setImageURIres(int res) {
        Uri uri =  Uri.parse("res:///" + res);
        setImageURI(uri);
    }


    public void setImageURIasset(int asset) {
        Uri uri =  Uri.parse("asset:///" + asset);
        setImageURI(uri);
    }

    public void setImageURIfile(File file) {
        Uri uri =  Uri.parse("file:///" + file);
        setImageURI(uri);
    }

}
