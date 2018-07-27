package jx.edu.com.jiangxue.api;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 作者：guojuan
 * 创建时间：2018/5/4 14:04
 * 功能描述：
 */

public class RetrofitStringHelper {

    private static RetrofitStringHelper mInstance;
    private Retrofit mRetrofit;

    private RetrofitStringHelper() {
        //0.创建OkHttp对象设置打印报文参数
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.e("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();
//                                .header("Authorization", "bearer " + MyUtils.getToken(MyApplication.getContext()));

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();

        //1.创建Retrofit对象
        mRetrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(RequestUrl.BASE_URL) // 定义访问的主机地址
                .addConverterFactory(StringConvertFactory.create())  //解析方法
//                .client(RetrofitUtils.getInstance().addTimeOut(40).build())  //构建自己的OkHttpClient
                .build();
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RetrofitStringHelper getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitStringHelper.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitStringHelper();
                }
            }
        }
        return mInstance;
    }

    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }
}
