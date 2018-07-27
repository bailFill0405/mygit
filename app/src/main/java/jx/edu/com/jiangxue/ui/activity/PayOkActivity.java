package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.util.ToastUtil;

public class PayOkActivity extends BaseRxActivity {
    private Intent intent;
    public static String mode = "pay";
    @BindView(R.id.tv_msg)
    TextView textView;
    @BindView(R.id.bt_ok)
    Button bt_ok;
    @BindView(R.id.iv)
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode=getIntent().getStringExtra("mode");
        if ("pay".equals(mode)) {
            textView.setText("支付成功");
            iv.setImageResource(R.mipmap.pay_ok);
        }else if("answer".equals(mode)){
            textView.setText("提交完成,感谢解答");
//            iv.setImageResource(R.mipmap.pay_ok);
        } else {
            textView.setText("发布成功");
            iv.setImageResource(R.mipmap.pay_ok);
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

    @Override
    public int layout() {
        return R.layout.activity_pay_ok;
    }

    public static void startActivity(Activity activity, String mode) {
        Intent intent = new Intent();
        intent.setClass(activity, PayOkActivity.class).putExtra("mode", mode);
        activity.startActivity(intent);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }

    @OnClick(R.id.bt_ok)
    void onClick() {
        if ("pay".equals(mode)) {
            finish();
        } else if("answer".equals(mode)){
            finish();
        }else {
            ToastUtil.showLongToast("发布成功");
            finish();
        }
    }

}
