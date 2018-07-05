package com.cmnt.core.net.convert;


import okhttp3.Request;

/**
 * Created by YuXin on 2018/7/4.
 */
public interface ParamsConvert {

    void convert(String url, Object data, String method, Request.Builder builder);

}
