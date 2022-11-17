package edu.touro.mco152.bm;

public interface UiInterface {
    Boolean startup(DiskWorker dw);
    void dynamicPropertyAdjustment();
    void stop();
    boolean isStopped();
    void setCompletionPercentage(int perc);
    void dynamicGuiUpdate(DiskMark... o);
}
