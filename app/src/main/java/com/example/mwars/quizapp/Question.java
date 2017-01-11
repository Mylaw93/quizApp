package com.example.mwars.quizapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwars on 19.12.2016.
 */

public class Question {

    public String question;
    public List<String> answers = new ArrayList<String>();

    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }



    public int getCorrectAnswer(){
        return 0;
    }
}
