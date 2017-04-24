package ch.epfl.alpano.gui;

public class LabeledListStringConverterTest {

    public static void main(String[] args) {
        LabeledListStringConverter c =
                new LabeledListStringConverter("a", "b", "c", "d", "e");
        System.out.println(c.fromString("a"));
        System.out.println(c.fromString("b"));
        System.out.println(c.fromString("c"));
        System.out.println(c.fromString("d"));
        System.out.println(c.fromString("e"));
        System.out.println(c.fromString("f"));


        System.out.println(c.toString(0));
        System.out.println(c.toString(1));
        System.out.println(c.toString(2));
        System.out.println(c.toString(3));
        System.out.println(c.toString(4));


    }

}
