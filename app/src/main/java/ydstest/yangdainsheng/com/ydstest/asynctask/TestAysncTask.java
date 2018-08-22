package ydstest.yangdainsheng.com.ydstest.asynctask;

import ydstest.yangdainsheng.com.ydstest.log.LogUtils;

/**
 * Created by yangdiansheng on 2018/8/22.
 */

public class TestAysncTask extends YdsAsyncTask<String,Integer,String> {


    @Override
    public String doInBackGround(String... strings) {
        LogUtils.log(" doInBackground=" + strings[0]);
        LogUtils.log(Thread.currentThread().getName());
        int times = 10;
        for (int i = 0; i < times; i++) {
            publishProgress(i);//提交之后，会执行onProcessUpdate方法
        }
        return "over";
    }

    //后端线程执行前执行
    @Override
    public void onPreExecute() {
        super.onPreExecute();
        LogUtils.log("onPreExecute");
        LogUtils.log(Thread.currentThread().getName());
    }

    //后端线程执行后执行
    @Override
    public void onPostExecute(String s) {
        super.onPostExecute(s);
        LogUtils.log("onPostExecute");
        LogUtils.log(Thread.currentThread().getName());
    }

    //进度回调
    @Override
    public void onProgressUpdate(Integer values) {
        super.onProgressUpdate(values);
        LogUtils.log("onProgressUpdate =" + values);
        LogUtils.log(Thread.currentThread().getName());
    }

}
