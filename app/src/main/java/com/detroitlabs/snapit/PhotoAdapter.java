package com.detroitlabs.snapit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by andrewgiang on 1/28/15.
 */
public class PhotoAdapter extends ParseQueryAdapter<Snap> {
    private static ParseQueryAdapter.QueryFactory<Snap> factory =
            new ParseQueryAdapter.QueryFactory<Snap>() {
                public ParseQuery<Snap> create() {
                    ParseQuery<Snap> query = new ParseQuery<>(Snap.class);
                    query.whereEqualTo("receiver", ParseUser.getCurrentUser());
                    query.include("sender");
                    return query;
                }
            };

    public PhotoAdapter(Context context) {
        super(context, factory);

    }

    @Override
    public View getItemView(Snap object, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_snap, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        final ParseUser sender = object.getSender();
        viewHolder.sender.setText(sender.getUsername());
        return convertView;
    }

    private static class ViewHolder {
        TextView sender;

        public ViewHolder(View convertView) {
            sender = (TextView) convertView.findViewById(R.id.sender);
        }
    }
}
