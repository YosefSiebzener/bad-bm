package edu.touro.mco152.bm.messagers;

import edu.touro.mco152.bm.observer.ObservableInterface;
import edu.touro.mco152.bm.observer.ObserverInterface;
import edu.touro.mco152.bm.persist.DiskRun;

public class SlackMessagerObserver implements ObserverInterface {

    ObservableInterface oi;
    @Override
    public void update(DiskRun run) {
        if (run.getRunMax() > run.getRunAvg() * 1.03 && run.getIoMode() == DiskRun.IOMode.READ) {
            SlackManager slackManager = new SlackManager("BadBm");
            slackManager.postMsg2OurChannel("Max run time was %" +
                    String.format("%,.2f", calculatePercentage(run.getRunMax(), run.getRunAvg())) +
                    " slower than the average time.");
        }
    }

    private double calculatePercentage(double maxTime, double avgTime) {
        return ((maxTime / avgTime) - 1) * 100;
    }

    @Override
    public void addSelfToObservable(ObservableInterface oi) {
        oi.registerObserver(this);
    }
}
