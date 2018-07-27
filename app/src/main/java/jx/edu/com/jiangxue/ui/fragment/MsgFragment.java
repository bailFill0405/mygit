package jx.edu.com.jiangxue.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.PagerFragment;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.ui.apdater.MsgAdapter;

/**
 * 消息
 */
public class MsgFragment extends PagerFragment {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MsgAdapter msgAdapter;

    public static MsgFragment newInstance() {
        MsgFragment chatFragment = new MsgFragment();
        return chatFragment;
    }

    @Override
    protected int layout() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<TestBean> initListData() {
        List<TestBean> data = new ArrayList<>();
        data.add(new TestBean(1));
        data.add(new TestBean(2));
        data.add(new TestBean(3));
        data.add(new TestBean(4));

        return data;
    }


    @Override
    public void onStart() {
        super.onStart();
        List<TestBean> testBeans = initListData();
        msgAdapter = new MsgAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(msgAdapter);
        msgAdapter.setNewData(testBeans);

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

    @Override
    public void visible() {

    }

    @Override
    public void invisible() {

    }
}
