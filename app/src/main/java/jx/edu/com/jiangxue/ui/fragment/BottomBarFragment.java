package jx.edu.com.jiangxue.ui.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxFragment;
import jx.edu.com.jiangxue.ui.activity.ReleaseActivity;
import jx.edu.com.jiangxue.util.UserUtils;


/**
 * 底部导航栏
 */
public class BottomBarFragment extends BaseRxFragment implements View.OnClickListener {
    private TextView mTvIndex;
    private TextView mTvMsg;
    private TextView mTvFind;
    private TextView mTvUser;
    private ImageView mIvAdd;
    private OnTabSelect onTabSelect;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onTabSelect = (OnTabSelect) activity;
        if (onTabSelect == null) {
            throw new RuntimeException("需要实现OnTabSelect");
        }
    }

    @Override
    protected int layout() {
        return R.layout.fragment_bottom_bar;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        mTvIndex = view.findViewById(R.id.mTvIndex);
        mTvMsg = view.findViewById(R.id.mTvMsg);
        mIvAdd = view.findViewById(R.id.mIvAdd);
        mTvUser = view.findViewById(R.id.mTvUser);
        mTvFind = view.findViewById(R.id.mTvFind);
        mTvIndex.setOnClickListener(this);
        mTvMsg.setOnClickListener(this);
        mTvUser.setOnClickListener(this);
        mTvFind.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
        changeView(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTvIndex:
                onTabSelect.onSelect(0);
                changeView(0);
                break;
            case R.id.mTvMsg:
                // 登录权限判断
//                if (MyUtils.intoLogins(getActivity())) {
                onTabSelect.onSelect(1);
                changeView(1);
//                }
                break;
            case R.id.mTvFind:
                // 登录权限判断
//                if (MyUtils.intoLogins(getActivity())) {
                onTabSelect.onSelect(2);
                changeView(2);
//                }
                break;
            case R.id.mTvUser:
                // 登录权限判断
//                if (MyUtils.intoLogins(getActivity())) {
                UserUtils.getInstance(getContext()).isUserLogin(true);
                onTabSelect.onSelect(3);
                changeView(3);
//                }
                break;
            case R.id.mIvAdd:
                //发布
                ReleaseActivity.startActivity(getActivity());
                break;
        }
    }

    public void changeView(int position) {
        switch (position) {
            case 0: {
                Drawable index = ContextCompat.getDrawable(getActivity(), R.mipmap.tab_home);
                index.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvIndex.setCompoundDrawables(null, index, null, null);
                mTvIndex.setTextColor(getResources().getColor(R.color.textSelectColor));

                Drawable msg = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_msg);
                msg.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvMsg.setCompoundDrawables(null, msg, null, null);
                mTvMsg.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable find = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_find);
                find.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvFind.setCompoundDrawables(null, find, null, null);
                mTvFind.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable user = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_my);
                user.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvUser.setCompoundDrawables(null, user, null, null);
                mTvUser.setTextColor(getResources().getColor(R.color.textUnselectColor));
            }
            break;
            case 1: {
                Drawable index = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_home);
                index.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvIndex.setCompoundDrawables(null, index, null, null);
                mTvIndex.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable msg = ContextCompat.getDrawable(getActivity(), R.mipmap.tab_msg);
                msg.setBounds(0, 0, (int) (index.getMinimumWidth() * 1.1), (int) (index.getMinimumHeight() * 0.9));
                mTvMsg.setCompoundDrawables(null, msg, null, null);
                mTvMsg.setTextColor(getResources().getColor(R.color.textSelectColor));

                Drawable find = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_find);
                find.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvFind.setCompoundDrawables(null, find, null, null);
                mTvFind.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable user = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_my);
                user.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvUser.setCompoundDrawables(null, user, null, null);
                mTvUser.setTextColor(getResources().getColor(R.color.textUnselectColor));
            }
            break;
            case 2: {
                Drawable index = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_home);
                index.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvIndex.setCompoundDrawables(null, index, null, null);
                mTvIndex.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable msg = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_msg);
                msg.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvMsg.setCompoundDrawables(null, msg, null, null);
                mTvMsg.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable find = ContextCompat.getDrawable(getActivity(), R.mipmap.tab_find);
                find.setBounds(0, 0, (int) (index.getMinimumWidth() * 1.1), (int) (index.getMinimumHeight() * 0.9));
                mTvFind.setCompoundDrawables(null, find, null, null);
                mTvFind.setTextColor(getResources().getColor(R.color.textSelectColor));

                Drawable user = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_my);
                user.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvUser.setCompoundDrawables(null, user, null, null);
                mTvUser.setTextColor(getResources().getColor(R.color.textUnselectColor));
            }
            break;
            case 3: {
                Drawable index = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_home);
                index.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvIndex.setCompoundDrawables(null, index, null, null);
                mTvIndex.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable msg = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_msg);
                msg.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvMsg.setCompoundDrawables(null, msg, null, null);
                mTvMsg.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable find = ContextCompat.getDrawable(getActivity(), R.mipmap.un_tab_find);
                find.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvFind.setCompoundDrawables(null, find, null, null);
                mTvFind.setTextColor(getResources().getColor(R.color.textUnselectColor));

                Drawable user = ContextCompat.getDrawable(getActivity(), R.mipmap.tab_my);
                user.setBounds(0, 0, (int) (index.getMinimumWidth() * 0.9), (int) (index.getMinimumHeight() * 0.9));
                mTvUser.setCompoundDrawables(null, user, null, null);
                mTvUser.setTextColor(getResources().getColor(R.color.textSelectColor));
            }
            break;
        }
    }

    public interface OnTabSelect {
        void onSelect(int position);
    }
}
