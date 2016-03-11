package android.deroid.com.fasteksi.Model;

/**
 * Created by gulshank on 04-02-2016.
 */
public class DrawerItem {

    String ItemName;
    int imgResID;

    public DrawerItem(String itemName, int imgResid) {
        super();
        ItemName = itemName;
        this.imgResID = imgResid;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public int getImgResID() {
        return imgResID;
    }
}
