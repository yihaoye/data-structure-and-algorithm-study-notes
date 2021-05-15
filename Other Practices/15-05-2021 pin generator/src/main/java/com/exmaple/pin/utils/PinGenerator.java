package com.exmaple.pin.utils;

import java.util.*;

public class PinGenerator { // PinGenerator could be considered as a util class globally
    private static Set<String> pinSet; // apply static variable and init() to load a valid pin set, the set is always the same for reuse

    public static boolean isValid(String digit) {
        init();
        return pinSet.contains(digit);
    }

    public static List<String> generate() {
        init();
        return generate(pinSet.size());
    }

    public static List<String> generate(Integer outputAmount) {
        init();
        List<String> tmp = new ArrayList<>(pinSet);
        Collections.shuffle(tmp);
        List<String> res = tmp.subList(0, outputAmount);
        while (outputAmount > 2 && isPinListOrdered(res)) { // return pins should be never sorted or reverse ordered
            Collections.shuffle(res);
        }
        return res;
    }

    private synchronized static void init() { // synchronized for thread safety, since consider the class may be used as util in a project
        if (pinSet == null || pinSet.isEmpty()) { // speed up once it init before
            pinSet = new HashSet<>();
            for (int i = 0; i <= 9999; i++) {
                String pin = filter(i);
                if (pin != null && !pin.isEmpty()) {
                    pinSet.add(pin);
                }
            }
        }
    }

    private static String filter(Integer digit) {
        if (digit > 9999 || digit < 0) return "";
        String str = String.format("%04d", digit); // prepend 0 for digit which smaller than 1000
        char[] cArr = str.toCharArray();
        for (int i=0; i<3; i++) {
            int diff = cArr[i]-cArr[i+1];
            if (diff == 0 || diff == 1 || diff == -1) return "";
        }
        return str;
    }

    private static boolean isPinListOrdered(List<String> pins) {
        List<String> sortedPins = new ArrayList<>(pins);
        List<String> reversePins = new ArrayList<>(pins);
        Collections.sort(sortedPins);
        Collections.reverse(reversePins);
        return pins.equals(sortedPins) || pins.equals(reversePins);
    }

    /* if want to prevent any duplicate digit within a pin
    private static String filter(Integer digit) {
        if (digit > 9999 || digit < 0) return "";
        String str = String.format("%04d", digit);
        char[] cArr = str.toCharArray();
        int[] tmpDigits = new int[10];
        for (int i=0; i<3; i++) {
            int diff = cArr[i]-cArr[i+1];
            if (diff == 0 || diff == 1 || diff == -1) return "";
            int tmp = cArr[i]-'0';
            tmpDigits[tmp]++;
            if (tmpDigits[tmp] > 1) return "";
        }
        return str;
    }
    */
}
