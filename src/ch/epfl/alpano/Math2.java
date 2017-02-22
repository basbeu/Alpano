package ch.epfl.alpano;

import static java.lang.Math.PI;
import static java.lang.Math.floor;
import static java.lang.Math.sin;
import static ch.epfl.alpano.Preconditions.checkArgument;

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
	
	/**
	 * Calcule la difference angulaire
	 * @param a1 Double angle en radian
	 * @param a2 Double angle en radian
	 * @return un double representant la difference angulaire entre les deux angles
	 */
	static double angularDistance(double a1,double a2){
		return floorMod(a2-a1+PI,PI2)-PI;
	}
	
	/**
	 * Calcule l'interpolation linéaire de f(x)
	 * @param y0 Double representant f(0)
	 * @param y1 Double representant f(1)
	 * @param x  Double representant la valeur a interpoler
	 * @return un double representant f(x) obtenue par interpolation lineaire
	 */
	static double lerp(double y0,double y1, double x){
		return y0+x*(y1-y0);
	}
	
	/**
	 * Calcule l'interpolation bilinéaire de f(x,y)
	 * @param z00 Double representant f(0,0)
	 * @param z10 Double representant f(1,0)
	 * @param z01 Double representant f(0,1)
	 * @param z11 Double representant f(1,1)
	 * @param x   Double representant la valeur x a interpoler
	 * @param y   Double representant la valeur  y a interpoler
	 * @return un double representant f(x,y) obtenue par interpolation bilineaire
	 */
	static double bilerp(double z00,double z10,double z01, double z11, double x, double y){
		double z1 = lerp(z00,z10,x);
		double z2 = lerp(z01,z11,x);
		return lerp(z1,z2,y);
	}
	
	/**
	 * Trouve le premier interval contenant un zero d'une fonction
	 * @param f DoubleUnaryOperator representant la fonction a tester
	 * @param minX Double representant le minimum de l'intervalle de recherche
	 * @param maxX Double representant le maximum de l'intervalle de recherche
	 * @param dX Double representant la taille des intervalles testes
	 * @return un Double representant la borne inferieur de l'intervalle de taille dX contenant un zero 
	 */
	static double firstIntervalContainingRoot(DoubleUnaryOperator f, double minX, double maxX, double dX){
		double x1=minX;
		double x2=minX+dX;
		do{
			if(f.applyAsDouble(x1)*f.applyAsDouble(x2)<0){
				return x1; 
			}
			x1+=dX;
			x2+=dX;
		}while(x1<=maxX);
		
		return Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Determine un intervalle plus petit ou egal à epsilon contenant un 0
	 * @param f	DoubleUnaryOperator representant la fonction a tester
	 * @param x1 Double representant la borne inferieur de l'intervalle
	 * @param x2 Double representant la borne superieur de l'intervalle
	 * @param epsilon Double representant l'intervalle
	 * @return un Double representant la taille max de l'intervalle a trouver
	 */
	static double improveRoot(DoubleUnaryOperator f, double x1,double x2, double epsilon){
		checkArgument(f.applyAsDouble(x1)*f.applyAsDouble(x2)<0,"f(x1) and f(x2) must be of different sign");
		
		do{
			double xM=(x1+x2)/2;
			double yM =f.applyAsDouble(xM); 
			if(yM==0){
				return xM;
			}else if(yM *f.applyAsDouble(x1)<0){
				x2=xM;
			}else {
				x1=xM;
			}
		}while(Math.abs(x1-x2)>epsilon);
		
		return x1;
	}
}
