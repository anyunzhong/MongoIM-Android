package im.mongo.ui.emotion;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class PackageEmotionItem {

    private String name;
    private String remoteThumb;
    private String remoteGif;
    private String localThumb;
    private String localGif;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemoteThumb() {
        return remoteThumb;
    }

    public void setRemoteThumb(String remoteThumb) {
        this.remoteThumb = remoteThumb;
    }

    public String getRemoteGif() {
        return remoteGif;
    }

    public void setRemoteGif(String remoteGif) {
        this.remoteGif = remoteGif;
    }

    public String getLocalThumb() {
        return localThumb;
    }

    public void setLocalThumb(String localThumb) {
        this.localThumb = localThumb;
    }

    public String getLocalGif() {
        return localGif;
    }

    public void setLocalGif(String localGif) {
        this.localGif = localGif;
    }
}
