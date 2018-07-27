package jx.edu.com.jiangxue.base;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

import jx.edu.com.jiangxue.ui.bean.What;
import jx.edu.com.jiangxue.util.LogUtil;

/**
 * Created by 赖恒熠 on 2018/7/25.
 * handle包裝类
 */

public abstract class BaseHandler extends Handler {
    private static final String TAG = "BaseHandle";
    protected WeakReference<BaseRxActivity> activityWeakReference;
    protected WeakReference<Fragment> fragmentWeakReference;

    private BaseHandler() {
    }//构造私有化,让调用者必须传递一个Activity 或者 Fragment的实例

    public BaseHandler(BaseRxActivity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    public BaseHandler(Fragment fragment) {
        this.fragmentWeakReference = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
        if (activityWeakReference == null || activityWeakReference.get() == null || activityWeakReference.get().isFinishing()) {
            // 确认Activity是否不可用
            LogUtil.i(TAG, "Activity is gone");
            handleMessage(msg, What.ACTIVITY_GONE);
        } else if (fragmentWeakReference == null || fragmentWeakReference.get() == null || fragmentWeakReference.get().isRemoving()) {
            //确认判断Fragment不可用
            LogUtil.i(TAG, "Fragment is gone");
            handleMessage(msg, What.ACTIVITY_GONE);
        } else {
            handleMessage(msg, msg.what);
        }
    }

    /**
     * 抽象方法用户实现,用来处理具体的业务逻辑
     *
     * @param msg
     * @param what {@link What}
     */
    public abstract void handleMessage(Message msg, int what);


}
