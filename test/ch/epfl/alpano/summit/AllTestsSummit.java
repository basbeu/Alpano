package ch.epfl.alpano.summit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GazetteerParserTest__Global.class,
        GazetteerParserTestProf.class, SummitTest__Global.class,
        SummitTestProf.class, TestingGazetteerParser.class })
public class AllTestsSummit {

}
