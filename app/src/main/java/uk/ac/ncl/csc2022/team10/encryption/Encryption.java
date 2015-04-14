package uk.ac.ncl.csc2022.team10.encryption;

/**
 * Created by szholdiyarov on 4/13/15.
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Encryption {

    private Map<Integer, String> numericalTable = new HashMap<Integer, String>();
    private String result;
    private Integer answer;
    private final static String USER_AGENT = "Mozilla/5.0";
    private Exception exception;

    public Encryption() {
        numericalTable.put(11, "a");
        numericalTable.put(12, "b");
        numericalTable.put(13, "c");
        numericalTable.put(14, "d");
        numericalTable.put(15, "e");

        numericalTable.put(21, "f");
        numericalTable.put(22, "g");
        numericalTable.put(23, "h");
        numericalTable.put(24, "i");
        numericalTable.put(25, "j");

        numericalTable.put(31, "k");
        numericalTable.put(32, "l");
        numericalTable.put(33, "m");
        numericalTable.put(34, "n");
        numericalTable.put(35, "o");

        numericalTable.put(41, "p");
        numericalTable.put(42, "q");
        numericalTable.put(43, "r");
        numericalTable.put(44, "s");
        numericalTable.put(45, "t");

        numericalTable.put(51, "u");
        numericalTable.put(52, "v");
        numericalTable.put(53, "w");
        numericalTable.put(54, "x");
        numericalTable.put(55, "y");

        numericalTable.put(61, "z");
        numericalTable.put(62, "1");
        numericalTable.put(63, "2");
        numericalTable.put(64, "3");
        numericalTable.put(65, "4");

        numericalTable.put(71, "5");
        numericalTable.put(72, "6");
        numericalTable.put(73, "7");
        numericalTable.put(74, "8");
        numericalTable.put(75, "9");

        numericalTable.put(81, "0");
        numericalTable.put(82, ".");
        numericalTable.put(83, ",");
        numericalTable.put(84, "?");
        numericalTable.put(85, "!");
    }

    public String encrypt(String text) {
        String[] textToEncrypt = text.toLowerCase().split("");
        for (int i = 0; i < textToEncrypt.length; i++) {
            Iterator<?> iterator = numericalTable.entrySet().iterator();
            while (iterator.hasNext()) {
                @SuppressWarnings("rawtypes")
                Map.Entry pair = (Map.Entry) iterator.next();
                if (pair.getValue().equals(textToEncrypt[i])) {
                    result = result + pair.getKey();
                }
            }
        }
        return result = result.substring(4);
    }

}

