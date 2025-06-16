package org.example;

import java.util.Scanner;
import java.util.Collections;
import java.util.List;

public abstract class Kamer {
    protected final Speler speler;
    protected final Scanner scanner;
    protected HintProvider hintProvider;

    public Kamer(Speler speler, Scanner scanner) {
        this.speler = speler;
        this.scanner = scanner;
        this.hintProvider = null;
    }

    public HintProvider getHintProvider() {
        return this.hintProvider;
    }

    public void setHintProvider(HintProvider hintProvider) {
        this.hintProvider = hintProvider;
    }

    public Speler getSpeler() {
        return speler;
    }

    // Default: geen vragen. Override in kamers met vragen!
    public List<Vraag> getVragen() {
        return Collections.emptyList();
    }

    protected void toonHint() {
        System.out.println("💡 Denk goed na voordat je antwoordt! Typ 'ja' bij een hintvraag voor hulp.");
    }

    public void accept(KeyJoker joker) {
        // standaardkamers doen niets
    }

    public final boolean speelKamer() {
        toonIntro();

        // 🔐 Joker pas hier vragen na uitleg
        if (speler.getJoker() == null) {
            int kamerNummer = speler.getHuidigeKamer();
            if (kamerNummer == 2 || kamerNummer == 4) {
                Game.kiesJokerVoorSpeler(speler, scanner);
            } else {
                System.out.println("\n🃏 Je mag alleen de HintJoker kiezen in deze kamer.");
                System.out.print("Wil je de HintJoker kiezen? (ja/nee): ");
                String antwoord = scanner.nextLine().trim().toLowerCase();
                if (antwoord.equals("ja")) {
                    speler.kiesJoker(new HintJoker());
                    System.out.println("✅ Je hebt gekozen voor de HintJoker.");
                } else {
                    System.out.println("Je hebt niet voor de HintJoker gekozen.");
                }
            }
        }

        boolean geslaagd = start();

        if (geslaagd) {
            verwerkSucces();
        } else {
            verwerkFalen();
        }

        return geslaagd;
    }

    protected void toonIntro() {
        System.out.println("\n📍 Je betreedt een kamer...");
        //  Kamerinfo tonen (ISP)
        Kamerinfo info = new Kamerinfo();
        info.showMessage();

        // Voeg kans toe om zwaard op te rapen als speler het nog niet heeft
        if (!speler.heeftZwaard()) {
            System.out.println("⚔️ Je ziet een oud zwaard aan de muur hangen.");
            System.out.print("Wil je het zwaard oppakken? (ja/nee): ");
            String keuze = scanner.nextLine().trim().toLowerCase();
            if (keuze.equals("ja")) {
                speler.geefZwaard();
            } else {
                System.out.println("Je laat het zwaard hangen en loopt verder.");
            }
        }
    }

    protected void verwerkSucces() {
        System.out.println("✅ Je hebt de kamer gehaald!");
    }

    protected void verwerkFalen() {
        System.out.println("❌ Niet gelukt. Probeer opnieuw.");
    }

    protected int hintTeller = 0;
    private final int MAX_HINTS = 3;

    protected boolean vraagHintNaFout() {
        if (hintProvider == null) return false;

        if (hintTeller >= MAX_HINTS) {
            System.out.println("🚫 Je hebt het maximum aantal hints bereikt.");
            return false;
        }

        System.out.print("Wil je een hint? (ja/nee): ");
        String antwoord = scanner.nextLine().trim().toLowerCase();

        switch (antwoord) {
            case "ja":
                hintTeller++;
                System.out.println("💡 Hint: " + hintProvider.getHint());
                return true;
            case "nee":
                return false;
            default:
                System.out.println("❌ Ongeldige invoer. Typ 'ja' of 'nee'.");
                return vraagHintNaFout(); // Herhaal
        }
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
            System.out.print("Wil je de fout beantwoorde vragen opnieuw proberen? (ja/nee): ");
            String keuze = scanner.nextLine().trim().toLowerCase();
            if (keuze.equals("ja")) return true;
            if (keuze.equals("nee")) return false;
            System.out.println("Voer 'ja' of 'nee' in.");
        }
    }

    public abstract boolean start();
}
