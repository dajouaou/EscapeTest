package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SprintRetrospectiveKamer extends Kamer {
    private final Speler speler;
    private final Scanner scanner;
    private VraagStrategie vraagStrategie;

    public SprintRetrospectiveKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
        this.vraagStrategie = new SprintRetrospectiveVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("💡 Welkom in de Sprint Retrospective kamer!");
        System.out.println("Je krijgt situaties die zich in een team voordoen en moet aangeven wat het team hiervan kan leren.\n");

        List<Vraag> vragen = vraagStrategie.getVragen();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }

            char antwoord = vraagAntwoord();
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!\n");
                vragenCorrect[i] = true;
                speler.notifyGameObservers("goed");
            } else {
                System.out.println("❌ Fout antwoord.\n");
                vragenCorrect[i] = false;
                speler.notifyGameObservers("fout");
            }
        }

        while (bevatFouteAntwoorden(vragenCorrect)) {
            System.out.println("Niet alle antwoorden waren correct.");
            boolean geldigAntwoord = false;

            while (!geldigAntwoord) {
                System.out.println("Wil je de fout beantwoorde vragen opnieuw proberen? (ja/nee)");
                String keuze = scanner.nextLine().trim().toLowerCase();

                if (keuze.equals("ja")) {
                    geldigAntwoord = true;
                } else if (keuze.equals("nee")) {
                    return false;
                } else {
                    System.out.println("Voer 'ja' of 'nee' in.");
                }
            }

            for (int i = 0; i < vragen.size(); i++) {
                if (!vragenCorrect[i]) {
                    Vraag vraag = vragen.get(i);
                    System.out.println(vraag.getVraag());
                    for (String optie : vraag.getOpties()) {
                        System.out.println(optie);
                    }

                    char antwoord = vraagAntwoord();
                    if (antwoord == vraag.getCorrectAntwoord()) {
                        System.out.println("✅ Correct!\n");
                        vragenCorrect[i] = true;
                        speler.notifyGameObservers("goed");
                    } else {
                        System.out.println("❌ Nog steeds fout.\n");
                        speler.notifyGameObservers("fout");
                    }
                }
            }
        }

        System.out.println("🎉 Goed gedaan! Je hebt de Sprint Retrospective succesvol afgerond.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.voegVoltooideKamerToe(gebruikersnaam, 5);
            dbManager.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }
        return true;
    }

    private boolean bevatFouteAntwoorden(boolean[] antwoorden) {
        for (boolean correct : antwoorden) {
            if (!correct) {
                return true;
            }
        }
        return false;
    }

    private char vraagAntwoord() {
        while (true) {
            System.out.print("Kies het juiste antwoord (A-D): ");
            String antwoord = scanner.nextLine().trim().toUpperCase();
            if (antwoord.length() == 1) {
                char c = antwoord.charAt(0);
                if (c >= 'A' && c <= 'D') {
                    return c;
                }
            }
            System.out.println("Ongeldige invoer. Voer A, B, C of D in.");
        }
    }
}
