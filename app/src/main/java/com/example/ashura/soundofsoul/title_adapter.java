package com.example.ashura.soundofsoul;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vidu on 26/2/17.
 */

public class title_adapter extends ArrayAdapter<view> {
    public title_adapter(Context context, ArrayList<view> datas){
        super(context,0,datas);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemview = convertView;
        if(listItemview==null){
            listItemview = LayoutInflater.from(getContext()).inflate(R.layout.title_view,parent,false);
            TextView title_text = (TextView) listItemview.findViewById(R.id.title_text);
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/sans.ttf");
            title_text.setTypeface(font);
        }

        final view current_Title = (view) getItem(position);
        TextView title_text = (TextView) listItemview.findViewById(R.id.title_text);
        title_text.setText(current_Title.getTitle_view());



        return listItemview;
    }

}
