package jx.edu.com.jiangxue.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jelly.mango.util.StatusBarUtils;

import jx.edu.com.jiangxue.R;

/**
 * Created by 赖恒熠 on 2018/7/16.
 */
public abstract class BaseWindow {
    protected PopupWindow window;
    protected Context context;
    protected LayoutInflater inflater;


    public BaseWindow(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        initWindow();
//        calWidthAndHeight(context);

    }

    @SuppressLint("WrongConstant")
    protected void initWindow() {
        View view = inflater.inflate(layout(), null, false);
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(context);

        int screenHeight = getScreenHeight(context);

        int popupWindowHeight = screenHeight - statusBarHeight ;
        window = new PopupWindow(view, getWidth(), popupWindowHeight);
        window.setOutsideTouchable(true);
        window.setFocusable(true);
//        window.setBackgroundDrawable(new ColorDrawable(0xaa000000));
//        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.window_bg)));
        window.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.main_menu_animstyle);
        if (Build.VERSION.SDK_INT != 24) {
            window.update();
        }
        initView(view);
    }

    protected int getWidth() {
        return LinearLayout.LayoutParams.MATCH_PARENT;
    }

    protected int getHeight() {
        return LinearLayout.LayoutParams.MATCH_PARENT;
    }

    protected abstract int layout();

    protected abstract void initView(View view);

    public void showAsDropDown(View view) {
        if (window != null) {
            if (Build.VERSION.SDK_INT < 24) {
                window.showAsDropDown(view);
            } else {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                window.showAtLocation(view, Gravity.NO_GRAVITY, 0, y + view.getHeight());
            }
        }
    }

    public void showAtLocation(View view, int mode) {
        if (window != null) {
            window.showAtLocation(view, mode, 0, 0);
        }
    }

    public boolean isShowing() {
        if (window != null) {
            return window.isShowing();
        }
        return false;
    }

    public void dismiss() {
        if (window != null) {
            window.dismiss();
        }
    }

    /**
     * 设置PopupWindow的大小
     *
     * @param context
     */
    public void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        window.setWidth(metrics.widthPixels);
        //设置高度为全屏高度的70%
        window.setHeight((int) (metrics.heightPixels * 1.0));
    }


    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);
        int height = screenSize.y;
        return height;
    }

    public float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }
}
