package ch.epfl.alpano;

import static ch.epfl.alpano.Math2.PI2;
import static ch.epfl.alpano.Math2.floorMod;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.lang.Math.PI;

/**
 * Classe permettant de manipuler des nombres representant des azimuts exprimes en radians 
 * 
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public interface Azimuth {

    /**
     * Savoir si l'argument est un azimut canonique 
     * @param azimuth double angle en radian
     * @return boolean vrai si l'angle est entre [0, 2*PI[ sinon faux
     */
    static boolean isCanonical(double azimuth) {
        return (0 <= azimuth && azimuth < PI2);
    }

    /**
     * Calcul l'azimut canonique equivalent a celui en argument
     * @param azimuth double angle en radian pas canonique
     * @return double azimut canonique
     */
    static double canonicalize(double azimuth) {
        return floorMod(azimuth, PI2);
    }

    /**
     * Transforme un azimut en angle mathematique 
     * @param azimuth double angle dans le sens horaire
     * @return double angle mathematique
     * @throws IllegalArgumentException si l'argument n'est pas un azimut canonique
     */
    static double toMath(double azimuth) {
        checkArgument(isCanonical(azimuth), "angle invalide");
        if(azimuth != 0){
            return (PI2) - azimuth;
        }else{
            return azimuth;
        }
    }

    /**
     * Transforme un angle mathematique en azimut 
     * @param angle double dans le sens mathematique
     * @return double azimut canonique
     * @throws IllegalArgumentException si l'argument n'est pas canonique
     */
    static double fromMath(double angle) {
        checkArgument(isCanonical(angle), "angle invalide");
        if(angle != 0){
            return (PI2) - angle;
        }else{
            return angle;
        }
    }

    /**
     * Exprime en cadran l'angle donne en argument
     * @param azimuth double angle donne en radian
     * @param n String nord
     * @param e String est
     * @param s String sud
     * @param w String ouest
     * @return String exprimant le cadran correspondant a l'azimut
     * @throws IllegalArgumentException si l'argument n'est pas canonique
     */
    static String toOctantString(double azimuth, String n, String e, String s, String w) {
        checkArgument(isCanonical(azimuth), "angle invalide");
        if((15 * PI / 8 <= azimuth && azimuth < PI2) || (0 <= azimuth && azimuth < PI / 8)){
            return n;
        }else if(PI / 8 <= azimuth && azimuth < 3 * PI / 8){
            return n + e;
        }else if(3 * PI / 8 <= azimuth && azimuth < 5 * PI / 8){
            return e;
        }else if(5 * PI / 8 <= azimuth && azimuth < 7 * PI / 8){
            return s + e;
        }else if(7 * PI / 8 <= azimuth && azimuth < 9 * PI / 8){
            return s;
        }else if(9 * PI / 8 <= azimuth && azimuth < 11 * PI / 8){
            return s + w;
        }else if(11 * PI / 8 <= azimuth && azimuth < 13 * PI / 8){
            return w;
        }else{
            return n + w;
        }
    }

}
