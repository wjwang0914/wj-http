package com.wj.android.http;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者：wangwnejie on 2017/9/18 17:19
 * 邮箱：wang20080990@163.com
 */

public interface ApiService {

    @GET
    Call<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params);

    @GET
    Call<ResponseBody> get(@Url String url);

    @GET
    Call<ResponseBody> getWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap);

    @GET
    Call<ResponseBody> getWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> post(@Url String url, @FieldMap Map<String, String> params);

    @POST
    Call<ResponseBody> post(@Url String url);

    @POST
    Call<ResponseBody> postBody(@Url String url, @Body RequestBody requestBody);

    @POST
    Call<ResponseBody> postBodyWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> postWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @PUT
    Call<ResponseBody> put(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> putWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @PATCH
    Call<ResponseBody> patch(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @DELETE
    Call<ResponseBody> delete(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> deleteWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> params);

    @Multipart
    @POST
    Call<ResponseBody> upload(@Url String url, @QueryMap Map<String, String> params, @Part List<MultipartBody.Part> parts);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);
}
