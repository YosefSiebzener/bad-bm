package edu.touro.mco152.bm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class AppTest {

    /**
     * Performance test.
     * <p>
     *     Checks Gui startup time.
     */
    @Test
    @Timeout(4)
    public void guiStartupTest() {
        App.init();
    }
}
