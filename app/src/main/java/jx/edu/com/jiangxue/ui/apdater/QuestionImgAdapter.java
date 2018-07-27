package jx.edu.com.jiangxue.ui.apdater;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jelly.mango.Mango;
import com.jelly.mango.MultiplexImage;

import java.util.ArrayList;

import jx.edu.com.jiangxue.R;
import jx.edu.com.jiangxue.bean.TestBean;
import jx.edu.com.jiangxue.bean.TestContant;
import jx.edu.com.jiangxue.ui.view.RoundImageView2;

/**
 * Created by 赖恒熠 on 2018/7/12.
 * extends BaseRecycAdapter<TestBean>
 */


public class QuestionImgAdapter extends BaseQuickAdapter<TestBean.TestBean2, BaseViewHolder> {
    public QuestionImgAdapter() {
        super(R.layout.item_img_type, null);
    }
    @Override
    protected void convert(BaseViewHolder helper,TestBean.TestBean2 item) {
        RoundImageView2 iv = helper.getView(R.id.iv);
        int num = item.getNum();
        String s = item.toString();
        switch (num) {
            case 1:
                setImgSize(iv, 675, 500);
                break;
            case 2:
                setImgSize(iv, 502, 373);
                break;
            case 3:
                setImgSize(iv, 290, 201);
                break;
            case 4:
                setImgSize(iv, 290, 201);
                break;
        }
        final int layoutPosition = helper.getLayoutPosition();
        final int size = getData().size();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<MultiplexImage> images = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    MultiplexImage multiplexImage = new MultiplexImage(TestContant.imgUrl[i], MultiplexImage.ImageType.NORMAL);
                    images.add(multiplexImage);
                }
                Mango.setImages(images);
                Mango.setPosition(layoutPosition);
                Mango.open(mContext);
            }
        });

    }

    private void setImgSize(RoundImageView2 iv, int width, int height) {
        ViewGroup.LayoutParams params = iv.getLayoutParams();
//        params.height = (int) DensityTool.dp2px(mContext.getResources(), height);
//        params.width = (int) DensityTool.dp2px(mContext.getResources(), width);
        params.height = (int) (height*1.35);
        params.width = (int) (width*1.35);
        iv.setLayoutParams(params);

    }


}
