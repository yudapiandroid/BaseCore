package com.cmnt.core.net;

import com.cmnt.core.net.cookie.PersistentCookieStore;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by YuXin on 2018/7/4.
 */

public class DefaultSpCookieJar implements CookieJar {

    private PersistentCookieStore store = new PersistentCookieStore(NetCoreUtils.getApp());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if(cookies != null){
            for (Cookie cookie : cookies){
                store.add(url,cookie);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return store.get(url) != null ? store.get(url) : new ArrayList<Cookie>();
    }

}
