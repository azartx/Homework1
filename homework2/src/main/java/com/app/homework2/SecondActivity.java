package com.app.homework2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        CentralComp subject = new CentralComp();

        new MiddleArithmetical(subject);

        subject.changeData(getIntentData());

    }

    private ArrayList<Integer> getIntentData() {
        Intent intent = getIntent();

        if (intent != null) {
            return intent.getIntegerArrayListExtra("ListOfNumbers");
        }
        return null;
    }

}