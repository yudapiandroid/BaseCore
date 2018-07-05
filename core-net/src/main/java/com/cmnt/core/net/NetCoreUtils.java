package com.cmnt.core.net;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by YuXin on 2018/7/4.
 */

public class NetCoreUtils {

    private static Application app;

    /**
     *
     * @param app
     * @param overTime  超时时间
     */
    public static void init(Application app,int overTime){
        NetCoreUtils.app = app;
        NetRequest.okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new DefaultSpCookieJar()).connectTimeout(overTime, TimeUnit.SECONDS).build();
    }

    public static Application getApp() {
        return app;
    }

}
