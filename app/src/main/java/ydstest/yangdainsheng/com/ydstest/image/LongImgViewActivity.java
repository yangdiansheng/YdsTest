package ydstest.yangdainsheng.com.ydstest.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import java.lang.annotation.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import ydstest.yangdainsheng.com.ydstest.R;

/**
 * author yangdiansheng
 * time 2019/1/2 下午5:46
 * this is
 **/
public class LongImgViewActivity extends Activity {

    @BindView(R.id.liv_content)
    LongImgView mLongImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String url = "http://img.zcool.cn/community/010a47589d2e50a801219c77135fcb.jpg";
//        Glide.with(getApplicationContext())
//                .asBitmap()
//                .load(url)
//                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                        Log.i("yyy","width = " + resource.getWidth());
//                        Log.i("yyy","height = " + resource.getHeight());
//                        mLongImgView.setBitMap(resource);
//                    }
//                });
    }
}
