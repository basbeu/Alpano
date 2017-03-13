package ch.epfl.alpano.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;

import ch.epfl.alpano.Interval1D;
import ch.epfl.alpano.Interval2D;

import static ch.epfl.alpano.Preconditions.checkArgument;
import static ch.epfl.alpano.dem.DiscreteElevationModel.sampleIndex;
import static java.lang.Math.toRadians;

/**
 * Classe immuable representant un MNT discret, par rapport a un fichier HGT
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class HgtDiscreteElevationModel implements DiscreteElevationModel {
    private ShortBuffer elevations;
    private final Interval2D extent;
    
    /**
     * Construit un HgtDiscreteElevationModel
     * @param file File representant le fichier .hgt associe au HgtDiscreteElevationModel 
     */
    public HgtDiscreteElevationModel(File file){
        int latitude = 0, longitude = 0;
        //controle du nom du fichier
        String name = file.getName();
        boolean isNameCorrect=true;
        if(name.length() != 11){
            isNameCorrect=false;
        }else{
            try{
                latitude = Integer.parseInt(name.substring(1, 3));
                longitude = Integer.parseInt(name.substring(4, 7));
            }catch(NumberFormatException e){
                isNameCorrect = false;
            }
            
            char lat = name.charAt(0);
            char lon = name.charAt(3);
            if((lat != 'N' && lat != 'S')||
                    (lon != 'E' && lon != 'W' )){
                isNameCorrect = false;
            }
            
            if(lat == 'S'){
                latitude *=-1;
            }
            if(lon == 'W'){
                longitude*=-1;
            }
            
            if(!name.substring(7,11).equals(".hgt")){
                isNameCorrect = false;
            }
        }
        checkArgument(isNameCorrect,"Name incorrect");
        
        //controle de la longueur du fichier
        long l = file.length();
        checkArgument(l == 25934402, "Length of the file incorrect");
        
        //Creation de l'Interval2D
        extent=new Interval2D(new Interval1D((int)sampleIndex(toRadians(longitude)),(int)sampleIndex(toRadians(longitude+1))),new Interval1D((int)sampleIndex(toRadians(latitude)),(int)sampleIndex(toRadians(latitude+1))));
        
        //Mappage du fichier
        try(FileInputStream s = new FileInputStream(file)){
            elevations = s.getChannel().map(MapMode.READ_ONLY, 0, l).asShortBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    @Override
    public void close() throws Exception {
        elevations = null;
    }

    @Override
    public Interval2D extent() {
        return extent;
    }

    @Override
    public double elevationSample(int x, int y) {
        checkArgument(extent.contains(x, y));
        int x0 = extent.iX().includedFrom();
        int y0 = extent.iY().includedTo();
        
        return elevations.get((y0-y-1)*3601+(x-x0));
    }
    
}
