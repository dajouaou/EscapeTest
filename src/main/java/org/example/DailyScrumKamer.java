package org.example;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;

public class DailyScrumKamer extends Kamer {

    private final DatabaseManager dbManager;
    private final Speler speler;
    private final int kamerNummer = 4;

    private Scanner scanner;

    public DailyScrumKamer(Speler speler, DatabaseManager dbManager, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.dbManager = dbManager;
        this.scanner = scanner;
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint()); // hint yurrr
        toonHint();
        System.out.println("Welkom bij de Daily Scrum kamer!");
        System.out.println("Je krijgt een lijst met teamleden en mogelijke status-updates.");
        System.out.println("Kies per teamlid de juiste status-update die zij het meest waarschijnlijk zouden geven.");
        System.out.println("Een vergeten of fout antwoord roept het monster 'Vertraging' op.\n");

        String[] teamleden = {"Jan (Developer)", "Sarah (Product Owner)", "Mark (Scrum Master)"};
        String[][] mogelijkeUpdates = {
                {
                        "A) Ik heb gisteren aan ticket PROJ-123 gewerkt en vandaag ga ik PROJ-124 afronden",
                        "B) Ik heb de financiële rapporten gecontroleerd en ga vandaag met klanten bellen",
                        "C) Ik heb de serverkast schoongemaakt en ga vandaag koffie halen",
                        "D) Ik heb de marketingstrategie bepaald en ga vandaag advertenties plaatsen"
                },
                {
                        "A) Informatie geven over prioriteiten of klantfeedback",
                        "B) Gedetailleerde technische oplossingen voorstellen",
                        "C) Ieders werktijden controleren",
                        "D) De sprintplanning volledig herzien"
                },
                {
                        "A) Nauwkeurige notulen maken van alles wat gezegd wordt",
                        "B) Zorgen dat het team het event binnen de timebox houdt en zich aan de focus houdt",
                        "C) Technische oplossingen aandragen voor problemen",
                        "D) Individuele teamleden aanspreken op hun prestaties"
                }
        };

        char[] juisteAntwoorden = {'A', 'A', 'B'};
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();

       // eerst alle vragen stellen periodt
        for (int i = 0; i < teamleden.length; i++) {
            System.out.println("Status-update voor: " + teamleden[i]);
            for (String optie : mogelijkeUpdates[i]) {
                System.out.println(optie);
            }

            char antwoordChar = vraagAntwoord();

            if (antwoordChar == juisteAntwoorden[i]) {
                System.out.println("✅ Correct!\n");
            } else {
                System.out.println("❌ Fout!");
                monsterVerschijnt();
                foutBeantwoordeVragen.add(i);
            }
        }

        // alleen fout beantwoorde vraagjes worden opnieuw gesteld dan kun je doorgaan yayy!!
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
                System.out.println("Status-update voor: " + teamleden[index]);
                for (String optie : mogelijkeUpdates[index]) {
                    System.out.println(optie);
                }

                char antwoordChar = vraagAntwoord();

                if (antwoordChar == juisteAntwoorden[index]) {
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
