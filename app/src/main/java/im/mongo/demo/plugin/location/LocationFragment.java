package im.mongo.demo.plugin.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

import im.mongo.R;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class LocationFragment extends Fragment {

    private MapView mapView;

    private AMap map;


    public LocationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();

        double lat = intent.getDoubleExtra("lat", 0);
        double lng = intent.getDoubleExtra("lng", 0);

        View view = inflater.inflate(R.layout.fragment_location, null);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();

        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        CameraUpdate update = CameraUpdateFactory.zoomTo(15);
        map.moveCamera(update);
        map.getUiSettings().setZoomControlsEnabled(false);

        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate locationUpdate = CameraUpdateFactory.changeLatLng(latLng);
        map.moveCamera(locationUpdate);

        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        map.addMarker(options);

        return view;
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
}
