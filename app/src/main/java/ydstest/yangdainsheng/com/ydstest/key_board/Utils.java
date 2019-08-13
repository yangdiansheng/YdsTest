package ydstest.yangdainsheng.com.ydstest.key_board;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

/**
 * 登录页用到的工具类
 */
public class Utils {

    //显示软键盘
    public static void showKeyBoard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
    }

    //关闭软键盘
    public static void hideKeyBoard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
        Cursor cursor = null;
        cursor.close();
    }
}
