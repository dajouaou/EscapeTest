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

    public Game(String gebruikersnaam, DatabaseManager dbManager, Scanner scanner) {
        this.speler = new Speler(gebruikersnaam);
        this.dbManager = dbManager;
        this.scanner = scanner;

        // slayyyy kamerobjecten toevoegen (kan vervangen worden door KamerFactory voor OCP)
        kamers.add(new SprintPlanningKamer(speler));
        kamers.add(new DailyScrumKamer(speler, dbManager, scanner));
        kamers.add(new ScrumBoardKamer(speler, scanner));
        kamers.add(new SprintReviewKamer(speler, scanner));
        kamers.add(new SprintRetrospectiveKamer(speler, scanner));
        kamers.add(new FinaleTiakamer(speler, scanner));
    }

    public void setCLI(CLI cli) {
        this.cli = cli;
    }

    public void toonStatus() {
        System.out.println("Huidige kamer: " + speler.getHuidigeKamer());
        System.out.println("Kamers gehaald: " + speler.getKamersGehaald());
        System.out.println(speler.heeftMonster() ? "Actieve belemmering: " + speler.getMonsterNaam() : "Geen actieve monsters.");
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

            if (nummer > 6) {
                System.out.println("Deze kamer bestaat niet. Delulu ahh🩷");
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
            kamer.setHintStrategy(new SimpeleHint());
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
        switch (nummer) {
            case 1: return "Sprint Planning";
            case 2: return "Daily Scrum";
            case 3: return "Scrum Board";
            case 4: return "Sprint Review";
            case 5: return "Sprint Retrospective";
            case 6: return "Finale TIA";
            default: return "Onbekende kamer";
        }
    }

    public Speler getSpeler() {
        return speler;
    }
}
