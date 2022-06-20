package com.wj.android.http;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor implements Interceptor {
    private Map<String, String> mHeaderMap;

    public HeaderInterceptor(Map<String, String> headerMap) {
        this.mHeaderMap = headerMap;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder requestBuilder = request.newBuilder();
        for (Map.Entry<String, String> entry: mHeaderMap.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        return chain.proceed(requestBuilder.build());
    }
}
