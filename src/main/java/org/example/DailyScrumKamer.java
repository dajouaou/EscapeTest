package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DailyScrumKamer extends Kamer {
    private final DatabaseManager dbManager;
    private final Speler speler;
    private final int kamerNummer = 4;
    private Scanner scanner;
    private VraagStrategie vraagStrategie;

    public DailyScrumKamer(Speler speler, DatabaseManager dbManager, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.dbManager = dbManager;
        this.scanner = scanner;
        this.vraagStrategie = new DailyScrumVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("Welkom bij de Daily Scrum kamer!");
        System.out.println("Je krijgt een lijst met teamleden en mogelijke status-updates.");
        System.out.println("Kies per teamlid de juiste status-update die zij het meest waarschijnlijk zouden geven.");
        System.out.println("Een vergeten of fout antwoord roept het monster 'Vertraging' op.\n");

        List<Vraag> vragen = vraagStrategie.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }

            char antwoordChar = vraagAntwoord();
            if (antwoordChar == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!\n");
            } else {
                System.out.println("❌ Fout!");
                monsterVerschijnt();
                foutBeantwoordeVragen.add(i);
            }
        }

        while (!foutBeantwoordeVragen.isEmpty()) {
            System.out.println("\n💀 Je hebt niet alle vragen correct beantwoord.");
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

            List<Integer> nogSteedsFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println(vraag.getVraag());
                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }

                char antwoordChar = vraagAntwoord();
                if (antwoordChar == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!\n");
                } else {
                    System.out.println("❌ Nog steeds fout.");
                    monsterVerschijnt();
                    nogSteedsFout.add(index);
                }
            }
            foutBeantwoordeVragen = nogSteedsFout;
        }

        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            dbManager.voegVoltooideKamerToe(gebruikersnaam, kamerNummer);
            System.out.println("🎉 Goed gedaan! Alle status-updates zijn correct gegeven.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }
        return true;
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

    private void monsterVerschijnt() {
        System.out.println("💀 Vertraging verschijnt! Het monster gromt: 'Tijdverspilling leidt tot vertraging!'\n");
    }
}