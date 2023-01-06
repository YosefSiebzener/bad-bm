package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.persist.DiskRun;

public interface ObserverInterface {

    void addSelfToObservable(ObservableInterface oi);
    void update(DiskRun run);
}
