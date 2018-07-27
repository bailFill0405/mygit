package jx.edu.com.jiangxue.ui.view.window;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseWindow;

/**
 * Created by 赖恒熠 on 2018/7/20.
 */

public class ShareWindow extends BaseWindow {
    private Button bt_cancel;
    public ShareWindow(Context context) {
        super(context);
    }

    @Override
    protected int layout() {
        return R.layout.window_share;
    }

    @Override
    protected void initView(View view) {

        bt_cancel = view.findViewById(R.id.bt_cancel);
        setListerner();
    }

    private void setListerner() {
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
