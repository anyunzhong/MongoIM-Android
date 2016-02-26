package im.mongo.demo.plugin.location;

import com.amap.api.maps2d.model.LatLng;

/**
 * Created by zhonganyun on 16/2/8.
 */
public class LocationInfo {

    private String name;
    private String address;
    private LatLng latLng;
    private boolean checked;

    public String getName() {
        return name;
    }

    public LocationInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LocationInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public LocationInfo setLatLng(LatLng latLng) {
        this.latLng = latLng;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public LocationInfo setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latLng=" + latLng +
                ", checked=" + checked +
                '}';
    }
}
