package edu.touro.mco152.bm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    /**
     * Performance test
     */
    @Test
    @Timeout(4)
    public void guiStartupTest() {
        App.init();
    }
}
