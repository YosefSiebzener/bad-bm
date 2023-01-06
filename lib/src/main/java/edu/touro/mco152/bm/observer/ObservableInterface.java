package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.persist.DiskRun;

import java.util.HashSet;

public interface ObservableInterface {

    void registerObserver(ObserverInterface oi);

    void unregisterObserver(ObserverInterface oi);

    void notifyObservers(DiskRun run);
}
