package com.example.mwars.quizapp;

import java.util.List;


/**
 * Created by mwars on 19.12.2016.
 */

public class QuestionSingleAnswer extends Question {

    public int correctAnswer;


    public QuestionSingleAnswer(String question, List<String> answers, int correctAnswer) {
        super(question, answers);
        this.correctAnswer = correctAnswer;

    }

    @Override
    public int getCorrectAnswer(){
        return correctAnswer;
    }
}
