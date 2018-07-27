package jx.edu.com.jiangxue.https;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jx.edu.com.jiangxue.api.Hosts;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 网络请求管理类，基于Request对retrofit的二次封装
 */
public class RequestHelper {
    public static final int ERROR_CODE = 500;
    private static final int CONNECT_TIMEOUT = 5;  //请求超时时间，单位：min
    private static final int RW_TIMEOUT = 5;       //读写超时时间，单位：min
    private static RequestHelper instance;
    private static RequestAPI requestAPI;
    private Interception interception;
    private static Map<String, RequestAPI> cache = Collections.synchronizedMap(new HashMap<String, RequestAPI>());

    private RequestHelper() {
    }

    public static RequestHelper getInstance() {
        return getInstance(Hosts.HOST_URL);
    }

    public static RequestHelper getInstance(String url) {
        requestAPI = cache.get(url);
        if (requestAPI == null) {
            init(url);
        }
        if (instance == null) {
            synchronized (RequestHelper.class) {
                if (instance == null) {
                    instance = new RequestHelper();
                }
            }
        }
        return instance;
    }


    /**
     * 构造retrofit请求api
     *
     * @return retrofit请求api
     */
    private static synchronized void init(String baseUrl) {
        OkHttpClient.Builder localBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            localBuilder.addInterceptor(loggingInterceptor);
        }
        localBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-type", "text/plain;charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Connection", "keep-alive")
                        .build();
                return chain.proceed(request);
            }
        });

        try {
//            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(
//                        java.security.cert.X509Certificate[] chain,
//                        String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(
//                        java.security.cert.X509Certificate[] chain,
//                        String authType) throws CertificateException {
//                }
//
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return new java.security.cert.X509Certificate[0];
//                }
//            }};
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts,
//                    new java.security.SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext
//                    .getSocketFactory();
//            localBuilder.sslSocketFactory(sslSocketFactory)
//                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();

            //设置超时
            localBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES);
            localBuilder.readTimeout(RW_TIMEOUT, TimeUnit.MINUTES);
            localBuilder.writeTimeout(RW_TIMEOUT, TimeUnit.MINUTES);
            //错误重连
            localBuilder.retryOnConnectionFailure(true);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(localBuilder.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync())
                    .build();
            requestAPI = retrofit.create(RequestAPI.class);
            cache.put(baseUrl, requestAPI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkInit() {
        if (requestAPI == null) {
            throw new RuntimeException("RequestHelper must be init,Please call init methoc.");
        }
    }


    public RxManager get(String url, HTCallBack cb) {
        checkInit();
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
        Subscription subscription = requestAPI.executeGetCall(url)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new MyError())
                .subscribe(new MyAction(cb, mCompositeSubscription));
        mCompositeSubscription.add(subscription);
        return new RxManager(mCompositeSubscription);
    }

    public RxManager get(String url, Map<String, Object> params, HTCallBack cb) {
        checkInit();
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
        Subscription subscription = requestAPI.executeGetCall(url, params)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new MyError())
                .subscribe(new MyAction(cb, mCompositeSubscription));
        mCompositeSubscription.add(subscription);
        return new RxManager(mCompositeSubscription);
    }

    public RxManager getWx(String url, Map<String, Object> params, HTCallBack cb) {
        checkInit();
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
        Subscription subscription = requestAPI.executeGetCall(url, params)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new MyError())
                .subscribe(new MyAction1(cb, mCompositeSubscription));
        mCompositeSubscription.add(subscription);
        return new RxManager(mCompositeSubscription);
    }

    public RxManager post(String url, HTCallBack cb) {
        checkInit();
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
        Subscription subscription = requestAPI.executePostCall(url)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new MyError())
                .subscribe(new MyAction(cb, mCompositeSubscription));
        mCompositeSubscription.add(subscription);
        return new RxManager(mCompositeSubscription);
    }

    public RxManager post(String url, Map<String, Object> params, HTCallBack cb) {
        checkInit();
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
        Subscription subscription = requestAPI.executePostCall(url, params)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new MyError())
                .subscribe(new MyAction(cb, mCompositeSubscription));
        mCompositeSubscription.add(subscription);
        return new RxManager(mCompositeSubscription);
    }

    public RxManager postImg(String url, File file, HTCallBack cb) {
        checkInit();
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
        Subscription subscription = requestAPI.postImg(url, toDesRequestBody("head"), toMultipartBody("head.jpg", file))
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new MyError())
                .subscribe(new MyAction(cb, mCompositeSubscription));
        mCompositeSubscription.add(subscription);
        return new RxManager(mCompositeSubscription);
    }

    class MyError implements Func1<Throwable, Response<ResponseBody>> {

        @Override
        public Response<ResponseBody> call(Throwable throwable) {
//            LogUtils.e(throwable);
            ResponseBody requestBody = ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), throwable.getMessage());
            return Response.error(ERROR_CODE, requestBody);
        }
    }

