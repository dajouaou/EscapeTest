package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Kamer> kamers = new ArrayList<>();
    private Speler speler;
    private CLI cli;
    private DatabaseManager dbManager;
    private Scanner scanner;
    private TimerPopup timer;

    public Game(String gebruikersnaam, DatabaseManager dbManager, Scanner scanner) {
        this.speler = new Speler(gebruikersnaam);
        this.dbManager = dbManager;
        this.scanner = scanner;

        // ➤ Joker keuze toevoegen bij aanvang
        kiesJokerVoorSpeler();

        // Voeg monsters toe als observers
        speler.addGameObserver(new Monster(new ScopeCreep(), "Sprint Planning", speler));
        speler.addGameObserver(new Monster(new Vertraging(), "Daily Scrum", speler));
        speler.addGameObserver(new Monster(new TechnicalDebt(), "Scrum Board", speler));
        speler.addGameObserver(new Monster(new Miscommunicatie(), "Sprint Review", speler));
        speler.addGameObserver(new Monster(new BlindeVlek(), "Sprint Retrospective", speler));
        speler.addGameObserver(new Monster(new TiaMonster(), "Finale TIA Kamer", speler));
        speler.addGameObserver(new Deur());

        // Kamers aanmaken
        for (int i = 1; i <= 7; i++) {
            kamers.add(KamerFactory.maakKamer(i, speler, scanner));
        }
    }

    private void kiesJokerVoorSpeler() {
        System.out.println("\n🃏 Kies één joker die je tijdens het spel mag inzetten:");
        System.out.println("1. HintJoker – Geeft een hint in elke kamer.");
        System.out.println("2. KeyJoker – Geeft een extra sleutel, maar alleen in Daily Scrum en Review.");

        while (true) {
            System.out.print("Voer '1' of '2' in: ");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) {
                speler.kiesJoker(new HintJoker());
                System.out.println("✅ Je hebt gekozen voor de HintJoker.");
                break;
            } else if (input.equals("2")) {
                speler.kiesJoker(new ReviewKeyJoker());
                System.out.println("✅ Je hebt gekozen voor de KeyJoker.");
                break;
            } else {
                System.out.println("Ongeldige invoer. Kies '1' of '2'.");
            }
        }
    }

    public void setCLI(CLI cli) {
        this.cli = cli;
    }

    public void toonStatus() {
        System.out.println("Huidige kamer: " + speler.getHuidigeKamer());
        System.out.println("Kamers gehaald: " + speler.getKamersGehaald());
        System.out.println(speler.heeftMonster() ? "Actieve belemmering: " + speler.getMonsterNaam() : "Geen actieve monsters.");
        System.out.println("Joker: " + (speler.getJoker() != null ? speler.getJoker().getClass().getSimpleName() : "Geen gekozen"));
        Integer laatsteVraag = speler.getLaatsteVraagVoorKamer(speler.getHuidigeKamer());
        if (laatsteVraag != null) {
            System.out.println("Laatste beantwoorde vraag in kamer " + speler.getHuidigeKamer() + ": Vraag " + laatsteVraag);
        } else {
            System.out.println("Je bent net begonnen met kamer " + speler.getHuidigeKamer());
        }
    }

    public void betreedKamer(int nummer) {
        try {
            int huidigeKamer = dbManager.getHuidigeKamer(speler.getGebruikersnaam());

            if (nummer > 7) {
                System.out.println("Deze kamer bestaat niet.");
                return;
            }

            if (nummer > huidigeKamer + 1) {
                System.out.println("🚫 Je kunt kamer " + nummer + " niet betreden voordat je kamer " +
                        (nummer - 1) + " hebt voltooid.");
                return;
            }

            speler.setHuidigeKamer(nummer);
            dbManager.updateHuidigeKamer(speler.getGebruikersnaam(), nummer);

            Kamer kamer = kamers.get(nummer - 1);

            // ❌ Automatische jokeractivatie is verwijderd zodat kamer zelf de vraag stelt

            boolean geslaagd = kamer.speelKamer();

            if (geslaagd) {
                speler.voegKamerToe(nummer);
                System.out.println("✅ Kamer " + nummer + " succesvol voltooid!");
                dbManager.voegVoltooideKamerToe(speler.getGebruikersnaam(), nummer);
                dbManager.updateSpelerStatus(speler.getGebruikersnaam(),
                        "Kamer " + nummer + " voltooid - " + getKamerNaam(nummer));
            } else {
                System.out.println("❌ Niet alle vragen waren correct. Probeer deze kamer opnieuw.");
            }
        } catch (SQLException e) {
            System.out.println("Databasefout: " + e.getMessage());
        }
    }

    private String getKamerNaam(int nummer) {
        return switch (nummer) {
            case 1 -> "Sprint Planning";
            case 2 -> "Daily Scrum";
            case 3 -> "Scrum Board";
            case 4 -> "Sprint Review";
            case 5 -> "Sprint Retrospective";
            case 6 -> "Voorwerpenkamer";
            case 7 -> "Finale TIA";
            default -> "Onbekende kamer";
        };
    }

    public Speler getSpeler() {
        return speler;
    }
}
