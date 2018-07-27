package jx.edu.com.jiangxue.ui.view.window;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseWindow;
import jx.edu.com.jiangxue.ui.activity.PayOkActivity;
import jx.edu.com.jiangxue.ui.apdater.Pay_TypeAdapter;
import jx.edu.com.jiangxue.ui.bean.PayTypeEntity;
import jx.edu.com.jiangxue.ui.view.KeyboardView;

/**
 * Created by 赖恒熠 on 2018/7/17.
 */

public class Pay_ItemType_Window extends BaseWindow {
    private KeyboardView keyboard;
    private List<PayTypeEntity> payTypeEntities;
    private Button bt_confirm;
    private RecyclerView rcy;
    private Pay_TypeAdapter adapter;
    private String pay_mode = "余额";
    private ResultPayType resultPayType;
    private String mode = "rmb";

    public void setResultPayType(ResultPayType resultPayType) {
        this.resultPayType = resultPayType;
    }

    interface ResultPayType {
        void resultSucces(String type);
    }

    public Pay_ItemType_Window(Context context, String mode, KeyboardView keyboard) {
        super(context);
        this.mode = mode;
        this.keyboard = keyboard;
    }

    @Override
    protected int layout() {
        return R.layout.window_pay_itemtype;
    }

    @Override
    protected void initView(View view) {
        bt_confirm = view.findViewById(R.id.bt_confirm);
        if ("rmb".equals(PayOkActivity.mode)) {
            pay_mode = "余额";
        } else {
            pay_mode = "积分支付";
        }
        payTypeEntities = initData();
        adapter = new Pay_TypeAdapter(payTypeEntities.size() - 1);
        rcy = view.findViewById(R.id.rcy);
        rcy.setLayoutManager(new LinearLayoutManager(context));
        rcy.setAdapter(adapter);

        adapter.setNewData(payTypeEntities);
        setListerner();

    }

    private void setListerner() {
        rcy.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapt, View view, int position) {
                adapter.setChecked(position);
                pay_mode = payTypeEntities.get(position).getType();
            }
        });
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultPayType.resultSucces(pay_mode);
                if(!keyboard.isVisible()){
                    keyboard.show();
                }
                dismiss();
            }
        });
    }

    private List<PayTypeEntity> initData() {
        List<PayTypeEntity> data = new ArrayList<>();
        data.add(new PayTypeEntity("余额"));
        data.add(new PayTypeEntity("支付宝"));
        data.add(new PayTypeEntity("微信"));
        data.add(new PayTypeEntity("积分支付"));
        return data;
    }
}
