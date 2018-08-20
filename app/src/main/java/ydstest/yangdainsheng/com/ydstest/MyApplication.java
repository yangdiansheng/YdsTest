package ydstest.yangdainsheng.com.ydstest;

import android.app.Application;
import android.content.Context;

/**
 * Created by yangdiansheng on 2018/8/20.
 */

public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
