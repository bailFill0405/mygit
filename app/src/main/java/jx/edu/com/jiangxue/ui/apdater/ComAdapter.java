package jx.edu.com.jiangxue.ui.apdater;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * listview通用适配器
 */
public class ComAdapter<T> extends BaseAdapter {
    protected ProgressDialog progressDialog;
    protected List<T> data;
    protected Context context;
    protected LayoutInflater inflater;

    public ComAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        progressDialog = new ProgressDialog(context);
//        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }


    public ComAdapter(Context context, List<T> data) {
        this.data = data;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        progressDialog = new ProgressDialog(context);
//        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void refresh(List<T> data) {
        this.data = data;
//        LogUtil.e("TestData", data.toString());
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
