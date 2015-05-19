package com.ydh.gva.util.net;



/**
 *
 * @author Administrator
 * 同服务端同步
 */
public final class ErrorCode {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 连接超时
     */
    public static final int TIMEOUT = 8000;
    /**
     * Session无效或者过期
     */
    public static final int INVALID_SESSION = 9000;
    /**
     * 数据库错误
     */
    public static final int DATABASE_ERROR = 9001;
    /**
     * 服务器维护中
     */
    public static final int SERVER_MAINTAINING = 9002;
    /**
     * 未知错误
     */
    public static final int UNKNOW_ERROR = 9003;
    /**
     * 参数错误
     */
    public static final int PARAMETER_ERROR = 9004;
    /**
     * 接口已经失效或者不存在
     */
    public static final int NO_SERVICE_INTERFACE = 9005;
    /**
     * 获取认证KEY失败
     */
    public static final int KEY_ERROR = 9006;
    /**
     * 用户名或者密码错误
     */
    public static final int USERNAME_OR_PASSWORD_ERROR = 9007;
    /**
     * 用户名不存在
     */
    public static final int NO_USERNAME = 9008;
    /**
     * 推荐人不存在
     */
    public static final int NO_RECOMMAND_PERSON = 9009;
    /**
     * 验证码错误
     */
    public static final int VALIDATIONCODE_ERROR = 9010;
    /**
     * 用户名已经存在
     */
    public static final int USERNAME_EXIST = 9011;
    /**
     * session不存在
     */
    public static final int SESSION_NO_EXIST = 9012;
    /**
     * 发送验证码失败(1.2.3按服务端提示)
     */
    public static final int SEND_VALIDATIONCODE_ERROR = 9013;
    /**
     * 广告不存在
     */
    public static final int NO_ADV = 9014;
    /**
     * 查询没有数据
     */
    public static final int NO_DATA = 9015;
    /**
     * 系统配置参数错误
     */
    public static final int SYSTEM_CONFIG_ERROR = 9016;
    /**
     * 新闻不存在
     */
    public static final int NO_NEWS = 9017;
    /**
     * 旧密码错误
     */
    public static final int OLD_PASSWORD_ERROR = 9018;
    /**
     * 签名验证失败
     */
    public static final int SIGN_VALIDATION_ERROR = 9019;
    /**
     * JSON无法解析
     */
    public static final int JSON_PARSE_ERROR = 9020;
    /**
     * 解密失败
     */
    public static final int CRYPT_ERROR = 9021;
    /**
     * accesstoken过期或者不存在
     */
    public static final int INVALID_ACCESS_TOKEN = 9022;
    /**
     * 分享微博失败
     */
    public static final int SHARE_WEIBO_ERROR = 9023;
    /**
     * 手机没有设置
     */
    public static final int NO_SETTING = 9024;
    /**
     * 推送失败
     */
    public static final int RECOMAND_FAIL = 9025;
    /**
     * 商品已被收藏
     */
    public static final int FAVORITE_ERROR = 9026;
    /**
     * 还未收藏
     */
    public static final int NO_COLLECTION = 9027;
    /**
     * 朋友不存在
     */
    public static final int NO_FRIEND = 9028;
    /**
     * 无促销商品
     */
    public static final int NO_PROTION = 9029;
    /**
     * 商品库存不够
     */
    public static final int NO_COUNT = 9030;
    /**
     * 商品细项不存在
     */
    public static final int NO_PRODUCT = 9031;
    /**
     * 订单号不存在
     */
    public static final int NO_ORDERID = 9032;
    /**
     * 余额不是50的倍数
     */
    public static final int NO_FIFTY_TIMES = 9033;
    /**
     * 密码错误
     */
    public static final int PASSWORD_ERROR=9034;
    /**
     * 没有设置角色
     */
    public static final int NO_ROLE = 9035;
    /**
     * 没有配置资源
     */
    public static final int NO_RESOURCES = 9036;
    
    /**
     * 显示服务器消息
     */
    public static final int SHOW_SERVICEMESSAGE = 9999;
    
    /**
     * 按客户端按服务端提示
     */
    public static final int SHOW_SERVICECLIENTMESSAGE = -9999;

    /**
     * 其他异常
     */
    public static final int OTHER_EXCEPTION = -2;
    /**
     * 网络异常
     */
    public static final int NET_ERROR = -1;

    /**
     * 网络未连接
     */
    public static final int NET_NOT_CONNECT = -3;
    
}
