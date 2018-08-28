package com.wj.android.http;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;

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
    public void onResponse(Call<ResponseBody> call, ResponseBody responseBody) {
        if(checkNull()) return;
        try{
            T bean = new Gson().fromJson(convertResponse(responseBody.string()), getType(this));
            onSuccess(bean, mBaseView.get());
        } catch (Exception e) {
            onFailure(call,e);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if(checkNull()) return;
        mBaseView.get().error(t,mRequestId);
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
