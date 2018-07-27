package jx.edu.com.jiangxue.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.PagerFragment;

/**
 * 首页
 */
public class UserFragment extends PagerFragment implements View.OnClickListener {


    public static UserFragment newInstance() {
        UserFragment indexFragment = new UserFragment();
        return indexFragment;
    }

    @Override
    protected int layout() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
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
