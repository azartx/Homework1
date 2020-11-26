package com.app.homework2.data;

import android.util.Log;

import com.app.homework2.interfaces.Notifier;
import com.app.homework2.interfaces.Observer;

import java.util.ArrayList;

// Третий пункт задания..уточняю :D
public class Evil implements Observer {

    private int firstPart;

    public Evil(Notifier notifier) {
        notifier.addObserver(this);
    }

    public int getFirstPart() {
        return firstPart;
    }

    public void update(ArrayList<Integer> numbers) {
        int secondPart = 0;

        // Обработка первой части массива
        for (int i = 0; i < numbers.size() / 2; i++) {
            firstPart += numbers.get(i);
        }

        // Обработка второй части массива
        for (int i = numbers.size() / 2 + 1; i < numbers.size(); i++) {
            secondPart -= numbers.get(i);
        }

        try{            // обработал, так как может придти деление на ноль
            firstPart /= (secondPart); // деление двух частей друг на друга
        }catch(ArithmeticException e){
            Log.i("TAG", "Arithmetical Exception Evil.java. Деление на ноль. " +
                    "Установил шаблонные данные.");
            firstPart = 999;
        }

        show(firstPart);
    }

    public void show(int finishData) {
        Log.i("TAG", "Махинации с массивом ( деление двух частей): " + finishData);
    }
}
