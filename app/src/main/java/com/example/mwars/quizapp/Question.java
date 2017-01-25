package com.example.mwars.quizapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwars on 19.12.2016.
 */

public class Question {

    int id;
    public String question;
    public List<String> answers = new ArrayList<String>();


    public Question(){}


    public Question(int id, String question, List<String> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public Question(String question, List<String> answers){
        this.question = question;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer(){

        return 0;
    }
}
