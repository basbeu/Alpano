package ch.epfl.alpano.gui;

/**
 * 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public enum UserParameter {
    OBSERVER_LONGITUDE(60_000, 120_000),
    OBSERVER_LATTITUDE(450_000, 480_000),
    OBSERVER_ELEVATION(300, 10000),
    CENTER_AZIMUTH(0, 359),
    HORIZONTAL_FIELD_OF_VIEW(1, 360),
    MAX_DISTANCE(10, 600),
    WIDTH(30, 16000),
    HEIGHT(10, 4000),
    SUPER_SAMPLING_EXPONENT(0, 2);
    
    private int min;
    private int max;
    
    private UserParameter(int min, int max){
        this.min = min;
        this.max = max;
    }
    
    public int min(){
        return min;
    }
    
    public int max(){
        return max;
    }
    
   /* public int sanitize(int value){
        return 
    }*/
}
