package ydstest.yangdainsheng.com.ydstest.handle_thread;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import ydstest.yangdainsheng.com.ydstest.log.LogUtils;

/**
 * Created by yangdiansheng on 2018/8/20.
 */

public class MyIntentService extends IntentService{

    public MyIntentService(String name) {
        super(name);
    }

    public MyIntentService() {
        this("MyIntentService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtils.log("服务开始了");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtils.log("处理我自己的事");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.log("服务结束了");
    }
}
