package edu.touro.mco152.bm;

public interface GuiInterface {
    public Boolean startup(DiskWorker dw);
    public void dynamicPropertyAdjustment();
    public void stop();
    public boolean isStopped();
    public void setCompletionPercentage(int perc);
    public void dynamicGuiUpdate(DiskMark... o);
}
