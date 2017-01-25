package com.example.mwars.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QuizActivity extends Activity {

    public TextView tvScore;
    public Question questSA, questMA;
    public ViewGroup singleAnswerView, multipleAnswerView;
    public List<Question> singleAnswerQuestions, multipleAnswerQuestions, questions;
    public ProgressBar progressBar;
    public Boolean isReady = true, running = true;
    public CountDownTimer countDownTimer;
    public int questionNr = 0, score = 0;
    public float percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        declareQuestion();
//        tutorial();
        startQuiz();

    }


    public void startQuiz() {
        tvScore = (TextView) findViewById(R.id.score_view);
//        while(running){
//        for (Question question : questions) {
        if (isReady == true)
            if (questionNr < questions.size())
                loadQuestionView(questions.get(questionNr));
//            }
//        }
    }


    public void loadQuestionView(final Question question) {
        isReady = false;
        questionNr += 1;
        tvScore.setText(questionNr + "/" + questions.size() + " - " + percent + "%");
        ViewGroup answersLayout = (ViewGroup) findViewById(R.id.answers_layout);
        LayoutInflater layInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        TextView tvQuestion = (TextView) findViewById(R.id.question_view);
        String questionType = question.getClass().getSimpleName();
        tvQuestion.setText(question.question);


        if (questionType.contains("QuestionSingleAnswer")) {

            singleAnswerView = (ViewGroup) layInflater.inflate(R.layout.single_answer, null);
            final RadioGroup rgView = (RadioGroup) singleAnswerView.getChildAt(0);
            answersLayout.addView(singleAnswerView, 3, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            for (int i = 0; i < rgView.getChildCount() - 1; i++) {
                RadioButton rbAnswer = (RadioButton) rgView.getChildAt(i);
                rbAnswer.setText(question.answers.get(i)       );
                rbAnswer.setTextColor(Color.BLACK);
            }

            startCountDown();
            Button bttnCheck = (Button) rgView.getChildAt(4);
            bttnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int checkedId = 0;
                    for (int i = 0; i < rgView.getChildCount() - 1; i++) {
                        RadioButton rbAnswer = (RadioButton) rgView.getChildAt(i);
                        if (rbAnswer.isChecked())
                            checkedId = i + 1;
                    }
                    checkAnswer(singleAnswerView, question, checkedId);
                }
            });
        } else if (questionType.contains("QuestionMultipleAnswer")) {

            multipleAnswerView = (ViewGroup) layInflater.inflate(R.layout.multiple_answer, null);
            answersLayout.addView(multipleAnswerView, 3, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            for (int i = 0; i < multipleAnswerView.getChildCount() - 1; i++) {
                CheckBox cbAnswer = (CheckBox) multipleAnswerView.getChildAt(i);
                cbAnswer.setText(question.answers.get(i));
                cbAnswer.setTextColor(Color.BLACK);

            }

            startCountDown();

            Button bttnCheck = (Button) multipleAnswerView.getChildAt(4);
            bttnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringBuilder checkedId = new StringBuilder();
                    for (int i = 0; i < multipleAnswerView.getChildCount() - 1; i++) {
                        CheckBox cbAnswer = (CheckBox) multipleAnswerView.getChildAt(i);
                        if (cbAnswer.isChecked())
                            checkedId.append(i + 1);
                    }
                    checkAnswer(multipleAnswerView, question, Integer.parseInt(checkedId.toString()));
                }
            });
        }
    }


    public void startCountDown(){
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        countDownTimer = new CountDownTimer(10000, 100) {
            @Override
            public void onTick(long tick) {
                progressBar.setProgress(Integer.parseInt(String.valueOf(tick/100)));
            }

            @Override
            public void onFinish() {
                new CountDownTimer(3000, 3000) {
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        if (!(questionNr == questions.size()))
                            nextQuestion();
                        else
                            showResult();
                    }
                }.start();
                Toast.makeText(getApplicationContext(), "KONIEC CZASU!!", Toast.LENGTH_LONG).show();
            }
        };
        countDownTimer.start();
    }


    public void checkAnswer(ViewGroup viewGroup, Question question, int checkedId) {
        if (countDownTimer!=null)
            countDownTimer.cancel();

        int correctAnswerId = question.getCorrectAnswer();
        Button buttonCheck;
        Boolean answerFlag = false;

        if (viewGroup.getChildCount() == 1) {                                                         // SINGLE ANSWER
            RadioGroup rgAnswers = (RadioGroup) viewGroup.getChildAt(0);
            buttonCheck = (Button) rgAnswers.getChildAt(rgAnswers.getChildCount() - 1);
            if (correctAnswerId == checkedId) {
                RadioButton correctAnswer = (RadioButton) rgAnswers.getChildAt(checkedId - 1);
                correctAnswer.setBackgroundColor(Color.GREEN);
                buttonCheck.setText("POPRAWNA ODPOWIEDŹ!");
                buttonCheck.setBackgroundColor(Color.GREEN);
                answerFlag = true;
            } else {
//                RadioButton badAnswer = (RadioButton) rgAnswers.getChildAt(checkedId - 1);
//                badAnswer.setBackgroundColor(Color.RED);
                buttonCheck.setText("ZŁA ODPOWIEDŹ!");
                buttonCheck.setBackgroundColor(Color.RED);
                answerFlag = false;
            }
        } else {                                                                                       // MULTIPLE ANSWERS
            buttonCheck = (Button) viewGroup.getChildAt(viewGroup.getChildCount() - 1);
            if (correctAnswerId == checkedId) {
                answerFlag = true;
                buttonCheck.setText("POPRAWNA ODPOWIEDŹ!");
                buttonCheck.setBackgroundColor(Color.GREEN);
            } else {
                answerFlag = false;
                buttonCheck.setText("ZŁA ODPOWIEDŹ!");
                buttonCheck.setBackgroundColor(Color.RED);
            }

            List<Integer> checkedDigits = new ArrayList<>(digits(checkedId));
            List<Integer> answersDigits = new ArrayList<>(digits(correctAnswerId));

            for (int i = 0; i < checkedDigits.size(); i++)
                if (answersDigits.contains(checkedDigits.get(i))) {
                    CheckBox correctAnswer = (CheckBox) viewGroup.getChildAt(checkedDigits.get(i) - 1);
                    correctAnswer.setBackgroundColor(Color.GREEN);
                }
//                    else {
//                        CheckBox badAnswer = (CheckBox) viewGroup.getChildAt(checkedDigits.get(i) - 1);
//                        badAnswer.setBackgroundColor(Color.RED);
//                    }
        }

        isReady = true;

        if (answerFlag == true) {
            answerFlag = false;
            score += 1;
        }

        percent = (Float.intBitsToFloat(score) / Float.intBitsToFloat(questionNr)) * 100;

        tvScore.setText(questionNr + "/" + questions.size() + " - " + percent + "%");


        new CountDownTimer(3000, 3000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                if (!(questionNr == questions.size()))
                    nextQuestion();
                else
                    showResult();
            }
        }.start();

        return;
    }


    public void nextQuestion() {
//        Log.d("QUEST NR", String.valueOf(questionNr));
        if (questionNr < questions.size())
            loadQuestionView(questions.get(questionNr));
    }


    public void showResult() {
//        Log.d("RESULT", String.valueOf(questionNr));
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("goodAnswer", score);
        intent.putExtra("badAnswer", questions.size() - score);
        startActivity(intent);

//        ViewGroup answersLayout = (ViewGroup) findViewById(R.id.answers_layout);
//        LayoutInflater layInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
//        ViewGroup resultView = (ViewGroup) layInflater.inflate(R.layout.result_answer, null);
//        answersLayout.addView(resultView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
//        TextView goodAnswer = (TextView) findViewById(R.id.text_good_answer);
//        TextView badAnswer = (TextView) findViewById(R.id.text_bad_answer);
//        goodAnswer.setText(String.valueOf(score));
//        badAnswer.setText(String.valueOf(questions.size() - score));
    }


    public void declareQuestion() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.addQuestion(new QuestionSingleAnswer("Jak nazywa się uznanie własnego dziecka za swoje?",
                Arrays.asList("przysposobienie", "aborcja", "adopcja", "symbioza"), 3));
        Toast.makeText(getApplicationContext(), db.getAllQuestions().get(0).getQuestion().toString(), Toast.LENGTH_LONG).show();


        questions = new ArrayList<>();
        questSA = new QuestionSingleAnswer("Jak nazywa się uznanie własnego dziecka za swoje?",
                Arrays.asList("przysposobienie", "aborcja", "adopcja", "symbioza"), 3);
        questMA = new QuestionMultipleAnswer("Kto NIE rozśmiesza ludzi w cyrku?",
                Arrays.asList("komik", "satyryk", "klown", "pajac"), Arrays.asList(1, 2, 4));
        questions.add(questMA);
        questions.add(questSA);
        questSA = new QuestionSingleAnswer("Jak nazywa się rybi tłuszcz?",
                Arrays.asList("smalec", "mleko", "masło", "tran"), 4);
        questMA = new QuestionMultipleAnswer("Który z napojów zawiera kofeinę?",
                Arrays.asList("inka", "kawa", "herbata", "coca-cola"), Arrays.asList(2, 4));
        questions.add(questMA);
        questions.add(questSA);
        questSA = new QuestionSingleAnswer("Którego dnia obchodzone jest Boże Narodzenie?",
                Arrays.asList("24-26 grudnia", "25-26 grudnia", "24-25 grudnia", "24-31 grudnia"), 2);
        questMA = new QuestionMultipleAnswer("Ile dni ma luty?",
                Arrays.asList("28", "30", "29", "27"), Arrays.asList(1, 3));
        questions.add(questMA);
        questions.add(questSA);
        questSA = new QuestionSingleAnswer("Jaka jest druga najbliższa gwiazda Ziemi?",
                Arrays.asList("Syriusz", "Słońce", "Polarna", "Centauri"), 4);
        questMA = new QuestionMultipleAnswer("Które z podanych owoców jest kwaśny?",
                Arrays.asList("Jabłko", "Grejfrut", "Cytryna", "Pomarańcz"), Arrays.asList(2, 3));
        questions.add(questMA);
        questions.add(questSA);
    }


    public void tutorial(){
        LayoutInflater layInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        ViewGroup tutorialLayout = (ViewGroup) layInflater.inflate(R.layout.tutorial, null);
        ViewGroup answersLayout = (ViewGroup) findViewById(R.id.answers_layout);
        answersLayout.addView(tutorialLayout, 3, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        new CountDownTimer(5000, 5000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                startQuiz();
            }
        }.start();
    }

    private List<Integer> digits(int i) {
        List<Integer> digits = new ArrayList<>();
        while (i > 0) {
            digits.add(i % 10);
            i /= 10;
        }
        return digits;
    }
}