package jx.edu.com.jiangxue.https;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * retrofit请求接口封装
 * Created by ZRP on 2016/9/19.
 */
interface RequestAPI {

    // =============get请求============
    @GET
    Observable<Response<ResponseBody>> executeGetCall(@Url String url, @QueryMap Map<String, Object> info);

    @GET
    Observable<Response<ResponseBody>> executeGetCall(@Url String url);

    // =============post请求============4

    @POST
    Observable<Response<ResponseBody>> executePostCall(@Url String url, @QueryMap Map<String, Object> body);

    @POST
    Observable<Response<ResponseBody>> executePostCall(@Url String url);

    @Multipart
    @POST
    Observable<Response<ResponseBody>>
    postImg(@Url String url, @Part("description") RequestBody description, @Part MultipartBody.Part file);

}