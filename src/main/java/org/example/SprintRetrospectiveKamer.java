package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SprintRetrospectiveKamer extends Kamer {

    private final Speler speler;
    private final Scanner scanner;

    public SprintRetrospectiveKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("\uD83D\uDCA1 Welkom in de Sprint Retrospective kamer!");
        System.out.println("Je krijgt situaties die zich in een team voordoen en moet aangeven wat het team hiervan kan leren.\n");

        String[] vragen = {
                "1. Wat is het hoofddoel van een Sprint Retrospective?",
                "2. Wie nemen er deel aan de Sprint Retrospective?",
                "3. Een teamlid voelde zich niet gehoord tijdens de sprint. Wat kan het team hiervan leren?",
                "4. De teamcommunicatie liep regelmatig vast. Wat is een mogelijke les?",
                "5. Tijdens de sprint waren er veel onderbrekingen. Wat is een goede aanpak?",
                "6. Wat gebeurt er met de inzichten uit een Sprint Retrospective?",
                "7. Hoe vaak vindt een Sprint Retrospective plaats?"
        };

        String[][] opties = {
                {"A) Problemen oplossen van vorige sprint", "B) Samen reflecteren en verbeteren als team", "C) Nieuwe stories maken", "D) Plannen van de volgende sprint"},
                {"A) Alleen de Scrum Master", "B) Alle teamleden inclusief de Scrum Master", "C) Alleen de Product Owner", "D) Alle stakeholders"},
                {"A) Er moet een aparte vergadering komen voor feedback", "B) De Scrum Master moet strenger worden", "C) Betere luisterafspraken maken en inclusiviteit bespreken", "D) Diegene moet minder kritisch zijn"},
                {"A) Meer documenten maken", "B) Slack verbieden", "C) Teamafspraken over communicatie verbeteren", "D) Dagelijks rapporteren"},
                {"A) Alles accepteren zoals het is", "B) Werk onderbreken zodra iets binnenkomt", "C) Onderbrekingen registreren en analyseren", "D) Iedereen extra werk geven"},
                {"A) Worden besproken maar niet vastgelegd", "B) Leidt tot concrete verbeteracties voor de volgende sprint", "C) Worden direct doorgegeven aan stakeholders", "D) Worden geëvalueerd in de volgende review"},
                {"A) Alleen aan het einde van het project", "B) Na elke sprint", "C) Een keer per maand", "D) Elke week"}
        };

        char[] juisteAntwoorden = {'B', 'B', 'C', 'C', 'C', 'B', 'B'};

        boolean[] vragenCorrect = new boolean[vragen.length];


        for (int i = 0; i < vragen.length; i++) {
            System.out.println(vragen[i]);
            for (String optie : opties[i]) {
                System.out.println(optie);
            }

            char antwoord = vraagAntwoord();

            if (antwoord == juisteAntwoorden[i]) {
                System.out.println("✅ Correct!\n");
                vragenCorrect[i] = true;
            } else {
                System.out.println("❌ Fout antwoord.\n");
                vragenCorrect[i] = false;
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

            for (int i = 0; i < vragen.length; i++) {
                if (!vragenCorrect[i]) {
                    System.out.println(vragen[i]);
                    for (String optie : opties[i]) {
                        System.out.println(optie);
                    }

                    char antwoord = vraagAntwoord();

                    if (antwoord == juisteAntwoorden[i]) {
                        System.out.println("✅ Correct!\n");
                        vragenCorrect[i] = true;
                    } else {
                        System.out.println("❌ Nog steeds fout.\n");
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
