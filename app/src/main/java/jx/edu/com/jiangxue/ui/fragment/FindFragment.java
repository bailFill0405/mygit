package jx.edu.com.jiangxue.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.PagerFragment;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.ui.apdater.FindTvAdapter;
import jx.edu.com.jiangxue.util.GridSpacingItemDecoration;

/**
 * 首页
 */
public class FindFragment extends PagerFragment implements View.OnClickListener {
    private RecyclerView recycler;

    public static FindFragment newInstance() {
        FindFragment indexFragment = new FindFragment();
        return indexFragment;
    }

    @Override
    protected int layout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        recycler=view.findViewById(R.id.recycler);
    }

    @Override
    public void onStart() {
        super.onStart();
        List<TestBean> testBeans = initListData();
        FindTvAdapter adapter=new FindTvAdapter();
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
//        recycler.setLayoutManager(staggered);
        int spanCount = 4; // 4 columns
        int spacing = 15; // 60px
        boolean includeEdge = true;
        recycler.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        adapter.setNewData(testBeans);
    }

    private List<TestBean> initListData() {
        List<TestBean> data = new ArrayList<>();
        data.add(new TestBean(1));
        data.add(new TestBean(2));
        data.add(new TestBean(3));
        data.add(new TestBean(4));
        data.add(new TestBean(1));
        data.add(new TestBean(2));
        data.add(new TestBean(3));
        data.add(new TestBean(4));
        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void visible() {

    }

    @Override
    public void invisible() {

    }
}
