package com.wj.android.http;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 作者：wangwnejie on 2023/3/22
 * 邮箱：wang20080990@163.com
 */

public abstract class SimpleGsonCallback<T> extends CommonCallback {

    protected abstract void onSuccess(T response);
    protected abstract void onFailure(Throwable t);

    protected String convertResponse(String response){
        return response;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, ResponseBody responseBody) {
        try{
            T bean = new Gson().fromJson(convertResponse(responseBody.string()), getType(this));
            onSuccess(bean);
        } catch (Exception e) {
            onFailure(call,e);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        onFailure(t);
    }

    private static <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("did not fill in the generic parameters");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }
}
