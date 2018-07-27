package jx.edu.com.jiangxue.ui.view.layout;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import jx.edu.com.jiangxue.base.BaseRxFragment;
import jx.edu.com.jiangxue.ui.view.BaseTabFragmentAdapter;

/**
 * 作者：舒海洋 <br>
 * 时间：2016/11/17 14:29.
 */

public class TabContentLayout extends FrameLayout {
    private FragmentManager mManager;
    private BaseTabFragmentAdapter mFragmentAdapter;
    private List<BaseRxFragment> fragments;
    private OnTabChangeListener mOnTabChangeListener;

    public TabContentLayout(Context context) {
        super(context);
    }

    public TabContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabContentLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setFragmentAdapter(BaseTabFragmentAdapter mFragmentAdapter) {
        if (mFragmentAdapter != null) {
            // 初始化变量
            this.mFragmentAdapter = mFragmentAdapter;
            this.mManager = mFragmentAdapter.getFragmentManager();
            this.fragments = new ArrayList<>(mFragmentAdapter.getCount());

            // 初始化fragment列表
            int length = mFragmentAdapter.getCount();
            for (int i = 0; i < length; i++) {
                fragments.add(mFragmentAdapter.getFragment(i));
            }

            if (length > 0)
                showAtPosition(0);
        }
    }

    public void setOnTabChangeListener(OnTabChangeListener mOnTabChangeListener) {
        this.mOnTabChangeListener = mOnTabChangeListener;
    }

    public void showAtPosition(int position) {
        if (mFragmentAdapter == null || position < 0 || position >= mFragmentAdapter.getCount()) {
            return;
        }

        if (fragments == null || fragments.size() == 0) {
            return;
        }

        int length = mFragmentAdapter.getCount();
        BaseRxFragment fragment;
        for (int i = 0; i < length; i++) {
            fragment = fragments.get(i);
            if (i == position) {
                if (fragment.isAdded()) {
                    if (!fragment.isVisible())
                        mManager.beginTransaction().show(fragment).commitAllowingStateLoss();
                } else {
                    mManager.beginTransaction().add(this.getId(), fragment).commitAllowingStateLoss();
                }

                if (mOnTabChangeListener != null)
                    mOnTabChangeListener.onVisible(i, fragment);
            } else {
                if (fragment.isAdded()) {
                    if (fragment.isVisible())
                        mManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
                }
            }
        }
    }

    public interface OnTabChangeListener {
        void onVisible(int position, BaseRxFragment fragment);
    }
}
