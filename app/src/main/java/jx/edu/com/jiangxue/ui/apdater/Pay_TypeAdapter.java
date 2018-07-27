package jx.edu.com.jiangxue.ui.apdater;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.ui.bean.PayTypeEntity;

/**
 * Created by 赖恒熠 on 2018/7/17.
 */

public class Pay_TypeAdapter extends BaseQuickAdapter<PayTypeEntity, BaseViewHolder> {
    private int checked = 0;

    public void setChecked(int checked) {
        this.checked = checked;
        notifyDataSetChanged();
    }

    public Pay_TypeAdapter(int mode) {
        super(R.layout.item_pay_type, null);
        checked=mode;
    }

    @Override
    protected void convert(BaseViewHolder helper, PayTypeEntity item) {
        ImageView iv_type = helper.getView(R.id.iv_type);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_msg = helper.getView(R.id.tv_msg);
        RadioButton rb = helper.getView(R.id.rb);
        String type = item.getType();
        tv_type.setText(type);
        switch (type) {
            case "余额":
                iv_type.setImageResource(R.mipmap.pig);
                tv_msg.setVisibility(View.GONE);
                break;
            case "微信":
                iv_type.setImageResource(R.mipmap.weixin);
                tv_msg.setVisibility(View.GONE);
                break;
            case "支付宝":
                iv_type.setImageResource(R.mipmap.alipay);
                tv_msg.setVisibility(View.GONE);
                break;
            case "积分支付":
                iv_type.setImageResource(R.mipmap.integral);
                tv_msg.setVisibility(View.VISIBLE);
                break;
        }
        if (checked == helper.getLayoutPosition()) {
            rb.setChecked(true);
        } else {
            rb.setChecked(false);
        }

    }
}
