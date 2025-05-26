package org.example;

import java.util.Scanner;

public abstract class Kamer {
    protected Speler speler;
    protected HintStrategy hintStrategy = new GeenHint(); // DIP

    public Kamer(Speler speler) {
        this.speler = speler;
    }

    public void setHintStrategy(HintStrategy hintStrategy) {
        this.hintStrategy = hintStrategy;
    }

    public void toonHint() {
        System.out.println("💡 Hint: " + hintStrategy.geefHint());
    }

    public final boolean speelKamer() {
        toonIntro();
        boolean geslaagd = start(); // laat kamer zijn eigen logica uitvoeren slay yuhh<3
        if (geslaagd) verwerkSucces();
        else verwerkFalen();
        return geslaagd;
    }

    protected void toonIntro() {
        System.out.println("📍 Je betreedt een kamer...");
    }

    protected void verwerkSucces() {
        System.out.println("✅ Je hebt de kamer gehaald!");
    }

    protected void verwerkFalen() {
        System.out.println("❌ Niet gelukt. Probeer opnieuw.");
    }

    protected boolean stelVraag(String vraag, String[] opties, char correctAntwoord) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + vraag);
        for (int i = 0; i < opties.length; i++) {
            System.out.println((char)('A' + i) + ". " + opties[i]);
        }
        System.out.print("Jouw antwoord (A/B/C/D): ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.length() != 1) return false;
        char antwoord = input.charAt(0);
        return antwoord == correctAntwoord;
    }

    public abstract boolean start(); // wordt opgeroepen in speelKamer()
}
