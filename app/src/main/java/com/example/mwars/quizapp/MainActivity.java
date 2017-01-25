package com.example.mwars.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void buttonClicked(View v){
        String btnName = getResources().getResourceEntryName(v.getId());
        Intent intent = new Intent();

        if(btnName.contains("button_quiz")){
//            Uri number = Uri.parse("tel:506630739");
//            intent = new Intent(Intent.ACTION_DIAL, number);
            intent = new Intent(this, QuizActivity.class);
        } else if(btnName.contains("button_settings")){
            intent = new Intent(this, Mod3Activity.class);
        } else if(btnName.contains("button_account")){
            intent = new Intent(this, AccountActivity.class);
        } else if(btnName.contains("button_mod3")){
            intent = new Intent(this, Mod3Activity.class);
        }

//        intent.putExtra();

        try{
            startActivity(intent);
        } catch (Exception e){
            Log.d("START INTENT EX. : ", e.toString());
        }
    }
}
