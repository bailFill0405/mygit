package jx.edu.com.jiangxue.ui.apdater;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.bean.TestBean;

/**
 * Created by 赖恒熠 on 2018/7/18.
 */

public class MsgAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public MsgAdapter() {
        super(R.layout.item_msg, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        TextView tv_msg_type = helper.getView(R.id.tv_msg_type);

        switch (item.getType()) {
            case 1:
                tv_msg_type.setText("评论了你的问题");
                break;
            case 2:
                tv_msg_type.setText("为你点赞");
                break;
            case 3:
                tv_msg_type.setText("分享了你的发布");
                break;
            case 4:
                tv_msg_type.setText("解答了你的发布");
                break;
        }
    }
}
