package ch.epfl.alpano.gui;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.util.StringConverter;

public final class FixedPointStringConverter extends StringConverter<Integer>{
    
    private final int decimal;
    
    public FixedPointStringConverter(int decimal){
        this.decimal = decimal;
    }

    @Override
    public Integer fromString(String string) {
        BigDecimal number = new BigDecimal (string);
        return number.setScale(decimal, RoundingMode.HALF_UP).movePointRight(decimal).intValueExact();
    }

    @Override
    public String toString(Integer object) {
        BigDecimal number = new BigDecimal(object);
        return number.movePointLeft(decimal).toPlainString();
    }

}
