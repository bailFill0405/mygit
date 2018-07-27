package jx.edu.com.jiangxue.api;

import retrofit2.Call;

/**
 * 作者：guojuan
 * 创建时间：2018/5/4 14:09
 * 功能描述：
 */


public class StringModel {
    private static StringModel mDataModel;
    private ApiI apiI;

    /**
     * 单例模式
     *
     * @return
     */
    public static StringModel getInstance() {
        if (mDataModel == null) {
            mDataModel = new StringModel();
        }
        return mDataModel;
    }


    private StringModel() {
        apiI = RetrofitStringHelper.getInstance().create(ApiI.class);
    }

    public Call<String> topay(String CategoryIds, String TopN) {
        Call<String> infoCall = apiI.topay(CategoryIds, TopN);
        return infoCall;
    }

}
