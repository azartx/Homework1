package com.app.homework2.data;

import android.util.Log;

import com.app.homework2.interfaces.Notifier;
import com.app.homework2.interfaces.Observer;

import java.util.ArrayList;

public class Summ implements Observer {

    private int summ;

    public Summ(Notifier notifier) {
        notifier.addObserver(this);
    }

    public int getSumm() {
        return summ;
    }

    public void update(ArrayList<Integer> numbers) {

        for (int i = 0; i < numbers.size(); i++) {
            summ += numbers.get(i);
        }

        show(summ);
    }

    public void show(int summ) {
        Log.i("TAG", "Сумма всех чисел массива: " + summ);
    }


}
