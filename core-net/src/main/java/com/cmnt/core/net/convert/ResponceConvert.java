package com.cmnt.core.net.convert;

import com.cmnt.core.net.EntryView;
import com.cmnt.core.net.EntrysView;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YuXin on 2018/7/4.
 */
public interface ResponceConvert {

    void convert(Call call, Response response, EntryView entryView);

    void convert(Call call, Response response, EntrysView entrysView);

}
