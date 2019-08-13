package ydstest.yangdainsheng.com.ydstest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ThreePointView extends View {

    public ThreePointView(Context context) {
        super(context);
        init();
    }

    public ThreePointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThreePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#E1E8FE"));
    }

    Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getX()/2,getY()/2,getWidth()/2,mPaint);
    }
}
