package ch.epfl.alpano.summit;

import static java.util.Objects.requireNonNull;

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
     * @throws IllegalArgumentException si le nom ou la position est nulle
     */
    public Summit(String name, GeoPoint position, int elevation){
        this.name = requireNonNull(name,"String name : null");
        this.position = requireNonNull(position, "GeoPoint position : null");
        this.elevation = elevation;
    }

    /**
     * @return un String representant le nom du sommet
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
        return name()+" "+position()+" "+elevation();
    }
    
}
