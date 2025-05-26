package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SprintReviewKamer extends Kamer {

    private final Speler speler;
    private final Scanner scanner;

    public SprintReviewKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint()); // hint instellen
        toonHint();                         // hint tonen
        System.out.println("\uD83D\uDCCB Welkom in de Sprint Review kamer!");
        System.out.println("Je moet feedback van stakeholders interpreteren en de impact ervan inschatten.\n");

        boolean alleAntwoordenCorrect;
        List<Integer> foutBeantwoordeVragen;

        String[] vragen = {
                "1. Wat is het hoofddoel van de Sprint Review?",
                "2. Wat gebeurt er vaak als er tijdens de Sprint Review feedback wordt gegeven?",
                "3. Wie nemen er typisch deel aan een Sprint Review?",
                "4. Wat is een mogelijk resultaat van een Sprint Review?",
                "5. Wanneer wordt de Sprint Review gehouden?",
                "6. Hoe beïnvloedt feedback de Product Backlog?",
                "7. Welke houding wordt verwacht van het team tijdens een Sprint Review?"
        };

        String[][] opties = {
                {"A) Sprint afsluiten", "B) Documentatie controleren", "C) Feedback verzamelen over het geleverde werk", "D) Nieuwe sprint plannen"},
                {"A) Het team wordt vervangen", "B) De Product Backlog wordt bijgewerkt", "C) Er worden nieuwe KPI’s opgesteld", "D) De sprint wordt ongeldig verklaard"},
                {"A) Alleen het ontwikkelteam", "B) Alleen de Scrum Master", "C) Het team en stakeholders", "D) Alleen de Product Owner"},
                {"A) Sprintplanning annuleren", "B) Vastleggen van nieuwe sprints", "C) Nieuwe items in de Product Backlog", "D) Scrum afschaffen"},
                {"A) Voor de Sprint Planning", "B) Aan het einde van de sprint", "C) Tijdens de Daily Scrum", "D) Voor de retrospective"},
                {"A) Er wordt niets mee gedaan", "B) Alleen als de Scrum Master het goedkeurt", "C) Kan leiden tot wijzigingen in prioriteit of inhoud", "D) Wordt genegeerd tenzij het een bug betreft"},
                {"A) Defensief en gesloten", "B) Open en samenwerkend", "C) Stil en observerend", "D) Kritisch en beoordelend"}
        };

        char[] juisteAntwoorden = {'C', 'B', 'C', 'C', 'B', 'C', 'B'};

        do {
            alleAntwoordenCorrect = true;
            foutBeantwoordeVragen = new ArrayList<>();

            for (int i = 0; i < vragen.length; i++) {
                System.out.println(vragen[i]);
                for (String optie : opties[i]) {
                    System.out.println(optie);
                }

                char antwoord = vraagAntwoord();

                if (antwoord == juisteAntwoorden[i]) {
                    System.out.println("✅ Correct!\n");
                } else {
                    System.out.println("❌ Fout antwoord.\n");
                    foutBeantwoordeVragen.add(i);
                    alleAntwoordenCorrect = false;
                }
            }

            if (!alleAntwoordenCorrect) {
                boolean geldigAntwoord = false;
                while (!geldigAntwoord) {
                    System.out.println("Wil je de fout beantwoorde vragen opnieuw proberen? (ja/nee)");
                    String keuze = scanner.nextLine().trim().toLowerCase();
                    if (keuze.equals("ja")) {
                        geldigAntwoord = true;

                        List<Integer> nogFout = new ArrayList<>();
                        for (int index : foutBeantwoordeVragen) {
                            System.out.println(vragen[index]);
                            for (String optie : opties[index]) {
                                System.out.println(optie);
                            }

                            char antwoord = vraagAntwoord();

                            if (antwoord == juisteAntwoorden[index]) {
                                System.out.println("✅ Correct!\n");
                            } else {
                                System.out.println("❌ Nog steeds fout.\n");
                                nogFout.add(index);
                            }
                        }

                        foutBeantwoordeVragen = nogFout;
                        alleAntwoordenCorrect = foutBeantwoordeVragen.isEmpty();

                    } else if (keuze.equals("nee")) {
                        return false;
                    } else {
                        System.out.println("Voer 'ja' of 'nee' in.");
                    }
                }
            }

        } while (!alleAntwoordenCorrect);

        System.out.println("🎉 Goed gedaan! Je hebt de Sprint Review succesvol doorlopen.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.voegVoltooideKamerToe(gebruikersnaam, 4);
            dbManager.updateSpelerStatus(gebruikersnaam, "");
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
}
