package com.app.homework3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // drawable res я не сортировал по папкам,
    // так как некоторые картинки используются в разных layouts.
    // этот код написан только для удобства

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.firstLiner);
        Button button2 = findViewById(R.id.firstTable);
        Button button3 = findViewById(R.id.secondLiner);
        Button button4 = findViewById(R.id.secondConstraint);
        Button button5 = findViewById(R.id.secondRelative);
        Button button6 = findViewById(R.id.thirdLiner);
        Button button7 = findViewById(R.id.thirdConstraint);
        Button button8 = findViewById(R.id.thirdRelative);
        Button button9 = findViewById(R.id.forthLiner);
        Button button10 = findViewById(R.id.forthConstraint);
        Button button11 = findViewById(R.id.forthRelative);
        Button button12 = findViewById(R.id.fiveth);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.firstLiner:
                        setContentView(R.layout.first_layout_leaner);
                        break;
                    case R.id.firstTable:
                        setContentView(R.layout.first_layout_table);
                        break;
                    case R.id.secondLiner:
                        setContentView(R.layout.second_layout_liner_frame);
                        break;
                    case R.id.secondConstraint:
                        setContentView(R.layout.second_layout_constraint);
                        break;
                    case R.id.secondRelative:
                        setContentView(R.layout.second_layout_relative);
                        break;
                    case R.id.thirdLiner:
                        setContentView(R.layout.third_layout_linear);
                        break;
                    case R.id.thirdConstraint:
                        setContentView(R.layout.third_layout_constraint);
                        break;
                    case R.id.thirdRelative:
                        setContentView(R.layout.third_layout_relative);
                        break;
                    case R.id.forthLiner:
                        setContentView(R.layout.forth_layout_liner);
                        break;
                    case R.id.forthConstraint:
                        setContentView(R.layout.forth_layout_constraint);
                        break;
                    case R.id.forthRelative:
                        setContentView(R.layout.forth_layout_relative);
                        break;
                    case R.id.fiveth:
                        setContentView(R.layout.fiveth_layout_constraint);
                        break;
                }

            }
        };

        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
        button5.setOnClickListener(onClickListener);
        button6.setOnClickListener(onClickListener);
        button7.setOnClickListener(onClickListener);
        button8.setOnClickListener(onClickListener);
        button9.setOnClickListener(onClickListener);
        button10.setOnClickListener(onClickListener);
        button11.setOnClickListener(onClickListener);
        button12.setOnClickListener(onClickListener);


    }
}
