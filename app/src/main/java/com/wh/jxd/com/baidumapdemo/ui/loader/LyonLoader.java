package com.wh.jxd.com.baidumapdemo.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.utils.DimenUtil;


import java.util.ArrayList;

/**
 * Created by kevin321vip on 2017/11/18.
 * load加载管理类
 */

public class LyonLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    //默认的加载样式
    private static final String DEFAULT_LOADER = LoadStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoadStyle> loadStyleEnum) {
        showLoading(context, loadStyleEnum.name());
    }

    /**
     * 显示加载框
     *
     * @param context
     * @param type
     */
    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.creat(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    /**
     * 加载默认的样式
     *
     * @param context
     */
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog loader : LOADERS) {
            if (loader != null) {
                //cancle和dismiss的区别是cancle有对应的回调
                if (loader.isShowing()) {
                    loader.cancel();
                }
            }
        }
    }
}
