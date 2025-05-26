package org.example;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SprintPlanningKamer extends Kamer {
    private List<Integer> foutBeantwoordeVragen = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in); // slay one scanner voor consistentie yurrr

    public SprintPlanningKamer(Speler speler) {
        super(speler);
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint()); // strategy Pattern + DIP (we cookin girlss)
        toonHint();                         // shows hint in de terminal purr
        System.out.println("Welkom bij de Sprintplanning Kamer!");
        System.out.println("Je zult leren hoe sprintplanning werkt door vraagjes te bantwoorden🤔");
        System.out.println("Beantwoord de vragen juist om de sleutel naar de volgende kamer te innen🔑 ");
        System.out.println("Een vergeten of fout antwoord? Een monster verschijnt👺.\n");

        foutBeantwoordeVragen.clear();
        boolean[] vragenCorrect = new boolean[5];

        vragenCorrect[0] = stelVraag(1, "Het team heeft een velocity van 20 punten. Welke set taken past het BESTE in deze sprint?",
                new String[]{
                        "A) Taak A (8 punten), Taak B (5 punten), Taak C (10 punten)",
                        "B) Taak A (8 punten), Taak B (5 punten), Taak C (3 punten), Taak D (5 punten)",
                        "C) Taak A (13 punten), Taak B (12 punten)",
                        "D) Taak A (5 punten), Taak B (5 punten), Taak C (5 punten), Taak D (5 punten)"
                }, 'D');

        vragenCorrect[1] = stelVraag(2, "Welke van deze taken zou je NIET meenemen in een sprint als je het scrum-monster wilt voorkomen?",
                new String[]{
                        "A) Een kleine bugfix voor een bestaande feature",
                        "B) Een nieuwe feature zonder duidelijke acceptatiecriteria",
                        "C) Een technische schuld item uit de backlog",
                        "D) Een kleine UI-aanpassing gevraagd door de PO"
                }, 'B');

        vragenCorrect[2] = stelVraag(3, "Het team heeft 5 dagen om een taak van 8 story points af te ronden. Wat is de beste actie?",
                new String[]{
                        "A) De taak opsplitsen in kleinere delen",
                        "B) Extra teamleden toevoegen aan de taak",
                        "C) De deadline verlengen",
                        "D) De punten aanpassen naar 5 om het te laten passen"
                }, 'A');

        vragenCorrect[3] = stelVraag(4, "Wat is het belangrijkste criterium voor het selecteren van taken tijdens sprint planning?",
                new String[]{
                        "A) Wat het management het meest wil zien",
                        "B) Wat past binnen de teamcapaciteit en bijdraagt aan het sprintdoel",
                        "C) Wat de meeste story points oplevert",
                        "D) Wat het snelst afgerond kan worden"
                }, 'B');

        vragenCorrect[4] = stelVraag(5, "Een stakeholder vraagt om een extra feature tijdens de sprint. Wat doe je?",
                new String[]{
                        "A) Onmiddellijk toevoegen om de stakeholder tevreden te stellen",
                        "B) Toevoegen en een andere taak verwijderen",
                        "C) Uitleggen dat nieuwe items eerst geëvalueerd moeten worden",
                        "D) Beloven dat het aan het einde wordt toegevoegd als er tijd is"
                }, 'C');

        boolean alleVragenCorrect = true;
        for (boolean correct : vragenCorrect) {
            if (!correct) {
                alleVragenCorrect = false;
                break;
            }
        }


        if (!alleVragenCorrect) {
            System.out.println("\n💀 Je hebt niet alle vragen correct beantwoord. Scope Creep dreigt!");

            while (true) {
                System.out.println("Wil je de fout beantwoorde vragen opnieuw proberen? (ja/nee)");
                String keuze = scanner.nextLine().trim().toLowerCase();

                if (keuze.equals("ja")) {
                    for (int vraagNr : foutBeantwoordeVragen) {
                        switch(vraagNr) {
                            case 1:
                                vragenCorrect[0] = stelVraag(1, "Het team heeft een velocity van 20 punten. Welke set taken past het BESTE in deze sprint?",
                                        new String[]{
                                                "A) Taak A (8 punten), Taak B (5 punten), Taak C (10 punten)",
                                                "B) Taak A (8 punten), Taak B (5 punten), Taak C (3 punten), Taak D (5 punten)",
                                                "C) Taak A (13 punten), Taak B (12 punten)",
                                                "D) Taak A (5 punten), Taak B (5 punten), Taak C (5 punten), Taak D (5 punten)"
                                        }, 'D');
                                break;
                            case 2:
                                vragenCorrect[1] = stelVraag(2, "Welke van deze taken zou je NIET meenemen in een sprint als je scope creep wilt voorkomen?",
                                        new String[]{
                                                "A) Een kleine bugfix voor een bestaande feature",
                                                "B) Een nieuwe feature zonder duidelijke acceptatiecriteria",
                                                "C) Een technische schuld item uit de backlog",
                                                "D) Een kleine UI-aanpassing gevraagd door de PO"
                                        }, 'B');
                                break;
                            case 3:
                                vragenCorrect[2] = stelVraag(3, "Het team heeft 5 dagen om een taak van 8 story points af te ronden. Wat is de beste actie?",
                                        new String[]{
                                                "A) De taak opsplitsen in kleinere delen",
                                                "B) Extra teamleden toevoegen aan de taak",
                                                "C) De deadline verlengen",
                                                "D) De punten aanpassen naar 5 om het te laten passen"
                                        }, 'A');
                                break;
                            case 4:
                                vragenCorrect[3] = stelVraag(4, "Wat is het belangrijkste criterium voor het selecteren van taken tijdens sprint planning?",
                                        new String[]{
                                                "A) Wat het management het meest wil zien",
                                                "B) Wat past binnen de teamcapaciteit en bijdraagt aan het sprintdoel",
                                                "C) Wat de meeste story points oplevert",
                                                "D) Wat het snelst afgerond kan worden"
                                        }, 'B');
                                break;
                            case 5:
                                vragenCorrect[4] = stelVraag(5, "Een stakeholder vraagt om een extra feature tijdens de sprint. Wat doe je?",
                                        new String[]{
                                                "A) Onmiddellijk toevoegen om de stakeholder tevreden te stellen",
                                                "B) Toevoegen en een andere taak verwijderen",
                                                "C) Uitleggen dat nieuwe items eerst geëvalueerd moeten worden",
                                                "D) Beloven dat het aan het einde wordt toegevoegd als er tijd is"
                                        }, 'C');
                                break;
                        }
                    }
                    break;
                }
                else if (keuze.equals("nee")) {
                    return false;
                }
                else {
                    System.out.println("Voer 'ja' of 'nee' in.");
                }
            }

            for (boolean correct : vragenCorrect) {
                if (!correct) {
                    return false;
                }
            }
        }
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.voegVoltooideKamerToe(gebruikersnaam, 1);
            dbManager.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }

        return true;
    }

    private boolean stelVraag(int vraagNr, String vraag, String[] opties, char juisteAntwoord) {
        System.out.println("\nVraag " + vraagNr + ": " + vraag);
        for (String optie : opties) {
            System.out.println(optie);
        }

        while (true) {
            System.out.println();
            System.out.print("Antwoord (A-D): ");
            String antwoord = scanner.nextLine().trim().toUpperCase();

            if (antwoord.length() == 1) {
                char antwoordChar = antwoord.charAt(0);
                if (antwoordChar >= 'A' && antwoordChar <= 'D') {
                    if (antwoordChar == juisteAntwoord) {
                        System.out.println("✅ Correct!");
                        return true;
                    } else {
                        System.out.println("❌ Fout!");
                        monsterVerschijnt();
                        if (!foutBeantwoordeVragen.contains(vraagNr)) {
                            foutBeantwoordeVragen.add(vraagNr);
                        }
                        return false;
                    }
                }
            }
            System.out.println("Ongeldige invoer. Voer A, B, C of D in.");
        }
    }

    private void monsterVerschijnt() {
        System.out.println("\n💀 Scope Creep verschijnt! Het monster gromt: 'Meer taken! Meer features! Meer chaos!'");
        System.out.println("Pas op! Onrealistische planning leidt tot Scope Creep!\n");
    }
}
