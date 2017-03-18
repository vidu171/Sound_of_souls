package com.example.ashura.soundofsoul;

import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vidu on 26/2/17.
 */

public class utils {
    public static ArrayList<poem_class> deivedArray ;

    public static ArrayList<poem_class> arrydivider (ArrayList<poem_class> completearray ){

        Random rand = new Random();

        int  n = rand.nextInt(2000);

        for(int i =0 ; i<10;i++){
            deivedArray.add(completearray.get(n+i));
        }
        return deivedArray;

    }

}
