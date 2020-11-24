package com.app.homework2.data;

import android.util.Log;

import com.app.homework2.interfaces.Notifier;
import com.app.homework2.interfaces.Observer;

import java.util.ArrayList;

public class MiddleArithmetical implements Observer {
    private Notifier notifier;

    public MiddleArithmetical(Notifier notifier){
        this.notifier = notifier;
        notifier.addObserver(this);
    }

    public void update(ArrayList<Integer> numbers) {
        int average = 0;

        for (int i = 0; i < numbers.size(); i++) {
            average += numbers.get(i);
        }

        if (average != 0){
            average /= numbers.size();
        }

        show(average);
    }


    public void show(int average){
        Log.i("TAG", "Среднее арифметическое: " + average);
    }


}
