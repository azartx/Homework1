package com.app.homework2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.homework2.data.CentralComp;
import com.app.homework2.data.Evil;
import com.app.homework2.data.MiddleArithmetical;
import com.app.homework2.data.Summ;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ArrayList<Integer> numbers = getIntentData();
        final TextView logOnScreen = findViewById(R.id.logView);

        if (numbers != null) {
            logOnScreen.setText(numbers.toString());
            Log.i("TAG", "Нагенеренные числа:\n" + numbers.toString());
        } else {
            Log.i("TAG", "Your ArrayList is null.");
            numbers = new ArrayList<>();
            numbers.add(12345);
            numbers.add(54321);
            logOnScreen.setText(numbers.toString());
        }

        CentralComp subject = new CentralComp();

        final MiddleArithmetical average = new MiddleArithmetical(subject);
        final Summ summ = new Summ(subject);
        final Evil evil = new Evil(subject);

        subject.changeData(numbers); // сразу отдаём команду обсерверу на уведомление подписчиков

        findViewById(R.id.showInformation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformation(logOnScreen, average, summ, evil);
            }
        });

        // По нажатии кнопки назад мы передаём расчеты в мэйн активити при помощи еще одного списка
        findViewById(R.id.backOnMainActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> data = new ArrayList<>();
                data.add(summ.getSumm());
                data.add(average.getAverage());
                data.add(evil.getFirstPart());


                intent.putIntegerArrayListExtra("data", data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void showInformation(TextView logOnScreen, MiddleArithmetical average, Summ summ, Evil evil) {
        // делаю кнопку неактивной после нажатия на нее и отображаю информацию в текст вью
        // кнопка снова станет активной после смены UI
        findViewById(R.id.showInformation).setClickable(false);
        String text = getString(R.string.data_textView,
                summ.getSumm(),
                average.getAverage(),
                evil.getFirstPart());
        logOnScreen.setText(logOnScreen.getText() + "\n\n" + text);
    }

    private ArrayList<Integer> getIntentData() {
        intent = getIntent();

        if (intent != null) {
            return intent.getIntegerArrayListExtra("ListOfNumbers");
        }
        return null;
    }

}