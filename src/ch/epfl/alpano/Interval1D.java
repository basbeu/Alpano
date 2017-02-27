package ch.epfl.alpano;

import static ch.epfl.alpano.Preconditions.checkArgument;

import java.util.Objects;

/**
 * Classe immuable representant un intervalle d'entiers unidimensionnel 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class Interval1D {
    
    private final int from,to;
    
    /**
     * Constructeur d'un intervalle d'entier unidimensionnel
     * @param includedFrom Premier entier de l'intervalle
     * @param includedTo Dernier entier de l'intervalle
     * @throws IllegalArgumentException si includedTo est strictement inferieur a includedFrom
     */
    public Interval1D(int includedFrom,int includedTo){
        checkArgument(includedTo >=includedFrom,"includedTo must be greater or equal to includedFrom");
        
        from=includedFrom;
        to=includedTo;
    }
    
    
    /**
     * @return le premier entier de l'intervalle
     */
    public int includedFrom(){
        return from;
    }
    
    /**
     * @return le dernier entier de l'intervalle
     */
    public int includedTo(){
        return to;
    }
    
    /**
     * Indique si un entier est compris dans l'intervalle
     * @param v entier a chercher dans l'intervalle
     * @return un booleen indiquant si l'entier en parametre est contenu dans l'intervalle
     */
    public boolean contains(int v){
        return (v >= from && v <= to);
    }
    
    /**
     * @return un entier pour la taille de l'intervalle
     */
    public int size(){
        return to-from+1;
    }
    
    /**
     * Calcule la taille de l'intersection avec l'intervalle en parametre
     * @param that Interval1D avec lequel on doit calculer la taille de l'intersection
     * @return un entier representant la taille de l'intersection (0 si l'intersection n'existe pas)
     */
    public int sizeOfIntersectionWith(Interval1D that){
        int size = 0;
        if(from <= that.includedFrom() && that.includedFrom()<=to){
            size = to-that.includedFrom()+1;
        }else if(from <= that.includedTo() && that.includedTo()<=to){
            size = that.includedTo()-from+1;
        }else if(from <= that.includedFrom() && that.includedTo()<=to){
            size = that.size();
        }else if(that.includedFrom() <= from && to<=that.includedTo()){
            size = size();
        }
        
        return size;
    }
    
    /**
     * Calcule l'union englobante avec le parametre
     * @param that Interval1D avec lequel il faut effectuer l'union englobante
     * @return un Interval1D qui represente l'union englobante
     */
    public Interval1D boundingUnion(Interval1D that){
        int boundFrom = (from<that.includedFrom()?from:that.includedFrom());
        int boundTo = (to>that.includedTo()?to:that.includedTo());
        
        return new Interval1D(boundFrom,boundTo);
    }
    
    /**
     * Indique si that est unionable avec this
     * @param that Interval1D avec lequel on verifie l'unionabilite
     * @return un booleen indiquant si this et that sont unionables
     */
    public boolean isUnionableWith(Interval1D that){
        
        return (this.size() + that.size() - this.sizeOfIntersectionWith(that) == this.boundingUnion(that).size());
    }
    
    /**
     * Calcule l'union avec that
     * @param that Interval1D avec lequel il faut effectuer l'union
     * @return un Interval1D representant l'union avec that
     * @throws IllegalArgumentException si l'intervalle that n'est pas unionable
     */
    public Interval1D union(Interval1D that){
        checkArgument(isUnionableWith(that));
        
        return boundingUnion(that);
    }
    
    @Override
    public boolean equals(Object that0) {
        if (this == that0)
            return true;
        if (that0 == null)
            return false;
        if (getClass() != that0.getClass())
            return false;
        Interval1D other = (Interval1D) that0;
        if (from != other.from)
            return false;
        if (to != other.to)
            return false;
        return true;
    }


    @Override
    public int hashCode() {
        return Objects.hash(includedFrom(), includedTo());
    }
    
    @Override
    public String toString() {
        return "["+from+".."+to+"]";
    }
}
