package com.app.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Integer> numbers = new ArrayList<>();
        final ArrayList<Integer> data = getIntentData();

        if (data != null) {
            TextView resultsView = findViewById(R.id.resultsView);
            String text = getString(R.string.data_textView,
                    data.get(0),
                    data.get(1),
                    data.get(2));
            resultsView.setText(text);
        }

        fillArrayList(numbers);

        findViewById(R.id.goSecActButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putIntegerArrayListExtra("ListOfNumbers", numbers);
                startActivity(intent);
            }
        });

    }

    private static void fillArrayList(ArrayList<Integer> list) {
        Random rand = new Random();

        int range = rand.nextInt(rand.nextInt(9998) + 1) + 1;
        int whileNumber = rand.nextInt(499) + 1;
        if (whileNumber % 2 != 0) {
            ++whileNumber;
        }

        list.add(rand.nextInt(range) + 1);

        int randomNumber;
        int counter = 0;

        // Цикл для заполнения списка.
        do {
            randomNumber = rand.nextInt(range) + 1;

            // Проверяем, имеется ли копия числа в списке.
            // Каждый раз, когда находит копию - возвращается в начало листа и проверяет новое число
            for (int i = 0; i < list.size(); ) {
                if (list.get(i) == randomNumber) {
                    i = 0;
                    randomNumber = rand.nextInt(range) + 1;
                } else {
                    i++;
                }
            }

            // если не нашел копий после for, число добавляется в список
            list.add(randomNumber);
            ++counter;

        } while (counter != whileNumber);

    }

    private ArrayList<Integer> getIntentData() {
        Intent intent = getIntent();

        if (intent != null) {
            return intent.getIntegerArrayListExtra("data");
        }
        return null;
    }

}