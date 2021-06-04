package com.wj.android.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 作者：wangwnejie on 2017/9/20 11:48
 * 邮箱：wang20080990@163.com
 */

public abstract class CommonCallback {

    public void onStart(Call<ResponseBody> call){

    }

    public abstract void onResponse(Call<ResponseBody> call, Response<ResponseBody> response);


    public abstract void onFailure(Call<ResponseBody> call, Throwable t, int code);

    public void onFinish(Call<ResponseBody> call) {

    }
}
