package ch.epfl.alpano.dem;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CompositeDiscreteElevationModelTest.class,
        ContinuousElevationModelTest.class, ElevationProfileTest.class,
        ElevationProfileTestProf.class, ExceptionPart4Test.class,
        ExceptionTest.class, HgtDiscreteElevationModelTest.class})
public class AllTestsDEM {

}
