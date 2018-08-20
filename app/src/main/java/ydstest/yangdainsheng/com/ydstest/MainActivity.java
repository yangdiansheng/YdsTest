package ydstest.yangdainsheng.com.ydstest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ydstest.yangdainsheng.com.ydstest.handle_thread.MyHandlerThread;
import ydstest.yangdainsheng.com.ydstest.handle_thread.MyIntentService;
import ydstest.yangdainsheng.com.ydstest.toast.ToastUtils;

public class MainActivity extends AppCompatActivity {


    @OnClick(R.id.tv_hello_world)
    void helloworld(){
        testHandlerThread();
    }

    public void testHandlerThread(){
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);
    }

    private MyHandlerThread myHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myHandlerThread = new MyHandlerThread();
        myHandlerThread.start();
    }
}
