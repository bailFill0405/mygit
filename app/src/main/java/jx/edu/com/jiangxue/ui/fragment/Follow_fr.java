package jx.edu.com.jiangxue.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.ui.apdater.QuestionItemAdapter;


/**
 * Created by 赖恒熠 on 2018/5/9.
 */
@SuppressLint("ValidFragment")

public class Follow_fr extends android.support.v4.app.Fragment {
    private static ProgressDialog progressDialog;
    private String titles;
    private QuestionItemAdapter adapter;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.listv)
    ListView listv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    public static Follow_fr getInstance(String title) {
        Follow_fr sf = new Follow_fr(title);
        return sf;
    }

    public Follow_fr() {
    }

    public Follow_fr(String title) {
        this.titles = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setCanceledOnTouchOutside(false);
        View v = inflater.inflate(R.layout.follow_item, null);
        ButterKnife.bind(this,v);
        if("关注".equals(titles)){
            tv.setVisibility(View.GONE);
            listv.setVisibility(View.VISIBLE);
            adapter=new QuestionItemAdapter(getContext());
            adapter.refresh(initTestData());
            listv.setAdapter(adapter);
        }else if("榜单".equals(titles)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(titles);
            listv.setVisibility(View.GONE);
        }else if("全球".equals(titles)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(titles);
            listv.setVisibility(View.GONE);
        }
        setListerner();
        initRefresh();
        return v;
    }

    private void setListerner() {
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
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
        StyleableToast.makeText(getActivity(), "暂无更多", Toast.LENGTH_LONG, R.style.myToast).show();
    }

    private void refresh() {
        StyleableToast.makeText(getActivity(), "刷新成功", Toast.LENGTH_LONG, R.style.myToast).show();
    }

    private List<TestBean> initTestData() {
        List<TestBean>data=new ArrayList<>();
        List<TestBean.TestBean2>data1=new ArrayList<>();
        data1.add(new TestBean.TestBean2(1));
        List<TestBean.TestBean2>data2=new ArrayList<>();
        data2.add(new TestBean.TestBean2(2));
        data2.add(new TestBean.TestBean2(2));
        List<TestBean.TestBean2>data3=new ArrayList<>();
        data3.add(new TestBean.TestBean2(3));
        data3.add(new TestBean.TestBean2(3));
        data3.add(new TestBean.TestBean2(3));
        List<TestBean.TestBean2>data4=new ArrayList<>();
        data4.add(new TestBean.TestBean2(4));
        data4.add(new TestBean.TestBean2(4));
        data4.add(new TestBean.TestBean2(4));
        data4.add(new TestBean.TestBean2(4));
        data.add(new TestBean(1,data1));
        data.add(new TestBean(2,data2));
        data.add(new TestBean(3,data3));
        data.add(new TestBean(4,data4));
        Log.i("danum",data.toString());
        return data;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}

