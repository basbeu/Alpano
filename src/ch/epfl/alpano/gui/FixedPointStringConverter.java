package ch.epfl.alpano.gui;


import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

import javafx.util.StringConverter;

/**
 * Classe public, héritant de StringConverter<Integer>, convertit entre chaînes de caractères et entiers. 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class FixedPointStringConverter extends StringConverter<Integer>{

    private final int decimal;

    /**
     * Constructeur d'un FixedPointStringConverter
     * @param int arrondi à un nombre de décimale fixe 
     */

    public FixedPointStringConverter(int decimal){
        this.decimal = decimal;
    }

    @Override
    public Integer fromString(String string) {
        BigDecimal number = new BigDecimal (string);
        return number.setScale(decimal, HALF_UP).movePointRight(decimal).intValueExact();
    }

    @Override
    public String toString(Integer object) {
        BigDecimal number = new BigDecimal(object);
        return number.movePointLeft(decimal).toPlainString();
    }

}
