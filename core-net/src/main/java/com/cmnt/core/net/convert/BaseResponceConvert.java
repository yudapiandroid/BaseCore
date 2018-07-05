package com.cmnt.core.net.convert;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cmnt.core.net.EntryView;
import com.cmnt.core.net.EntrysView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YuXin on 2018/7/4.
 */

public class BaseResponceConvert implements ResponceConvert {

    private static final String JSON_R_E = "数据错误请稍后再试";

    @Override
    public void convert(Call call, Response response, EntryView entryView) {
        try {
            String jsonData = response.body().string();
            parseResponceEntry(jsonData,entryView);
        } catch (Exception e) {
            entryView.onError(JSON_R_E,500);
        }
    }

    /**
     *
     * 返回实体 只能是一个实体泛型
     *
     * @param jsonData
     */
    private void parseResponceEntry(String jsonData, EntryView entryView) throws ClassNotFoundException {
        Class clazz = entryView.getClass();
        String str = getTTypeStr(clazz);
        if(TextUtils.isEmpty(str)){
            //没有泛型 返回String
            entryView.onSuccess(jsonData);
            return;
        }
        str = str.replace("<",",").replace(">","").replace("class ","");
        String[] enTy = str.split(",");
        if(enTy.length == 1){
            entryView.onSuccess(JSON.parseObject(jsonData,Class.forName(enTy[0])));
        } else {
            entryView.onError("EntryView only one entry!!!",400);
        }
    }// end m


    @Override
    public void convert(Call call, Response response, EntrysView entrysView) {
        try {
            String jsonData = response.body().string();
            parseResponceEntrys(jsonData,entrysView);
        } catch (Exception e) {
            entrysView.onError(JSON_R_E,500);
        }
    }

    private void parseResponceEntrys(String jsonData, EntrysView entrysView) {
        // FIXME: 2018/7/4
    }


    private String getTTypeStr(Class clazz){
        Type type = clazz.getGenericSuperclass();
        if(type == null || !(type instanceof ParameterizedType)){
            return null;
        }
        ParameterizedType pt = (ParameterizedType) type;
        Type[] types = pt.getActualTypeArguments();
        if(types == null || types.length == 0){
            return null;
        }
        return types[0].toString();
    }

}
