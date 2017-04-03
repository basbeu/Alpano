package ch.epfl.testProf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GeoPointTest.class, Interval1DTest.class,
        Interval2DTest.class })
public class AllTestsProf {

}
