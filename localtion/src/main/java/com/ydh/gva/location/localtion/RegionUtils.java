package com.ydh.gva.location.localtion;


import android.content.Context;
import android.text.TextUtils;

import com.ydh.gva.location.core.SysRegion;

import java.util.HashMap;

/**
 * 定位工具类
 * Created by yx on 2014/9/24.
 */
public class RegionUtils {

    private static RegionUtils regionUtils = null;
    public static final int Type_Province = 1;  // 省
    public static final int Type_City = 2;      // 市
    public static final int Type_Region = 3;    // 区
    public AddressHelper addressHelper;

    public static RegionUtils get(Context context) {
        if (null == regionUtils) {
            regionUtils = new RegionUtils();
        }

        if (regionUtils.addressHelper == null) {
            regionUtils.addressHelper = new AddressHelper(context);
        }
        return regionUtils;
    }


    /**
     * 获得地址字符串
     * @param adderssMap
     * @return
     */
    public static String getAddressName(HashMap<Integer,String> adderssMap){
        if(adderssMap == null)
            return  null;

        StringBuilder sb = new StringBuilder();
        if(!TextUtils.isEmpty(adderssMap.get(Type_Province)))
            sb.append(adderssMap.get(Type_Province));
        sb.append("#");
        if(!TextUtils.isEmpty(adderssMap.get(Type_City)))
            sb.append(adderssMap.get(Type_City));
        sb.append("#");
        if(!TextUtils.isEmpty(adderssMap.get(Type_Region)))
            sb.append(adderssMap.get(Type_Region));
        return  sb.toString();
    }


    /**
     * 解析地址,区域数组中可能存在null
     * 1.重庆市##武隆县 = 重庆市#重庆市市辖区#武隆县
     * 2.海南省##琼中黎族苗族自治县 = 海南省#琼中黎族苗族自治县#
     * 3.香港特別行政區##荃灣區 = 香港特别行政区#香港特别行政区#荃湾区 (區,区;別,别;灣,湾)
     *
     * @param address 地址
     * @param sep     分隔符
     * @return
     */
    public HashMap<Integer, SysRegion> parseAddress(String address, String sep) {


        String[] addressData = address.split(sep);
        HashMap<Integer, SysRegion> map = new HashMap<Integer, SysRegion>();

        try{
            int superId = 1;
            boolean zhhk = false;
            //省级 type=1
            String regionName = addressData[0];
            SysRegion region = getRegion(regionName, Type_Province, superId);
            if (null == region) {
                //尝试繁体定位
                regionName = convertZhhk(regionName, true);
                region = getRegion(regionName, Type_Province, superId);
                if (region != null) {
                    zhhk = true;
                }
            }
            //
            if (null != region) {
                map.put(Type_Province, region);
                superId = region.getRegionId();
                //市级 type=2
                regionName = addressData[1];
                if (!"".equals(regionName)) {
                    regionName = convertZhhk(regionName, zhhk);
                    region = getRegion(regionName, Type_City, superId);
                    if (null != region) {
                        map.put(Type_City, region);
                        superId = region.getRegionId();
                        //区级 type=3
                        regionName = addressData[2];
                        if (!"".equals(regionName)) {
                            regionName = convertZhhk(regionName, zhhk);
                            region = getRegion(regionName, Type_Region, superId);
                            if (null != region) {
                                map.put(Type_Region, region);
                            } else {
                                //找不到区域.尝试更新数据库
                            }
                        } else {
                            //区级名称为空.不处理
                        }
                    }
                } else {
                    //市级名称为空.可能是市辖区
                    region = getRegion(addressData[0] + "市辖区", Type_City, superId);
                    if (null != region) {
                        map.put(Type_City, region);
                        superId = region.getRegionId();
                        regionName = addressData[2];
                        if (!"".equals(regionName)) {
                            regionName = convertZhhk(regionName, zhhk);
                            region = getRegion(regionName, Type_Region, superId);
                            if (null != region) {
                                map.put(Type_Region, region);
                            } else {
                                //找不到区域.尝试更新数据库
                            }
                        } else {
                            //区级名称为空.不处理
                        }
                    } else {
                        //可能是县级上升为市
                        regionName = addressData[2];
                        if (!"".equals(regionName)) {
                            regionName = convertZhhk(regionName, zhhk);
                            region = getRegion(regionName, Type_City, superId);
                            if (null != region) {
                                map.put(Type_City, region);
                            } else {
                                //找不到区域.尝试更新数据库
                            }
                        } else {
                            //区级名称为空.不处理
                        }
                    }
                }
            } else {
                //找不到区域.尝试更新数据库
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }


    private String convertZhhk(String name, boolean zhhk) {
        if (zhhk) {
            return ChangeCode.toSimple(name);
        }
        return name;
    }

    private SysRegion getRegion(String regionName, int type, int superId) {
        SysRegion region = new SysRegion();
        region.setRegnType(type);
        region.setRegionName(regionName);
        region.setSuperId(superId);
        String parent_id = addressHelper.getIdByName(regionName, type + "");
        if(!TextUtils.isEmpty(parent_id))
            region.setRegionId(Integer.parseInt(parent_id));
        return region;
    }

    /**
     * 解析地址,取得最小区域id
     *
     * @param address 地址
     * @param sep     分隔符
     * @return
     */
    public int parseFineRegionId(String address, String sep) {
        HashMap<Integer,SysRegion> map = parseAddress(address, sep);
        int regionId=-1;
        if(map.get(Type_Region) != null)
            regionId= map.get(Type_Region).getRegionId();

        if( regionId!= -1 ){
            return regionId;
        }
        if(map.get(Type_City) != null)
            regionId= map.get(Type_City).getRegionId();
        if( regionId!= -1 ){
            return regionId;
        }
        if(map.get(Type_Province) != null)
            regionId= map.get(Type_Province).getRegionId();
        if( regionId!= -1 ){
            return regionId;
        }
        return -1;
    }


    public int parseRegionId(String adCode ,String township){
        String region_id = null;
        if(TextUtils.isEmpty(township)){
            region_id = addressHelper.getIdByAdCode(adCode);
        }else{
            region_id = addressHelper.getIdByAdCodeAndTownShip(adCode, township);
            if(TextUtils.isEmpty(region_id)){
                region_id = addressHelper.getIdByAdCode(adCode);
            }
        }

        if(TextUtils.isEmpty(region_id))
            return  -1;
        else
            return Integer.parseInt(region_id);
    }

}
