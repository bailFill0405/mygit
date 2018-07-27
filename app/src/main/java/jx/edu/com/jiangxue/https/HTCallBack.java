package jx.edu.com.jiangxue.https;

/**
 * Created by hp on 2018/4/9.
 */

public abstract class HTCallBack<T> {
    public abstract void onSuccess(T t);

    public abstract void onError(ApiException e);
}
