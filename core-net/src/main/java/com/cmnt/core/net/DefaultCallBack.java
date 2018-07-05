package com.cmnt.core.net;

import android.os.Handler;
import android.os.Looper;

import com.cmnt.core.net.convert.ResponceConvert;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by YuXin on 2018/7/4.
 */

public class DefaultCallBack implements Callback {


    private EntryView entryView;
    private EntrysView entrysView;
    private ResponceConvert responceConvert;

    public DefaultCallBack(EntryView entryView, EntrysView entrysView, ResponceConvert responceConvert) {
        this.entryView = entryView;
        this.entrysView = entrysView;
        this.responceConvert = responceConvert;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        // FIXME: 2018/7/4
        handError(e.getMessage(),500);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.isSuccessful()){
            handSuccess(call,response);
        }else{
            handError(response.message(),response.code());
        }
    } // end m


    /**
     *
     * 处理网络请求成功
     *
     * @param call
     * @param response
     */
    private void handSuccess(final Call call, final Response response) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(entryView != null && responceConvert != null){
                    responceConvert.convert(call,response,entryView);
                }
                if(entrysView != null && responceConvert != null){
                    responceConvert.convert(call,response,entrysView);
                }
            }
        });
    }


    private void handError(final String message, final int code){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                handErrorInMainThread(message,code);
            }
        });
    }

    private void handErrorInMainThread(String message, int code) {
        if(entryView != null){
            entryView.onError(message,code);
        }
        if(entrysView != null){
            entrysView.onError(message,code);
        }
    }

}
