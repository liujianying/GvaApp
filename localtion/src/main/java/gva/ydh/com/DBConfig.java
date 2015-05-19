package gva.ydh.com;

/**
 * Created by liujianying on 15/5/18.
 */
public class DBConfig {
    public static final int AddressDBVersion = 1;       // 保存用户登录信息
    /** 地区数据库版本号 **/
    public static final int ADDRESS_DB_VERSION = 4;
    public static final String LocationInfo = "LocationInfo";               // 定位信息
    public static final String UserLastSelectCityId = "UserLastSelectCityId";         //用户最后选择的城市ID
    public static final String UserLastSelectCityName = "UserLastSelectCityName";     //用户最后选择的城市名称
    public static final String UserLastSelectRegionId = "UserLastSelectRegionId";     //用户最后选择的区域ID
    public static final String UserLastSelectRegionName = "UserLastSelectRegionName"; //用户最后选择的区域名称
}
