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
     * Boundary conditions and Error.
     * <p>
     *      Tests boundaries where the same number is given twice,
     *      negative to positive, negative to negative, min is zero,
     *      max is zero, and where the values are far apart.
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
            randomChecker(-1234, 1234); // Far apart (and negative to positive)
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
