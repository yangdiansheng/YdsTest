package ydstest.yangdainsheng.com.ydstest;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ydstest.yangdainsheng.com.ydstest.asynctask.MyAynctask;
import ydstest.yangdainsheng.com.ydstest.asynctask.TestAysncTask;
import ydstest.yangdainsheng.com.ydstest.handle_thread.MyHandlerThread;
import ydstest.yangdainsheng.com.ydstest.handle_thread.MyIntentService;
import ydstest.yangdainsheng.com.ydstest.image.LongImgViewActivity;
import ydstest.yangdainsheng.com.ydstest.log.LogUtils;
import ydstest.yangdainsheng.com.ydstest.retrofit.AvailableLimitBean;
import ydstest.yangdainsheng.com.ydstest.retrofit.RequestService;
import ydstest.yangdainsheng.com.ydstest.retrofit.RequestServiceForRxJava;
import ydstest.yangdainsheng.com.ydstest.retrofit.RequestServiceString;
import ydstest.yangdainsheng.com.ydstest.retrofit.call.CustomCallAdapterFactory;
import ydstest.yangdainsheng.com.ydstest.retrofit.converter.StringConverterFactory;
import ydstest.yangdainsheng.com.ydstest.toast.ToastUtils;

public class MainActivity extends Activity {


    @OnClick(R.id.tv_long_img)
    void longImg(){
        Intent intent = new Intent(this,LongImgViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_request)
    void request(){
        /**
         * 在默认情况下Retrofit只支持将HTTP的响应体转换换为ResponseBody,
         * 而Converter就是Retrofit为我们提供用于将ResponseBody转换为我们想要的类型   Converter是对于Call<T>中T的转换
         * CallAdapter则可以对Call转换
         */
        request4();

    }

    @OnClick(R.id.tv_rxjava)
    void rxjava(){
        /**
         * 异步
         * 简洁
         * 扩展观察者实现
         */

        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i("yyy","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("yyy","onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("yyy","onNext = " +  s);
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {

            /**
             * 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，
             * 例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
             * onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作
             */
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        //被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        //订阅
        observable.subscribe(observer);
    }

    //自定义CallAdapter
    private void request4(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yapi.demo.qunar.com/mock/72419/")
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(CustomCallAdapterFactory.create())
                .build();
        RequestServiceString service = retrofit.create(RequestServiceString.class);
        Call<String> call = service.getAvailableLimit();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtils.log(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //自定义Converter
    private void request3(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yapi.demo.qunar.com/mock/72419/")
                .addConverterFactory(StringConverterFactory.create())
                .build();
        RequestServiceString service = retrofit.create(RequestServiceString.class);
        Call<String> call = service.getAvailableLimit();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtils.log(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //Rxjava converter转换Call<T>
    private void request2(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yapi.demo.qunar.com/mock/72419/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestServiceForRxJava service = retrofit.create(RequestServiceForRxJava.class);
        service.getAvailableLimit().subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.log("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        LogUtils.log("onError");
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        try {
                            LogUtils.log(response.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //基本请求
    private void request1(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yapi.demo.qunar.com/mock/72419/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestService service = retrofit.create(RequestService.class);
        Call<ResponseBody> call = service.getAvailableLimit();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    LogUtils.log(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void test(){
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
