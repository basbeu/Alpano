package ch.epfl.alpano;

import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.lang.Math.PI;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

import java.util.function.DoubleUnaryOperator;

/**
 * Interface fournissant des methodes mathematiques
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public interface Math2 {
    double PI2 = 2 * PI;

    /**
     * Eleve un nombre au carre
     * @param x double a elever au carre
     * @return double valeur elevee au carre
     */
    static double sq(double x){
        return x * x;
    }

    /**
     * Calcule le reste de la division entiere par defaut
     * @param x double representant le numerateur
     * @param y double representant le denominateur
     * @return double representant le reste de la division entiere par defaut de x par y
     */
    static double floorMod(double x, double y){
        return x - y * floor(x / y);
    }

    /**
     * Calcule du sinus verse d'un nombre
     * @param x double auquel il faut calculer le demi sinus verse
     * @return double representant le sinus verse de x
     */
    static double haversin(double x){
        return sq(sin(x / 2));
    }

    /**
     * Calcule la difference angulaire
     * @param a1 double angle en radian
     * @param a2 double angle en radian
     * @return double representant la difference angulaire entre les deux angles
     */
    static double angularDistance(double a1,double a2){
        return floorMod(a2 - a1 + PI,PI2) - PI;
    }

    /**
     * Calcule l'interpolation lineaire de f(x)
     * @param y0 double representant f(0)
     * @param y1 double representant f(1)
     * @param x  double representant la valeur a interpoler
     * @return double representant f(x) obtenue par interpolation lineaire
     */
    static double lerp(double y0,double y1, double x){
        return y0 + x * (y1 - y0);
    }

    /**
     * Calcule l'interpolation bilineaire de f(x,y)
     * @param z00 double representant f(0,0)
     * @param z10 double representant f(1,0)
     * @param z01 double representant f(0,1)
     * @param z11 double representant f(1,1)
     * @param x   double representant la valeur x a interpoler
     * @param y   double representant la valeur  y a interpoler
     * @return double representant f(x,y) obtenue par interpolation bilineaire
     */
    static double bilerp(double z00,double z10,double z01, double z11, double x, double y){
        double z1 = lerp(z00,z10,x);
        double z2 = lerp(z01,z11,x);
        return lerp(z1,z2,y);
    }

    /**
     * Trouve le premier interval contenant un zero d'une fonction
     * @param f DoubleUnaryOperator representant la fonction a tester
     * @param minX double representant le minimum de l'intervalle de recherche
     * @param maxX double representant le maximum de l'intervalle de recherche
     * @param dX double representant la taille des intervalles testes
     * @return double representant la borne inferieur de l'intervalle de taille dX contenant un zero
     * @throws IllegalArgumentException si les intervalles ne sont pas valides 
     */
    static double firstIntervalContainingRoot(DoubleUnaryOperator f, double minX, double maxX, double dX){
        checkArgument(minX <= maxX && 0 < dX, "Le minimum doit être plus petit ou égal au maximum et la taille de l'intervalle doit être supérieur à 0");
        double x1 = minX;
        double x2 = minX + dX;
        
        while(x2 <= maxX){
            if(f.applyAsDouble(x1) * f.applyAsDouble(x2) < 0){
                return x1; 
            }
            x1 += dX;
            x2 += dX;
        }

        return Double.POSITIVE_INFINITY;
    }

    /**
     * Determine un intervalle plus petit ou egal a epsilon contenant un 0
     * @param f	DoubleUnaryOperator representant la fonction a tester
     * @param x1 double representant la borne inferieur de l'intervalle
     * @param x2 double representant la borne superieur de l'intervalle
     * @param epsilon double representant l'intervalle
     * @return double representant la taille max de l'intervalle a trouver
     * @throws IllegalArgumentException si f(x1) et f(x2) sont de meme signe
     */
    static double improveRoot(DoubleUnaryOperator f, double x1,double x2, double epsilon){
        checkArgument(f.applyAsDouble(x1) * f.applyAsDouble(x2) < 0, "f(x1) et f(x2) doivent être de signe différent");

        while(Math.abs(x1 - x2) > epsilon){
            double xM = (x1 + x2) / 2;
            double yM = f.applyAsDouble(xM); 
            if(yM == 0){
                return xM;
            }else if(yM * f.applyAsDouble(x1) < 0){
                x2 = xM;
            }else {
                x1 = xM;
            }
        }

        return x1;
    }
}
