package jx.edu.com.jiangxue.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhouxiaochen on 2018/1/10.
 */

public abstract class BaseRecycAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Activity mContext;
    protected List<T> mData;

    public BaseRecycAdapter(Activity mContext, List<T> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return initView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindData(position, (BaseRecycleViewHolder) holder);
    }

    /**
     * 初始化布局，实例化ViewHolder
     *
     * @param
     * @return
     */
    protected abstract BaseRecycleViewHolder initView(ViewGroup convertView, int viewType);

    /**
     * 绑定数据
     *
     * @param holder
     */
    protected abstract void bindData(int position, BaseRecycleViewHolder holder);
}
