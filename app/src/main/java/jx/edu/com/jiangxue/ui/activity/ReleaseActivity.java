package jx.edu.com.jiangxue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.base.BaseRxActivity;
import jx.edu.com.jiangxue.ui.apdater.ImagePickerAdapter;
import jx.edu.com.jiangxue.ui.view.window.SelectDialog;
import jx.edu.com.jiangxue.util.EmptyUtils;
import jx.edu.com.jiangxue.util.GlideImageLoader;

public class ReleaseActivity extends BaseRxActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private String mode="rmb";
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 4;               //允许选择图片最大数
    @BindColor(R.color.textUnselectColor)
    int textUnselectColor;
    @BindColor(R.color.bg)
    int textSelectColor;
    @BindBitmap(R.mipmap.integral_gray)
    Bitmap integral_gray;
    @BindBitmap(R.mipmap.integral_light)
    Bitmap integral_light;
    @BindBitmap(R.mipmap.rmb_gray)
    Bitmap rmb_gray;
    @BindBitmap(R.mipmap.rmb_light)
    Bitmap rmb_light;
    @BindBitmap(R.mipmap.gray_boder_left)
    Bitmap gray_boder_left;
    @BindBitmap(R.mipmap.gray_boder_right)
    Bitmap gray_boder_right;
    @BindBitmap(R.mipmap.yellow_boder_left)
    Bitmap yellow_boder_left;
    @BindBitmap(R.mipmap.yellow_boder_right)
    Bitmap yellow_boder_right;
    @BindViews({R.id.tv_back, R.id.tv_release, R.id.tv_integral, R.id.tv_rmb, R.id.tv_integral_unit, R.id.tv_rmb_unit})
    List<TextView> tvs;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindViews({R.id.fl_integral, R.id.fl_rmb})
    List<FrameLayout> fls;
    @BindViews({R.id.iv_integral, R.id.iv_rmb})
    List<ImageView> ivs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void etValidate() {

    }

    @Override
    protected void BoardShow() {

    }

    @Override
    protected void BoardHide() {

    }

    @Override
    protected void initWindow() {
        super.initWindow();
    }

    @Override
    public int layout() {
        return R.layout.activity_release;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        initImagePicker();
        initWidget();
    }

    @Override
    protected void keyBordAction(TextView v, int actionId, KeyEvent event) {

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

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @OnClick({R.id.tv_back, R.id.tv_release, R.id.fl_integral, R.id.fl_rmb})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_release:
                PayOkActivity.startActivity(this, "release");
                finish();
                break;
            case R.id.fl_integral:
                mode="integral";
                PayActivity.startActivityForResults(ReleaseActivity.this,"integral");
                break;
            case R.id.fl_rmb:
                mode="rmb";
                PayActivity.startActivityForResults(ReleaseActivity.this,"rmb");
                break;
        }
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, ReleaseActivity.class);
        activity.startActivity(intent);
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
                                Intent intent = new Intent(ReleaseActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(ReleaseActivity.this, ImageGridActivity.class);
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
        } else if (resultCode == PayActivity.PAY_RESULT) {
            if (EmptyUtils.isNotEmpty(data)) {
                String pay_num_rmb = data.getStringExtra(PayActivity.PAY_NUM_RMB);
                updateButtomView(pay_num_rmb);
            }
        }
    }

    private void updateButtomView(String pay_num_rmb) {
        switch (mode) {
            case "integral":
                fls.get(1).setBackgroundResource(R.mipmap.gray_boder_right);
                fls.get(0).setBackgroundResource(R.mipmap.yellow_boder_left);
                tvs.get(2).setTextColor(textSelectColor);
                tvs.get(2).setText(pay_num_rmb);
                tvs.get(3).setTextColor(textUnselectColor);
                tvs.get(3).setText("0");
                tvs.get(4).setTextColor(textSelectColor);
                tvs.get(5).setTextColor(textUnselectColor);
                ivs.get(0).setImageBitmap(integral_light);
                ivs.get(1).setImageBitmap(rmb_gray);
                break;
            case "rmb":
                fls.get(0).setBackgroundResource(R.mipmap.gray_boder_left);
                fls.get(1).setBackgroundResource(R.mipmap.yellow_boder_right);
                tvs.get(2).setTextColor(textUnselectColor);
                tvs.get(2).setText("0");
                tvs.get(3).setTextColor(textSelectColor);
                tvs.get(3).setText(pay_num_rmb);
                tvs.get(4).setTextColor(textUnselectColor);
                tvs.get(5).setTextColor(textSelectColor);
                ivs.get(0).setImageBitmap(integral_gray);
                ivs.get(1).setImageBitmap(rmb_light);
                break;
        }
    }
}
