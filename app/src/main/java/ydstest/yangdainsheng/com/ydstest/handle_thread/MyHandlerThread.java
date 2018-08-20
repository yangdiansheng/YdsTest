package ydstest.yangdainsheng.com.ydstest.handle_thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Executors;

import ydstest.yangdainsheng.com.ydstest.log.LogUtils;

/**
 * Created by yangdiansheng on 2018/8/20.
 */

public class MyHandlerThread extends Thread{

    private Handler mHanlder;

    public static class MyHandler extends Handler{

        public MyHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.log("handleMessage = " + Thread.currentThread().getName());
        }
    }

    public MyHandlerThread(){
        init();
    }

    private void init() {
        LogUtils.log("init = " + Thread.currentThread().getName());
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        mHanlder = new MyHandler(looper);
    }

    @Override
    public void run() {
        super.run();
        LogUtils.log("run = " + Thread.currentThread().getName());
        mHanlder.sendEmptyMessage(1);
    }

    public void startThread(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.log("Runnable = " + Thread.currentThread().getName());
                mHanlder.sendEmptyMessage(1);
            }
        });
    }
}
