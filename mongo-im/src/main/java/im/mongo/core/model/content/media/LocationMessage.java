package im.mongo.core.model.content.media;

import android.net.Uri;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 16/2/8.
 */
@MessageContent.Tag(type = MessageContent.Type.LOCATION, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class LocationMessage extends MediaMessage {

    private double lat;
    private double lng;
    private String address;
    private Uri imageUri;
    private String thumbImage;

    //高德地图静态api
    private String mapStaticUrl;

    public String getThumbImage() {
        return thumbImage;
    }

    public LocationMessage setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public LocationMessage setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public LocationMessage setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LocationMessage setAddress(String address) {
        this.address = address;
        return this;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public LocationMessage setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
        return this;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public void decode(byte[] bytes) {

    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", address='" + address + '\'' +
                ", imageUri=" + imageUri +
                ", thumbImage='" + thumbImage + '\'' +
                '}';
    }

    public String getMapStaticUrl() {
        return mapStaticUrl;
    }

    public LocationMessage setMapStaticUrl(String mapStaticUrl) {
        this.mapStaticUrl = mapStaticUrl;
        return this;
    }
}
