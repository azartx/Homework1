package com.app.homework2.data;

import android.util.Log;

import com.app.homework2.interfaces.Notifier;
import com.app.homework2.interfaces.Observer;

import java.util.ArrayList;

// Третий пункт задания..уточняю :D
public class Evil implements Observer {
    private Notifier notifier;

    public Evil(Notifier notifier) {
        this.notifier = notifier;
        notifier.addObserver(this);
    }

    public void update(ArrayList<Integer> numbers) {
        int firstPart = 0;
        int secondPart = 0;
        int finishPart = 0;

        // Обработка первой части массива
        for (int i = 0; i < numbers.size() / 2; i++) {
            firstPart += numbers.get(i);
        }

        // Обработка второй части массива
        for (int i = numbers.size() / 2 + 1; i < numbers.size(); i++) {
            secondPart -= numbers.get(i);
        }
        // Пока что деление работает не правильно.
        finishPart = firstPart / (secondPart);

        show(finishPart, firstPart, secondPart);
    }


    public void show(double average, int first, int second) {
        Log.i("TAG", "Махинации с массивом: " + average + " " + first + " " + second);
    }


}
