package com.cmnt.core.net;

import okhttp3.Request;

/**
 * Created by YuXin on 2018/7/5.
 */

public interface GenerateNetBuilder {

    Request.Builder generate(String url,Object data,String method);

}
