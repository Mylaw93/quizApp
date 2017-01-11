package com.example.mwars.quizapp;

import android.app.Activity;
import android.os.Bundle;
 import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class Mod3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod3);


        NumberPicker numPicker = (NumberPicker) findViewById(R.id.number_picker);
        numPicker.setMaxValue(12);
        numPicker.setMinValue(1);
        numPicker.setValue(1);
        numPicker.setOnLongPressUpdateInterval(100);
        numPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                TextView tvPicker = (TextView) findViewById(R.id.text_picker);
//                Toast.makeText(getApplicationContext(), "OLD:" + oldVal + " - NEW:" + newVal, Toast.LENGTH_SHORT).show();
                tvPicker.setText(String.valueOf(newVal));

            }
        });
    }
}
