package com.ydh.gva.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujianying on 15/5/22.
 * @商城列表实体类
 */
public class BisEntity implements Parcelable {

    private String provinceId;
    private String bisAddress;
    private String districtId;
    private String status;
    private String cityId;
    private String businessId;
    private String bisName;


    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getBisAddress() {
        return bisAddress;
    }

    public void setBisAddress(String bisAddress) {
        this.bisAddress = bisAddress;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBisName() {
        return bisName;
    }

    public void setBisName(String bisName) {
        this.bisName = bisName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.provinceId);
        dest.writeString(this.bisAddress);
        dest.writeString(this.districtId);
        dest.writeString(this.status);
        dest.writeString(this.cityId);
        dest.writeString(this.businessId);
        dest.writeString(this.bisName);
    }

    public BisEntity() {
    }

    private BisEntity(Parcel in) {
        this.provinceId = in.readString();
        this.bisAddress = in.readString();
        this.districtId = in.readString();
        this.status = in.readString();
        this.cityId = in.readString();
        this.businessId = in.readString();
        this.bisName = in.readString();
    }

    public static final Parcelable.Creator<BisEntity> CREATOR = new Parcelable.Creator<BisEntity>() {
        public BisEntity createFromParcel(Parcel source) {
            return new BisEntity(source);
        }

        public BisEntity[] newArray(int size) {
            return new BisEntity[size];
        }
    };
}
