package org.example;

import java.util.Scanner;

public class TestKamer extends Kamer {
    public TestKamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
    }

    @Override
    public boolean start() {
        return true; // dummy implementatie
    }
}
