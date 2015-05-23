package com.ydh.gva.location.localtion;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.ydh.gva.location.config.LocationModeConfig;

import java.util.HashMap;
import java.util.Stack;

import gva.ydh.com.util.AppLog;

public class LocationControler implements AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {

    private static final String TAG = "LocationControler";
    /**
     * 定位信息
     */
    private LocationInfoBean locationInfoBean;
    /**
     * 获取单例
     */
    private volatile static LocationControler locationControler;
    /**
     * 高德地图管理
     */
    private LocationManagerProxy mAMapLocManager;
    /**
     * 获取到context
     */
    private Context context;
    /**
     * 地图回调
     */
    private OnLocationResultCallBack callBack;
    /**
     * 当前的地图操作类
     */
    private LocationControlBean locationControlBean;
    /**
     * 更新到服务器中的数据
     */
    private UpdateServerLocation updateServerLocation;


    private static final float DEFAULT_SPACE_DISTANCE = 10;

    private static final long DEFAULT_SPACE_TIME = 5 * 1000;


    private Stack<OnLocationResultCallBack> listenStacks;
    /**
     * ************************根据经纬度进行解码需要的属性***********************************
     */
    private GeocodeSearch geocodeSearch;

    private RegeocodeQuery query;

    private LatLonPoint latLonPoint;

    /**
     * 当前是否已经有一个正在定位
     */
    private boolean isLocationing;


