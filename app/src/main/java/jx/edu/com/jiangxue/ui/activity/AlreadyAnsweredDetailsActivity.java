package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.ui.apdater.QuestionImgAdapter;
import jx.edu.com.jiangxue.ui.view.window.ShareWindow;

public class AlreadyAnsweredDetailsActivity extends BaseRxActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.ecy)
    RecyclerView ecy;
    @BindView(R.id.ecy_teacher)
    RecyclerView ecy_teacher;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    private TestBean data;
    private ShareWindow window;
    @Optional
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("问题详情");
        iv_right.setImageResource(R.mipmap.pig);
        data = getIntent().getParcelableExtra("data");
        initViewData();
        initRefresh();
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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        ecy.setLayoutManager(manager);
        final QuestionImgAdapter questionImgAdapter = new QuestionImgAdapter();
        List<TestBean.TestBean2> test = data.getTest();
        questionImgAdapter.setNewData(test);
        ecy.setAdapter(questionImgAdapter);

        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayout.HORIZONTAL);
        ecy_teacher.setLayoutManager(manager2);
        ecy_teacher.setAdapter(questionImgAdapter);

        window = new ShareWindow(this);
    }
    private void initRefresh() {
        refreshLayout.setPrimaryColorsId(R.color.textSelectColor, android.R.color.white);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                refresh();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMoreWithNoMoreData();
                onLoad();
            }
        });
    }

    private void onLoad() {
        StyleableToast.makeText(this, "暂无更多", Toast.LENGTH_LONG, R.style.myToast).show();
    }

    private void refresh() {
        StyleableToast.makeText(this, "刷新成功", Toast.LENGTH_LONG, R.style.myToast).show();
    }
    @Override
    public int layout() {
        return R.layout.activity_already_answered_details;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        iv_back.setImageResource(R.mipmap.back);
        appbar.setBackgroundResource(R.color.bg);
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }

    @OnClick({R.id.iv_back, R.id.iv_right,R.id.rl_back})
    void onClick(View view) {
        switch (view.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_right:
                //分享
                if (window.isShowing()) {
                    window.dismiss();
                } else {
//                    window.calWidthAndHeight(this);
                    window.showAsDropDown(iv_right);
                }
                break;
        }
    }
    public static void startActivity(Activity activity, TestBean testBean) {
        Intent intent = new Intent();
        intent.setClass(activity, AlreadyAnsweredDetailsActivity.class).putExtra("data", testBean);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (window != null && window.isShowing()) {
            window.dismiss();
            return;
        }

        super.onBackPressed();
    }
}
