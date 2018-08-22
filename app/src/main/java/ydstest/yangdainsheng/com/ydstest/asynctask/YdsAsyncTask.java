package ydstest.yangdainsheng.com.ydstest.asynctask;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangdiansheng on 2018/8/21.
 * 自己实现一个AsyncTask，只要实现子线程执行任务，实时通知主线程
 */
//这里使用泛型限定参数，进度，和返回值
public abstract class YdsAsyncTask<Params,Progress,Result> {

    private static final int MESSAGE_POST_RESULT = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;

    private static class InternalHandler extends Handler {
        public InternalHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AsyncTaskResult result = (AsyncTaskResult)msg.obj;
            switch (msg.what){

                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mResult);
                    break;
                case MESSAGE_POST_RESULT:
                    result.mTask.onPostExecute(result.mResult);
                    break;
            }
        }
    }

    //用于子线程运行子线程执行的回调
    private static abstract class WorkRunnable<Params,Result> implements Callable<Result>{
        Params[] mParams;
    }

    //主线程Handler用于通知进度更新
    private static Handler mInternalHandler;
    private final WorkRunnable<Params,Result> mWorker;
    private final FutureTask<Result> mFutrueTask;
    //执行任务添加到队列中，然后在使用线程池从队列中取出运行
    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
    private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;
    private static class SerialExecutor implements Executor {

        final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();
        Runnable mActive = null;

        @Override
        public void execute(final Runnable runnable) {
            mTasks.offer(new Runnable() {
                @Override
                public void run() {
                    try{
                        runnable.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null){
                scheduleNext();
            }
        }

        private synchronized void scheduleNext(){
            //服务下一个
            for (Runnable runnable:mTasks){
                Log.i("yyy",runnable.toString());

            }
            if ((mActive = mTasks.poll()) != null){
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    public static class AsyncTaskResult<Result>{
        final YdsAsyncTask mTask;
        final Result mResult;

        public AsyncTaskResult(YdsAsyncTask task,Result result){
            mTask = task;
            mResult = result;
        }
    }

    //任务服务线程池
    public static final Executor THREAD_POOL_EXECUTOR;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }


    public YdsAsyncTask(){
        mInternalHandler = getMainHandler();
        mWorker = new WorkRunnable<Params, Result>() {
            @Override
            public Result call() throws Exception {
                Result result = null;
                try {
                    result = doInBackGround(mParams);
                } finally {

                }
                return result;
            }
        };
        mFutrueTask = new FutureTask<Result>(mWorker){
            @Override
            protected void done() {
                super.done();
                //这里在线程执行结束后会返回结果
                try {
                    postResult(get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    postResult(null);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    postResult(null);
                }
            }
        };
    }

    private Result postResult(Result result){
        Message message = mInternalHandler.obtainMessage(MESSAGE_POST_RESULT,
                new AsyncTaskResult<Result>(this, result));
        message.sendToTarget();
        return result;
    }



    //获取主线程handler用于更新主线程进度
    public Handler getMainHandler() {
        synchronized (YdsAsyncTask.class){
            if (mInternalHandler == null){
                mInternalHandler = new InternalHandler(Looper.getMainLooper());
            }
            return mInternalHandler;
        }
    }

    //子线程中要执行的方法接收泛型可变参数，返回要返回的值
    public abstract Result doInBackGround(Params... params);


    public final YdsAsyncTask<Params,Progress,Result> execute(Params... mParams){
        return executeOnExecutor(sDefaultExecutor,mParams);
    }


    private final YdsAsyncTask<Params,Progress,Result> executeOnExecutor(Executor executor,Params... mParams){
        onPreExecute();
        mWorker.mParams = mParams;
        executor.execute(mFutrueTask);
        return this;
    }

    //在doInBackGround之前执行
    public void onPreExecute(){

    }

    //任务执行完成后调用
    public void onPostExecute(Result result){

    }

    public void publishProgress(Progress progress){
        mInternalHandler.obtainMessage(MESSAGE_POST_PROGRESS,new AsyncTaskResult<Progress>(this,progress)).sendToTarget();
    }

    //进度回调
    public void onProgressUpdate(Progress progress){

    }
}
