package com.example.ashura.soundofsoul;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidu on 10/3/17.
 */

public  class poemLoader extends AsyncTaskLoader<List<poem_class>> {
    public poemLoader(Context context){
        super(context);
    }

    @Override
    public List<poem_class> loadInBackground() {
        List<poem_class>poem = new ArrayList<poem_class>();
        return poem;
    }
}
