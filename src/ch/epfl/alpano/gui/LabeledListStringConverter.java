package ch.epfl.alpano.gui;

import static ch.epfl.alpano.Preconditions.checkArgument;


import java.util.ArrayList;
import java.util.List;

import javafx.util.StringConverter;

/**
 * Classe public, héritant de StringConverter<Integer>, convertit entre chaînes de caractères et entiers. 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class LabeledListStringConverter extends StringConverter<Integer> {

    private final List<String> s;
    
    /**
     * Constructeur d'un LabeledListStringConverter
     * @param strings String il y en a un nombre variable
     */
    public LabeledListStringConverter (String...strings){ 
        s = new ArrayList<>();
        for(String string : strings){
            checkArgument(string != null, "Un élément est null");
               s.add(string);
        }
    }
    
    @Override
    public Integer fromString(String arg0) {
        checkArgument(s.indexOf(arg0) != (-1), "l'élément n'est pas dans la liste");
        return s.indexOf(arg0);
        
    }

    @Override
    public String toString(Integer object) {
        if(object > s.size())
        {
            throw new IndexOutOfBoundsException("Index invalide");
        }
        return s.get(object);
    }

}