/*
    class MyErrorAction implements Action1<Throwable> {
        private HTCallBack cb;
        private CompositeSubscription mCompositeSubscription;

        public MyErrorAction(HTCallBack cb, CompositeSubscription mCompositeSubscription) {
            this.cb = cb;
            this.mCompositeSubscription = mCompositeSubscription;
        }

        @Override
        public void call(Throwable throwable) {
            if (cb != null) {
                cb.onError(new ApiException(-1, throwable.getMessage()));
            }
            if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
                mCompositeSubscription.unsubscribe();
            }
        }
    }
*/

    class MyAction implements Action1<Response<ResponseBody>> {
        private HTCallBack cb;
        private CompositeSubscription mCompositeSubscription;

        public MyAction(HTCallBack cb, CompositeSubscription mCompositeSubscription) {
            this.cb = cb;
            this.mCompositeSubscription = mCompositeSubscription;
        }

        @Override
        public void call(Response<ResponseBody> responseBodyResponse) {
//            LogUtils.i(responseBodyResponse);
            boolean intercept = false;
            if (interception != null) {
                intercept = interception.intercept(responseBodyResponse);
            }
            if (!intercept) {
                if (responseBodyResponse.isSuccessful()) {
                    if (cb != null) {
                        try {
                            String result = responseBodyResponse.body().string();
//                            LogUtils.i(result);
                            Type t = ((ParameterizedType) cb.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                            HttpResponse response = new Gson().fromJson(result, t);
                            if (response.isResult()) {
                                cb.onSuccess(response);
                            } else {
                                cb.onError(new ApiException(response.getCode(), response.getMsg()));
                            }
                        } catch (Exception e) {
//                            LogUtils.e("MyAction", e);
                            cb.onError(new ApiException(responseBodyResponse.code(), e.getMessage()));
                        }
                    }
                } else {
                    if (cb != null) {
                        int code = responseBodyResponse.code();
                        String msg;
                        try {
                            msg = responseBodyResponse.errorBody().string();
                        } catch (Exception e) {
                            try {
                                msg = responseBodyResponse.body().string();
                            } catch (IOException e1) {
                                msg = responseBodyResponse.message();
                            }
                        }
                        cb.onError(new ApiException(code, msg));
                    }
                }
            }
            if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
                mCompositeSubscription.unsubscribe();
            }
        }
    }

    class MyAction1 implements Action1<Response<ResponseBody>> {
        private HTCallBack cb;
        private CompositeSubscription mCompositeSubscription;

        public MyAction1(HTCallBack cb, CompositeSubscription mCompositeSubscription) {
            this.cb = cb;
            this.mCompositeSubscription = mCompositeSubscription;
        }

        @Override
        public void call(Response<ResponseBody> responseBodyResponse) {
            boolean intercept = false;
            if (interception != null) {
                intercept = interception.intercept(responseBodyResponse);
            }
            if (!intercept) {
                if (responseBodyResponse.isSuccessful()) {
                    if (cb != null) {
                        try {
                            String result = responseBodyResponse.body().string();
//                            Type t = ((ParameterizedType) cb.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                            cb.onSuccess(result);
                        } catch (IOException e) {
//                            LogUtils.e("MyAction", e);
                            cb.onError(new ApiException(responseBodyResponse.code(), e.getMessage()));
                        }
                    }
                } else {
                    if (cb != null) {
                        int code = responseBodyResponse.code();
                        String msg;
                        try {
                            msg = responseBodyResponse.errorBody().string();
                        } catch (Exception e) {
                            try {
                                msg = responseBodyResponse.body().string();
                            } catch (IOException e1) {
                                msg = responseBodyResponse.message();
                            }
                        }
                        cb.onError(new ApiException(code, msg));
                    }
                }
            }
            if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
                mCompositeSubscription.unsubscribe();
            }
        }
    }

    private RequestBody toRequestBody(Map<String, Object> map) {
        if (map == null) {
            map = Collections.emptyMap();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
    }

    private RequestBody toDesRequestBody(String des) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), des);
    }

    private MultipartBody.Part toMultipartBody(String fileName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("text/plain"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    }

    public void setInterception(Interception interception) {
        this.interception = interception;
    }

    public interface Interception {
        public boolean intercept(Response<ResponseBody> responseBodyResponse);
    }
}

