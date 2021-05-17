package com.exmaple.pin.utils;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PinGeneratorTest {

    @Test
    public void testNoDuplicatePin() {
        List<String> pins = PinGenerator.generate(500);
        assert hasDuplicate(pins) == false;
        assert pins.size() == 500;
    }

    @Test
    public void testAllValidPinList() {
        List<String> allValidPinListA = PinGenerator.generate();
        List<String> allValidPinListB = PinGenerator.generate();
        Collections.sort(allValidPinListA);
        Collections.sort(allValidPinListB);
        assert allValidPinListA.equals(allValidPinListB);
    }

    @Test
    public void testWithValidAndInvalidExample() {
        List<String> adjacentDuplicateDigitPins = Arrays.asList("5581", "6008", "1355");
        for (String adjacentDuplicateDigitPin : adjacentDuplicateDigitPins) {
            if (PinGenerator.isValid(adjacentDuplicateDigitPin)) assert false;
        }
        List<String> adjacentIncreasedByOneDigitPins = Arrays.asList("1234", "2457", "7934");
        for (String adjacentIncreasedByOneDigitPin : adjacentIncreasedByOneDigitPins) {
            if (PinGenerator.isValid(adjacentIncreasedByOneDigitPin)) assert false;
        }
        List<String> adjacentDecreasedByOneDigitPins = Arrays.asList("4321", "1042", "3598");
        for (String adjacentDecreasedByOneDigitPin : adjacentDecreasedByOneDigitPins) {
            if (PinGenerator.isValid(adjacentDecreasedByOneDigitPin)) assert false;
        }
        List<String> validDigitPins = Arrays.asList("1357", "7531", "8080", "2024");
        for (String validDigitPin : validDigitPins) {
            if (!PinGenerator.isValid(validDigitPin)) assert false;
        }
        // assert PinGenerator.isValid("2024") == false; // if want to prevent any duplicate digit within a pin
    }

    @Test
    public void testWithMultipleThreads() {
        List<List<String>> allValidPinLists = IntStream.range(0, 2).parallel().mapToObj(i -> { return PinGenerator.generate(); }).collect(Collectors.toList());
        assert hasDuplicate(allValidPinLists.get(0)) == false;
        assert hasDuplicate(allValidPinLists.get(1)) == false;
        Collections.sort(allValidPinLists.get(0));
        Collections.sort(allValidPinLists.get(1));
        assert allValidPinLists.get(0).equals(allValidPinLists.get(1));

        List<List<String>> pinLists = IntStream.range(0, 2).parallel().mapToObj(i -> { return PinGenerator.generate(300); }).collect(Collectors.toList());
        assert pinLists.get(0).size() == 300;
        assert pinLists.get(1).size() == 300;
        assert hasDuplicate(pinLists.get(0)) == false;
        assert hasDuplicate(pinLists.get(1)) == false;

        assert allValidPinLists.get(0).containsAll(pinLists.get(0));
        assert allValidPinLists.get(0).containsAll(pinLists.get(1));
    }

    public boolean hasDuplicate(List<String> pins) {
        Set<String> set = new HashSet<>();
        for (String pin : pins) { // convert list to set and then compare list length will make code lean, but may slower than this
            if (set.contains(pin)) {
                System.out.println("Duplicated PIN: " + pin);
                return true;
            }
            set.add(pin);
        }
        return false;
    }
}
