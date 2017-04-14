package com.example.ashura.soundofsoul;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by vidu on 26/2/17.
 */


public class title_adapter extends ArrayAdapter<poem_class> {

    ArrayList<poem_class> data;
    public title_adapter(Context context, ArrayList<poem_class> datas){
        super(context,0,datas);
        data=datas;
    }

                @NonNull
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final poem_class current_poem = data.get(position);

                            View listItemview = convertView;
                            if(listItemview==null) {
                                listItemview = LayoutInflater.from(getContext()).inflate(R.layout.title_view, parent, false);
                                TextView title_text = (TextView) listItemview.findViewById(R.id.title_text);
                                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/sans.ttf");
                                title_text.setTypeface(font);

                                ImageView title_share = (ImageView) listItemview.findViewById(R.id.title_share);
                                if (current_poem.poem_lines == "x") {


                                    title_share.setVisibility(View.GONE);

                                } else {


                                    title_share.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            InputStream ins = getContext().getResources().openRawResource(
                                                    getContext().getResources().getIdentifier("a" + String.valueOf(current_poem.poem_id),
                                                            "raw", getContext().getPackageName()));

                                            InputStreamReader reader = new InputStreamReader(ins, Charset.forName("UTF-8"));
                                            BufferedReader bufferedReader = new BufferedReader(reader);

                                            StringBuilder output = new StringBuilder();
                                            String text = null;
                                            try {
                                                String line = bufferedReader.readLine();
                                                while (line != null) {
                                                    output.append(line);
                                                    line = bufferedReader.readLine();
                                                }


                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                JSONObject root = new JSONObject(output.toString());
                                                text = root.getString("text");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Intent i = new Intent();
                                            i.setAction(Intent.ACTION_SEND);
                                            i.putExtra(Intent.EXTRA_TEXT, text);
                                            i.setType("text/plain");
                                            getContext().startActivity(i);
                                        }
                                    });

                                }
                            }



                            final poem_class current_Title = getItem(position);
                    if(current_Title.poem_title!=null) {
                        TextView title_text = (TextView) listItemview.findViewById(R.id.title_text);
                        title_text.setText(current_Title.poem_title);
                        if(current_Title.poem_autor!="x") {
                            TextView authour_name = (TextView) listItemview.findViewById(R.id.author);
                            authour_name.setText(current_Title.poem_autor);
                        }

                    }
                    else if(current_Title.poem_title==null){
                        TextView authour_name = (TextView) listItemview.findViewById(R.id.title_text);
                        authour_name.setText(current_Title.poem_autor);
                    }


                    return listItemview;
                }

}
