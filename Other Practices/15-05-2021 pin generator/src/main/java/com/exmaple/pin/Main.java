package com.exmaple.pin;

import com.exmaple.pin.utils.PinGenerator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> pins = PinGenerator.generate();
        for (String pin : pins) {
            System.out.println(pin);
        }
    }
}
