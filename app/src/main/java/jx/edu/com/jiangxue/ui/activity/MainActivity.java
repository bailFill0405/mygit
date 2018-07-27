package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.base.BaseRxFragment;
import jx.edu.com.jiangxue.base.PagerFragment;
import jx.edu.com.jiangxue.ui.fragment.BottomBarFragment;
import jx.edu.com.jiangxue.ui.fragment.FindFragment;
import jx.edu.com.jiangxue.ui.fragment.IndexFragment;
import jx.edu.com.jiangxue.ui.fragment.MsgFragment;
import jx.edu.com.jiangxue.ui.fragment.UserFragment;
import jx.edu.com.jiangxue.ui.view.BaseTabFragmentAdapter;
import jx.edu.com.jiangxue.ui.view.layout.TabContentLayout;


public class MainActivity extends BaseRxActivity implements BottomBarFragment.OnTabSelect {
    private TabContentLayout pagerLayout;
    private List<PagerFragment> fgs;

    private MainPageAdapter mainPageAdapter;
    private BottomBarFragment bottomBarFragment;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public int layout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        pagerLayout = findViewById(R.id.pagerLayout);
        bottomBarFragment = (BottomBarFragment) getSupportFragmentManager().findFragmentById(R.id.mBottomBarFragment);
        initPage();
    }


    private void initPage() {
        fgs = new ArrayList<>(4);
        fgs.add(IndexFragment.newInstance());
        fgs.add(MsgFragment.newInstance());
        fgs.add(FindFragment.newInstance());
        fgs.add(UserFragment.newInstance());
        mainPageAdapter = new MainPageAdapter(getSupportFragmentManager(), fgs);
        pagerLayout.setFragmentAdapter(mainPageAdapter);
        pagerLayout.setOnTabChangeListener(new TabContentLayout.OnTabChangeListener() {
            @Override
            public void onVisible(int position, BaseRxFragment fragment) {
                bottomBarFragment.changeView(position);
            }
        });
    }

    @Override
    public void onSelect(int position) {
        pagerLayout.showAtPosition(position);
    }

    class MainPageAdapter extends BaseTabFragmentAdapter {
        List<PagerFragment> fgs;

        MainPageAdapter(FragmentManager fm, List<PagerFragment> fgs) {
            super(fm);
            this.fgs = fgs;
        }

        @Override
        public int getCount() {
            return fgs.size();
        }

        @Override
        public BaseRxFragment getFragment(int position) {
            return fgs.get(position);
        }

        @Override
        public String getTitle(int position) {
            return null;
        }
    }

    @Override
    protected boolean enableSliding() {
        return false;
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
