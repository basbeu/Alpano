package ch.epfl.alpano;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AzimuthTest.class, DistanceTest.class, GeoPointTest.class,
        Interval1DTest.class, Interval2DTest.class, Math2Test.class,
        PanoramaComputerTestProf.class, PanoramaParametersTest__Global.class,
        PanoramaParametersTestMJ.class, PanoramaParametersTestProf.class,
        PanoramaParametersTestsAG.class, PanoramaTestProf.class,
        Part6ExceptionTest.class, PreconditionsTest.class })
public class AllTestsAlpano {

}
