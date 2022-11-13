package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiskRunTest {
    DiskRun dr = new DiskRun();

    /**
     * Boundary conditions (existence).
     * <p>
     *     Tested with null value (which should produce a result).
     * <p>
     *     *Broken test*
     * @param d date (which will be set to null.)
     */
    @ParameterizedTest
    @NullSource
    public void getDurationTest(Date d) {
        dr.setEndTime(d);
        assertEquals("unknown", dr.getDuration());
    }


    @ParameterizedTest
    @ValueSource(ints = {1,23,60,12345,Integer.MAX_VALUE })
    public void getDurationTest(int amountOfSecsToAdd) {
        Calendar time = Calendar.getInstance();
        Date now = time.getTime();
        time.add(Calendar.SECOND, amountOfSecsToAdd);
        System.out.println(time.getTime());
        dr.setEndTime(time.getTime());
        assertEquals("" + (amountOfSecsToAdd % 60) + "s", dr.getDuration(), "Failed when value was "
                + amountOfSecsToAdd + ".");
        time.setTime(now);
        /*  I use setTime with the original time from when we start running the method rather than
         *  Calendar.getInstance() because as the test runs, the current seconds may change.
         */

    }
}
