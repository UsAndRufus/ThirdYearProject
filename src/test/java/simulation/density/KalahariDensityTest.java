package simulation.density;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;

import static org.junit.Assert.*;

public class KalahariDensityTest {

    private KalahariDensity kalahariDensity;
    private int immediacyFactor;
    private int maximumDistance = 2;

    private Grid grid;

    @Before
    public void setUp() throws Exception {
        kalahariDensity = new KalahariDensity(immediacyFactor, maximumDistance);
    }

    @Test
    public void calculateFor() throws Exception {

    }

}