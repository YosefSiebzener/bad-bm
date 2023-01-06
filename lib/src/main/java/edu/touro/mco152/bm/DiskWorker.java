package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.CommandInterface;
import edu.touro.mco152.bm.commands.DoReads;
import edu.touro.mco152.bm.commands.DoWrites;
import edu.touro.mco152.bm.commands.Executor;
import edu.touro.mco152.bm.messagers.SlackMessagerObserver;
import edu.touro.mco152.bm.observer.ObservableInterface;
import edu.touro.mco152.bm.persist.PersistenceObserver;
import edu.touro.mco152.bm.ui.Gui;

import javax.swing.*;

import static edu.touro.mco152.bm.App.*;

/**
 * Run the disk benchmarking as a Swing-compliant thread (only one of these threads can run at
 * once.) Cooperates with Swing to provide and make use of interim and final progress and
 * information, which is also recorded as needed to the persistence store, and log.
 * <p>
 * Depends on static values that describe the benchmark to be done having been set in App and Gui classes.
 * The DiskRun class is used to keep track of and persist info about each benchmark at a higher level (a run),
 * while the DiskMark class described each iteration's result, which is displayed by the UI as the benchmark run
 * progresses.
 * <p>
 * This class only knows how to do 'read' or 'write' disk benchmarks. It is instantiated by the
 * startup() method.
 */

public class DiskWorker {
    PersistenceObserver pi = new PersistenceObserver();
    Gui gui = new Gui();
    SlackMessagerObserver slackMessagerObserver = new SlackMessagerObserver();
    protected UiInterface gi;

    public DiskWorker(UiInterface gi) {
        this.gi = gi;
    }

    protected Boolean startBm(UiInterface gi) throws Exception {

        /*
          We 'got here' because: a) End-user clicked 'Start' on the benchmark UI,
          which triggered the start-benchmark event associated with the App::startBenchmark()
          method.  b) startBenchmark() then instantiated a DiskWorker, and called
          its (super class's) execute() method, causing Swing to eventually
          call this startBm() method.
         */
        System.out.println("*** starting new worker thread");
        msg("Running readTest " + App.readTest + "   writeTest " + App.writeTest);
        msg("num files: " + App.numOfMarks + ", num blks: " + App.numOfBlocks
                + ", blk size (kb): " + App.blockSizeKb + ", blockSequence: " + App.blockSequence);

        Gui.updateLegend();  // init chart legend info

        if (App.autoReset) {
            App.resetTestData();
            Gui.resetTestData();
        }

        /*
          The GUI allows either a write, read, or both types of BMs to be started. They are done serially.
         */
        if (App.writeTest) {
            ObservableInterface doWrites = new DoWrites(gi, numOfMarks, numOfBlocks, blockSizeKb, blockSequence);
            registerObservers(doWrites);
            Executor.execute((CommandInterface) doWrites);
        }

        /*
          Most benchmarking systems will try to do some cleanup in between 2 benchmark operations to
          make it more 'fair'. For example a networking benchmark might close and re-open sockets,
          a memory benchmark might clear or invalidate the Op Systems TLB or other caches, etc.
         */

        // try renaming all files to clear catch
        if (App.readTest && App.writeTest && !gi.isStopped()) {
            JOptionPane.showMessageDialog(Gui.mainFrame,
                    "For valid READ measurements please clear the disk cache by\n" +
                            "using the included RAMMap.exe or flushmem.exe utilities.\n" +
                            "Removable drives can be disconnected and reconnected.\n" +
                            "For system drives use the WRITE and READ operations \n" +
                            "independantly by doing a cold reboot after the WRITE",
                    "Clear Disk Cache Now", JOptionPane.PLAIN_MESSAGE);
        }

        // Same as above, just for Read operations instead of Writes.
        if (App.readTest) {
            ObservableInterface doReads = new DoReads(gi, numOfMarks, numOfBlocks, blockSizeKb, blockSequence);
            registerObservers(doReads);
            Executor.execute((CommandInterface) doReads);
        }
        App.nextMarkNumber += App.numOfMarks;
        return true;
    }

    private void registerObservers(ObservableInterface oi) {

        pi.addSelfToObservable(oi);
        gui.addSelfToObservable(oi);
        slackMessagerObserver.addSelfToObservable(oi);
    }
}
