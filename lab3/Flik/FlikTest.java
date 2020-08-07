import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlikTest {
    @Test
    public void TestisSameNumber() {
        int a = 1;
        int b = 1;
        int c = 2;
        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(a, c));
    }
}
