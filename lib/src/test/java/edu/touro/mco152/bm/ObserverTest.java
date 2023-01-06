package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.CommandInterface;
import edu.touro.mco152.bm.commands.DoReads;
import edu.touro.mco152.bm.commands.DoWrites;
import edu.touro.mco152.bm.commands.Executor;
import edu.touro.mco152.bm.observer.ObservableInterface;
import edu.touro.mco152.bm.observer.ObserverInterface;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Properties;

public class ObserverTest implements ObserverInterface {

    private final int NUM_OF_MARKS = 25;
    private final int NUM_OF_BLOCKS = 128;
    private final int BLOCK_SIZE_KB = 2048;
    private final DiskRun.BlockSequence BLOCK_SEQUENCE = DiskRun.BlockSequence.SEQUENTIAL;

    private static boolean flag = false;
    @Override
    public void addSelfToObservable(ObservableInterface oi) {
        oi.registerObserver(this);
    }

    @Override
    public void update(DiskRun run) {
        flag = true;
    }

    @BeforeAll
    public static void assertFlagIsFalse() {
        Assert.isFalse(getFlag(), "Flag is "+ getFlag());
    }

    @Test
    public void setFlagUsingObserverForWrites() {
        setupDefaultAsPerProperties();
        ObservableInterface doWrites = new DoWrites(new DiskWorkerTest(), NUM_OF_MARKS, NUM_OF_BLOCKS, BLOCK_SIZE_KB, BLOCK_SEQUENCE);
        addSelfToObservable(doWrites);
        Executor.execute((CommandInterface) doWrites);
    }

    @Test
    public void setFlagUsingObserverForReads() {
        setupDefaultAsPerProperties();
        ObservableInterface doWrites = new DoReads(new DiskWorkerTest(), NUM_OF_MARKS, NUM_OF_BLOCKS, BLOCK_SIZE_KB, BLOCK_SEQUENCE);
        addSelfToObservable(doWrites);
        Executor.execute((CommandInterface) doWrites);
    }

    @AfterAll
    public static void checkFlag() {
        Assert.isTrue(getFlag(), "Flag is " + getFlag());
    }

    private static boolean getFlag() {
        return flag;
    }
    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    private void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference
        App.dataDir = new File(App.locationDir.getAbsolutePath()+File.separator+App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        }
        else
        {
            App.dataDir.mkdirs(); // create data dir if not already present
        }

    }
}
