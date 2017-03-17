package ch.epfl.alpano.dem;


import java.io.File;

import org.junit.Test;

public class HgtDiscreteElevationModelTest {

    @Test
    public void testCornerSW() throws Exception {
        HgtDiscreteElevationModel h = new HgtDiscreteElevationModel(new File("N46E007.hgt"));
        System.out.println(7.+" "+46.);
        h.elevationSample(7*3600, 3600*46); 
        h.close();
    }
    
    @Test
    public void testCornerNW() throws Exception {
        HgtDiscreteElevationModel h = new HgtDiscreteElevationModel(new File("N46E007.hgt"));
        //System.out.println(7.+" "+47.);
        h.elevationSample(7*3600, 3600*47);
        h.close();
    }
    
    @Test
    public void testCornerSE() throws Exception {
        HgtDiscreteElevationModel h = new HgtDiscreteElevationModel(new File("N46E007.hgt"));
        System.out.println(8.+" "+46.);
        h.elevationSample(8*3600, 3600*46); 
        h.close();
    }

    @Test
    public void testCornerNE() throws Exception {
        HgtDiscreteElevationModel h = new HgtDiscreteElevationModel(new File("N46E007.hgt"));
        //System.out.println(8.+" "+47.);
        h.elevationSample(8*3600, 3600*47); 
        h.close();
    }
    
}
