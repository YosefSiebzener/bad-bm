package edu.touro.mco152.bm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiskMarkTest {
    DiskMark dm = new DiskMark(DiskMark.MarkType.READ);

    /**
     * Boundary conditions and cross-checking.
     * <p>
     *      Tested less than, equal to, and greater than
     *      the amount of decimal spaces formatted for
     *      with positive and negative values as well as
     *      with whole numbers or just fractional parts.
     *
     * <p>
     *      Cross-checks against the build-in String
     *      formatter.
     * @param num number to be formatted
     */
    @ParameterizedTest
    @ValueSource(doubles = {-.34,-5.1,-5.123,-5.12345,.34,134.1,134.123,134.12345})
    public void getBwMbSecAsStringDecimalNumberTests(double num) {
        dm.setBwMbSec(num);
        assertEquals("" + Double.parseDouble(String.format("%,.3f", num)), dm.getBwMbSecAsString());
    }

    /**
     * Boundary conditions.
     * <p>
     *     Tested 0, small positive number, small negative number
     *     large positive number, large negative number, and maximum
     *     and minimum values.
     * @param num number to be formatted
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1,-1,23455,-2345,Integer.MIN_VALUE,Integer.MAX_VALUE})
    public void getBwMbSecAsStringWholeNumberTests(int num) {
        dm.setBwMbSec(num);
        assertEquals("" + num, dm.getBwMbSecAsString());
    }
}
