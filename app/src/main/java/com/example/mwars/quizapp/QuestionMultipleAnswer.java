package com.example.mwars.quizapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwars on 19.12.2016.
 */

public class QuestionMultipleAnswer extends Question {

    public List<Integer> correctAnswers = new ArrayList<>();


    public QuestionMultipleAnswer(String question, List<String> answers, List<Integer> correctAnswers) {
        super(question, answers);
        this.correctAnswers = correctAnswers;

    }

    @Override
    public int getCorrectAnswer() {
        StringBuilder correctAnswer = new StringBuilder();
        for (int i = 0; i < correctAnswers.size(); i++) {
            correctAnswer.append(correctAnswers.get(i));
        }
        return Integer.parseInt(correctAnswer.toString());
    }
}
