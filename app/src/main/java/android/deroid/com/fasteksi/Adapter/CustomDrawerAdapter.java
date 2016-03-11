package android.deroid.com.fasteksi.Adapter;

import android.app.Activity;
import android.content.Context;
import android.deroid.com.fasteksi.Model.DrawerItem;
import android.deroid.com.fasteksi.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gulshank on 04-02-2016.
 */
public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResId;

    public CustomDrawerAdapter(Context context, int resource , List<DrawerItem> itemList) {
        super(context, resource , itemList);
        this.context = context;
        this.drawerItemList = itemList;
        this.layoutResId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder holder;
        View view = convertView;

        if(view == null){

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            holder = new DrawerItemHolder();

            view = inflater.inflate(layoutResId ,parent ,false);
            holder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
            holder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            view.setTag(holder);
        }else {
            holder = (DrawerItemHolder) view.getTag();
        }

        DrawerItem item = (DrawerItem) this.drawerItemList.get(position);
        holder.icon.setImageDrawable(view.getResources().getDrawable(item.getImgResID()));
        holder.ItemName.setText(item.getItemName());

        return  view;
    }


    private class DrawerItemHolder {

        TextView ItemName;
        ImageView icon;
    }
}
