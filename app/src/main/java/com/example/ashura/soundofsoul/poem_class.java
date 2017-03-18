package com.example.ashura.soundofsoul;

/**
 * Created by vidu on 26/2/17.
 */

public class poem_class {

    String title_view = new String();
    String poem_title = new String();
    String poem_autor = new String();
    String poem_lines = new String();
    int poem_id;

    public poem_class (String author){
        poem_autor = author;
    }

    public poem_class (String title , String authour , String lines){

        poem_title = title;
        poem_autor = authour;
        poem_lines = lines;


    }
    public  poem_class (String title , String author  , int id){
        poem_title=title;
        poem_autor=author;
        poem_id=id;
    }


    public String getTitle_view(){
        return title_view;
    }
}
