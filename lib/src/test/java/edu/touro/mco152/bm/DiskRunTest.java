package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiskRunTest {
    DiskRun dr = new DiskRun();

    /**
     * Boundary test.
     * <p>
     *     Tested with null value.
     * @param d date (which will be set to null.)
     */
    @ParameterizedTest
    @NullSource
    public void getDurationTest(Date d) {
        dr.setEndTime(d);
        assertEquals("unknown", dr.getDuration());
    }

    @Test
    public void getDurationTest() {
        Calendar time = Calendar.getInstance();
        Random random = new Random();
        Date now = time.getTime();
        for (int i = 0; i < 20; i++) {
            int amountOfSecsToAdd = Math.abs(random.nextInt());
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
}
