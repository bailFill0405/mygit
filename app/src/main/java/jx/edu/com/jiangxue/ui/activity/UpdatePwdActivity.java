package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseHandler;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.util.EditUtil;

public class UpdatePwdActivity extends BaseRxActivity {
    private int time = 60;
    private final int WHAT_NEWS_AUTO_RUN = 1;
    private final int DELAY_TIME = 1000;
    private String mode = "reset";
    private boolean isFlag = false;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_pwd_agin)
    EditText et_pwd_agin;
    @BindView(R.id.bt_reset)
    Button bt_reset;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_code)
    TextView tv_code;
    private Handler handler = new BaseHandler(this) {
        @Override
        public void handleMessage(Message msg, int what) {
            switch (msg.what) {
                case WHAT_NEWS_AUTO_RUN:// 计时开始
                    time--;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            tv_code.setText("已发送(" + time + "s)");
                            if (time == 0) {
                                time = 60;
                                tv_code.setText("获取验证码");
                                tv_code.setEnabled(true);
//                                tv_code.setTextColor(Color.parseColor("#ffffff"));
                            } else {
                                handler.sendEmptyMessageDelayed(WHAT_NEWS_AUTO_RUN, DELAY_TIME);
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = getIntent().getStringExtra("mode");
        setMode();
    }

    private void setMode() {
        if ("reset".equals(mode)) {
            tv_title.setText("重置密码");
            tv_msg.setText("手机已经通过验证，请输入新密码");
            et_pwd.setHint("请输入新密码");
            et_pwd_agin.setHint("请再再次确认新密码");
            bt_reset.setText("重置密码");
            et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            et_pwd_agin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            tv_code.setVisibility(View.GONE);
            et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            et_pwd_agin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            tv_title.setText("找回密码");
            tv_msg.setText("请输入您注册时的手机号或邮箱");
            et_pwd.setHint("手机号或邮箱");
            et_pwd_agin.setHint("请输入验证码");
            bt_reset.setText("下一步");
            et_pwd_agin.setInputType(InputType.TYPE_CLASS_NUMBER);
            et_pwd.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@."));
            tv_code.setVisibility(View.VISIBLE);
            et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            et_pwd_agin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        }
    }

    @Override
    protected void etValidate() {
        List<EditText> lists = new ArrayList<>();
        lists.add(et_pwd);
        lists.add(et_pwd_agin);
        if (!EditUtil.etIsEmpty(EditUtil.getSoftReferenceList(lists))) {
            isFlag = true;
            bt_reset.setAlpha(1);
        } else {
            isFlag = false;
            bt_reset.setAlpha((float) 0.5);
        }
    }

    @Override
    protected void BoardShow() {

    }

    @Override
    protected void BoardHide() {

    }

    @Override
    public int layout() {
        return R.layout.activity_update_pwd;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        iv_back.setImageResource(R.mipmap.back);
        appbar.setBackgroundResource(R.color.bg);
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }

    public static void startActivity(Activity activity, String mode) {
        Intent intent = new Intent();
        intent.setClass(activity, UpdatePwdActivity.class).putExtra("mode", mode);
        activity.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.bt_reset, R.id.tv_code})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_code:
                if (!EditUtil.etIsEmpty(et_pwd)) {
                    code();
                } else {
                    StyleableToast.makeText(this, "请先完善账号信息", Toast.LENGTH_LONG, R.style.myToast).show();
                }
                break;
            case R.id.bt_reset:
                if (isFlag) {
                    if("reset".equals(mode)){
                        if(!EditUtil.verificationPwd(et_pwd,et_pwd_agin)){
                            reset();
                        }else {
                            StyleableToast.makeText(this, "两次密码不一致", Toast.LENGTH_LONG, R.style.myToast).show();
                        }
                    }else {
                        reset();
                    }

                } else {
                    StyleableToast.makeText(this, "请先完密码信息", Toast.LENGTH_LONG, R.style.myToast).show();
                }
                break;
        }
    }

    private void code() {
        tv_code.setEnabled(false);
        handler.sendEmptyMessageDelayed(WHAT_NEWS_AUTO_RUN, DELAY_TIME);
        StyleableToast.makeText(this, "短信发送成功", Toast.LENGTH_LONG, R.style.myToast).show();
    }

    private void reset() {
        StyleableToast.makeText(this, "重置成功", Toast.LENGTH_LONG, R.style.myToast).show();

    }
}
