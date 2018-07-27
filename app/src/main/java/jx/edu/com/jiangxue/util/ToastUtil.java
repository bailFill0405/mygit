package jx.edu.com.jiangxue.util;

import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import jx.edu.com.jiangxue.MyApplication;
import jx.edu.com.jiangxue.R;


/**
 * 作者：guojuan
 * 创建时间：2017/12/25 15:23
 * 功能描述：
 */
public class ToastUtil {

    private static Toast toast;

    public static void showShortToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(),
                    str,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public static void showLongToast(String str) {
        StyleableToast.makeText(MyApplication.getContext(), str, Toast.LENGTH_LONG, R.style.myToast).show();
//        if (toast == null) {
//            toast = Toast.makeText(MyApplication.getContext(),
//                    str,
//                    Toast.LENGTH_LONG);
//        } else {
//            toast.setText(str);
//        }
//        toast.show();
    }
}
