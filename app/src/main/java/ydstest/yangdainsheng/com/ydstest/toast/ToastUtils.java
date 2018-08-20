package ydstest.yangdainsheng.com.ydstest.toast;

import android.widget.Toast;

import ydstest.yangdainsheng.com.ydstest.MyApplication;

/**
 * Created by yangdiansheng on 2018/8/20.
 */

public class ToastUtils {
    public static void showToast(String s){
        Toast.makeText(MyApplication.getContext(),s,Toast.LENGTH_SHORT).show();
    }
}
