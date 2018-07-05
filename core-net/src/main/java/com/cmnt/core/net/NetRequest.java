package com.cmnt.core.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cmnt.core.net.convert.ParamsConvert;
import com.cmnt.core.net.convert.ResponceConvert;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 *
 * Created by YuXin on 2018/7/4.
 *
 *
 */
public class NetRequest {

    public static final int NO_NET_CODE = 0x00FA; // 没有网络返回的编码
    private static final String NO_NET_MESSAGE = "请检查网络设置";

    private List<ParamsConvert> paramsConverts = new ArrayList<>();
    private ResponceConvert responceConvert;
    private GenerateNetBuilder netBuilder;
    public static OkHttpClient okHttpClient;

    public NetRequest(ResponceConvert convert,GenerateNetBuilder netBuilder,ParamsConvert... pcs){
        paramsConverts.clear();
        if(pcs != null){
            for (int i = 0;i < pcs.length;i++){
                paramsConverts.add(pcs[i]);
            }
        }
        this.netBuilder = netBuilder;
        this.responceConvert = convert;
    } // end m

    public synchronized void get(String url,Object data,EntryView callBack){
        doNetByMethod(url,data,HttpMethod.GET,callBack);
    }
    public synchronized void get(String url,Object data,EntrysView callBack){
        doNetByMethod(url,data,HttpMethod.GET,callBack);
    }
    public synchronized void post(String url,Object data,EntryView callBack){
        doNetByMethod(url,data,HttpMethod.POST,callBack);
    }
    public synchronized void post(String url,Object data,EntrysView callBack){
        doNetByMethod(url,data,HttpMethod.POST,callBack);
    }


    public synchronized void doNetByMethod(String url, Object data, String method, final EntryView callBack){
        if(!isNetworkAvailable()){
            callBack.onError(NO_NET_MESSAGE,NO_NET_CODE);
        }
        doNetByMethod(url, data, method, new DefaultCallBack(callBack,null,responceConvert));
    }

    public synchronized void doNetByMethod(String url,Object data,String method,EntrysView callBack){
        if(!isNetworkAvailable()){
            callBack.onError(NO_NET_MESSAGE,NO_NET_CODE);
        }
        doNetByMethod(url, data, method, new DefaultCallBack(null,callBack,responceConvert));
    }

    private synchronized void doNetByMethod(String url,Object data,String method,Callback callback){
        if(netBuilder == null){
            throw  new NullPointerException(" ==> GenerateNetBuilder is must ...");
        }
        Request.Builder builder = netBuilder.generate(url,data,method);
        builder.method(method,null);
        if(paramsConverts != null){
            for (ParamsConvert pc : paramsConverts){
                pc.convert(url,data, method,builder);
            }
        }
        doNet(builder.build(),callback);
    }


    public synchronized void doNet(Request request, Callback responseCallBack) {
        if(okHttpClient == null){
            throw new NullPointerException("  ==>  call NetCoreUtils.init() ?? ");
        }
        if(request == null){
            return;
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(responseCallBack);
    }

    public static boolean isNetworkAvailable() {
        if(NetCoreUtils.getApp() == null){
            return true;
        }
        ConnectivityManager connectivity = (ConnectivityManager) NetCoreUtils.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

}
