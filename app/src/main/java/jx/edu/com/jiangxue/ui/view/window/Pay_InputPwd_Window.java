package jx.edu.com.jiangxue.ui.view.window;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseWindow;
import jx.edu.com.jiangxue.ui.activity.PayActivity;
import jx.edu.com.jiangxue.ui.activity.PayOkActivity;
import jx.edu.com.jiangxue.ui.apdater.KeyboardAdapter;
import jx.edu.com.jiangxue.ui.view.KeyboardView;
import jx.edu.com.jiangxue.ui.view.PwdEditText;

/**
 * Created by 赖恒熠 on 2018/7/16.
 */

public class Pay_InputPwd_Window extends BaseWindow implements KeyboardAdapter.OnKeyboardClickListener{
    protected ProgressDialog progressDialog;
    private View anchorView;
    private PwdEditText mPetPwd;
    private RelativeLayout ll_pay_type;
    private Pay_Result pay_result;
    private Pay_ItemType_Window itemType_window;
    private ImageView iv_type;
    private TextView tv_type,tv_pay_type;
    private KeyboardView keyboardView;
    private List<String> datas;
    private String mode="rmb";
    //    private PayPsdInputView mPetPwd;
    public Pay_InputPwd_Window(Context context, View anchorView,String mode) {
        super(context);
        //传入activity中的view作为锚点view
        this.anchorView = anchorView;
        this.mode=mode;
    }

    public void setPay_result(Pay_Result pay_result) {
        this.pay_result = pay_result;
    }

    @Override
    public void onKeyClick(View view, RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 9: // 按下小数点
                String num = mPetPwd.getText().toString().trim();
                if (!num.contains(datas.get(position))) {
                    num += datas.get(position);
                    mPetPwd.setText(num);
                    mPetPwd.setSelection(mPetPwd.getText().length());
                }
                break;
            default: // 按下数字键
                if ("0".equals(mPetPwd.getText().toString().trim())) { // 第一个数字按下0的话，第二个数字只能按小数点
                    break;
                }
                mPetPwd.setText(mPetPwd.getText().toString().trim() + datas.get(position));
                mPetPwd.setSelection(mPetPwd.getText().length());
                break;
        }
    }

    @Override
    public void onDeleteClick(View view, RecyclerView.ViewHolder holder, int position) {
        // 点击删除按钮
        String num = mPetPwd.getText().toString().trim();
        if (num.length() > 0) {
            mPetPwd.setText(num.substring(0, num.length() - 1));
            mPetPwd.setSelection(mPetPwd.getText().length());
        }
    }


    public static interface Pay_Result {
        void resultMsg(String result);
    }

    @Override
    protected int layout() {
        return R.layout.window_input_pwd;
    }

    @Override
    protected void initWindow() {
        super.initWindow();

    }
    @Override
    protected void initView(View view) {
        tv_pay_type=view.findViewById(R.id.tv_pay_type);
        keyboardView=view.findViewById(R.id.keyboard_view);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        mPetPwd = view.findViewById(R.id.pet_pwd);
        ll_pay_type = view.findViewById(R.id.ll_pay_type);
        iv_type = view.findViewById(R.id.iv_type);
        tv_type = view.findViewById(R.id.tv_type);
        itemType_window = new Pay_ItemType_Window(context,mode,keyboardView);
        setListerner();
        initKeyBord();
        String mode = PayActivity.mode;

        if("rmb".equals(mode)){
            iv_type.setImageResource(R.mipmap.pig);
            tv_type.setText("余额");
        }else {
            iv_type.setImageResource(R.mipmap.integral);
            tv_type.setText("积分支付");
        }
//       test();

    }

    private void initKeyBord() {
        if (Build.VERSION.SDK_INT <= 10) {
            mPetPwd.setInputType(InputType.TYPE_NULL);
        } else {
            PayActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mPetPwd, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mPetPwd.setOnClickListener(new View.OnClickListener() {
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

    private void setListerner() {
        itemType_window.setResultPayType(new Pay_ItemType_Window.ResultPayType() {
            @Override
            public void resultSucces(String type) {
                tv_type.setText(type);
                switch (type) {
                    case "余额":
                        iv_type.setImageResource(R.mipmap.pig);
                        break;
                    case "微信":
                        iv_type.setImageResource(R.mipmap.weixin);
                        break;
                    case "支付宝":
                        iv_type.setImageResource(R.mipmap.alipay);
                        break;
                    case "积分支付":
                        iv_type.setImageResource(R.mipmap.integral);
                        break;
                }
            }
        });
        ll_pay_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyboardView.isVisible()) {
                    keyboardView.dismiss();
                }

                if (itemType_window.isShowing()) {
                    itemType_window.dismiss();
                } else {
                    itemType_window.calWidthAndHeight(context);
                    itemType_window.showAtLocation(anchorView, Gravity.TOP);
                }
            }
        });
        mPetPwd.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {

            @Override
            public void onInputFinish(String password) {
                pay_result.resultMsg("ok");
                //支付成功处理

                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
                progressDialog.setMessage("请稍等...");
//                try {
//                    Thread.sleep(500);
//                    progressDialog.dismiss();
                    PayOkActivity.startActivity((Activity) context,"pay");
                    dismiss();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


            }
        });
    }

    void test(){
//        mPetPwd.setComparePassword( new PayPsdInputView.onPasswordListener() {
//
//            @Override
//
//            public void onDifference(String oldPsd, String newPsd) {
//
//                // TODO: 2018/1/22  和上次输入的密码不一致  做相应的业务逻辑处理
//                ToastUtil.showLongToast("两次密码输入不同" + oldPsd + "!=" + newPsd);
//                mPetPwd.cleanPsd();
//            }
//
//            @Override
//            public void onEqual(String psd) {
//                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
//                ToastUtil.showLongToast("密码相同" + psd);
//                mPetPwd.cleanPsd();
//                mPetPwd.setComparePassword("");
//            }
//
//            @Override
//            public void inputFinished(String inputPsd) {
//                // TODO: 2018/1/3 输完逻辑
//                ToastUtil.showLongToast( "输入完毕：" + inputPsd );
//                mPetPwd.setComparePassword(inputPsd);
//
//            }
//
//        });
    }
}
