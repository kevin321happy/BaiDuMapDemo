package com.wh.jxd.com.baidumapdemo.ui.activity;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.base.BaseActivity;
import com.wh.jxd.com.baidumapdemo.bean.MarkBean;
import com.wh.jxd.com.baidumapdemo.widget.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMapClickListener {

    private MapView mMapView;
    private NavigationView mNavigationView;
    private ListView mListView;
    private DrawerLayout mDrawerLayout;
    private BaiduMap mBaiduMap;
    private boolean TrafficEnabled = false;
    private BitmapDescriptor mCurrentMarker;
    private CustomPopWindow mZoomPopWindow;
    private CustomPopWindow mLayoutPopWindow;
    private UiSettings mUiSettings;
    private boolean mEnableAllUiSetting = false;
    private List<MarkBean> mMarkerOptions = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        LatLng latLng = new LatLng(27.840796, 112.942943);
        moveCenterPoint(latLng);
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapClickListener(this);

        mNavigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);
        setLatLngBounds();
//        showLocation();
    }

    /**
     * 移动到指定的中心点
     */
    public void moveCenterPoint(LatLng latLng) {
        //Build模式构建对象
        MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(14)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
    }

    /**
     * 显示地图的范围
     */
    private void setLatLngBounds() {

    }

    /**
     * 允许所有的手势和控件
     *
     * @param show
     */
    private void EnableAllGestureAndWidget(boolean show) {
        mUiSettings = mBaiduMap.getUiSettings();
        //比例尺
        mMapView.showScaleControl(show);
        //缩放按钮
        mMapView.showZoomControls(show);
        //平移手势
        mUiSettings.setScrollGesturesEnabled(show);
        //允许缩放
        mUiSettings.setZoomGesturesEnabled(show);
        //地图俯视(3D)
        mUiSettings.setOverlookingGesturesEnabled(show);
        //地图旋转
        mUiSettings.setRotateGesturesEnabled(show);
        //禁止所有的手势
        //mUiSettings .setAllGesturesEnabled(false);
    }

    /**
     * 显示定位
     */
    private void showLocation() {
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(28.2277800000, 112.9388600000);
        Location location = new Location("湖南省长沙市市政府");
        //new Location();
        //构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        //设置定位数据
        mBaiduMap.setMyLocationData(locData);  // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_check);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 侧拉菜单的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_one:
                showSwitchLayoutPop();
                break;
            case R.id.nav_two:
                drawOnMap();
                break;
            case R.id.nav_three:
                showZoomPop();
                break;
            case R.id.nav_four:
                mEnableAllUiSetting = mEnableAllUiSetting == true ? false : true;
                EnableAllGestureAndWidget(mEnableAllUiSetting);
                break;
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    /**
     * 在地图上绘制
     */
    private void drawOnMap() {
        addMarks();
        addText();


    }

    /**
     * 添加文字标记
     */
    private void addText() {
        //定义文字所显示的坐标点
        LatLng llText = new LatLng(28.211784, 112.908465);

//构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(48)
                .fontColor(0xFFFF00FF)
                .text("裘马地图")
                .rotate(-30)
                .position(llText);

//在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);


    }

    /**
     * 添加自定义标注点
     */
    private void addMarks() {
        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        mMarkerOptions.add(new MarkBean("湘银纳帕溪谷", "湖南省湘潭市岳塘区宝塔街道云峰村西北方向", 27.840796, 112.942943));
        mMarkerOptions.add(new MarkBean("东风园小区", "湖南省湘潭市岳塘区下摄司街道半边街社区", 27.809111, 112.937440));
        mMarkerOptions.add(new MarkBean("江南城", "湖南省湘潭市岳塘区板塘街道农联村西南方向约1.08公里", 27.846723, 112.991522));
        mMarkerOptions.add(new MarkBean("兴江花苑", "湖南省湘潭市岳塘区滴水埠街道江滨社区西南方向", 27.875797, 112.977357));
        mMarkerOptions.add(new MarkBean("平安佳园-南门", "湖南省湘潭市雨湖区楠竹山镇罗金塘村西南方向", 27.887140, 112.905691));
        mMarkerOptions.add(new MarkBean("湘钢希望大厦", "湖南省湘潭市岳塘区霞城乡岳塘村西北方向", 27.820804, 112.926104));
        mMarkerOptions.add(new MarkBean("江滨社区", "湖南省湘潭市岳塘区滴水埠街道江滨社区", 27.879288, 112.978129));
        mMarkerOptions.add(new MarkBean("金桥城", "湖南省湘潭市岳塘区红旗街道红旗社区西方向", 27.910717, 112.942635));
        mMarkerOptions.add(new MarkBean("湘潭中心", "湖南省湘潭市岳塘区建设路街道建设路社区西南方向", 27.840848, 112.9243973292));

        //显示InfoWindow

        for (MarkBean markerOption : mMarkerOptions) {
            LatLng LatLng = new LatLng(markerOption.getLongitude(), markerOption.getLatitude());
            //创建InfoWindow展示的view
            View view = View.inflate(this, R.layout.custom_info_window, null);
            view.setBackground(getResources().getDrawable(R.drawable.bg_info_window));
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            InfoWindow mInfoWindow = new InfoWindow(view, LatLng, 100);
            mBaiduMap.showInfoWindow(mInfoWindow);
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_horse);
            OverlayOptions option = new MarkerOptions()
                    .animateType(MarkerOptions.MarkerAnimateType.grow)
                    .position(LatLng)
                    .icon(bitmap);
            Marker marker = (Marker) mBaiduMap.addOverlay(option);
            // 将信息保存
            Bundle bundle = new Bundle();
            bundle.putSerializable("marker", markerOption);
            marker.setExtraInfo(bundle);
//            options.add(option);
        }
        //在地图上批量添加
//        mBaiduMap.addOverlays(options);
    }

    /**
     * 显示放大级别的Pop
     */
    private void showZoomPop() {
        if (mZoomPopWindow == null) {
            mZoomPopWindow = new CustomPopWindow.PopWindowBuild(this)
                    .setView(LayoutInflater.from(this).inflate(R.layout.pop_switch_layout, null))
                    .size(300, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setOnDissmissListener(mOnDismissListener)
                    .setFocusable(true)
                    .creat();
        }
        View contentView = mZoomPopWindow.getContentView();
        mListView = (ListView) contentView.findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(mOnZoomItemClickListener);
        adapter.addAll(new String[]{"默认级别", "较小级别", "较大级别", "最大级别"});
        adapter.notifyDataSetChanged();
        mZoomPopWindow.showAsDropDown(mMapView, Gravity.BOTTOM, Gravity.RIGHT);
    }

    /**
     * 显示切换图层的POP
     */
    private void showSwitchLayoutPop() {
        if (mLayoutPopWindow == null) {
            mLayoutPopWindow = new CustomPopWindow.PopWindowBuild(this)
                    .setView(LayoutInflater.from(this).inflate(R.layout.pop_switch_layout, null))
                    .size(300, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setOnDissmissListener(mOnDismissListener)
                    .setFocusable(true)
                    .creat();
        }
        View contentView = mLayoutPopWindow.getContentView();
        mListView = (ListView) contentView.findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(mOnLayoutItemClickListener);
        adapter.addAll(new String[]{"普通图", "卫星图", "交通图", "空白图"});
        adapter.notifyDataSetChanged();
        mLayoutPopWindow.showAsDropDown(mMapView, Gravity.BOTTOM, Gravity.RIGHT);
    }

    /**
     * 图层条目的点击事件
     */
    private AdapterView.OnItemClickListener mOnZoomItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    //普通
                    changeMapZoom(14);
                    break;
                case 1:
                    //较小
                    changeMapZoom(12);
                    break;
                case 2:
                    //较大
                    changeMapZoom(16);
                    break;
                case 3:
                    //较最大
                    changeMapZoom(18);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 改变地图的放大状态
     */
    public void changeMapZoom(int zoom) {
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom));
    }

    /**
     * 图层条目的点击事件
     */
    private AdapterView.OnItemClickListener mOnLayoutItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    //普通
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    break;
                case 1:
                    //卫星
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    break;
                case 2:
                    //交通
                    TrafficEnabled = TrafficEnabled == true ? false : true;
                    mBaiduMap.setTrafficEnabled(TrafficEnabled);
                    break;
                case 3:
                    //空白
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * POPWindow消失
     */
    private PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

        }
    };

    /**
     * mark点击的回调I
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        //获得marker中的数据
        MarkBean mark = (MarkBean) marker.getExtraInfo().get("marker");
        LatLng position = marker.getPosition();
        //移动到这个中心点
        moveCenterPoint(position);
        //创建InfoWindow展示的view
        View view = View.inflate(this, R.layout.custom_info_window, null);
        view.setBackground(getResources().getDrawable(R.drawable.bg_info_window));
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        LinearLayout ll_detail = (LinearLayout) view.findViewById(R.id.ll_detail);
        tv_title.setText(mark.getTitle() == null ? "湖南" : mark.getTitle());
        tv_content.setText(mark.getAddress() == null ? "湘潭" : "地   址：" + mark.getAddress());
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(view, position, 0);
        mBaiduMap.showInfoWindow(mInfoWindow);
        ll_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectDetailActivity.class);
                startActivity(intent);
                //TODO 去查看详情
                Toast.makeText(MainActivity.this, "查看详情", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    /**
     * 当点击地图时InfoWindow消失
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }
}
