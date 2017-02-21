package ch.epfl.alpano;

import static java.lang.Math.PI;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

import java.util.function.DoubleUnaryOperator;

/**
* Interface fournissant des methodes mathématiques
*
* @author Philippine Favre (258854)
* @author Bastien Beuchat  (257117)
*/
public interface Math2 {
	static final double PI2 = 2*PI;
	
	/**
	 * Eleve un nombre au carre
	 * @param x Double a elever au carre
	 * @return Un double eleve au carre
	 */
	static double sq(double x){
		return x*x;
	}
	
	/**
	 * Calcule le reste de la division entiere par defaut
	 * @param x Double representant le numerateur
	 * @param y Double representant le denominateur
	 * @return Un double representant le reste de la division entiere par defaut de x par y
	 */
	static double floorMod(double x, double y){
		return x-y*floor(x/y);
	}
	
	/**
	 * Calcule du sinus verse d'un nombre
	 * @param x Double auquel il faut calculer le sinus verse
	 * @return un double representant le sinus verse de x
	 */
	static double haversin(double x){
		return sq(sin(x/2));
	}
	
	static double angularDistance(double a1,double a2){
		//checkArgument();
		
		return floorMod(a1-a2+PI,PI2);
	}
	
	static double lerp(double y0,double y1, double x){
		return 0;
	}
	
	static double bilerp(double z00,double z10,double z01, double z11, double x, double y){
		return 0;
	}
	
	static double firstIntervalContainingRoot(DoubleUnaryOperator f, double minX, double maxX, double dX){
		return 0;
	}
	
	static double improveRoot(DoubleUnaryOperator f, double x1,double x2, double epsilon){
		return 0;
	}
}
