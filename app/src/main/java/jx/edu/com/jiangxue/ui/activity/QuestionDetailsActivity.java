package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.ui.apdater.QuestionImgAdapter;

public class QuestionDetailsActivity extends BaseRxActivity {
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.tv_uName)
    TextView tv_uName;
    @BindView(R.id.ecy)
    RecyclerView ecy;
    @BindView(R.id.tv_answer)
    TextView tv_answer;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    private TestBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getIntent().getParcelableExtra("data");
        initViewData();
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

    private void initViewData() {
        tv_title.setText("问题详情");
//        iv_right.setImageResource();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        ecy.setLayoutManager(manager);
        final QuestionImgAdapter questionImgAdapter = new QuestionImgAdapter();
        List<TestBean.TestBean2> test = data.getTest();

        questionImgAdapter.setNewData(test);
        ecy.setAdapter(questionImgAdapter);

        int type = data.getType();
        if (2 == type) {
            Drawable answer = getResources().getDrawable(R.mipmap.answer);
            answer.setBounds(0, 0, answer.getMinimumWidth(), answer.getMinimumHeight());
            Drawable unanswered = getResources().getDrawable(R.mipmap.unanswered);
            unanswered.setBounds(0, 0, unanswered.getMinimumWidth(), unanswered.getMinimumHeight());
            tv_comment.setCompoundDrawables(answer, null, null, null);
            tv_uName.setCompoundDrawables(unanswered, null, null, null);
            tv_comment.setText("");
        } else {
            Drawable comment = getResources().getDrawable(R.mipmap.comment);
            comment.setBounds(0, 0, comment.getMinimumWidth(), comment.getMinimumHeight());
            Drawable in_the_solution = getResources().getDrawable(R.mipmap.in_the_solution);
            in_the_solution.setBounds(0, 0, in_the_solution.getMinimumWidth(), in_the_solution.getMinimumHeight());
            tv_comment.setCompoundDrawables(comment, null, null, null);
            tv_uName.setCompoundDrawables(in_the_solution, null, null, null);
            tv_comment.setText("40");

        }
    }

    @OnClick({R.id.rl_back, R.id.iv_right, R.id.tv_answer})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_right:
                //分享
                break;
            case R.id.tv_answer:
                //解答
                AnswerActivity.startActivity(this,data);
                finish();
                break;
        }
    }

    @Override
    public int layout() {
        return R.layout.activity_question_details_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        iv_back.setImageResource(R.mipmap.back);
        appbar.setBackgroundResource(R.color.bg);
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }

    public static void startActivity(Activity activity, TestBean testBean) {
        Intent intent = new Intent();
        intent.setClass(activity, QuestionDetailsActivity.class).putExtra("data", testBean);
        activity.startActivity(intent);
    }
}
