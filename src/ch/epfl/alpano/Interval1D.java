package ch.epfl.alpano;

import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Objects;

/**
 * Classe immuable representant un intervalle d'entiers unidimensionnel 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class Interval1D {

    private final int from, to;

    /**
     * Constructeur d'un intervalle d'entier unidimensionnel
     * @param includedFrom int premier entier de l'intervalle
     * @param includedTo int dernier entier de l'intervalle
     * @throws IllegalArgumentException si includedTo est strictement inferieur a includedFrom
     */
    public Interval1D(int includedFrom, int includedTo){
        checkArgument(includedTo >= includedFrom, "includedTo doit être plus grand ou égal à includedFrom");

        from = includedFrom;
        to = includedTo;
    }


    /**
     * Accesseur public du premier entier de l'intervalle
     * @return int le plus petit element de l'intervalle
     */
    public int includedFrom(){
        return from;
    }

    /**
     * Accesseur public du dernier entier de l'intervalle
     * @return int le plus grand element de l'intervalle
     */
    public int includedTo(){
        return to;
    }

    /**
     * Indique si un entier est compris dans l'intervalle
     * @param v int entier a chercher dans l'intervalle
     * @return boolean indiquant si l'entier en parametre est contenu dans l'intervalle
     */
    public boolean contains(int v){
        return (v >= includedFrom() && v <= includedTo());
    }

    /**
     * Calcul la taille de l'intervalle
     * @return int taille de l'intervalle
     */
    public int size(){
        return includedTo() - includedFrom() + 1;
    }

    /**
     * Calcule la taille de l'intersection avec l'intervalle en parametre
     * @param that Interval1D avec lequel on doit calculer la taille de l'intersection
     * @return int entier representant la taille de l'intersection (0 si l'intersection n'existe pas)
     */
    public int sizeOfIntersectionWith(Interval1D that){
        int minTo = min(includedTo(), that.includedTo());
        int maxFrom = max(includedFrom(), that.includedFrom());

        //test si il y a une intersection
        if(maxFrom <= minTo){
//                
            //calcul de l'intersection
            return minTo - maxFrom + 1;
        }else{
            //pas d'intersection
            return 0;
        }
    }

    /**
     * Calcule l'union englobante avec le parametre
     * @param that Interval1D avec lequel il faut effectuer l'union englobante
     * @return Interval1D qui represente l'union englobante
     */
    public Interval1D boundingUnion(Interval1D that){
        int boundFrom = min(includedFrom(), that.includedFrom());
        int boundTo = max(includedTo(), that.includedTo());

        return new Interval1D(boundFrom, boundTo);
    }

    /**
     * Indique si that est unionable avec this
     * @param that Interval1D avec lequel on verifie l'unionabilite
     * @return boolean indiquant si this et that sont unionables
     */
    public boolean isUnionableWith(Interval1D that){

        return (this.size() + that.size() - this.sizeOfIntersectionWith(that) == this.boundingUnion(that).size());
    }

    /**
     * Calcule l'union avec that
     * @param that Interval1D avec lequel il faut effectuer l'union
     * @return Interval1D representant l'union avec that
     * @throws IllegalArgumentException si l'intervalle that n'est pas unionable
     */
    public Interval1D union(Interval1D that){
        checkArgument(isUnionableWith(that));

        return boundingUnion(that);
    }

    @Override
    public boolean equals(Object that0) {
        if (that0 == null)
            return false;
        if (getClass() != that0.getClass())
            return false;
        Interval1D other = (Interval1D) that0;
        if (includedFrom() != other.includedFrom())
            return false;
        if (includedTo() != other.includedTo())
            return false;
        return true;
    }


    @Override
    public int hashCode() {
        return Objects.hash(includedFrom(), includedTo());
    }

    @Override
    public String toString() {
        return "["+includedFrom()+".."+includedTo()+"]";
    }
}
