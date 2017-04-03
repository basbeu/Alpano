package ch.epfl.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.epfl.alpano.AllTestsAlpano;
import ch.epfl.alpano.dem.AllTestsDEM;
import ch.epfl.alpano.summit.AllTestsSummit;
import ch.epfl.testProf.AllTestsProf;

@RunWith(Suite.class)
@SuiteClasses({AllTestsProf.class, AllTestsSummit.class,AllTestsDEM.class,AllTestsAlpano.class})
public class AllTests {

}
