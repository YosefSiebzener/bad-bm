package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.List;

import static edu.touro.mco152.bm.App.dataDir;

public class UiInterfaceSwingImplementation extends SwingWorker<Boolean, DiskMark> implements UiInterface {
    DiskWorker dw;
    @Override
    public Boolean startup(DiskWorker dw) {
        this.dw = dw;
        execute();
        return null;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        return dw.startBm(this);
    }

    @Override
    public void dynamicPropertyAdjustment() {
        addPropertyChangeListener((final PropertyChangeEvent event) -> {
            switch (event.getPropertyName()) {
                case "progress":
                    int value = (Integer) event.getNewValue();
                    Gui.progressBar.setValue(value);
                    long kbProcessed = (value) * App.targetTxSizeKb() / 100;
                    Gui.progressBar.setString(kbProcessed + " / " + App.targetTxSizeKb());
                    break;
                case "state":
                    switch ((StateValue) event.getNewValue()) {
                        case STARTED:
                            Gui.progressBar.setString("0 / " + App.targetTxSizeKb());
                            break;
                        case DONE:
                            break;
                    } // end inner switch
                    break;
            }
        });
    }

    @Override
    public void stop() {
        cancel(true);
    }

    @Override
    public boolean isStopped() {
        return isCancelled();
    }

    @Override
    public void setCompletionPercentage(int perc) {
        setProgress(perc);
    }

    @Override
    public void dynamicGuiUpdate(DiskMark... dm) {
        publish(dm);
    }

    /**
     * Process a list of 'chunks' that have been processed, ie that our thread has previously
     * published to Swing. For my info, watch Professor Cohen's video -
     * Module_6_RefactorBadBM Swing_DiskWorker_Tutorial.mp4
     * @param markList a list of DiskMark objects reflecting some completed benchmarks
     */
    @Override
    protected void process(List<DiskMark> markList) {
        markList.stream().forEach((dm) -> {
            if (dm.type == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }

    @Override
    protected void done() {
        if (App.autoRemoveData) {
            Util.deleteDirectory(dataDir);
        }
        App.state = App.State.IDLE_STATE;
        Gui.mainFrame.adjustSensitivity();
    }
}
