package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.Optional;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.ui.apdater.KeyboardAdapter;
import jx.edu.com.jiangxue.ui.view.KeyboardView;
import jx.edu.com.jiangxue.ui.view.window.Pay_InputPwd_Window;
import jx.edu.com.jiangxue.ui.view.window.Pay_ItemType_Window;
import jx.edu.com.jiangxue.util.ViewUtils;

public class PayActivity extends BaseRxActivity implements KeyboardAdapter.OnKeyboardClickListener {
    public static Window window;
    public static final int PAY_RESULT = 6000;
    public static final String PAY_NUM_RMB = "金额支付";
    private static Intent intent;
    public static String mode = "rmb";
    private Pay_InputPwd_Window pwd_window;
    private Pay_ItemType_Window itemType_window;
    private List<String> datas;

    @BindViews({R.id.iv_back,R.id.iv_pay_rmb})
    List<ImageView> ivs;
    @BindViews({R.id.tv_title,R.id.tv_pay_type})
    List<TextView> tvs;
    @BindView(R.id.et_pay_rmb)
    EditText et_pay_rmb;
    @BindView(R.id.bt_confirm)
    Button bt_confirm;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvs.get(0).setText("支付");
        // 设置不调用系统键盘
        initKeyboardView();
        mode=getIntent().getStringExtra("mode");
        if("rmb".equals(mode)){
            tvs.get(1).setText("支付金额");
            ivs.get(1).setImageResource(R.mipmap.rmb_gray);
        }else {
            tvs.get(1).setText("支付积分");
            ivs.get(1).setImageResource(R.mipmap.integral_gray);
        }
    }

    @Override
    protected void etValidate() {

    }

    @Override
    protected void BoardShow() {

    }

    @Override
    protected void BoardHide() {

    }

    private void initKeyboardView() {
        window = getWindow();
        if (Build.VERSION.SDK_INT <= 10) {
            et_pay_rmb.setInputType(InputType.TYPE_NULL);
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(et_pay_rmb, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        et_pay_rmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!keyboardView.isVisible()) {
                    keyboardView.show();
                }
            }
        });
        datas = keyboardView.getDatas();
        keyboardView.setOnKeyBoardClickListener(this);
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static void startActivityForResults(Activity activity,String mode) {
        intent = new Intent();
        intent.setClass(activity, PayActivity.class).putExtra("mode",mode);
        activity.startActivityForResult(intent, PAY_RESULT);
    }

    @Override
    public int layout() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        iv_back.setImageResource(R.mipmap.back);
        appbar.setBackgroundResource(R.color.bg);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }

    @Optional
    @Nullable
    @OnClick({R.id.iv_back, R.id.bt_confirm,R.id.rl_back})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_confirm:
                hideKeyBoardView();

                pwd_window = new Pay_InputPwd_Window(this, bt_confirm,mode);
                pwd_window.setPay_result(new Pay_InputPwd_Window.Pay_Result() {
                    @Override
                    public void resultMsg(String result) {
                        intent.putExtra(PAY_NUM_RMB, ViewUtils.getEditTextVaule(et_pay_rmb));
                        setResult(PAY_RESULT, intent);
                        finish();
                    }
                });
                if (pwd_window.isShowing()) {
                    pwd_window.dismiss();
                } else {
//                    pwd_window.calWidthAndHeight(this);
                    pwd_window.showAsDropDown(bt_confirm);
                }
                break;
        }
    }

    private void hideKeyBoardView() {
        if (keyboardView.isVisible()) {
            keyboardView.dismiss();
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (keyboardView.isVisible()) {
            keyboardView.dismiss();
            return;
        }
        if (pwd_window != null && pwd_window.isShowing()) {
            pwd_window.dismiss();
            return;
        }
        if (itemType_window != null && itemType_window.isShowing()) {
            itemType_window.dismiss();
            return;
        }

        super.onBackPressed();
    }


    @Override
    public void onKeyClick(View view, RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 9: // 按下小数点
                String num = et_pay_rmb.getText().toString().trim();
                if (!num.contains(datas.get(position))) {
                    num += datas.get(position);
                    et_pay_rmb.setText(num);
                    et_pay_rmb.setSelection(et_pay_rmb.getText().length());
                }
                break;
            default: // 按下数字键
                if ("0".equals(et_pay_rmb.getText().toString().trim())) { // 第一个数字按下0的话，第二个数字只能按小数点
                    break;
                }
                et_pay_rmb.setText(et_pay_rmb.getText().toString().trim() + datas.get(position));
                et_pay_rmb.setSelection(et_pay_rmb.getText().length());
                break;
        }
    }

    @Override
    public void onDeleteClick(View view, RecyclerView.ViewHolder holder, int position) {
        // 点击删除按钮
        String num = et_pay_rmb.getText().toString().trim();
        if (num.length() > 0) {
            et_pay_rmb.setText(num.substring(0, num.length() - 1));
            et_pay_rmb.setSelection(et_pay_rmb.getText().length());
        }
    }

}
