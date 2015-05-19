package com.ydh.gva.core;

/**
 * @用户登录信息类
 * Created by liujianying on 14-9-28.
 */
public class LoginInfo {

    /** 用户名 */
    private String userName;
    /** 用户密码 */
    private String password;
    /** 是否自动登录 */
    private boolean isAutomaticLogin;
    /** 用户ID */
    private String userId;
    /** key */
    private String key;
    /** session */
    private String session;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isAutomaticLogin() {
        return isAutomaticLogin;
    }

    public void setAutomaticLogin(boolean isAutomaticLogin) {
        this.isAutomaticLogin = isAutomaticLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
