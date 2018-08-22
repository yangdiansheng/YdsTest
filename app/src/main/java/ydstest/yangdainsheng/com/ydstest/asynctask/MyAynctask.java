package ydstest.yangdainsheng.com.ydstest.asynctask;

import android.os.AsyncTask;

import ydstest.yangdainsheng.com.ydstest.log.LogUtils;

/**
 * Created by yangdiansheng on 2018/8/20.
 */

public class MyAynctask extends AsyncTask<String,Integer,String> {

    @Override
    protected String doInBackground(String... strings) {
        LogUtils.log(" doInBackground=" + strings[0]);
        LogUtils.log(Thread.currentThread().getName());
        int times = 10;
        for (int i = 0; i < times; i++) {
            publishProgress(i);//提交之后，会执行onProcessUpdate方法
        }
        return "over";
    }

    //在doInBackground前执行
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LogUtils.log("onPreExecute");
        LogUtils.log(Thread.currentThread().getName());
    }

    //doInBackground后执行
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        LogUtils.log("onPostExecute = " + s);
        LogUtils.log(Thread.currentThread().getName());
    }

    //进度回调
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        LogUtils.log("onProgressUpdate =" + values[0]);
        LogUtils.log(Thread.currentThread().getName());
    }

    //执行cancel方法后执行
    @Override
    protected void onCancelled() {
        super.onCancelled();
        LogUtils.log("onCancelled");
        LogUtils.log(Thread.currentThread().getName());
    }
}