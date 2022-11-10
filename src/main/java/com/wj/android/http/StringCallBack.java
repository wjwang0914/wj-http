package com.wj.android.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 作者：wangwnejie on 2017/9/21 22:10
 * 邮箱：wang20080990@163.com
 */

public abstract class StringCallBack extends CommonCallback {

    protected abstract void onSuccess(String response);

    @Override
    public void onResponse(Call<ResponseBody> call, ResponseBody responseBody) {
        try {
            onSuccess(responseBody.string());
        } catch (IOException e) {
            onFailure(call, e);
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("StringCallBack",t.toString());
    }
}
