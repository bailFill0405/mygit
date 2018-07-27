package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.ui.bean.UserMsg;
import jx.edu.com.jiangxue.ui.view.SmartScollview;
import jx.edu.com.jiangxue.util.EditUtil;
import jx.edu.com.jiangxue.util.UserUtils;
import jx.edu.com.jiangxue.util.ViewUtils;

public class Login_PwdActivity extends BaseRxActivity {
    private boolean isFlag = false;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.tv_clause)
    TextView tv_clause;
    @BindView(R.id.tv_code_login)
    TextView tv_code_login;
    @BindView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.scrollView)
    SmartScollview scrollView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int layout() {
        return R.layout.activity_login__pwd;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tv_right.setText("注册新账户");
        et_account.addTextChangedListener(watcher);
        et_pwd.addTextChangedListener(watcher);
        et_account.setOnEditorActionListener(editorActionListener);
        scrollView.setScanScrollChangedListener(smartScrollChangedListener);
    }



    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            et_pwd.requestFocus();//让editText2获取焦点
            et_pwd.setSelection(et_pwd.getText().length());
        }
    }


    @OnClick({R.id.tv_right, R.id.rl_back, R.id.tv_clause, R.id.tv_code_login, R.id.tv_forget_pwd, R.id.bt_login})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                RegisterActivity.startActivity(this);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_clause:
                AgreementActivity.startActivity(this);
                break;
            case R.id.tv_code_login:
                CodeLoginActivity.startActivity(this);
                finish();
                break;
            case R.id.tv_forget_pwd:
                UpdatePwdActivity.startActivity(this, "reget");
                break;
            case R.id.bt_login:

                if (isFlag) {
                    login();
                } else {
                    StyleableToast.makeText(this, "请先完善登录信息", Toast.LENGTH_LONG, R.style.myToast).show();
                }
                break;
        }
    }

    private void login() {
        UserUtils.getInstance(this).saveUserLoginMsg(new UserMsg(ViewUtils.getEditTextVaule(et_account), ViewUtils.getEditTextVaule(et_pwd), true));
        MainActivity.startActivity(this);
    }


    protected void etValidate() {
        List<EditText> lists = new ArrayList<>();
        lists.add(et_account);
        lists.add(et_pwd);
        if (!EditUtil.etIsEmpty(EditUtil.getSoftReferenceList(lists))) {
            isFlag = true;
            bt_login.setAlpha(1);
        } else {
            isFlag = false;
            bt_login.setAlpha((float) 0.5);
        }
    }

    @Override
    protected void BoardShow() {
        new Handler().postDelayed(new Runnable() {
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

    public static void startActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, Login_PwdActivity.class);
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
                if (Login_PwdActivity.this.getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(Login_PwdActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
    };
}
