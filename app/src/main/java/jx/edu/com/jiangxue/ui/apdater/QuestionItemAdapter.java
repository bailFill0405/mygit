package jx.edu.com.jiangxue.ui.apdater;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.ui.activity.AlreadyAnsweredDetailsActivity;
import jx.edu.com.jiangxue.ui.activity.QuestionDetailsActivity;

/**
 * Created by 赖恒熠 on 2018/7/12.
 */

public class QuestionItemAdapter extends ComAdapter<TestBean> {

    private Context contexts;

    public QuestionItemAdapter(Context context) {
        super(context);
        this.contexts = context;
    }

    //
//    @Override
//    public int getViewTypeCount() {
//        return 4;
//    }
    public int getItemViewType(int positon) {
        int ret = 0;
        TestBean bean = (TestBean) getItem(positon);
        switch (bean.getType()) {
            case 1:
                ret = 1;
                break;
            case 2:
                ret = 2;
                break;
            case 3:
                ret = 3;
                break;
            case 4:
                ret = 4;
                break;
        }
        return ret;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_list_question, parent, false);
            holder = new ViewHolder(convertView);
        }
        initData(holder, position);
        return convertView;
    }

    private List<TestBean.TestBean2> test = new ArrayList<>();

    private void initData(ViewHolder holder, int position) {
        final TestBean testBean = data.get(position);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        holder.ecy.setLayoutManager(manager);
        final QuestionImgAdapter questionImgAdapter = new QuestionImgAdapter();
        test = testBean.getTest();
        questionImgAdapter.setNewData(test);
        holder.ecy.setAdapter(questionImgAdapter);

        int type = testBean.getType();
        Log.i("sa1", String.valueOf(type));
        if (2 == type) {
            Drawable answer = contexts.getResources().getDrawable(R.mipmap.answer);
            answer.setBounds(0, 0, answer.getMinimumWidth(), answer.getMinimumHeight());
            Drawable unanswered = contexts.getResources().getDrawable(R.mipmap.unanswered);
            unanswered.setBounds(0, 0, unanswered.getMinimumWidth(), unanswered.getMinimumHeight());
            holder.tv_comment.setCompoundDrawables(answer, null, null, null);
            holder.tv_uName.setCompoundDrawables(unanswered, null, null, null);
            holder.tv_comment.setText("");
            holder.tv_describe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuestionDetailsActivity.startActivity((Activity) context,testBean);
                }
            });
        } else {
            final Drawable comment = contexts.getResources().getDrawable(R.mipmap.comment);
            comment.setBounds(0, 0, comment.getMinimumWidth(), comment.getMinimumHeight());
            Drawable in_the_solution = contexts.getResources().getDrawable(R.mipmap.in_the_solution);
            in_the_solution.setBounds(0, 0, in_the_solution.getMinimumWidth(), in_the_solution.getMinimumHeight());
            holder.tv_comment.setCompoundDrawables(comment, null, null, null);
            holder.tv_comment.setText("40");
            holder.tv_uName.setCompoundDrawables(in_the_solution, null, null, null);
            holder.tv_describe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlreadyAnsweredDetailsActivity.startActivity((Activity) context,testBean);
                }
            });
        }
    }

    public static class ViewHolder {
        @BindView(R.id.ecy)
        public RecyclerView ecy;
        @BindView(R.id.tv_comment)
        TextView tv_comment;
        @BindView(R.id.tv_uName)
        TextView tv_uName;
        @BindView(R.id.tv_describe)
        TextView tv_describe;

        public ViewHolder(View convertView) {
            convertView.setTag(this);
            ButterKnife.bind(this, convertView);
        }
    }
}
