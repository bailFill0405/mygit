package jx.edu.com.jiangxue.api;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者：guojuan
 * 创建时间：2018/5/4 14:03
 * 功能描述：
 */

public interface ApiI {

    @POST("?command=TopNews")
    Call<String> topay(@Query("CategoryIds") String CategoryIds,
                       @Query("TopN") String TopN);
}
