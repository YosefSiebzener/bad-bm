package edu.touro.mco152.bm;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    public void deleteDirectoryTest() {
        assertFalse(Util.deleteDirectory(new File("nonExistentPath")));
        new File("tempTestDirectory").mkdirs();
        assertTrue(Util.deleteDirectory(new File("tempTestDirectory")));
    }

    /**
     * Boundary conditions (Range) and Error.
     * <p>
     *      Tests boundaries where the same number is given twice,
     *      negative to positive, negative to negative, min is zero,
     *      max is zero, and where the values are as far apart as
     *      allowed according to the javadoc.
     * <p>
     *      Method should throw IllegalArgumentException if min is
     *      greater than max.
     */
    @Test
    public void randIntTest() {
        // Run test many times to make sure that we did not just get "lucky values" with random() method.
        for (int i = 0; i < 20; i++) {
            randomChecker(5, 10);   // Simple case
            randomChecker(10, 10);  // Same number
            randomChecker(-5, 8);   // Negative to positive
            randomChecker(-17, -4); // Negative to negative
            randomChecker(0, 3);    // Min is 0
            randomChecker(-3, 0);   // Max is 0
            randomChecker(0, Integer.MAX_VALUE - 1); // As far apart as allowed
        }
        assertThrows(IllegalArgumentException.class, () -> Util.randInt(5,4));
    }

    private void randomChecker(int min, int max) {
        int returnedValue;
        try {
            returnedValue = Util.randInt(min, max);
        }
        catch (IllegalArgumentException iae) {
            fail("min was " + min + ". max was " + max + ".");
            return;
        }
        assertTrue(returnedValue >= min);
        assertTrue(returnedValue <= max);
    }
}
