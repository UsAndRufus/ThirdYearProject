package simulation.grid;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    private Position position00, position00Other, position23, position32, positionNegative23;

    @Before
    public void setUp() throws Exception {
        position00 = new Position(0,0);
        position00Other = new Position(0,0);
        position23 = new Position(2,3);
        position32 = new Position(3,2);
        positionNegative23 = new Position(-2,3);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals("Hash code should be (2 * 29) + 3 = 61", 61, position23.hashCode());

        assertNotEquals("Hash code for (3,2) should be different to (2,3)",
                position23.hashCode(), position32.hashCode());

        assertNotEquals("Hash code for (2,3) should be different to (-2,3)",
                position23.hashCode(), positionNegative23.hashCode());

        assertEquals("Hash code for (0,0) should be same as other object which is (0,0)",
                position00.hashCode(), position00Other.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals("Object should be equal to itself", position00, position00);

        assertEquals("(0,0) should be equal to other (0,0)", position00, position00Other);

        assertNotEquals("(2,3) should not be equal to (3,2)", position23, position32);

        assertNotEquals("(2,3) should not be equal to (-2,3)", position23, positionNegative23);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("String should be (2,3)", "(2,3)", position23.toString());

        assertNotEquals("String for (3,2) should be different to (2,3)",
                position32.toString(), position23.toString());

        assertNotEquals("String for (2,3) should be different to (-2,3)",
                position23.toString(), positionNegative23.toString());

        assertEquals("String for (0,0) should be same as other object which is (0,0)",
                position00.toString(), position00Other.toString());

    }

}