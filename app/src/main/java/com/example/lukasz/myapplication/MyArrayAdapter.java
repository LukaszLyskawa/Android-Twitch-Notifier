package com.example.lukasz.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;
import com.example.lukasz.myapplication.TwitchApiJson.FollowsStreams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukasz on 14.12.15.
 */
public class MyArrayAdapter extends ArrayAdapter<FollowsStreams> {

    private List<FollowsStreams> items;
    private LayoutInflater inflater;

    public MyArrayAdapter(Context context, int resource) {
        super(context, resource);

        inflater = LayoutInflater.from(context);

        items = new ArrayList<>();
    }

    public void setItems(List<FollowsStreams> items){
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
        }
        FollowsStreams item = items.get(position);
        convertView.findViewById(R.id.itemPreview).animate().rotationY(360).setDuration(500);
        new DownloadImageTask(((ImageView)convertView.findViewById(R.id.itemPreview))).execute(item.getPreview().getMedium());
        ((TextView) convertView.findViewById(R.id.itemTitle)).setText(item.getChannel().getDisplay_name());
        ((TextView) convertView.findViewById(R.id.itemViewers)).setText(withSuffix(item.getViewers()));
        ((TextView) convertView.findViewById(R.id.itemGame)).setText(item.getGame());
        ((TextView) convertView.findViewById(R.id.itemStatus)).setText(item.getChannel().getStatus());
        return convertView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public List<FollowsStreams> getItems(){
        return items;
    }

    private static String withSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp - 1));
    }
}

