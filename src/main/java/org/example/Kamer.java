
package org.example;

import java.util.Scanner;
import org.example.Game;
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
            Game.kiesJokerVoorSpeler(speler, scanner);
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

    protected boolean vraagHintNaFout() {
        System.out.print("Wil je een hint? (ja/nee): ");
        String antwoord = scanner.nextLine().trim().toLowerCase();
        if (antwoord.equals("ja")) {
            System.out.println("💡 Hint: " + hintProvider.getHint());
            return true;
        }
        return false;
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
