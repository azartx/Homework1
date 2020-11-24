package com.app.homework2.data;

import android.util.Log;

import com.app.homework2.interfaces.Notifier;
import com.app.homework2.interfaces.Observer;

import java.util.ArrayList;

public class Summ implements Observer {
    private Notifier notifier;

    public Summ(Notifier notifier){
        this.notifier = notifier;
        notifier.addObserver(this);
    }

    public void update(ArrayList<Integer> numbers) {
        int average = 0;

        for (int i = 0; i < numbers.size(); i++) {
            average += numbers.get(i);
        }

        show(numbers, average);
    }


    public void show(ArrayList<Integer> numbers, int average){
        Log.i("TAG", "Сумма всех чисел массива: " + average);
    }


}
