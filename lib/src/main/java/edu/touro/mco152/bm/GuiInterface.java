package edu.touro.mco152.bm;

public interface GuiInterface {
    public void startup(DiskWorker dw);
    public void runBm();
    public void dynamicPropertyAdjustment();
    public void stop();
    public boolean isStopped();
    public void setCompletionPercentage(int perc);
    public void dynamicGuiUpdate(DiskMark... o);
}
