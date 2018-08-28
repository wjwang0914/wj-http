package com.wj.android.http;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;


/**
 * 作者：wangwnejie on 2017/9/28 14:24
 * 邮箱：wang20080990@163.com
 */

public class GlobalConfig {
    public static final String DEFAULT_BASE_URL = "http://square.github.io/retrofit/";
    private volatile static GlobalConfig sInstance;
    private OkHttpClient.Builder mOkHttpClientBuilder;

    private GlobalConfig() {
        mOkHttpClientBuilder = new OkHttpClient.Builder();
    }

    public static GlobalConfig getInstance() {
        if (sInstance == null) {
            synchronized (GlobalConfig.class) {
                if (sInstance == null) {
                    sInstance = new GlobalConfig();
                }
            }
        }
        return sInstance;
    }

    public GlobalConfig debug(boolean debug) {
        if (debug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            addInterceptor(loggingInterceptor);
        }
        return this;
    }

    public GlobalConfig cookieJar(CookieJar cookieJar){
        mOkHttpClientBuilder.cookieJar(cookieJar);
        return this;
    }

    public GlobalConfig connectTimeout(int timeout) {
        mOkHttpClientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
        return this;
    }

    public GlobalConfig readTimeout(int timeout) {
        mOkHttpClientBuilder.readTimeout(timeout, TimeUnit.SECONDS);
        return this;
    }

    public GlobalConfig writeTimeout(int timeout) {
        mOkHttpClientBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
        return this;
    }

    public GlobalConfig retryCount(int retryCount) {
        addInterceptor(new RetryIntercepter(retryCount));
        return this;
    }

    public GlobalConfig addInterceptor(Interceptor interceptor) {
        mOkHttpClientBuilder.addInterceptor(interceptor);
        return this;
    }

    public GlobalConfig addNetworkInterceptor(Interceptor interceptor) {
        mOkHttpClientBuilder.addNetworkInterceptor(interceptor);
        return this;
    }

    public GlobalConfig addCallAdapterFactory(CallAdapter.Factory factory) {
        XRetrofit.getRetrofitBuilder().addCallAdapterFactory(factory);
        return this;
    }

    public GlobalConfig addConverterFactory(Converter.Factory factory) {
        XRetrofit.getRetrofitBuilder().addConverterFactory(factory);
        return this;
    }

    public GlobalConfig callFactory(Call.Factory factory) {
        XRetrofit.getRetrofitBuilder().callFactory(factory);
        return this;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return mOkHttpClientBuilder;
    }


}
