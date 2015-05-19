package gva.ydh.com.localtion;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一的用户选择的城市和区域信息缓存获取
 *
 * @author linbing
 */
public class UserCityCacher {

    private static UserCityCacher cityCacher = new UserCityCacher();

    private Object lock = new Object();

    public static final String KEY_CITY_OR_REGION_ID = "ID"; //筛选城市和区域的id，优先区域
    public static final String KEY_CITY_OR_REGION_NAME = "NAME"; //筛选城市和区域的名称，优先区域

    private UserCityCacher() {

    }

    public static UserCityCacher getCityCacher() {
        return cityCacher;
    }

    //首页和乐商首页切换城市使用
    private boolean homePageChangeCity; //在首页中切换了城市

    private boolean leshopHomePageChangeCity; //在乐商首页中切换了城市

    //--------------------------


    private boolean userWantLocation; //是否用户触发了定位

    private String cityId; //城市id

    private String cityName; //城市名称

    private String regionId; //城市下的区域id

    private String regionName; //区域的名称

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public boolean isUserWantLocation() {
        return userWantLocation;
    }

    public void setUserWantLocation(boolean userWantLocation) {
        this.userWantLocation = userWantLocation;
    }


    public boolean isHomePageChangeCity() {
        return homePageChangeCity;
    }

    public void setHomePageChangeCity(boolean homePageChangeCity) {
        this.homePageChangeCity = homePageChangeCity;
    }

    public boolean isLeshopHomePageChangeCity() {
        return leshopHomePageChangeCity;
    }

    public void setLeshopHomePageChangeCity(boolean leshopHomePageChangeCity) {
        this.leshopHomePageChangeCity = leshopHomePageChangeCity;
    }

    /**
     * 从SharePrefrence里面获取城市和区域信息
     *
     * @param context
     */
    public void initFromSp(Context context) {
//        String cityId = SharedPreUtil.get(context, SharedPreUtil.UserLastSelectCityId, null);
//        String cityName = SharedPreUtil.get(context, SharedPreUtil.UserLastSelectCityName, null);
//
//        String regionId = SharedPreUtil.get(context, SharedPreUtil.UserLastSelectRegionId, null);
//        String regionName = SharedPreUtil.get(context, SharedPreUtil.UserLastSelectRegionName, null);

        this.cityId = cityId;
        this.cityName = cityName;
        this.regionId = regionId;
        this.regionName = regionName;
    }

    /**
     * 设置城市
     *
     * @param context
     * @param cityId
     * @param cityName
     */
    public void setCityInfo(Context context, String cityId, String cityName) {
        synchronized (lock) {
            this.cityId = cityId;
            this.cityName = cityName;

//            SharedPreUtil.set(context, SharedPreUtil.UserLastSelectCityId, cityId);
//            SharedPreUtil.set(context, SharedPreUtil.UserLastSelectCityName, cityName);
        }
    }

    /**
     * 设置区域信息
     *
     * @param context
     * @param regionId
     * @param regionName
     */
    public void setRegionInfo(Context context, String regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;

//        SharedPreUtil.set(context, SharedPreUtil.UserLastSelectRegionId, regionId);
//        SharedPreUtil.set(context, SharedPreUtil.UserLastSelectRegionName, regionName);
    }

    /**
     * 设置城市和区域内容
     *
     * @param context
     * @param cityId
     * @param cityName
     * @param regionId
     * @param regionName
     */
    public void setCityInfo(Context context, String cityId, String cityName
            , String regionId, String regionName) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.regionId = regionId;
        this.regionName = regionName;

//        SharedPreUtil.set(context, SharedPreUtil.UserLastSelectCityId, cityId);
//        SharedPreUtil.set(context, SharedPreUtil.UserLastSelectCityName, cityName);
//        SharedPreUtil.set(context, SharedPreUtil.UserLastSelectRegionId, regionId);
//        SharedPreUtil.set(context, SharedPreUtil.UserLastSelectRegionName, regionName);
    }

    /**
     * 获取区域和城市的信息，如果有区域，优先返回区域信息
     *
     * @return
     */
    public Map<String, String> selectRegionOrCity() {
        Map<String, String> result = new HashMap<String, String>();
        if (!CommonStringUtils.isBlank(regionId)
                && !CommonStringUtils.isBlank(regionName)) {
            result.put(KEY_CITY_OR_REGION_ID, regionId);
            result.put(KEY_CITY_OR_REGION_NAME, regionName);
        } else if (!CommonStringUtils.isBlank(cityId)
                && !CommonStringUtils.isBlank(cityName)) {
            result.put(KEY_CITY_OR_REGION_ID, cityId);
            result.put(KEY_CITY_OR_REGION_NAME, cityName);
        }
        return result;
    }


    /**
     * 获取区域和城市的Id，如果有区域，优先返回区域信息
     *
     * @return
     */
    public String selectRegionIdOrCityId() {
        if (!CommonStringUtils.isBlank(regionId)
                && !CommonStringUtils.isBlank(regionName)) {
            return regionId;
        } else if (!CommonStringUtils.isBlank(cityId)
                && !CommonStringUtils.isBlank(cityName)) {
            return cityId;
        }
        return null;
    }

    /**
     * 获取区域和城市的名称，如果有区域，优先返回区域信息
     *
     * @return
     */
    public String selectRegionNameOrCityName() {
        if (!CommonStringUtils.isBlank(regionId)
                && !CommonStringUtils.isBlank(regionName)) {
            return regionName;
        } else if (!CommonStringUtils.isBlank(cityId)
                && !CommonStringUtils.isBlank(cityName)) {
            return cityName;
        }
        return null;
    }

    /**
     * 判断是否已经设置了城市信息
     *
     * @param context
     * @return
     */
    public boolean hasCityInfo(Context context) {
        synchronized (lock) {
            initFromSp(context);
            return !CommonStringUtils.isBlank(cityId)
                    && !CommonStringUtils.isBlank(cityName);
        }
    }
}
