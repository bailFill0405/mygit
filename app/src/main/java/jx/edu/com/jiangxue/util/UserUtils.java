package jx.edu.com.jiangxue.util;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.SoftReference;

import jx.edu.com.jiangxue.ui.activity.Login_PwdActivity;
import jx.edu.com.jiangxue.ui.bean.SharedPreferencesContant;
import jx.edu.com.jiangxue.ui.bean.UserMsg;

/**
 * Created by 赖恒熠 on 2018/7/26.
 */

public class UserUtils {
    private SharedPreferencesHelper helper;
    private SoftReference<SharedPreferencesHelper> softSp;

    private Context context;

    private UserUtils() {
    }

    private UserUtils(Context context) {
        this.context = context;
        helper = new SharedPreferencesHelper(context, SharedPreferencesContant.USER_MSG);
        softSp = new SoftReference<SharedPreferencesHelper>(helper);
    }

    public static UserUtils getInstance(Context context) {
        return new UserUtils(context);
    }

    private SharedPreferencesHelper getSoftSp() {

        return softSp != null ? softSp.get() : null;
    }

    /**
     * 判断用户有无登录
     *
     * @return
     */
    public boolean isUserLogin() {
        if (softSp != null) {
            return (boolean) softSp.get().getSharedPreference(SharedPreferencesContant.USER_IS_LOGIN, false);
        }
        return false;
    }

    /**
     * 登录权限跳转
     *
     * @param isToLogin 是否需要跳转
     */
    public void isUserLogin(boolean isToLogin) {
        if (isToLogin) {
            if (softSp != null) {
                boolean isUserLogin = (boolean) softSp.get().getSharedPreference(SharedPreferencesContant.USER_IS_LOGIN, false);
                if (!isUserLogin) {
                    Login_PwdActivity.startActivity((Activity) context);
                }
            }
        }
    }

    /**
     * 保存用户登录信息
     *
     * @param userMsg
     */
    public void saveUserLoginMsg(UserMsg userMsg) {
        if (softSp != null) {
            SharedPreferencesHelper helper = softSp.get();
            helper.put(SharedPreferencesContant.USER_ACOUNT, userMsg.getAccount());
            helper.put(SharedPreferencesContant.USER_PASSWORD, userMsg.getPwd());
            helper.put(SharedPreferencesContant.USER_IS_LOGIN, true);
        }
    }

    /**
     * 获取用户登录信息
     */
    public UserMsg getUserLoginMsg() {
        UserMsg userMsg = new UserMsg();
        if (softSp != null) {
            SharedPreferencesHelper helper = softSp.get();
            userMsg.setAccount((String) helper.getSharedPreference(SharedPreferencesContant.USER_ACOUNT, "0"));
            userMsg.setPwd((String) helper.getSharedPreference(SharedPreferencesContant.USER_PASSWORD, "0"));
            userMsg.setLogin((boolean) helper.getSharedPreference(SharedPreferencesContant.USER_IS_LOGIN, false));
        }
        return userMsg;
    }

    /**
     * 滞空用户登录信息
     */
    public void removeUserLoginMsg() {
        if (softSp != null) {
            SharedPreferencesHelper helper = softSp.get();
            helper.put(SharedPreferencesContant.USER_ACOUNT, "0");
            helper.put(SharedPreferencesContant.USER_MSG, "0");
            helper.put(SharedPreferencesContant.USER_IS_LOGIN, false);
        }
    }
}
