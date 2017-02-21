package ch.epfl.alpano;

/**
*
*
* @author Philippine Favre (258854)
* @author Bastien Beuchat  (257117)
*/

public interface Preconditions {
	
	public static void checkArgument(boolean b) throws IllegalArgumentException{
		if(!b){
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkArgument(boolean b, String message){
		if(!b){
			throw new IllegalArgumentException(message);
		}
	}
}
