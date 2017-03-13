package ch.epfl.alpano.dem;  

import static ch.epfl.alpano.Azimuth.isCanonical;
import static java.lang.Math.asin;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static ch.epfl.alpano.Distance.toRadians;
import static ch.epfl.alpano.Azimuth.toMath;
import static java.lang.Math.PI;
import static ch.epfl.alpano.Math2.PI2;
import static java.lang.Math.ceil;
import static ch.epfl.alpano.Math2.lerp;
import ch.epfl.alpano.GeoPoint;
import static ch.epfl.alpano.Math2.floorMod;

public final class ElevationProfile {
    
    private final ContinuousElevationModel elevation;
    private final double length;
    private final GeoPoint [] tab;
    private final int ESPACE = 4096;
    
    public ElevationProfile (ContinuousElevationModel elevationModel, GeoPoint origin, double azimuth, double length){
       if(!(isCanonical(azimuth)) || length <= 0){
           throw new IllegalArgumentException ();
       }
       
       if(elevationModel == null || origin == null){
           throw new NullPointerException();
       }
       
       elevation = elevationModel;
       this.length = length;
    
       
       tab = new GeoPoint [(int) (ceil(length/4096)+1)];
       for(int i=0; i<tab.length; i++){
           double latitude = asin(sin(origin.latitude())*cos(toRadians(ESPACE*i)) + 
               cos(origin.latitude())*sin(toRadians(ESPACE*i))*cos(toMath(azimuth)));
       
           double longitude = (floorMod(origin.longitude() - asin(sin(toMath(azimuth))*sin(toRadians(ESPACE*i))/cos(latitude)) + PI, PI2) - PI);
           
           tab[i] = new GeoPoint(longitude, latitude); 
           
       }
    }
    
    public double elevationAt(double x){
        isIn(x, length);
        
        return elevation.elevationAt(positionAt(x));
    }
    
    public GeoPoint positionAt(double x){
        isIn(x, length);
        
        
        int borneInf = (int)Math.floor(x/ESPACE);
        int borneSup = borneInf + 1;
        
        double longitudeA = lerp(tab[borneInf].longitude(), tab[borneSup].longitude(), x/ESPACE - borneInf);
        double latitudeA = lerp(tab[borneInf].latitude(), tab[borneSup].latitude(), x/ESPACE - borneInf);
        
        GeoPoint p = new GeoPoint(longitudeA, latitudeA);
       
        return p;
    }
    
    public double slopeAt(double x){
        isIn(x, length);
        
        return elevation.slopeAt(positionAt(x));
    }
    
    
    
    private void isIn (double position, double longueur){
        if(!(0<=position && position<=longueur)){
            throw new IllegalArgumentException();
        }        
    }

}
