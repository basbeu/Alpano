package ch.epfl.alpano;

/**
* Interface fournissant des methodes de contrôle
*
* @author Philippine Favre (258854)
* @author Bastien Beuchat  (257117)
*/

public interface Preconditions {
	
	/**
	 * Leve une exception si son argument est faux
	 * @param b expression booleenne a verifier
	 * @throws IllegalArgumentException exception levee si le parametre booleen est faux
	 */
	public static void checkArgument(boolean b) throws IllegalArgumentException{
		if(!b){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Leve une exception si son argument est faux
	 * @param b expression booleenne a verifier
	 * @param message message d'erreur 
	 * @throws IllegalArgumentException exception levee si le paramêtre booleen est faux
	 */
	public static void checkArgument(boolean b, String message) throws IllegalArgumentException{
		if(!b){
			throw new IllegalArgumentException(message);
		}
	}
}
