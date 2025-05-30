package org.example;

import java.util.Scanner;

public interface Voorwerp {
}

class VoorwerpenKamer extends Kamer {
    private final Oppakbaar sleutel;
    private final InteractiefVoorwerp puzzel;
    private final Scanner scanner;

    public VoorwerpenKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.scanner = scanner;
        this.sleutel = new Sleutel("Escape Sleutel");
        this.puzzel = new Puzzel("Wat is de derde Scrum waarde na Focus en Openheid?", "Respect");
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();

        System.out.println("🧩 Je komt een raadsel tegen op een bord...");
        puzzel.startInteractie(scanner);

        System.out.println("🔒 Na het oplossen van de puzzel, vind je een sleutel op de grond.");
        sleutel.pakOp(speler);

        return true;
    }
}

