package jx.edu.com.jiangxue.ui.apdater;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.bean.TestBean;

/**
 * Created by 赖恒熠 on 2018/7/18.
 */

public class FindTvAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public FindTvAdapter() {
        super(R.layout.item_find, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        TextView tv_likes = helper.getView(R.id.tv_likes);
        switch (item.getType()) {
            case 1:
//                tv_likes.setText("八级钢琴");
                tv_likes.setText("钢琴");
                break;
            case 2:
//                tv_likes.setText("钢琴");
                tv_likes.setText("八级钢琴");
                break;
            case 3:
                tv_likes.setText("小一上数学");
                break;
            case 4:
                tv_likes.setText("小一上数学");
                break;
        }
    }
}
