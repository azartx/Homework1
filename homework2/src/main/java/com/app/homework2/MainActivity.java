package com.app.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Integer> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers = new ArrayList<>(fillArray());

        findViewById(R.id.goSecActButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putIntegerArrayListExtra("ListOfNumbers", numbers);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void resultsOnScreen(ArrayList<Integer> results) {
        if (results != null) {
            TextView resultsView = findViewById(R.id.resultsView);
            String text = getString(R.string.data_textView,
                    results.get(0),
                    results.get(1),
                    results.get(2));
            resultsView.setText(text);
        }
    }

    private ArrayList<Integer> fillArray() {
        HashSet<Integer> set = new HashSet<Integer>();
        Random rand = new Random();
        int forBound = rand.nextInt(499) + 1;

        for (int i = 0; i < forBound; i++) {
            set.add(rand.nextInt(9998) + 1);
        }
        return new ArrayList<>(set);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Log.i("TAG2", data.getIntegerArrayListExtra("data").toString());
            numbers = fillArray();
            resultsOnScreen(data.getIntegerArrayListExtra("data"));
        }
    }

}