package com.app.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Integer> numbers = new ArrayList<>();

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

        list.add(rand.nextInt(499) + 1);

        int randomNumber;
        int counter = 0;

        // Цикл для заполнения списка.
        do {
            randomNumber = rand.nextInt(499) + 1;

            // Проверяем, имеется ли копия числа в списке.
            for (int i = 0; i < list.size(); ) {
                if (list.get(i) == randomNumber) {
                    i = 0;
                    randomNumber = rand.nextInt(499) + 1;
                } else {
                    i++;
                }
            }

            list.add(randomNumber);
            ++counter;

        } while (counter != 19);

    }

}