package ch.epfl.alpano;

/**
 *methodes permettant de manipuler des nombres representant des azimuts exprimes en radians 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static ch.epfl.alpano.Preconditions.checkArgument;

public interface Azimuth {
    /**
     * methode pour savoir si l'argument est un azimut canonique 
     * @param azimuth angle en radian
     * @return vrai si l'angle est entre [0, 2*PI[ sinon faux
     */
    public static boolean isCanonical(double azimuth) {
        if(0 <= azimuth && azimuth < 2 * PI){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * methode retournant l'azimut canonique equivalent a celui en argument
     * @param azimuth angle en radian pas canonique
     * @return azimut canonique
     */
    public static double canonicalize(double azimuth) {
        return Math2.floorMod(azimuth,2*PI);
    }
    
    /**
     * methode transformant un azimut en angle mathematique 
     * @param azimuth angle dans le sens horaire
     * @return angle mathematique
     * @throws IllegalArgumentException si l'argument n'est pas un azimut canonique
     */
    public static double toMath(double azimuth) {
        checkArgument(0 <= azimuth && azimuth < 2*PI, "angle invalide");
        if(azimuth != 0){
            return (2*PI) - azimuth;
        }else{
            return azimuth;
        }
    }
    
    /**
     * methode transformant un angle mathematique en azimut 
     * @param azimuth angle dans le sens mathematique
     * @return azimut canonique
     * @throws IllegalArguentException si l'argument n'est pas canonique
     */
    public static double fromMath(double azimuth) {
        return toMath(azimuth);
    }
    
    /**
     * methode pour exprimer en cadran l'angle donne en argument
     * @param azimuth angle donne en radian
     * @param n nord
     * @param e est
     * @param s sud
     * @param w ouest
     * @return string exprimant le cadran correspondant a l'azimut
     * @throws IllegalArgumentException si l'argument n'est pas canonique
     */
    public static String toOctantString(double azimuth, String n, String e, String s, String w) {
        checkArgument(0 <= azimuth && azimuth < 2*PI, "angle invalide");
        if((15*PI/8 <= azimuth && azimuth < 2*PI) || (0<= azimuth && azimuth < PI/8)){
            return n;
        }else if(PI/8 <= azimuth && azimuth < 3*PI/8){
            return n+e;
        }else if(3*PI/8 <= azimuth && azimuth < 5*PI/8){
            return e;
        }else if(5*PI/8 <= azimuth && azimuth < 7*PI/8){
            return s+e;
        }else if(7*PI/8 <= azimuth && azimuth < 9*PI/8){
            return s;
        }else if(9*PI/8 <= azimuth && azimuth < 11*PI/8){
            return s+w;
        }else if(11*PI/8 <= azimuth && azimuth < 13*PI/8){
            return w;
        }else{
            return n+w;
        }
    }

}
