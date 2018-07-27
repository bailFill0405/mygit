package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import butterknife.OnClick;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;

public class AgreementActivity extends BaseRxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public int layout() {
        return R.layout.activity_agreement;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tv_title.setText("匠学用户服务协议");
        appbar.setBackgroundResource(R.color.bg);
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }

    @OnClick(R.id.rl_back)
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, AgreementActivity.class);
        activity.startActivity(intent);
    }
}
