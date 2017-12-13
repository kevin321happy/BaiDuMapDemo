package com.wh.jxd.com.baidumapdemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by kevin321vip on 2017/12/11.
 * 自定义的PopWindow，构建者模式链式调用
 */

public class CustomPopWindow {
    private Context mContext;
    private int mHeight;
    private int mWidth;
    private boolean mIsFocusable = true;
    private boolean mIsOutside = true;

    private int mResLayoutId = -1;
    private View mContentView;
    private PopupWindow mPopupWindow;
    private int mAnimationStyle = -1;

    private boolean mClippEnable = true;//default is true
    private boolean mIgnoreCheekPress = false;
    private int mInputMode = -1;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private int mSoftInputMode = -1;
    private boolean mTouchable = true;//default is ture
    private View.OnTouchListener mOnTouchListener;

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    public int getHeight() {
        return mHeight;
    }


    public void setHeight(int height) {
        mHeight = height;
    }


    public int getWidth() {
        return mWidth;
    }


    public void setWidth(int width) {
        mWidth = width;
    }

    public CustomPopWindow(Context context) {
        mContext = context;
    }

    /**
     * 显示POPWindow
     *
     * @param anchor
     * @param xOff
     * @param yOff
     * @return
     */
    public CustomPopWindow showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    public CustomPopWindow showAsDropDown(View view) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(view);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CustomPopWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }

    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     *
     * @param parent
     * @param gravity
     * @param x       the popup's x location offset
     * @param y       the popup's y location offset
     * @return
     */
    public CustomPopWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    /**
     * 关闭POPWindow
     */

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismiss();
            }
        }
    }

    /**
     * Build对象,持有pop对象
     */
    public static class PopWindowBuild {
        private CustomPopWindow mCustomPopWindow;

        public PopWindowBuild(Context context) {
            mCustomPopWindow = new CustomPopWindow(context);
        }

        public PopWindowBuild size(int width, int height) {
            mCustomPopWindow.mWidth = width;
            mCustomPopWindow.mHeight = height;
            return this;
        }

        public PopWindowBuild setFocusable(boolean focusable) {
            mCustomPopWindow.mIsFocusable = focusable;
            return this;
        }

        public PopWindowBuild setView(int resLayout) {
            mCustomPopWindow.mResLayoutId = resLayout;
            mCustomPopWindow.mContentView = null;
            return this;
        }

        public PopWindowBuild setView(View view) {
            mCustomPopWindow.mContentView = view;
            mCustomPopWindow.mResLayoutId = -1;
            return this;
        }

        public PopWindowBuild setOutsideTouchable(boolean outsideTouchable) {
            mCustomPopWindow.mIsOutside = outsideTouchable;
            return this;
        }

        /**
         * 设置弹窗动画
         *
         * @param animationStyle
         * @return
         */
        public PopWindowBuild setAnimationStyle(int animationStyle) {
            mCustomPopWindow.mAnimationStyle = animationStyle;
            return this;
        }


        public PopWindowBuild setIgnoreCheekPress(boolean ignoreCheekPress) {
            mCustomPopWindow.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public PopWindowBuild setInputMethodMode(int mode) {
            mCustomPopWindow.mInputMode = mode;
            return this;
        }

        public PopWindowBuild setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener) {
            mCustomPopWindow.mOnDismissListener = onDissmissListener;
            return this;
        }


        public PopWindowBuild setSoftInputMode(int softInputMode) {
            mCustomPopWindow.mSoftInputMode = softInputMode;
            return this;
        }


        public PopWindowBuild setTouchable(boolean touchable) {
            mCustomPopWindow.mTouchable = touchable;
            return this;
        }

        public PopWindowBuild setTouchIntercepter(View.OnTouchListener touchIntercepter) {
            mCustomPopWindow.mOnTouchListener = touchIntercepter;
            return this;
        }

        public CustomPopWindow creat() {
            //构建Coustom
            mCustomPopWindow.build();
            return mCustomPopWindow;
        }
    }

    /**
     * 构建返回一个POPwindow
     *
     * @return
     */
    private PopupWindow build() {
        //构建POPWindow
        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null);
        }
        if (mWidth != 0 && mHeight != 0) {
            mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
        } else {
            mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (mAnimationStyle != -1) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }
        apply(mPopupWindow);
        mPopupWindow.setFocusable(mIsFocusable);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(mIsOutside);
        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }

        mPopupWindow.update();
        return mPopupWindow;

    }

    /**
     * 设置一些属性
     *
     * @param popupWindow
     */
    private void apply(PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(mClippEnable);
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress();
        }
        if (mInputMode != -1) {
            popupWindow.setInputMethodMode(mInputMode);
        }
        if (mSoftInputMode != -1) {
            popupWindow.setSoftInputMode(mSoftInputMode);
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener);
        }
        popupWindow.setTouchable(mTouchable);

    }
}
