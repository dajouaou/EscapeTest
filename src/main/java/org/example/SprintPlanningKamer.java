package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SprintPlanningKamer extends Kamer {
    private List<Integer> foutBeantwoordeVragen = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private VraagStrategie vraagStrategie;

    public SprintPlanningKamer(Speler speler) {
        super(speler);
        this.vraagStrategie = new SprintPlanningVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("Welkom bij de Sprintplanning Kamer!");
        System.out.println("Je zult leren hoe sprintplanning werkt door vraagjes te beantwoorden");
        System.out.println("Beantwoord de vragen juist om de sleutel naar de volgende kamer te innen");
        System.out.println("Een vergeten of fout antwoord? Een monster verschijnt.\n");

        foutBeantwoordeVragen.clear();
        List<Vraag> vragen = vraagStrategie.getVragen();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            vragenCorrect[i] = stelVraag(i + 1, vraag.getVraag(), vraag.getOpties(), vraag.getCorrectAntwoord());
        }

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
                        Vraag vraag = vragen.get(vraagNr - 1);
                        vragenCorrect[vraagNr - 1] = stelVraag(vraagNr, vraag.getVraag(), vraag.getOpties(), vraag.getCorrectAntwoord());
                    }
                    break;
                } else if (keuze.equals("nee")) {
                    return false;
                } else {
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
                        speler.notifyGameObservers("goed");
                        return true;
                    } else {
                        System.out.println("❌ Fout!");
                        speler.notifyGameObservers("fout");
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
