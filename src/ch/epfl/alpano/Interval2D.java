package ch.epfl.alpano;

import static ch.epfl.alpano.Preconditions.checkArgument;

import java.util.Objects;

/**
 * Classe immuable representant un intervalle d'entiers bidimensionnel 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class Interval2D {
    private final Interval1D iX, iY;
    
    /**
     * Constructeur d'un intervalle d'entiers bidimensionnel 
     * en faisant le produit cartesien de deux intervalles unidimensionnels
     * @param iX Interval1D representant la premiere dimension
     * @param iY Interval1D representant la deuxieme dimension
     * @throws NullPointerException si un des deux parametre est null
     */
    public Interval2D(Interval1D iX, Interval1D iY){
        if(iX == null || iY == null){
            throw new NullPointerException("Un interval ne doit pas être null");
        }
        
        this.iX = iX;
        this.iY = iY;
    }
    
    /**
     * Accesseur public du premier intervalle
     * @return Interval1D representant la premiere dimension
     */
    public Interval1D iX(){
        return iX;
    }
    
    /**
     * Accesseur public du deuxieme intervalle
     * @return Interval1D representant la deuxieme dimension
     */
    public Interval1D iY(){
        return iY;
    }
    
    /**
     * Indique si un couple d'entier est compris dans l'intervalle
     * @param x int representant le premier nombre du couple 
     * @param y int representant le deuxieme nombre du couple
     * @return boolean indiquant si le couple est compris dans l'intervalle
     */
    public boolean contains(int x, int y){
        return (iX.contains(x) && iY.contains(y));
    }
    
    /**
     * Calcul la taille de l'intervalle
     * @return int taille de l'intervalle
     */
    public int size(){
        return iX.size() * iY.size();
    }
   
    /**
     * Calcule la taille de l'intersection avec l'intervalle en parametre
     * @param that Interval2D avec lequel on calcule la taille de l'intersection
     * @return int representant la taille de l'intersection (0 si l'intersection n'existe pas)
     */
    public int sizeOfIntersectionWith(Interval2D that){
        return that.iX().sizeOfIntersectionWith(iX) * that.iY().sizeOfIntersectionWith(iY);
    }
    
    /**
     * Calcule l'union englobante avec le parametre
     * @param that Interval2D avec lequel l'union englobante est effectue
     * @return un Interval2D representant l'union englobante
     */
    public Interval2D boundingUnion(Interval2D that){
        return new Interval2D(iX().boundingUnion(that.iX()),iY().boundingUnion(that.iY()));
    }
    
    /**
     * Indique si l'union avec le parametre est possible
     * @param that Interval2D avec lequel l'unionabilite est verifiee
     * @return un booleen indiquant si l'union est possible avec le parametre
     */
    public boolean isUnionableWith(Interval2D that){
        return size()+that.size()-sizeOfIntersectionWith(that) == boundingUnion(that).size();
    }
    
    /**
     * Calcule l'union avec le parametre
     * @param that Interval2D avec lequel l'union est effectue
     * @return Interval2D representant l'union
     * @throws IllegalArgumentException si l'union n'est pas possible
     */
    public Interval2D union(Interval2D that){
        checkArgument(isUnionableWith(that));
        
        return boundingUnion(that);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Interval2D other = (Interval2D) obj;
        if (iX == null) {
            if (other.iX != null)
                return false;
        } else if (!iX.equals(other.iX))
            return false;
        if (iY == null) {
            if (other.iY != null)
                return false;
        } else if (!iY.equals(other.iY))
            return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(iX(), iY());
    }

    @Override
    public String toString() {
        return iX.toString()+"x"+iY.toString();
    }
}
