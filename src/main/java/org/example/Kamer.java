package org.example;

import java.util.Scanner;

public abstract class Kamer {
    protected final Speler speler;
    protected final Scanner scanner;
    protected HintProvider helpHintProvider;
    protected HintProvider funnyHintProvider;

    public Kamer(Speler speler, Scanner scanner) {
        this.speler = speler;
        this.scanner = scanner;

        // Haal standaard providers op
        this.helpHintProvider = getHelpHintProvider();
        this.funnyHintProvider = getFunnyHintProvider();
    }

    public void setHelpHintProvider(HintProvider helpHintProvider) {
        this.helpHintProvider = helpHintProvider;
    }

    public void setFunnyHintProvider(HintProvider funnyHintProvider) {
        this.funnyHintProvider = funnyHintProvider;
    }

    protected void toonHint() {
        System.out.println("💡 Denk goed na voordat je antwoordt! Typ 'ja' bij een hintvraag voor hulp.");
    }

    public final boolean speelKamer() {
        toonIntro();
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
            while (true) {
                System.out.print("Kies type hint - 'help' voor een uitleg, 'funny' voor iets luchtigs: ");
                String keuze = scanner.nextLine().trim().toLowerCase();
                if (keuze.equals("help")) {
                    System.out.println("🛠️ Hulp Hint: " + helpHintProvider.getHint());
                    break;
                } else if (keuze.equals("funny")) {
                    System.out.println("😂 Grappige Hint: " + funnyHintProvider.getHint());
                    break;
                } else {
                    System.out.println("Ongeldige keuze. Typ 'help' of 'funny'.");
                }
            }
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

    protected abstract HintProvider getHelpHintProvider();
    protected abstract HintProvider getFunnyHintProvider();
    public abstract boolean start();
}
