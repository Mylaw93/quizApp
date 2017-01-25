package com.example.mwars.quizapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_answer);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int goodAnswer = extras.getInt("goodAnswer");
            int badAnswer = extras.getInt("badAnswer");

            TextView tvGoodAnswer = (TextView) findViewById(R.id.text_good_answer);
            tvGoodAnswer.setText(String.valueOf(goodAnswer));
            TextView tvBadAnswer= (TextView) findViewById(R.id.text_bad_answer);
            tvBadAnswer.setText(String.valueOf(badAnswer));
        }

    }
}
