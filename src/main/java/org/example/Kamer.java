package org.example;

import java.util.Scanner;

public abstract class Kamer {
    protected Speler speler;
    protected HintStrategy hintStrategy = new GeenHint();

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
        boolean geslaagd = start();
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

    protected char vraagAntwoord(Scanner scanner, int maxOpties) {
        while (true) {
            System.out.print("Kies het juiste antwoord (A-" + (char)('A' + maxOpties - 1) + "): ");
            String antwoord = scanner.nextLine().trim().toUpperCase();
            if (antwoord.length() == 1) {
                char c = antwoord.charAt(0);
                if (c >= 'A' && c < ('A' + maxOpties)) {
                    return c;
                }
            }
            System.out.println("Ongeldige invoer. Probeer opnieuw.");
        }
    }

    protected boolean wilOpnieuwProberen(Scanner scanner) {
        while (true) {
            System.out.println("Wil je de fout beantwoorde vragen opnieuw proberen? (ja/nee)");
            String keuze = scanner.nextLine().trim().toLowerCase();
            if (keuze.equals("ja")) return true;
            if (keuze.equals("nee")) return false;
            System.out.println("Voer 'ja' of 'nee' in.");
        }
    }

    public abstract boolean start();
}
