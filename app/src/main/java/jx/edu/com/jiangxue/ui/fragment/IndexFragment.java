package jx.edu.com.jiangxue.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.PagerFragment;
import jx.edu.com.jiangxue.ui.apdater.IndexPagerAdapter;
import jx.edu.com.jiangxue.ui.bean.TabEntity;

/**
 * 首页
 */
public class IndexFragment extends PagerFragment implements View.OnClickListener {
    private CommonTabLayout mTabLayout;
    private String[] mTitles = {"关注", "榜单","全球"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager mViewPager;
    public static IndexFragment newInstance() {
        IndexFragment indexFragment = new IndexFragment();
        return indexFragment;
    }

    @Override
    protected int layout() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        mTabLayout = view.findViewById(R.id.tl_1);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        mFragments.add(Follow_fr.getInstance("关注"));
        mFragments.add(Follow_fr.getInstance("榜单"));
        mFragments.add(Follow_fr.getInstance("全球"));
        mTabLayout.setTabData(mTabEntities);
        mViewPager = view.findViewById(R.id.vp_2);
        mViewPager.setAdapter(new IndexPagerAdapter(getChildFragmentManager(),mFragments,mTitles));
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
