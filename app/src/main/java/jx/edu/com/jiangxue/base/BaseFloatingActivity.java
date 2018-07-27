package jx.edu.com.jiangxue.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 作者：guojuan
 * 创建时间：2017/6/7 13:29
 * 功能描述：悬浮activity基类
 */


public abstract class BaseFloatingActivity extends RxAppCompatActivity {
    protected ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getContentViewId());
        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        initView(savedInstanceState);
    }

    protected void setWindow(double heigth, double width, int which) {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (int) (display.getHeight() * heigth);
        params.width = (int) (display.getWidth() * width);
        params.alpha = 1.0f;
        getWindow().setAttributes(params);
        getWindow().setGravity(which);
        setFinishOnTouchOutside(true);
    }

    public abstract int getContentViewId();

    public abstract void initView(Bundle savedInstanceState);
}
