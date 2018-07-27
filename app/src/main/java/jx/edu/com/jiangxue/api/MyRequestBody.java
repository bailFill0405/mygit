package jx.edu.com.jiangxue.api;

import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyRequestBody {


    public static RequestBody create(Object object){
        String strJson = new Gson().toJson(object);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),strJson);
    }

    public static MultipartBody.Part creatMultipart(File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return  MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    }

}
