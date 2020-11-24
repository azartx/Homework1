package com.app.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.homework2.data.CentralComp;
import com.app.homework2.data.MiddleArithmetical;
import com.app.homework2.data.Summ;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        CentralComp subject = new CentralComp();
        ArrayList<Integer> numbers = getIntentData();
        if (numbers != null) {
            Log.i("TAG", "Нагенеренные числа:\n" + numbers.toString());
        } else {
            Log.i("TAG", "Your ArrayList is null");
        }

        new MiddleArithmetical(subject);
        new Summ(subject);

        subject.changeData(numbers);

    }

    private ArrayList<Integer> getIntentData() {
        Intent intent = getIntent();

        if (intent != null) {
            return intent.getIntegerArrayListExtra("ListOfNumbers");
        }
        return null;
    }

}