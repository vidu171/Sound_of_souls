package com.example.ashura.soundofsoul;

import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vidu on 26/2/17.
 */

public class utils {
    public static ArrayList<view> deivedArray ;

    public static ArrayList<view> arrydivider (ArrayList<view> completearray ){

        Random rand = new Random();

        int  n = rand.nextInt(2000);

        for(int i =0 ; i<10;i++){
            deivedArray.add(completearray.get(n+i));
        }
        return deivedArray;

    }

}
