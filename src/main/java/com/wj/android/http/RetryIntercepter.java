package com.wj.android.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：wangwnejie on 2017/9/28 16:23
 * 邮箱：wang20080990@163.com
 */

public class RetryIntercepter implements Interceptor {
    private int maxRetry;
    private int retryNum;

    public RetryIntercepter(int maxRetry) {
        this.maxRetry = maxRetry;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            response = chain.proceed(request);
        }
        return response;
    }
}
