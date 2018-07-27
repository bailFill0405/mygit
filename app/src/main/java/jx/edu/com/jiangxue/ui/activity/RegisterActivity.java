package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import jx.edu.com.jiangxue.ui.view.SmartScollview;
import jx.edu.com.jiangxue.util.EditUtil;

public class RegisterActivity extends BaseRxActivity {
    private boolean isFlag = false;
    private int time = 60;
    private final int WHAT_NEWS_AUTO_RUN = 1;
    private final int DELAY_TIME = 1000;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.bt_register)
    Button bt_register;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.ll_picker)
    LinearLayout ll_picker;
    @BindView(R.id.tv_clause)
    TextView tv_clause;
    @BindView(R.id.scrollView)
    SmartScollview scrollView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_country_code)
    TextView tv_country_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void etValidate() {
        List<EditText> lists = new ArrayList<>();
        lists.add(et_phone);
        lists.add(et_code);
        lists.add(et_pwd);
        if (!EditUtil.etIsEmpty(EditUtil.getSoftReferenceList(lists))) {
            isFlag = true;
            bt_register.setAlpha(1);
        } else {
            isFlag = false;
            bt_register.setAlpha((float) 0.5);
        }
    }

    @Override
    protected void BoardShow() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scrollView == null || linearLayout == null) {
                    return;
                }
                int offset = linearLayout.getMeasuredHeight() - scrollView.getHeight();
                if (offset < 0) {
                    offset = 0;
                }

                scrollView.scrollTo(0, offset);

            }
        }, 100);
    }

    @Override
    protected void BoardHide() {

    }

    @OnClick({R.id.tv_right, R.id.rl_back, R.id.tv_code, R.id.bt_register, R.id.ll_picker, R.id.tv_clause})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                Login_PwdActivity.startActivity(this);
                finish();
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_code:
                if (!EditUtil.etIsEmpty(et_phone)) {
                    code();
                } else {
                    StyleableToast.makeText(this, "请先完善账号信息", Toast.LENGTH_LONG, R.style.myToast).show();
                }
                break;
            case R.id.bt_register:
                if (isFlag) {
                    if (!EditUtil.verificationPwd(et_pwd)) {
                        register();
                    } else {
                        StyleableToast.makeText(this, "密码长度未符合规定", Toast.LENGTH_LONG, R.style.myToast).show();
                    }
                } else {
                    StyleableToast.makeText(this, "请先完善注册信息", Toast.LENGTH_LONG, R.style.myToast).show();
                }
                break;
            case R.id.ll_picker:
                CountryActivity.startActivityForResult(this);

                break;
            case R.id.tv_clause:
                AgreementActivity.startActivity(this);
                break;
        }
    }

    private void code() {
        tv_code.setEnabled(false);
        handler.sendEmptyMessageDelayed(WHAT_NEWS_AUTO_RUN, DELAY_TIME);
        StyleableToast.makeText(this, "短信发送成功", Toast.LENGTH_LONG, R.style.myToast).show();
    }

    private void register() {
        StyleableToast.makeText(this, "注册成功", Toast.LENGTH_LONG, R.style.myToast).show();
        finish();
        Login_PwdActivity.startActivity(this);
    }

    @Override
    public int layout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tv_right.setText("已有账号？");
        et_phone.addTextChangedListener(watcher);
        et_pwd.addTextChangedListener(watcher);
        et_code.addTextChangedListener(watcher);
        et_phone.setOnEditorActionListener(editorActionListener);
        et_code.setOnEditorActionListener(editorActionListener);
        scrollView.setScanScrollChangedListener(smartScrollChangedListener);
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            if (et_phone.hasFocus()) {
                et_code.requestFocus();//让et_code获取焦点
                et_code.setSelection(et_code.getText().length());
            } else if (et_code.hasFocus()) {
                et_pwd.requestFocus();//让et_pwd获取焦点
                et_pwd.setSelection(et_pwd.getText().length());
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 12:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String countryName = bundle.getString("countryName");
                    String countryNumber = bundle.getString("countryNumber");
                    tv_country_code.setText(countryNumber);

                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static void startActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //TODO:
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            etValidate();
        }

        @Override
        public void afterTextChanged(Editable s) {
            //TODO:
        }
    };
    private SmartScollview.ISmartScrollChangedListener smartScrollChangedListener = new SmartScollview.ISmartScrollChangedListener() {
        @Override
        public void onScrolledToBottom() {

        }

        @Override
        public void onToBottom() {

        }

        @Override
        public void onScrolledToTop() {

        }

        @Override
        public void onToTop() {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                if (RegisterActivity.this.getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(RegisterActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
    };

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
}
