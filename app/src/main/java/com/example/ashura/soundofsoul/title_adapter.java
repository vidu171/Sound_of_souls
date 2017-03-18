package com.example.ashura.soundofsoul;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vidu on 26/2/17.
 */

public class title_adapter extends ArrayAdapter<poem_class> {
    public title_adapter(Context context, ArrayList<poem_class> datas){
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
