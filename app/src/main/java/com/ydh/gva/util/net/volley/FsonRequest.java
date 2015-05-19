package com.ydh.gva.util.net.volley;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.util.net.volley.toolbox.HttpHeaderParser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by liujianying on 15/5/17.
 */
public class FsonRequest<S, T> extends Request<T> {

    private final static int CONNECT_COUNT = 0;
    private volatile boolean isAgainRequest = false;// 是否再次请求
    private boolean isSendRequest = true;
    private volatile int isConnectFlag = 0;
    private final Type requestDataType;
    private final Type responseDataType;
    private final Response.Listener<T> mListener;
    private final Map<String, String> mHeaders;
    private Gson mGson;
    private S mPayload;
    private String mURL;

    public FsonRequest(int method, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener, Map<String, String> headers, S payload) {
        super(method, url, errorListener);

        this.mListener = listener;
        this.mURL = url;
        this.mHeaders = headers;
        this.mPayload = payload;

        Type superclass = getClass().getGenericSuperclass();
        this.requestDataType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        this.responseDataType = ((ParameterizedType) superclass).getActualTypeArguments()[1];
        VolleyLog.v("Invoking GsonRequest for " + url);
        VolleyLog.v("request="
                + requestDataType
                + " : response="
                + responseDataType
                + " : method="
                + method
                + " : url= "
                + url
                + " : headers = "
                + headers
                + " : mParams = "
                + payload);
    }

    public FsonRequest(int method, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener, Map<String, String> headers) {
        this(method, url, listener, errorListener, headers, null);
    }

    public FsonRequest(int method, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(method, url, listener, errorListener, null, null);
    }

    public FsonRequest(int method, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener, S payload) {
        this(method, url, listener, errorListener, null, payload);
    }


    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

  /*private void setTimeout(int timeout) {

    RetryPolicy policy = new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    setRetryPolicy(policy);
  }*/

    @Override
    public byte[] getBody() throws AuthFailureError {
        return JSON.toJSONBytes(mPayload);
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = null;

            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            VolleyLog.v("Receiving response for " + mURL);
            VolleyLog.v("parseNetworkResponse : "
                    + responseDataType
                    + " :url="
                    + mURL
                    + " :statusCode="
                    + response.statusCode
                    + " :JSON= "
                    + json);

            YDHData ydhData = JSON.parseObject(json, responseDataType);
            ydhData.GsonEnncryptToString();// 数据还原
            return  Response.success(
                    (T) JSON.parseObject(json, responseDataType),
                    HttpHeaderParser.parseCacheHeaders(response));

//            do {
//                if (isSendRequest) {
//
//                    int resultCode = ydhData.getResultCode();
//
//                    if (resultCode == 0) {
//                        return Response.success(
//                                (T) JSON.parseObject(json, responseDataType),
//                                HttpHeaderParser.parseCacheHeaders(response));
//                        isAgainRequest = false;
//                    }else {
//                        managerResultCode(resultCode, ydhData);
//                    }
//                }
//
//            } while (isAgainRequest);


        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }


//    private void managerResultCode(final int resultCode, final Object object) throws Exception {
//
//        switch (resultCode) {
//
//            case ErrorCode.INVALID_SESSION:// Session无效或者过期
//            case ErrorCode.SESSION_NO_EXIST:// Session不存在
//            case ErrorCode.KEY_ERROR:// 获取认证KEY失败
//            case ErrorCode.CRYPT_ERROR:// 获取认证KEY失败
//
//
//                isSendRequest = false;
//                isAgainRequest = true;
//                if(isConnectFlag <= CONNECT_COUNT) {
//                    LoginInfo logiInfo = SharedPreUtil.Instance().getLoginInfo();
//
//                    if(TextUtils.isEmpty(logiInfo.getUserName())) {
//                        callback.errorMessage(resultCode,responseContent);
//                        isAgainRequest = false;
//                        return;
//                    }
//                    try {
//                        String str = OkHttpUtil.post(HttpRequestUrl.Instance().getManagerLogin(logiInfo.getUserName(), logiInfo.getPassword()),
//                                HttpRequestBody.Instance().requestManagerLoginBody(logiInfo.getUserName(), logiInfo.getPassword()));
//
//                        AppLog.I("str  === " + str);
//                        YDHData ydhData = JSON.parseObject(str, YDHData.class);
//                        ydhData.GsonEnncryptToString();// 数据还原
//                        int resultCode_r = ydhData.getResultCode();
//                        if (0 == resultCode_r && callback != null) {
//                            logiInfo = JSON.parseObject(ydhData.getData(), LoginInfo.class);
//                            final JSONObject body_json = new JSONObject(body);
//                            String data = null;
//                            isConnectFlag++;
//                            isSendRequest = true;
//                            if (body_json.getInt("encryptType") == 1 && !body_json.isNull("data")) {
//                                String data_Aes = body_json.getString("data");
//                                data = AESCrypto.decrypt(SystemVal.getLoginInfo().getKey(), data_Aes);
//                            }
//                            body_json.put("session", logiInfo.getSession());
//                            if (body_json.getInt("encryptType") == 1 && !body_json.isNull("data")) {
//                                body_json.put("data", AESCrypto.encrypt(logiInfo.getKey(), data));
//                            }
//                            body = body_json.toString();
//                            /*    更新用户key   ****/
//                            SystemVal.setLoginInfo(null);
//
//                        } else if (0 != resultCode && callback != null) {
//                            callback.fetchSuccess(object);
//                            isAgainRequest = false;
//                        }
//                    } catch (Exception e){
//                        e.printStackTrace();
//                        callback.errorMessage(resultCode,responseContent);
//                        isAgainRequest = false;
//                    }
//                } else {
//                    isAgainRequest = false;
//                }
//
//                break;
//
//            default:
//                isAgainRequest = false;
//                callback.fetchSuccess(object);
//                break;
//        }
//
//    }


}
