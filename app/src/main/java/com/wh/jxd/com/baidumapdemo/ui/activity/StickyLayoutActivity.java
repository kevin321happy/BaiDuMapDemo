package com.wh.jxd.com.baidumapdemo.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.base.BaseActivity;
import com.wh.jxd.com.baidumapdemo.ui.fragment.TabFragment;
import com.wh.jxd.com.baidumapdemo.utils.StatusBarUtil;
import com.wh.jxd.com.baidumapdemo.utils.ToastUtils;
import com.wh.jxd.com.baidumapdemo.widget.SimpleViewPagerIndicator;
import com.wh.jxd.com.baidumapdemo.widget.StickyNavLayout;

/**
 * Created by kevin321vip on 2017/12/19.
 */

public class StickyLayoutActivity extends BaseActivity implements SimpleViewPagerIndicator.onTabClickListener {
    private String[] mTitles = new String[]{"射手", "坦克", "法师", "辅助", "战士"};
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private TabFragment[] mFragments = new TabFragment[mTitles.length];
    private Toolbar toolbar;
    private StickyNavLayout sticky_layout;
    private View decorView;
    private View status_bar;
    private RelativeLayout topView;
    private ImageView tv_image;
    private RecyclerView rcv_course;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_layout);
        initViews();
        initDatas();
        initEvents();
    }

    private void initEvents() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDatas() {
        mIndicator.setTitles(mTitles);
        mIndicator.setmOnTabClickListener(this);

        for (int i = 0; i < mTitles.length; i++) {
            mFragments[i] = (TabFragment) TabFragment.newInstance(mTitles[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void initViews() {
        sticky_layout = (StickyNavLayout) findViewById(R.id.sticky_layout);
        topView = (RelativeLayout) findViewById(R.id.id_stickynavlayout_topview);

        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv_image = (ImageView) findViewById(R.id.iv_ima);
        rcv_course = (RecyclerView) findViewById(R.id.rcv_course);

        initRecycle();

        status_bar = findViewById(R.id.status_bar);
        status_bar.setAlpha(0);

        sticky_layout.setMonScrollListener(new StickyNavLayout.onScrollListenre() {
            @Override
            public void onScrolling(float precent) {
                ToastUtils.show("回调出来的透明度：" + precent);
                float offset = 1 - precent / 1000;
                if (0 == offset) {
                    toolbar.setAlpha(1);
                    status_bar.setAlpha(1);
                    toolbar.setBackgroundColor(ContextCompat.getColor(StickyLayoutActivity.this, R.color.white));//使用colors.xml文件中的颜色
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(StickyLayoutActivity.this, R.color.transparent));//使用colors.xml文件中的颜色
                    //Toolbar背景色透明度
                    toolbar.setAlpha(offset);
//                    tv_image.setAlpha(offset);
                    status_bar.setAlpha(1 - offset);
                }
//                toolbar.setBackgroundColor(Color.argb((int) (offset * 255), 18, 176, 242));
            }
        });

		/*
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.id_stickynavlayout_topview);
		TextView tv = new TextView(this);
		tv.setText("我的动态添加的");
		tv.setBackgroundColor(0x77ff0000);
		ll.addView(tv, new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, 600));
		*/
    }

    /**
     * 初始化Reycle的数据
     */
    private void initRecycle() {
        rcv_course.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        CourseClassAdapter classAdapter = new CourseClassAdapter();
        rcv_course.setAdapter(classAdapter);
    }
    @Override
    public void onTabClick(int position) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        }
    }
}
