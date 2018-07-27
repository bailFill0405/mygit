package jx.edu.com.jiangxue.ui.view;

import android.support.v4.app.FragmentManager;

import jx.edu.com.jiangxue.base.BaseRxFragment;


/**
 * 作者：舒海洋 <br>
 * 时间：2016/11/17 14:04.
 */

public abstract class BaseTabFragmentAdapter {
    private FragmentManager mManager;

    public BaseTabFragmentAdapter(FragmentManager mManager) {
        this.mManager = mManager;
    }

    public FragmentManager getFragmentManager() {
        return mManager;
    }

    public abstract int getCount();

    public abstract BaseRxFragment getFragment(int position);

    public abstract String getTitle(int position);
}
