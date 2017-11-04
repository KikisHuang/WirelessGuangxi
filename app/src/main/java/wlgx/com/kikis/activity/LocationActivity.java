package wlgx.com.kikis.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.SearchAdapter;
import wlgx.com.kikis.listener.moveListener;
import wlgx.com.kikis.utils.InputTask;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.InputTask.clear;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/16.
 */
public class LocationActivity extends Activity implements LocationSource, AMapLocationListener, moveListener, AMap.OnMapClickListener, TextWatcher, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {
    private static final String TAG = "LocationActivity";

    private MapView mapView = null;
    private AMap aMap;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private double latitude;
    private double longitude;

    private String adcode = "";
    private SearchAdapter mAdapter;
    private EditText edit;
    private ListView lv;
    private String city = "";
    private String address = "";
    private moveListener move;
    private ImageView clear_img;
    private RippleView submit_info;
    private GeocodeSearch geocoderSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        setTitles(this, "GPS定位");
        move = this;
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.map_view);
        edit = (EditText) findViewById(R.id.ed);
        clear_img = (ImageView) findViewById(R.id.clear_img);
        lv = (ListView) findViewById(R.id.search_list);
        submit_info = (RippleView) findViewById(R.id.submit_info);
        submit_info.setOnClickListener(this);
        clear_img.setOnClickListener(this);
        mAdapter = new SearchAdapter(this, move);
        lv.setAdapter(mAdapter);
        edit.addTextChangedListener(this);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        //第一次进入不弹出软键盘;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            aMap.setOnMapClickListener(this);
        }

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {

        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_gcoding));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//        getMap().setLatLonQuanVisible(false);
        aMap.getUiSettings().setCompassEnabled(false);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        mAdapter = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;

        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，

            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();

        }
    }

    private void getAddressA1ndAdCode() {

        LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        // 设置同步逆地理编码请求
        geocoderSearch.getFromLocationAsyn(query);

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    /*开启定位时*/
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                Log.e(TAG, amapLocation.getLatitude() + "");
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();
                city = amapLocation.getCity();
//                city = amapLocation.getCity();
                address = amapLocation.getAddress();
                mlocationClient.stopLocation();

                adcode = amapLocation.getAdCode();
//                select = 1;
//                listView.removeFooterView(listNot);
//                doSearchQuery();
            } else {
                /*没开启定位时*/
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见下方错误码表。

                Log.i("erro info：", amapLocation.getErrorCode() + "---" + amapLocation.getErrorInfo());
            }
        }
    }

    //地图点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图后清理图层插上图标，在将其移动到中心位置
        aMap.clear();
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        MarkerOptions otMarkerOptions = new MarkerOptions();
        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding));
        otMarkerOptions.position(latLng);
        aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        lv.setVisibility(View.VISIBLE);
        if (s.length() > 0) {
            Log.i(TAG, "info ====" + s.toString() + "----city ====" + city);
            InputTask.getInstance(this, mAdapter).onSearch(s.toString(), city);
        }
    }

    @Override
    public void onMove(double lati, double longi, String title) {
        //点击地图后清理图层插上图标，在将其移动到中心位置
        LatLng lt = new LatLng(lati, longi);
        aMap.clear();
        latitude = lati;
        longitude = longi;
        MarkerOptions otMarkerOptions = new MarkerOptions();

        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding));
        otMarkerOptions.position(lt);
        aMap.addMarker(otMarkerOptions);
        edit.setText(title);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati, longi), 20));
        lv.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_info:
                getAddressA1ndAdCode();
                break;
            case R.id.clear_img:
                edit.setText("");
                break;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (rCode == 1000) {

            address = result.getRegeocodeAddress().getFormatAddress();
            adcode = result.getRegeocodeAddress().getAdCode();
            Log.i(TAG, "address ====" + address + "----adcode ====" + adcode + "latitude ===" + latitude + "longitude" + longitude);
            String provinceId  = adcode.substring(0,2)+"0000";
            String cityId  = adcode.substring(0,4)+"00";

            Intent intent = new Intent();
            intent.putExtra("Map_adcode", adcode);
            intent.putExtra("Map_provinceId", provinceId);
            intent.putExtra("Map_cityId", cityId);
            intent.putExtra("Map_address", address);
            intent.putExtra("Map_latitude", String.valueOf(latitude));
            intent.putExtra("Map_longitude", String.valueOf(longitude));
            setResult(1008, intent);
            clear();
            finish();
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
    }
}
