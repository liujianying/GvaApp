package com.ydh.gva.ui.activitys;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujianying on 15/5/22.
 */
public class HomeCfg implements Parcelable {
    private String boxId;
    private String moreImg;
    private String title;
    private String moreUrl;
    private String boxItemId;
    private String urlType;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getMoreImg() {
        return moreImg;
    }

    public void setMoreImg(String moreImg) {
        this.moreImg = moreImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    public String getBoxItemId() {
        return boxItemId;
    }

    public void setBoxItemId(String boxItemId) {
        this.boxItemId = boxItemId;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.boxId);
        dest.writeString(this.moreImg);
        dest.writeString(this.title);
        dest.writeString(this.moreUrl);
        dest.writeString(this.boxItemId);
        dest.writeString(this.urlType);
    }

    public HomeCfg() {
    }

    private HomeCfg(Parcel in) {
        this.boxId = in.readString();
        this.moreImg = in.readString();
        this.title = in.readString();
        this.moreUrl = in.readString();
        this.boxItemId = in.readString();
        this.urlType = in.readString();
    }

    public static final Parcelable.Creator<HomeCfg> CREATOR = new Parcelable.Creator<HomeCfg>() {
        public HomeCfg createFromParcel(Parcel source) {
            return new HomeCfg(source);
        }

        public HomeCfg[] newArray(int size) {
            return new HomeCfg[size];
        }
    };
}
