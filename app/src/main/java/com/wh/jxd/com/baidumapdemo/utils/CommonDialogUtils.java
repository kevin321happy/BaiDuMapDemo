package com.wh.jxd.com.baidumapdemo.utils;

import android.app.Activity;
import android.content.Context;


import com.wh.jxd.com.baidumapdemo.CustomProgressDialog;
import com.wh.jxd.com.baidumapdemo.R;


/**
 * Created by zhpan on 2017/5/26.
 * Description:
 */

public class CommonDialogUtils {
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context context, String msg) {
       /* if (context == null || context.isFinishing()) {
            return;
        }*/
//       .setTheme(R.style.ProgressDialogStyle)
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(context)
                    .setTheme(R.style.ProgressDialogStyle)
                    .setMessage(msg)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 判断加载框是否在显示状态
     *
     * @return
     */
    public boolean isShowing() {
        return mProgressDialog.isShowing();
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context context) {
        /*if (activity == null || activity.isFinishing()) {
            return;
        }*/
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(context)
                    .setTheme(R.style.ProgressDialogStyle)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
