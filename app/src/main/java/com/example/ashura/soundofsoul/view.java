package com.example.ashura.soundofsoul;

/**
 * Created by vidu on 26/2/17.
 */

public class view {

    String title_view = new String();
    String poem_title = new String();
    String poem_autor = new String();
    String poem_lines = new String();

    public view (String Title){

        title_view = Title;
    }

    public view (String title , String authour , String lines){

        poem_title = title;
        poem_autor = authour;
        poem_lines = lines;


    }

    public String getTitle_view(){
        return title_view;
    }
}
