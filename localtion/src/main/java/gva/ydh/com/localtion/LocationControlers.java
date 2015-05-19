package gva.ydh.com.localtion;

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
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.util.HashMap;

import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 15/5/18.
 */
public class LocationControlers implements AMapLocationListener, OnGeocodeSearchListener {

    private static final String TAG = "LocationControlers";
    /**
     * 位置变化的通知时间，单位为毫秒
     */
    private static final long MIN_TIME_MS = 60 * 1000;
    /**
     * 位置变化通知距离，单位为米。
     */
    private static final float MIN_DISTANCE = 10;
    /**
     * @地理编码与逆地理编码功能介绍
     */
    private GeocodeSearch geocoderSearch;
    /**
     * 定位信息
     */
    private LocationInfoBean locationInfoBean;


    private static LocationControlers locationControler;
    private LocationManagerProxy aMapLocManager;

    private Context context;

    private LocationControlers(Context context) {
        this.context = context;
        aMapLocManager = LocationManagerProxy.getInstance(context);
        locationInfoBean = new LocationInfoBean();
        initGeocodeSearch();
    }

    public static LocationControlers getInstance(Context context) {

        if (locationControler == null) {
            synchronized (LocationControlers.class) {
                if (locationControler == null) {
                    locationControler = new LocationControlers(context);
                }
            }
        }
        return locationControler;
    }


    /**
     * @初始化GeocodeSearch
     */
    public void initGeocodeSearch() {
        geocoderSearch = new GeocodeSearch(context);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }


    /**
     * @启动定位
     */
    public void startLocation() {
        if (aMapLocManager == null) {
            aMapLocManager = LocationManagerProxy.getInstance(context);
            aMapLocManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, MIN_TIME_MS, MIN_DISTANCE, this);
        } else {
            aMapLocManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, MIN_TIME_MS, MIN_DISTANCE, this);
        }
    }


    /**
     * @停止定位
     */
    public void stopLocation() {
        if (aMapLocManager != null) {
            aMapLocManager.removeUpdates(this);
            aMapLocManager.destory();
        }
        aMapLocManager = null;
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
            //获取位置信息
            AppLog.I(TAG, "onLocationChanged  定位成功");
            Double geoLat = amapLocation.getLatitude();
            Double geoLng = amapLocation.getLongitude();
            AppLog.I("TAG", "onLocationChanged   location.getLatitude()=" + geoLat + "location.getLongitude()=" + geoLng);
            LatLonPoint latLonPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
            getAddress(latLonPoint);

        } else {
            AppLog.I(TAG, "onLocationChanged 定位失败");
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 0) {
            {
                if (result != null && result.getRegeocodeAddress() != null
                        && result.getRegeocodeAddress().getFormatAddress() != null) {
//				addressName = result.getRegeocodeAddress().getFormatAddress()
//						+ "附近";
//				LogUitl.SystemOut(result.toString());
                    HashMap<Integer,String> map = new HashMap<Integer, String>();
                    if(!TextUtils.isEmpty(result.getRegeocodeAddress().getProvince())){
                        locationInfoBean.setProvinceName(result.getRegeocodeAddress().getProvince());
                        map.put(RegionUtils.Type_Province, result.getRegeocodeAddress().getProvince());
                    }

                    if(!TextUtils.isEmpty(result.getRegeocodeAddress().getCity())){
                        locationInfoBean.setCityName(result.getRegeocodeAddress().getCity());
                        map.put(RegionUtils.Type_City, result.getRegeocodeAddress().getCity());
                    }else {
                        //上海市 的city为“”，此时需要用省替换
                        locationInfoBean.setCityName(result.getRegeocodeAddress().getProvince());
                        map.put(RegionUtils.Type_City, result.getRegeocodeAddress().getProvince());
                    }

                    //详细地址，定位回调地址不靠谱
                    if(!TextUtils.isEmpty(result.getRegeocodeAddress().getFormatAddress())) {
                        locationInfoBean.setUser_current_location(result.getRegeocodeAddress().getFormatAddress());
                    }

                    if(!TextUtils.isEmpty(result.getRegeocodeAddress().getDistrict())){
                        locationInfoBean.setDistrict(result.getRegeocodeAddress().getDistrict());
                        map.put(RegionUtils.Type_Region, result.getRegeocodeAddress().getDistrict());
                    }

                    // 如果区域数据不到的话设置镇的(没有区的话 可能是是镇的数据)
                    if(!TextUtils.isEmpty(result.getRegeocodeAddress().getTownship())){
                        locationInfoBean.setTownsLoaction(result.getRegeocodeAddress().getTownship());
                        if(TextUtils.isEmpty(result.getRegeocodeAddress().getDistrict()))
                            map.put(RegionUtils.Type_Region, result.getRegeocodeAddress().getTownship());
                    }

                    String addressName = RegionUtils.getAddressName(map);
                    if(!TextUtils.isEmpty(addressName))
                        locationInfoBean.setReginId(RegionUtils.get(context).parseRegionId(locationInfoBean.getAdCode(), result.getRegeocodeAddress().getTownship())+"");
                    locationInfoBean.setRegionIdForFlee(RegionUtils.get(context).parseFineRegionId(addressName,"#"));
//                    locationFinishAction(true);
                    // WeiLeApplication.parseRegionID = RegionUtils.get().parseFineRegionId(addressName,"#");
                    //非用户主动定位情况下，如果用户已经设置了城市信息，则不需要使用定位的城市
                    AddressHelper addressHelper = new AddressHelper(context);
                    if(!UserCityCacher.getCityCacher().hasCityInfo(context)) {
                        //城市
                        if(!CommonStringUtils.isBlank(locationInfoBean.getCityName())) {
                            String cityId = addressHelper.getIdByName(locationInfoBean.getCityName(),"2");
                            if(!CommonStringUtils.isBlank(cityId)) {
                                UserCityCacher.getCityCacher().setCityInfo(context, cityId, locationInfoBean.getCityName());
                            }
                        }
                        //地区
                        String regionName = map.get(RegionUtils.Type_Region);
                        if(!CommonStringUtils.isBlank(regionName)) {
                            String regionId = addressHelper.getIdByName(regionName,"3");
                            if(!CommonStringUtils.isBlank(regionId)) {
                                UserCityCacher.getCityCacher().setRegionInfo(context, regionId, regionName);
                            }
                        }
                    }else {
                        //如果是用户主动定位
                        if(UserCityCacher.getCityCacher().isUserWantLocation()) {
                            //如果用户选择城市和定位城市不同，则无需处理
                            if(CommonStringUtils.isBlank(locationInfoBean.getCityName())) {
                                //重置用户主动定位
                                UserCityCacher.getCityCacher().setUserWantLocation(false);
                                return;
                            }
                            String cityId = UserCityCacher.getCityCacher().getCityId();
                            String locationCityId = addressHelper.getIdByName(locationInfoBean.getCityName(), "2");
                            //地区
                            String regionName = map.get(RegionUtils.Type_Region);
                            if(locationCityId == null || !locationCityId.equals(cityId)
                                    || CommonStringUtils.isBlank(regionName)) {
                                //重置用户主动定位
                                UserCityCacher.getCityCacher().setUserWantLocation(false);
                                return;
                            }
                            String regionId = addressHelper.getIdByName(regionName,"3");
                            if(CommonStringUtils.isBlank(regionId)) {
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
                }
            }
        } else {
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
