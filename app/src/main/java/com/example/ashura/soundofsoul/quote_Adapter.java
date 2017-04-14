package com.example.ashura.soundofsoul;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by vidu on 21/3/17.
 */

public class quote_Adapter extends ArrayAdapter<quotes_class> {

    TextView quote_text;

    public quote_Adapter( Context context, ArrayList<quotes_class> resource) {
        super(context, 0, resource);
    }

    private class ViewHolder{
        TextView quote = quote_text;
        ImageView share ;
    }
    ViewHolder holder = new ViewHolder();

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        final quotes_class current_quote = getItem(position);
        View listitemview = convertView;

        if (listitemview == null) {

            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.quote_view, parent, false);
        }
            holder.quote = (TextView) listitemview.findViewById(R.id.quotes_text);



            holder.quote.setText(current_quote.mtext);
            holder.share = (ImageView) listitemview.findViewById(R.id.quote_share);

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_TEXT,current_quote.mtext);
                    i.setType("text/plain");
                    getContext().startActivity(i);



                }
            });
            listitemview.setTag(holder);


        return listitemview;
    }
}
