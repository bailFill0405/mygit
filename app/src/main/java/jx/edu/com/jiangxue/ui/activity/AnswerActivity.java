package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.ui.apdater.ImagePickerAdapter;
import jx.edu.com.jiangxue.ui.view.window.SelectDialog;
import jx.edu.com.jiangxue.util.GlideImageLoader;
import jx.edu.com.jiangxue.util.ViewUtils;

public class AnswerActivity extends BaseRxActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 4;
    private boolean isFlag = false;
    //允许选择图片最大数
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_answer)
    EditText et_answer;
    @BindView(R.id.et_analysis)
    EditText et_analysis;
    @BindView(R.id.bt_send)
    Button bt_send;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("解答");
        setListerner();
    }

    protected void etValidate() {
        String answer = ViewUtils.getEditTextVaule(et_answer);
        String analysis = ViewUtils.getEditTextVaule(et_analysis);
        if (!TextUtils.isEmpty(answer) && !TextUtils.isEmpty(analysis)) {
            bt_send.setBackgroundResource(R.mipmap.radio_bt_buttom2);
            bt_send.setTextColor(getResources().getColor(R.color.bg));
            isFlag = true;
        } else {
            bt_send.setTextColor(getResources().getColor(R.color.textUnselectColor));
            bt_send.setBackgroundResource(R.mipmap.radio_bt_buttom);
            isFlag = false;
        }

    }

    @Override
    protected void BoardShow() {

    }

    @Override
    protected void BoardHide() {

    }

    private void setListerner() {


//        et_answer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                 /*判断是否是“GO”键*/
//                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    /*隐藏软键盘*/
//                    InputMethodManager imm = (InputMethodManager) v
//                            .getContext().getSystemService(
//                                    Context.INPUT_METHOD_SERVICE);
//                    if (imm.isActive()) {
//                        imm.hideSoftInputFromWindow(
//                                v.getApplicationWindowToken(), 0);
//                    }
//                    etValidate();
//                    Log.i("sadasda", "点击了完成");
//
//                    return true;
//                }
//                return false;
//            }
//        });
//        et_analysis.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                //判断是否是“完成”键
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    //隐藏软键盘
//                    InputMethodManager imm = (InputMethodManager) v
//                            .getContext().getSystemService(
//                                    Context.INPUT_METHOD_SERVICE);
//                    if (imm.isActive()) {
//                        imm.hideSoftInputFromWindow(
//                                v.getApplicationWindowToken(), 0);
//                    }
//                    etValidate();
//                    Log.i("sadasda", "点击了完成");
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public int layout() {
        return R.layout.activity_answer;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        iv_back.setImageResource(R.mipmap.back);
        appbar.setBackgroundResource(R.color.bg);
        initImagePicker();
        initWidget();

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(true);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public static void startActivity(Activity activity, TestBean testBean) {
        Intent intent = new Intent();
        intent.setClass(activity, AnswerActivity.class).putExtra("data", testBean);
        activity.startActivity(intent);
    }

    @Optional
    @Nullable
    @OnClick({R.id.iv_back, R.id.bt_send, R.id.rl_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_send:
                if (isFlag) {
                    PayOkActivity.startActivity(this, "answer");
                    finish();
                }
                break;

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(AnswerActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(AnswerActivity.this, ImageGridActivity.class);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }
                    }
                }, names);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(AnswerActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
            etValidate();
            // 上传代码
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

    }
}
