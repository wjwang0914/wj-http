package com.wj.android.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 作者：wangwnejie on 2017/9/21 22:10
 * 邮箱：wang20080990@163.com
 */

public abstract class StringCallBack extends CommonCallback {

    protected abstract void onSuccess(String response);

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            if (!response.isSuccessful()) {
                this.onFailure(call, new Exception(String.format("%s : %d", "request failed, response's code is", response.code())), response.code());
                return;
            }

            if (response.body() == null) {
                this.onFailure(call, new Exception("service return data empty"), response.code());
                return;
            }
            onSuccess((response.body()).string());
        } catch (IOException e) {
            onFailure(call, e, response.code());
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t, int code) {
        Log.e("StringCallBack",t.toString());
    }
}
