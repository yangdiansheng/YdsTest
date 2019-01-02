package ydstest.yangdainsheng.com.ydstest.key_board;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 监听软键盘是否弹起
 */
public class KeyboardShowUtil {

    public interface IKeyBoardVisibleListener {
        void onSoftKeyBoardVisible(boolean visible, int keyboardHeight);
    }

    boolean isVisiableForLast = false;

    public void addOnSoftKeyBoardVisibleListener(Activity activity, final IKeyBoardVisibleListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int hight = decorView.getHeight();
                //获得键盘高度
                int keyboardHeight = hight - displayHight;
                boolean visible = (double) displayHight / hight < 0.8;
                if (visible != isVisiableForLast) {
                    listener.onSoftKeyBoardVisible(visible, keyboardHeight);
                }
                isVisiableForLast = visible;
            }
        });
    }
}