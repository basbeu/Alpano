package ch.epfl.alpano;

/**
* Interface fournissant des méthodes de contrôle
*
* @author Philippine Favre (258854)
* @author Bastien Beuchat  (257117)
*/

public interface Preconditions {
	
	/**
	 * Lève une exception si son argument est faux
	 * @param b expression booléenne à vérifier
	 * @throws IllegalArgumentException exception levée si le paramêtre booléen est faux
	 */
	public static void checkArgument(boolean b) throws IllegalArgumentException{
		if(!b){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Lève une exception si son argument est faux
	 * @param b expression booléenne à vérifier
	 * @param message message d'erreur
	 * @throws IllegalArgumentException exception levée si le paramêtre booléen est faux
	 */
	public static void checkArgument(boolean b, String message) throws IllegalArgumentException{
		if(!b){
			throw new IllegalArgumentException(message);
		}
	}
}
