package ch.epfl.alpano;

/**
 * Interface fournissant des methodes de controle
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public interface Preconditions {

    /**
     * Leve une exception si son argument est faux
     * @param b boolean a verifier
     * @throws IllegalArgumentException exception levee si le parametre booleen est faux
     */
    static void checkArgument(boolean b) throws IllegalArgumentException{
        if(!b){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Leve une exception si son argument est faux
     * @param b boolean a verifier
     * @param message String message d'erreur 
     * @throws IllegalArgumentException exception levee si le parametre booleen est faux
     */
    static void checkArgument(boolean b, String message) throws IllegalArgumentException{
        if(!b){
            throw new IllegalArgumentException(message);
        }
    }
}
