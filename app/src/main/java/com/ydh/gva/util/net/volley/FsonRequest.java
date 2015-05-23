package com.ydh.gva.util.net.volley;

import com.alibaba.fastjson.JSON;
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
            ydhData.fsonEnncryptToString();// 数据还原
            return  Response.success(
                    (T) JSON.parseObject(json, responseDataType),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }




}
