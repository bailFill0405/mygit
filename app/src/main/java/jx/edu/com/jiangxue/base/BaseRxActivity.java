package jx.edu.com.jiangxue.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.listerner.SoftKeyBoardListener;
import jx.edu.com.jiangxue.ui.view.layout.SlidingLayout;

/**
 * RxActivity基础类
 */
public abstract class BaseRxActivity extends RxAppCompatActivity {
    protected ProgressDialog progressDialog;
    @BindView(R.id.tv_right)
    protected TextView tv_right;
    @BindView(R.id.rl_back)
    protected RelativeLayout rl_back;
    @BindView(R.id.iv_back)
    protected ImageView iv_back;
    @BindView(R.id.iv_right)
    protected ImageView iv_right;
    @BindView(R.id.tv_title)
    protected TextView tv_title;
    @BindView(R.id.tv_left)
    protected TextView tv_left;
    @BindView(R.id.appbar)
    protected RelativeLayout appbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(layout());
        ButterKnife.bind(this);
        initView(savedInstanceState);
        addFuntion();
    }

    private void addFuntion() {
        setSlidingLayout();
        initProgressDialog();
        hideToolBar();
        setSoftKeyBoardListener();
    }

    @LayoutRes
    public abstract int layout();

    public abstract void initView(@Nullable Bundle savedInstanceState);

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    ;

    /**
     * 监听软键盘隐藏和显示的操作
     */
    private void setSoftKeyBoardListener() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                getWindow().setBackgroundDrawableResource(R.color.bg);//防止界面透明后，软键盘出现的瞬间能看到下层界面
                BoardShow();
            }

            @Override
            public void keyBoardHide(int height) {
                getWindow().setBackgroundDrawableResource(R.color.transparent);
                etValidate();
                BoardHide();
            }
        });
    }

    private void setSlidingLayout() {
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    private void hideToolBar() {
        getSupportActionBar().hide();
    }
    /**
     * 验证EditText中的内容是否合乎标准
     */
    protected abstract void etValidate();

    /**
     * 软键盘显示操作
     */
    protected abstract void BoardShow();
    /**
     * 软键盘隐藏
     */
    protected abstract void BoardHide();

    protected void initWindow() {

    }

    /**
     * 是否启用左滑退出（默认：是）
     */
    protected boolean enableSliding() {
        return true;
    }

    /**
     * 监听软键盘确认键的操作（默认提交）
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            etValidate();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 设置软键盘按下回车监听的接口（根据需求设置这个监听可以改变按下回车的action操作）
     */
    protected EditText.OnEditorActionListener editorActionListener =

            new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    keyBordAction(v, actionId, event);
                    return true;
                }
            };

    /**
     * 实现软键盘监听回车action操作的代码
     *
     * @param v
     * @param actionId
     * @param event
     */
    protected abstract void keyBordAction(TextView v, int actionId, KeyEvent event);

    /**
     * 增加业务测试接口
     */
    private TestListerner testListerner;

    public interface TestListerner {
        void test();
    }

    public TestListerner getTestListerner() {
        return testListerner;
    }

    public void setTestListerner(TestListerner testListerner) {
        this.testListerner = testListerner;
    }
}
