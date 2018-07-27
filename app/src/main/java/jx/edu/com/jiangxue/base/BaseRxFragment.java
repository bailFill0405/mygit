package jx.edu.com.jiangxue.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * RxFragment基础类
 */
public abstract class BaseRxFragment extends RxFragment {
    protected ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layout(), container, false);
        ButterKnife.bind(this, view);
        return inflater.inflate(layout(), null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        initView(view, savedInstanceState);
    }

    @LayoutRes
    protected abstract int layout();

    protected abstract void initView(View view, @Nullable Bundle savedInstanceState);
}
