package ch.epfl.alpano;

/**
* Interface fournissant des m�thodes de contr�le
*
* @author Philippine Favre (258854)
* @author Bastien Beuchat  (257117)
*/

public interface Preconditions {
	
	/**
	 * L�ve une exception si son argument est faux
	 * @param b expression bool�enne � v�rifier
	 * @throws IllegalArgumentException exception lev�e si le param�tre bool�en est faux
	 */
	public static void checkArgument(boolean b) throws IllegalArgumentException{
		if(!b){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * L�ve une exception si son argument est faux
	 * @param b expression bool�enne � v�rifier
	 * @param message message d'erreur
	 * @throws IllegalArgumentException exception lev�e si le param�tre bool�en est faux
	 */
	public static void checkArgument(boolean b, String message) throws IllegalArgumentException{
		if(!b){
			throw new IllegalArgumentException(message);
		}
	}
}
