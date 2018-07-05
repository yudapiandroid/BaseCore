package com.cmnt.core.net;

import java.util.List;

/**
 * Created by YuXin on 2018/7/4.
 */

public interface EntrysView<T> {

    void onSuccess(List<T> data,int total);

    void onError(String message,int code);

}
