package com.app.homework2.data;

import com.app.homework2.interfaces.Notifier;
import com.app.homework2.interfaces.Observer;

import java.util.ArrayList;
import java.util.List;

                                                        // Обсервер
public class CentralComp implements Notifier {
    private List observers;
    private ArrayList<Integer> numbers;

    public CentralComp() {
        observers = new ArrayList();
    }

    // добавить слушателя
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    // удалить слушателя
    public void removeObserver(Observer obs) {
        int i = observers.indexOf(obs);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    // уведомить слушателей
    public void notifyObserver() {
        for (int i = 0; i < observers.size(); i++) {
            Observer obs = (Observer) observers.get(i);
            obs.update(numbers);
        }
    }

    public void changeData(ArrayList<Integer> numbers) {
        this.numbers = numbers;
        notifyObserver();
    }

}
