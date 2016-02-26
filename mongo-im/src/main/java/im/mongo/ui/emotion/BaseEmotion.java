package im.mongo.ui.emotion;

import android.view.View;

/**
 * Created by zhonganyun on 16/2/11.
 */
public abstract class BaseEmotion {

    protected String icon;
    protected int iconRes;
    protected int pageCount;
    protected int itemCount;
    protected int pageOffset;

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public abstract View getPageView(final int pageIndex);
}
