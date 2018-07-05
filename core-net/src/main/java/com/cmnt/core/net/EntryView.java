package com.cmnt.core.net;

/**
 * Created by YuXin on 2018/7/4.
 */

public interface EntryView<T> {

    void onSuccess(T t);

    void onError(String messgae,int code);

}
