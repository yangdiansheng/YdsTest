package ydstest.yangdainsheng.com.ydstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ydstest.yangdainsheng.com.ydstest.toast.ToastUtils;

public class MainActivity extends AppCompatActivity {


    @OnClick(R.id.tv_hello_world)
    void helloworld(){
        ToastUtils.showToast("helloworld");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
