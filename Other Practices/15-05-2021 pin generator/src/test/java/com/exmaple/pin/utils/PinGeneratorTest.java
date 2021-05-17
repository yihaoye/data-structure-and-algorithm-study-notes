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
        assert PinGenerator.isValid("1234") == false;
        assert PinGenerator.isValid("4321") == false;
        assert PinGenerator.isValid("1042") == false;
        assert PinGenerator.isValid("5581") == false;
        assert PinGenerator.isValid("1357") == true;
        assert PinGenerator.isValid("7531") == true;
        assert PinGenerator.isValid("8080") == true;
        assert PinGenerator.isValid("2024") == true;
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
