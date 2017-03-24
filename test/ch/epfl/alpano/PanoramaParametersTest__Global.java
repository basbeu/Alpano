package ch.epfl.alpano;

import static java.lang.Math.PI;
import static org.junit.Assert.*;

import org.junit.Test;

public class PanoramaParametersTest__Global {

	PanoramaParameters pTest = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
			1280, Math.toRadians(162d), Math.PI, 4, 2500, 800);

	// Constructor
	@Test(expected = java.lang.NullPointerException.class)
	public void testPanoramaParametersConstructorNullGeoPoint() {
		PanoramaParameters p = new PanoramaParameters(null, 1280, Math.toRadians(162d), Math.toRadians(27d), 300000,
				2500, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNoCanonicalAzimuthCornerCase() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math2.PI2, Math.toRadians(27d), 300000, 2500, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNoCanonicalAzimuth() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math2.PI2 + Math.PI, Math.toRadians(27d), 300000, 2500, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorInvalidHorizontalFieldOfViewCornerCase() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), 0, 300000, 2500, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorInvalidHorizontalFieldOfView() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2 + 3, 300000, 2500, 800);
	}

	@Test
	public void testPanoramaParametersConstructorValidHorizontalFieldOfViewCornerCase() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2, 300000, 2500, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNullWidth() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2 + 3, 300000, 2500, 0);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNegativeWidth() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2 + 3, 300000, 2500, -2);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNullHeight() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2 + 3, 300000, 0, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNegativeHeight() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2 + 3, 300000, -5, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNullMaximalDistance() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2 + 3, 0, 2500, 800);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testPanoramaParametersConstructorNegativeMaximalDistance() {
		PanoramaParameters p = new PanoramaParameters(new GeoPoint(Math.toRadians(6.8087), Math.toRadians(6.8087)),
				1280, Math.toRadians(162d), Math2.PI2 + 3, -6, 2500, 800);
	}

	// Methods

	// azimuthForX
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testAzimuthForXNegativeIndex() {
		pTest.azimuthForX(-1);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testAzimuthForXInvalidIndex() {
		pTest.azimuthForX(pTest.width() - 1 + 0.000001);
	}

	@Test
	public void testAzimuthForXValidIndexCornerCase1() {
		pTest.azimuthForX(0);
	}

	@Test
	public void testAzimuthForXValidIndexCornerCase2() {
		pTest.azimuthForX(pTest.width() - 1);
	}

	// azimuthForY
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testAzimuthForYNegativeIndex() {
		pTest.altitudeForY(-1);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testAzimuthForYInvalidIndex() {
		pTest.altitudeForY(pTest.height() - 1 + 0.000001);
	}

	@Test
	public void testAzimuthForYValidIndexCornerCase1() {
		pTest.altitudeForY(0);
	}

	@Test
	public void testAzimuthForYValidIndexCornerCase2() {
		pTest.altitudeForY(pTest.height() - 1);
	}

	// xForAzimuth

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testXForAzimuthInvalidValue() {
		pTest.xForAzimuth(pTest.centerAzimuth() + (pTest.horizontalFieldOfView() / 2) + 1);
	}

	@Test
	public void testXForAzimuthValidCornerCaseLowerBound() {
		pTest.xForAzimuth(pTest.centerAzimuth() - (pTest.horizontalFieldOfView() / 2));
	}

	@Test
	public void testXForAzimuthValidCornerCaseUpperBound() {
		pTest.xForAzimuth(pTest.centerAzimuth() + (pTest.horizontalFieldOfView() / 2));
	}

	// YForAltitude

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testYForAltitudeInvalidValue() {
		pTest.yForAltitude(pTest.centerAzimuth() + (pTest.horizontalFieldOfView() / 2) + 1);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testYForAltitudeNegativeValue() {
		pTest.yForAltitude(-3);
	}
	


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionOnAzimuth() {
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, 2.1*PI, PI/6, 30000, 2500, 800);
    }
    
    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionOnObserver() {
        PanoramaParameters p = new PanoramaParameters(null, 3600, PI, PI/6, 30000, 2500, 800);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionOnHorizontalView() {
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, 0, 30000, 2500, 800);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionOnMaxDistance() {
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/6, 0, 2500, 800);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionOnWidth() {
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/6, 30000, 0, 800);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionOnHeight() {
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/6, 30000, 2500, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void azimuthForXthrowsException(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        p.azimuthForX(2500);
    }
    
   
    
    @Test(expected = IllegalArgumentException.class)
    public void xForAzimuthThrowsException(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800); 
        p.xForAzimuth(4*PI/3);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void yForAltitudeThrowsException(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        p.yForAltitude(p.verticalFieldOfView());
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void altitudeForYthrowsException(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        p.altitudeForY(800);
    }
    
    @Test
    public void constructorWorks(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, 2*PI, 30000, 2500, 800);
    }
    
   
    
    @Test
    public void azimuthForXWorks(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        PanoramaParameters p2 = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, 11*PI/6 , PI, 30000, 2500, 800);
        assertEquals(5*PI/6, p.azimuthForX(0), 1e-7);
        assertEquals(7*PI/6, p.azimuthForX(2499), 1e-7);
        assertEquals(7*PI/6, p.azimuthForX(2499), 1e-7);
        assertEquals(3.4560871, p.azimuthForX(2000), 1e-7);
        assertEquals(3.0370405, p.azimuthForX(1000), 1e-7);
        assertEquals(PI/3, p2.azimuthForX(2499), 1e-7);
        
    }
    
    @Test
    public void xForAzimuthWorks(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        assertEquals(1874.25, p.xForAzimuth(13*PI/12), 1e-7);
        assertEquals(624.75, p.xForAzimuth(11*PI/12), 1e-7);
        assertEquals(2499, p.xForAzimuth(7*PI/6), 1e-7);
    }
    //TODO: Expected : 1874.25 but was 1873.75000000...005 : OK ?
    
    @Test
    public void altitudeForYWorks(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        assertEquals(0, p.altitudeForY(399.5), 1e-7);
        assertEquals(p.verticalFieldOfView()/2, p.altitudeForY(0), 1e-7);
        assertEquals(0.1485520336, p.altitudeForY(45), 1e-7);
    } 
   
    
    @Test
    public void yForAltitudeWorks(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        assertEquals(0, p.yForAltitude(p.verticalFieldOfView()/2), 1e-7);
        assertEquals(799, p.yForAltitude(-(p.verticalFieldOfView()/2)), 1e-7);
        
    }
        
    
    @Test
    public void linearSampleIndexWorks(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        assertEquals(1999999, p.linearSampleIndex(2499, 799),0);
        assertEquals(0, p.linearSampleIndex(0, 0), 0);
        assertEquals(955439 ,p.linearSampleIndex(439, 382), 0);
        assertEquals(1499601, p.linearSampleIndex(2101, 599),0);
    }
    
    @Test
    public void isValidWorks(){
        PanoramaParameters p = new PanoramaParameters(new GeoPoint(PI, PI/2), 3600, PI, PI/3, 30000, 2500, 800);
        assertTrue(p.isValidSampleIndex(2400, 50));
        assertTrue(p.isValidSampleIndex(2499, 799));
        assertTrue(p.isValidSampleIndex(0, 0));
        assertFalse(p.isValidSampleIndex(2500, 40));
        assertFalse(p.isValidSampleIndex(400, 800));
    }
    

	
	PanoramaParameters pp=new PanoramaParameters(new GeoPoint(0,0),1000,PI/12, PI/3, 100000, 8, 8);
	
	@Test
	public void xForAzimuthWorksOnLimitCases() {
		assertEquals(0, pp.xForAzimuth((23.0000000000000001*PI)/12.0), 1e-10); //works
		assertEquals(3.5, pp.xForAzimuth(PI/12.0), 1e-10); //works
		assertEquals(7, pp.xForAzimuth(2.999999999999999*PI/12.0), 1e-10); //works
	}
	
	@Test
	public void azimuthForXWorksOnLimitCases() {
		assertEquals(23*PI/12, pp.azimuthForX(0), 1e-5);//works
		assertEquals(3*PI/12, pp.azimuthForX(7), 1e-5);//works
	}
	
	@Test
	public void xForAzimuthWorksOnGenericCases(){
		assertEquals(1, pp.xForAzimuth(23*PI/12.0 + PI/21.0), 1e-10);
	}
	
	@Test 
	public void azimuthForXWorksOnGenericCases(){
		assertEquals(23*PI/12 + PI/21, pp.azimuthForX(1), 1e-5);
	}
	
	@Test
	public void yForAltitudeWorksOnLimitCases() {
		assertEquals(3.5, pp.yForAltitude(0), 1e-5);
		assertEquals(0, pp.yForAltitude(pp.verticalFieldOfView()/2), 1e-5);
		assertEquals(7, pp.yForAltitude(-pp.verticalFieldOfView()/2), 1e-5);
	}
	
	@Test
	public void yForAltitudeWorksOnGenericCases() {
		assertEquals(2, pp.yForAltitude(3*PI/42), 1e-5);
		assertEquals(5, pp.yForAltitude(-3*PI/42), 1e-5);
	}
	
	@Test
	public void altitudeForYWorksOnLimitCases() {
		assertEquals(PI/6, pp.altitudeForY(0), 1e-5);
		assertEquals(0, pp.altitudeForY(3.5), 1e-5);
		assertEquals(-PI/6, pp.altitudeForY(7), 1e-5);
	} // TODO: 2PI de décalage si on canonicalise
	
	@Test
	public void altitudeForYWorksOnGenericCases() {
		assertEquals(PI/21, pp.altitudeForY(2.5), 1e-5);
		assertEquals(-PI/21, pp.altitudeForY(4.5), 1e-5);
		assertEquals(-5*PI/42, pp.altitudeForY(6), 1e-5);
	} // TODO: 2PI de décalage si on canonicalise
	
	@Test(expected=IllegalArgumentException.class)
	public void xForAzimuthThrowsWellOne(){
		pp.xForAzimuth(22.99999*PI/12);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void xForAzimuthThrowsWellTwo(){
		pp.xForAzimuth(3*PI/12+1e-10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void xForAzimuthThrowsWellThree(){
		pp.xForAzimuth(37*PI/12);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void azimuthForXThrowsWellOne(){
		pp.azimuthForX(7.01);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void azimuthForXThrowsWellTwo(){
		pp.azimuthForX(-2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void yForAltitudeThrowsWellOne(){
		pp.yForAltitude(PI/5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void yForAltitudeThrowsWellTwo(){
		pp.yForAltitude(-PI/5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void yForAltitudeThrowsWellThree(){
		pp.yForAltitude(PI);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void altitudeForYThrowsWellOne(){
		pp.altitudeForY(-0.0001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void altitudeForYThrowsWellTwo(){
		pp.altitudeForY(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void altitudeForYThrowsWellThree(){
		pp.altitudeForY(7.05);
	}
	


    private static PanoramaParameters p = new PanoramaParameters(
            new GeoPoint(0, 0), 1000, 0, Math.PI / 2.0, 3000, 101, 51);

    @Test
    public void verticalFieldOfViewWorksOnRandomPanorama() {
        double expectedValue = Math.PI / 4.0;
        double actualValue = p.verticalFieldOfView();
        assertEquals(expectedValue, actualValue, 1e-10);
    }//TODO: quand the expected value c'est 0.7853981633974483 et qu'on obtient 0.7853981633974484: OK?

    @Test
    public void azimuthForXWorksForMidPixel() {
        double expectedAzimuth = 0;
        double actualAzimuth = p.azimuthForX(50.0);
        assertEquals(expectedAzimuth, actualAzimuth, 1e-10);
    }

    @Test
    public void azimuthForXWorksForLowPixel() {
        double expectedAzimuth = Azimuth.canonicalize(-Math.PI / 4);
        double actualAzimuth = p.azimuthForX(0);
        assertEquals(expectedAzimuth, actualAzimuth, 1e-10);
    }

    @Test
    public void azimuthForXWorksForUppPixel() {
        double expectedAzimuth = Math.PI / 4;
        double actualAzimuth = p.azimuthForX(100);
        assertEquals(expectedAzimuth, actualAzimuth, 1e-10);
    }

    @Test
    public void xForAzimuthWorksForMidAzimuth() {
        double expectedAzimuth = 50.0;
        double actualAzimuth = p.xForAzimuth(0);
        assertEquals(expectedAzimuth, actualAzimuth, 1e-10);
    }

    @Test
    public void xForAzimuthWorksForUppPixel() {
        double expectedAzimuth = 100.0;
        double actualAzimuth = p.xForAzimuth(Math.PI / 4);
        assertEquals(expectedAzimuth, actualAzimuth, 1e-10);
    }
    
    @Test
    public void altitudeForYWorksForMidPixel() {
        double expectedAltitude = 0;
        double actualAltitude = p.altitudeForY(25.0);
        assertEquals(expectedAltitude, actualAltitude, 1e-10);
    }

    @Test
    public void altitudeForYWorksForLowPixel() {
        double expectedAltitude = Azimuth.canonicalize(Math.PI / 8);
        double actualAltitude = p.altitudeForY(0);
        assertEquals(expectedAltitude, actualAltitude, 1e-10);
    }

    @Test
    public void altitudeForYWorksForUppPixel() {
        double expectedAzimuth = -Math.PI / 8;
        double actualAzimuth = p.altitudeForY(50);
        assertEquals(expectedAzimuth, actualAzimuth, 1e-10);
    }
    //TODO: Pour ce test: 2*PI de trop par rapport à expected value si on canonicalise
    
    @Test
    public void yForAltitudeWorksForMidAzimuth() {
        double expectedAltitude = 25.0;
        double actualAltitude = p.yForAltitude(0);
        assertEquals(expectedAltitude, actualAltitude, 1e-10);
    }

    @Test
    public void yForAltitudeWorksForLowPixel() {
        double expectedAltitude = 0.0;
        double actualAltitude = p.yForAltitude(Math.PI / 8);
        assertEquals(expectedAltitude, actualAltitude, 1e-10);
    }

    @Test
    public void yForAltitudeWorksForUppPixel() {
        double expectedAltitude = 50.0;
        double actualAltitude = p.yForAltitude(-Math.PI / 8);
        assertEquals(expectedAltitude, actualAltitude, 1e-10);
    }

}
