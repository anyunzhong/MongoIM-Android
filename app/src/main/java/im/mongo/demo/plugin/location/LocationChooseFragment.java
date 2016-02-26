package im.mongo.demo.plugin.location;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.PlainTableView;
import net.datafans.android.common.widget.table.TableView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.TableViewDataSource;
import net.datafans.android.common.widget.table.TableViewDelegate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import im.mongo.R;

public class LocationChooseFragment extends Fragment implements AMapLocationListener, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, TableViewDataSource, TableViewDelegate {

    private MapView mapView;

    private AMap map;

    private AMapLocationClient locationClient;

    private GeocodeSearch geocodeSearch;

    private boolean firstLocated = false;

    private boolean enableSearch = false;

    private TableView<LocationInfo> tableView;

    private List<LocationInfo> locations = new ArrayList<>();

    private LocationInfo locatedInfo;

    private LocationInfo selectedInfo;

    private ImageView locationFocusView;

    private LatLng locatedPoint;

    public LocationChooseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.location_choose, container, false);

        locationFocusView = (ImageView) view.findViewById(R.id.locationFocus);
        locationFocusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCenter(locatedPoint);
            }
        });

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        LinearLayout tableContainer = (LinearLayout) view.findViewById(R.id.tableContainer);
        addTable(tableContainer);

        initMap();
        startLocation();

        return view;
    }

    private void initMap() {

        map = mapView.getMap();

        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        map.setOnCameraChangeListener(this);

        CameraUpdate update = CameraUpdateFactory.zoomTo(15);
        map.moveCamera(update);

        map.getUiSettings().setZoomControlsEnabled(false);
    }

    private void addTable(LinearLayout container) {
        PlainTableView.Builder<LocationInfo> builder = new PlainTableView.Builder<>();
        builder.setContext(getActivity());
        builder.setEnableRefresh(false);
        builder.setEnableLoadMore(false);
        builder.setEnableAutoLoadMore(false);
        builder.setDataSource(this).setDelegate(this);
        tableView = builder.build();
        container.addView(tableView.getView());
    }


    private void startLocation() {
        locationClient = new AMapLocationClient(getActivity());
        locationClient.setLocationListener(this);
        locationClient.startLocation();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onLocationChanged(AMapLocation location) {

        if (!firstLocated) {
            firstLocated = true;
            LogHelper.info(location.toString());
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            changeCenter(point);
            changeLocationFocusState(true);
            locatedPoint = point;
        }

    }

    private void changeCenter(LatLng latLng) {
        CameraUpdate update = CameraUpdateFactory.changeLatLng(latLng);
        map.moveCamera(update);


    }


    @Override
    public int getRows(int i) {
        return locations.size();
    }

    @Override
    public TableViewCell getTableViewCell(int section, int row) {
        return new LocationCell(R.layout.location_cell, getActivity());
    }

    @Override
    public Object getEntity(int section, int row) {
        return locations.get(row);
    }

    @Override
    public int getItemViewType(int i, int i1) {
        return 0;
    }

    @Override
    public int getItemViewTypeCount() {
        return 1;
    }

    @Override
    public void onClickRow(int section, int row) {
        enableSearch = false;
        LocationInfo info = locations.get(row);
        changeCenter(info.getLatLng());
        changeLocationFocusState(false);
        selectedInfo = info;

        for (LocationInfo i : locations) {
            i.setChecked(false);
        }

        info.setChecked(true);

        tableView.reloadData();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

        if (!enableSearch) {
            enableSearch = true;
            return;
        }
        LogHelper.error("区域改变： " + cameraPosition.target + "   " + Math.abs(cameraPosition.target.latitude - locatedPoint.latitude));

        if (Math.abs(cameraPosition.target.latitude - locatedPoint.latitude) < 0.000001)
            changeLocationFocusState(true);
        else
            changeLocationFocusState(false);

        //获得中心点地址
        if (geocodeSearch == null) {
            geocodeSearch = new GeocodeSearch(getActivity());
            geocodeSearch.setOnGeocodeSearchListener(this);
        }

        LatLonPoint point = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
        LogHelper.error("point: " + point);
        RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);

        //获得poi列表
        PoiSearch.Query poiQuery = new PoiSearch.Query("", "", "");
        poiQuery.setPageSize(20);
        poiQuery.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(getActivity(), poiQuery);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(point, 1000));
        poiSearch.searchPOIAsyn();

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        LogHelper.info("geo result: " + regeocodeResult.getRegeocodeAddress().getFormatAddress());

        LocationInfo info = new LocationInfo();
        String address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
        info.setName("定位");
        info.setAddress(address);
        info.setChecked(true);
        info.setLatLng(new LatLng(regeocodeResult.getRegeocodeQuery().getPoint().getLatitude(), regeocodeResult.getRegeocodeQuery().getPoint().getLongitude()));
        locatedInfo = info;
        selectedInfo = info;
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LogHelper.error("poi: " + poiResult);
        if (poiResult == null) return;
        List<PoiItem> items = poiResult.getPois();
        if (items == null) return;

        locations.clear();

        for (PoiItem item : items) {
            LocationInfo info = new LocationInfo();
            info.setName(item.getTitle());
            info.setAddress(item.getCityName() + item.getSnippet());
            info.setLatLng(new LatLng(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude()));
            locations.add(info);
        }

        locations.add(0, locatedInfo);
        tableView.reloadData();
        tableView.getAdapter().getListView().setSelection(0);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    private void changeLocationFocusState(boolean focused) {
        if (focused) {
            locationFocusView.setImageResource(R.mipmap.location_my_current);
        } else {
            locationFocusView.setImageResource(R.mipmap.location_my);
        }
    }


    public void send() {


        map.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {


                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String path = Environment.getExternalStorageDirectory() + "/test_"
                            + sdf.format(new Date()) + ".png";
                    FileOutputStream fos = new FileOutputStream(path);
                    boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    LogHelper.error(bitmap.getWidth() + "   " + bitmap.getHeight());
                    try {
                        fos.flush();
                    } catch (IOException e) {
                        LogHelper.error(e);
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        LogHelper.error(e);
                    }
                    if (b) {
                        LogHelper.debug("成功");
                        Intent intent = new Intent();
                        intent.putExtra("lat", selectedInfo.getLatLng().latitude);
                        intent.putExtra("lng", selectedInfo.getLatLng().longitude);
                        intent.putExtra("address", selectedInfo.getAddress());
                        intent.putExtra("uri", Uri.fromFile(new File(path)));
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();

                    } else {
                        LogHelper.error("截图失败");
                        getActivity().finish();
                    }
                } catch (FileNotFoundException e) {
                    LogHelper.error(e);
                }

            }
        });

    }


}
