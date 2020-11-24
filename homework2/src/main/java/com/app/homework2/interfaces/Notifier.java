package com.app.homework2.interfaces;

public interface Notifier{
    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObserver();
}
