package ch.epfl.alpano.dem;  

import static ch.epfl.alpano.Azimuth.isCanonical;
import static java.lang.Math.asin;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static ch.epfl.alpano.Distance.toRadians;
import static ch.epfl.alpano.Azimuth.toMath;
import static java.lang.Math.PI;
import static ch.epfl.alpano.Math2.PI2;
//import static java.lang.Math.ceil;
import static ch.epfl.alpano.Math2.lerp;
import ch.epfl.alpano.GeoPoint;
import static ch.epfl.alpano.Math2.floorMod;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.lang.Math.scalb;
import static java.lang.Math.pow;
import static java.lang.Math.floor;

import static java.util.Objects.requireNonNull;

public final class ElevationProfile {
    
    private final ContinuousElevationModel elevation;
    private final double length;
    private final GeoPoint [] tab;
    private final int SPACING_EXPONENT = 12;
    private final int SPACING = (int)pow(2,SPACING_EXPONENT);
    
    public ElevationProfile (ContinuousElevationModel elevationModel, GeoPoint origin, double azimuth, double length){
       checkArgument(isCanonical(azimuth) && length > 0);
       
       elevation = requireNonNull(elevationModel);
       this.length = length;
    
       //tab = new GeoPoint [(int) (ceil(length/4096)+1)];
       tab = new GeoPoint [(int) (scalb(length,-SPACING_EXPONENT)+1)];
       for(int i=0; i<tab.length; i++){
           double latitude = asin(sin(origin.latitude())*cos(toRadians(SPACING*i)) + 
               cos(origin.latitude())*sin(toRadians(SPACING*i))*cos(toMath(azimuth)));
       
           double longitude = (floorMod(origin.longitude() - asin(sin(toMath(azimuth))*sin(toRadians(SPACING*i))/cos(latitude)) + PI, PI2) - PI);
           
           tab[i] = new GeoPoint(longitude, latitude);        
       }
    }
    
    public double elevationAt(double x){
        isIn(x, length);
        
        return elevation.elevationAt(positionAt(x));
    }
    
    public GeoPoint positionAt(double x){
        isIn(x, length);
        
        double index = scalb(x,-SPACING_EXPONENT);
        int borneInf = (int)floor(index);
        double longitudeA=0,latitudeA=0;
        
       if(borneInf != tab.length-1){
            int borneSup = borneInf + 1;
            longitudeA = lerp(tab[borneInf].longitude(), tab[borneSup].longitude(), index - borneInf);
            latitudeA  = lerp(tab[borneInf].latitude(), tab[borneSup].latitude(), index - borneInf);
        
            return new GeoPoint(longitudeA,latitudeA);
       }else{
           return tab[borneInf];
       }
    }
    
    public double slopeAt(double x){
        isIn(x, length);
        
        return elevation.slopeAt(positionAt(x));
    }
    
    
    
    private void isIn (double position, double longueur){
        checkArgument(0<=position && position<=longueur);
    }

}
