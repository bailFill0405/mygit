package jx.edu.com.jiangxue.util;

import android.text.TextUtils;
import android.widget.EditText;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赖恒熠 on 2018/7/25.
 */

public class EditUtil {

    public static boolean etIsEmpty(List<EditText> lists) {
        boolean isFlag = false;
        List<String> data = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            String editTextVaule = ViewUtils.getEditTextVaule(lists.get(i));
            if (TextUtils.isEmpty(editTextVaule)) {
                isFlag = true;
                return isFlag;
            }
        }
        return isFlag;
    }

    public static boolean etIsEmpty(EditText editText) {
        boolean isFlag = false;
        String editTextVaule = ViewUtils.getEditTextVaule(editText);
        if (TextUtils.isEmpty(editTextVaule)) {
            isFlag = true;
            return isFlag;
        }
        return isFlag;
    }

    public static List<EditText> getSoftReferenceList(List<EditText> lists) {
        SoftReference<List<EditText>> aSoftRef = new SoftReference(lists);
        if (aSoftRef != null) {
            return aSoftRef.get();
        }
        return null;
    }

    public static boolean verificationPwd(EditText editText, EditText editText2) {
        String editTextVaule = ViewUtils.getEditTextVaule(editText);
        String editTextVaule2 = ViewUtils.getEditTextVaule(editText2);
        if (editTextVaule.equals(editTextVaule2)) {
            return true;
        }
        if (!juguePassWord(editTextVaule) || !juguePassWord(editTextVaule)) {
            return true;
        }
        return false;
    }

    public static boolean verificationPwd(EditText editText) {
        String editTextVaule = ViewUtils.getEditTextVaule(editText);
        if (!juguePassWord(editTextVaule)) {
            return true;
        }
        return false;
    }

    public static boolean juguePassWord(String pwd) {
//        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        String regex = "^[a-zA-Z\\d\\.@]{6,16}$";
        boolean matches = pwd.matches(regex);
        return matches;
    }
}