    private LocationControler(Context context) {
        this.context = context;
        mAMapLocManager = LocationManagerProxy.getInstance(context);
        locationInfoBean = LocationInfoBean.Instance(LocationModeConfig.Instance().context);
        locationControlBean = new LocationControlBean();
        listenStacks = new Stack<OnLocationResultCallBack>();
        geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    public static LocationControler getInstance() {

        if (locationControler == null) {
            synchronized (LocationControler.class) {
                if (locationControler == null) {
                    locationControler = new LocationControler(LocationModeConfig.Instance().context);
                }
            }
        }
        return locationControler;
    }

    public static double getLat() {
        return getInstance().getLocationInfoBean().getLatitude();
    }

    public static double getLng() {
        return getInstance().getLocationInfoBean().getLongitude();
    }

    public static String getUser_current_location() {
        return getInstance().getLocationInfoBean().getUser_current_location();
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLocationChanged(AMapLocation location) {

        if (location != null && location.getAMapException().getErrorCode() == 0) {
            AppLog.i(TAG, "onLocationChanged  定位成功");
            AppLog.i("TAG", "onLocationChanged   location.getLatitude()=" + location.getLatitude() + "location.getLongitude()=" + location.getLongitude());
            locationInfoBean.setLatitude(location.getLatitude());
            locationInfoBean.setLongitude(location.getLongitude());
            locationInfoBean.setAdCode(location.getAdCode());
            //该字段不靠谱，放在逆编码设置
//			locationInfoBean.setUser_current_location(desc);
            locationInfoBean.setLastLoadingTime(System.currentTimeMillis());
            disableMyLocation();
            //地理解码
            startGeocoderLocation();
        } else {
            AppLog.i(TAG, "onLocationChanged 定位失败");
            AppLog.i(TAG, "onLocationChanged locationControlBean.getSpaceRunTime()=" + locationControlBean.getSpaceRunTime());
            //判断重复定位次数是否大于0
            if (locationControlBean.getSpaceRunTime() - 1 < 0) {
                //定位完成操作
                locationFinishAction(false);
                disableMyLocation();
            } else {
                locationControlBean.setSpaceRunTime(locationControlBean.getSpaceRunTime() - 1);
            }
        }

    }


    /**
     * 初始化locaitoninfo的信息
     */
    private void initLocationInfo(LocationInfoBean locationInfoBean) {

        locationInfoBean.setCityName("");
        locationInfoBean.setDistrict("");
        locationInfoBean.setLastLoadingTime(0);
        locationInfoBean.setLatitude(0);
        locationInfoBean.setLongitude(0);
        locationInfoBean.setProvinceName("");
        locationInfoBean.setTownsLoaction("");
        locationInfoBean.setUser_current_location("");

    }

    private void resetLocationControl() {
        locationControlBean.setUpdateServerLocation(true);
        locationControlBean.setSpaceDistance(DEFAULT_SPACE_DISTANCE);
        locationControlBean.setSpaceRunTime(0);
        locationControlBean.setSpaceTime(0);
    }

    /**
     * 调用定位信息
     *
     * @param locationControlBean      定位操作
     * @param onLocationResultCallBack 定位回调
     */
    public void requestUpdateLocation(LocationControlBean locationControlBean,
                                      OnLocationResultCallBack onLocationResultCallBack) {
        AppLog.i(TAG, "requestUpdateLocation 正在调用定位信息。。。。");
        if (null == mAMapLocManager) {
            mAMapLocManager = LocationManagerProxy.getInstance(context);
        }
        this.locationControlBean = locationControlBean;
        this.callBack = onLocationResultCallBack;
        setLocationUpdateType(locationControlBean);
    }

    /**
     * 立即定位
     *
     * @param onLocationResultCallBack
     */

    public void requestLocationImediately(OnLocationResultCallBack onLocationResultCallBack) {
        AppLog.i(TAG, "requestLocationImediately 正在调用定位信息。。。。");
        if (null == mAMapLocManager) {
            mAMapLocManager = LocationManagerProxy.getInstance(context);
        }
        resetLocationControl();
        locationControlBean.setUpdatelType(LocationUpdateType.IMMEDIATELY);
        this.callBack = onLocationResultCallBack;
        setLocationUpdateType(locationControlBean);
    }

    /**
     * 立即定位
     *
     * @param spaceDistance            位置变化通知距离，单位为米。
     * @param onLocationResultCallBack
     */
    public void requestLocationImediately(float spaceDistance, OnLocationResultCallBack onLocationResultCallBack) {
        AppLog.i(TAG, "requestLocationImediately 正在调用定位信息。spaceDistance=" + spaceDistance);
        if (null == mAMapLocManager) {
            mAMapLocManager = LocationManagerProxy.getInstance(context);
        }
        resetLocationControl();
        locationControlBean.setUpdatelType(LocationUpdateType.IMMEDIATELY);
        locationControlBean.setSpaceDistance(spaceDistance);
        this.callBack = onLocationResultCallBack;
        setLocationUpdateType(locationControlBean);
    }

    /**
     * 间隔定位
     *
     * @param spaceDistance            位置变化通知距离，单位为米。
     * @param spaceTime                间隔时间
     * @param spaceRunTime             间隔次数
     * @param onLocationResultCallBack 回调
     */
    public void requestLocationSpaceTime(float spaceDistance, int spaceTime, int spaceRunTime, OnLocationResultCallBack onLocationResultCallBack) {
        AppLog.i(TAG, "requestLocationSpaceTime 正在调用定位信息。spaceDistance=" + spaceDistance + ";spaceTime=" + spaceTime + ";spaceRunTime=" + spaceRunTime);
        if (null == mAMapLocManager) {
            mAMapLocManager = LocationManagerProxy.getInstance(context);
        }
        resetLocationControl();
        locationControlBean.setUpdatelType(LocationUpdateType.SPACETIME);
        locationControlBean.setSpaceDistance(spaceDistance);
        locationControlBean.setSpaceTime(spaceTime);
        locationControlBean.setSpaceRunTime(spaceRunTime);
        this.callBack = onLocationResultCallBack;
        setLocationUpdateType(locationControlBean);
    }

    /**
     * 间隔定位
     *
     * @param spaceTime                间隔时间
     * @param spaceRunTime             间隔次数
     * @param onLocationResultCallBack 回调
     */
    public void requestLocationSpaceTime(int spaceTime, int spaceRunTime, OnLocationResultCallBack onLocationResultCallBack) {
        AppLog.i(TAG, "requestLocationSpaceTime 正在调用定位信息。spaceTime=" + spaceTime + ";spaceRunTime=" + spaceRunTime);
        if (null == mAMapLocManager) {
            mAMapLocManager = LocationManagerProxy.getInstance(context);
        }
        resetLocationControl();
        locationControlBean.setUpdatelType(LocationUpdateType.SPACETIME);
        locationControlBean.setSpaceTime(spaceTime);
        locationControlBean.setSpaceRunTime(spaceRunTime);
        this.callBack = onLocationResultCallBack;
        setLocationUpdateType(locationControlBean);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
//				addressName = result.getRegeocodeAddress().getFormatAddress()
//						+ "附近";
//				LogUitl.SystemOut(result.toString());
                HashMap<Integer, String> map = new HashMap<Integer, String>();
                if (!TextUtils.isEmpty(result.getRegeocodeAddress().getProvince())) {
                    locationInfoBean.setProvinceName(result.getRegeocodeAddress().getProvince());
                    map.put(RegionUtils.Type_Province, result.getRegeocodeAddress().getProvince());
                }
                if (!TextUtils.isEmpty(result.getRegeocodeAddress().getCity())) {
                    locationInfoBean.setCityName(result.getRegeocodeAddress().getCity());
                    map.put(RegionUtils.Type_City, result.getRegeocodeAddress().getCity());
                } else {
                    //上海市 的city为“”，此时需要用省替换
                    locationInfoBean.setCityName(result.getRegeocodeAddress().getProvince());
                    map.put(RegionUtils.Type_City, result.getRegeocodeAddress().getProvince());
                }

                //详细地址，定位回调地址不靠谱
                if (!TextUtils.isEmpty(result.getRegeocodeAddress().getFormatAddress())) {
                    locationInfoBean.setUser_current_location(result.getRegeocodeAddress().getFormatAddress());
                }

                if (!TextUtils.isEmpty(result.getRegeocodeAddress().getDistrict())) {
                    locationInfoBean.setDistrict(result.getRegeocodeAddress().getDistrict());
                    map.put(RegionUtils.Type_Region, result.getRegeocodeAddress().getDistrict());
                }

                // 如果区域数据不到的话设置镇的(没有区的话 可能是是镇的数据)
                if (!TextUtils.isEmpty(result.getRegeocodeAddress().getTownship())) {
                    locationInfoBean.setTownsLoaction(result.getRegeocodeAddress().getTownship());
                    if (TextUtils.isEmpty(result.getRegeocodeAddress().getDistrict()))
                        map.put(RegionUtils.Type_Region, result.getRegeocodeAddress().getTownship());
                }

                String addressName = RegionUtils.getAddressName(map);
                if (!TextUtils.isEmpty(addressName))
                    locationInfoBean.setReginId(RegionUtils.get(LocationModeConfig.Instance().context).parseRegionId(locationInfoBean.getAdCode(), result.getRegeocodeAddress().getTownship()) + "");
                locationInfoBean.setRegionIdForFlee(RegionUtils.get(LocationModeConfig.Instance().context).parseFineRegionId(addressName, "#"));
                locationFinishAction(true);
                // WeiLeApplication.parseRegionID = RegionUtils.get().parseFineRegionId(addressName,"#");
                //非用户主动定位情况下，如果用户已经设置了城市信息，则不需要使用定位的城市
                AddressHelper addressHelper = new AddressHelper(context);
                if (!UserCityCacher.getCityCacher().hasCityInfo(context)) {
                    //城市
                    if (!CommonStringUtils.isBlank(locationInfoBean.getCityName())) {
                        String cityId = addressHelper.getIdByName(locationInfoBean.getCityName(), "2");
                        if (!CommonStringUtils.isBlank(cityId)) {
                            UserCityCacher.getCityCacher().setCityInfo(context, cityId, locationInfoBean.getCityName());
                        }
                    }
                    //地区
                    String regionName = map.get(RegionUtils.Type_Region);
                    if (!CommonStringUtils.isBlank(regionName)) {
                        String regionId = addressHelper.getIdByName(regionName, "3");
                        if (!CommonStringUtils.isBlank(regionId)) {
                            UserCityCacher.getCityCacher().setRegionInfo(context, regionId, regionName);
                        }
                    }
                } else {
                    //如果是用户主动定位
                    if (UserCityCacher.getCityCacher().isUserWantLocation()) {
                        //如果用户选择城市和定位城市不同，则无需处理
                        if (CommonStringUtils.isBlank(locationInfoBean.getCityName())) {
                            //重置用户主动定位
                            UserCityCacher.getCityCacher().setUserWantLocation(false);
                            return;
                        }
                        String cityId = UserCityCacher.getCityCacher().getCityId();
                        String locationCityId = addressHelper.getIdByName(locationInfoBean.getCityName(), "2");
                        //地区
                        String regionName = map.get(RegionUtils.Type_Region);
                        if (locationCityId == null || !locationCityId.equals(cityId)
                                || CommonStringUtils.isBlank(regionName)) {
                            //重置用户主动定位
                            UserCityCacher.getCityCacher().setUserWantLocation(false);
                            return;
                        }
                        String regionId = addressHelper.getIdByName(regionName, "3");
                        if (CommonStringUtils.isBlank(regionId)) {
                            //重置用户主动定位
                            UserCityCacher.getCityCacher().setUserWantLocation(false);
                            return;
                        }
                        //用户选择城市和定位城市一样，则使用定位后的区域
                        UserCityCacher.getCityCacher().setRegionInfo(context, regionId, regionName);
                        //重置用户主动定位
                        UserCityCacher.getCityCacher().setUserWantLocation(false);
                    }
                }
            } else {

            }
        } else {
            if (!TextUtils.isEmpty(locationInfoBean.getAdCode()))
                locationInfoBean.setReginId(RegionUtils.get(LocationModeConfig.Instance().context).parseRegionId(locationInfoBean.getAdCode(), null) + "");
            locationFinishAction(true);
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    public interface OnLocationResultCallBack {
        public void OnLocationResultSuccess(LocationInfoBean locationInfoBean);

        public void OnLocationResultFailure(LocationInfoBean locationInfoBean);
    }

    /**
     * 取消地图监听
     */
    public void disableMyLocation() {
        if (mAMapLocManager != null) {
            mAMapLocManager.removeUpdates(this);
        }
        isLocationing = false;
    }


    public void destoryMyLocation() {
        if (mAMapLocManager != null) {
            mAMapLocManager.removeUpdates(this);
            mAMapLocManager.destory();
        }
        isLocationing = false;
        mAMapLocManager = null;
    }

    /**
     * 设置请求地理位置的方式
     *
     * @param locationControlBean
     */
    public void setLocationUpdateType(LocationControlBean locationControlBean) {
        mAMapLocManager.setGpsEnable(true);
        //如果传进来是立即更新类型且
        if (locationControlBean.getUpdatelType() == LocationUpdateType.IMMEDIATELY) {
            //当前的回调监听添加到栈中,并立即执行定位请求
            listenStacks.push(callBack);
            if (!isLocationing) {
                //改变定位状态
                isLocationing = true;
                mAMapLocManager.requestLocationData(
                        LocationProviderProxy.AMapNetwork, -1, locationControlBean.getSpaceDistance(), this);
            }
            //间隔更新
        } else if (locationControlBean.getUpdatelType() == LocationUpdateType.SPACETIME) {
            long dif = System.currentTimeMillis() - locationInfoBean.getLastLoadingTime();
            AppLog.i(TAG, "setLocationUpdateType 两者的时间差是:" + dif);
            //时间差大于间隔时间且栈中只有当前的监听则请求网络数据
            if (dif > locationControlBean.getSpaceTime() && !isLocationing) {
                //改变定位状态
                isLocationing = true;
                listenStacks.push(callBack);
                mAMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork, DEFAULT_SPACE_TIME, locationControlBean.getSpaceDistance(), this);
                //时间差小于间隔时间说明之前已经有定位过，直接通知
            } else if (dif <= locationControlBean.getSpaceTime()) {
                listenStacks.push(callBack);
                notifyStackListen(true);
            } else {
                //其他情况则把当前的添加到栈中
                listenStacks.push(callBack);
            }
        }
    }


    /**
     * 提供外界获取到定位的信息
     *
     * @return
     */
    public LocationInfoBean getLocationInfoBean() {
        return locationInfoBean;
    }

    /**
     * 是否有定位信息
     *
     * @return
     */
    public boolean hasLocationInfo() {
        if (locationInfoBean != null && locationInfoBean.getLatitude() > 0 && locationInfoBean.getLongitude() > 0) {
            return true;
        }
        return false;

    }

    /**
     * 移除回调栈
     */
    private void clearStacks() {

        while (!listenStacks.isEmpty()) {
            listenStacks.pop();
        }
    }


    /**
     * 通知监听进行操作
     *
     * @param LocatedSuccess 是否定位成功
     */
    private void notifyStackListen(boolean LocatedSuccess) {

        while (!listenStacks.isEmpty()) {
            OnLocationResultCallBack listen = listenStacks.pop();
            if (listen != null) {
                if (LocatedSuccess) {
                    listen.OnLocationResultSuccess(locationInfoBean);
                } else {
                    listen.OnLocationResultFailure(locationInfoBean);
                }
            }
        }
    }


    /**
     * 通过获取到的经纬度开始地理编译
     */
    private void startGeocoderLocation() {

        latLonPoint = new LatLonPoint(locationInfoBean.getLatitude(), locationInfoBean.getLongitude());
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, DEFAULT_SPACE_DISTANCE,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocodeSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 是否需要定位
     *
     * @param time
     */

    public boolean isNeedToLocatation(long time) {
        long dif = System.currentTimeMillis() - locationInfoBean.getLastLoadingTime();
        return dif >= time ? true : false;
    }

    /**
     * 定位完成操作
     *
     * @param isLocateSuccess 是否定位成功
     */

    private void locationFinishAction(boolean isLocateSuccess) {
        notifyStackListen(isLocateSuccess);
        if (isLocateSuccess) {
            LocationInfoBean.setLocationInfoBeanss(locationInfoBean);
        }
    }

}
