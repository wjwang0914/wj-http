package com.wj.android.http;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 作者：wangwnejie on 2017/9/20 15:35
 * 邮箱：wang20080990@163.com
 */

public abstract class GsonCallback<T> extends CommonCallback {

    private WeakReference<BaseView> mBaseView;
    private int mRequestId;

    public GsonCallback(BaseView baseView) {
        this(baseView, 0);
    }

    public GsonCallback(BaseView baseView, int requestId) {
        mBaseView = new WeakReference<BaseView>(baseView);
        mRequestId = requestId;
    }

    protected abstract void onSuccess(T response, BaseView baseView);

    private boolean checkNull() {
        return mBaseView == null || mBaseView.get() == null;
    }

    protected String convertResponse(String response){
        return response;
    }

    @Override
    public void onStart(Call<ResponseBody> call) {
        if(checkNull()) return;
        mBaseView.get().start(mRequestId);

    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(checkNull()) return;
        try{
            if (!response.isSuccessful()) {
                onFailure(call, new Exception(String.format("%s : %d", "request failed, response's code is", response.code())), response.code());
                return;
            }

            if (response.body() == null) {
                onFailure(call, new Exception("service return data empty"), response.code());
                return;
            }
            T bean = (new Gson()).fromJson(this.convertResponse(((ResponseBody)response.body()).string()), getType(this));
            onSuccess(bean, (BaseView)this.mBaseView.get());
        } catch (Exception e) {
            onFailure(call,e, response.code());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t, int code) {
        if(checkNull()) return;
        mBaseView.get().error(t, code, mRequestId);
    }

    @Override
    public void onFinish(Call<ResponseBody> call) {
        super.onFinish(call);
        if(checkNull()) return;
        mBaseView.get().end(mRequestId);
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
