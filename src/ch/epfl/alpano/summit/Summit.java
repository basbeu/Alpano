package ch.epfl.alpano.summit;

import ch.epfl.alpano.GeoPoint;

/**
 * Classe immuable representant un sommet 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class Summit {
    private final String name;
    private final GeoPoint position;
    private final int elevation;
    
    /**
     * Constructeur d'un sommet
     * @param name String representant le nom du sommet
     * @param position GeoPoint representant les coordonnees geographique du sommet
     * @param elevation int representant l'altitude du sommet
     */
    public Summit(String name, GeoPoint position, int elevation){
        this.name = name;
        this.position = position;
        this.elevation = elevation;
    }

    /**
     * @return un Strig representant le nom du sommet
     */
    public String name(){
        return name;
    }
    
    /**
     * @return un GeoPoint representant les coordonnees geographique du sommet
     */
    public GeoPoint position(){
        return position;
    }
    
    /**
     * @return un int representant l'altitude du sommet
     */
    public int elevation(){
        return elevation;
    }
    
    @Override
    public String toString() {
        return name+" "+position+" "+elevation;
    }
    
}
