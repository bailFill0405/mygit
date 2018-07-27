package jx.edu.com.jiangxue.util;

import android.widget.EditText;

/**
 * Created by 赖恒熠 on 2018/7/16.
 */

public class ViewUtils {
    public static String getEditTextVaule(EditText editText) {
        return editText != null ? editText.getText().toString().trim() : "";
    }
}
