package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrumBoardKamer extends Kamer {
    private final Speler speler;
    private final Scanner scanner;
    private VraagStrategie vraagStrategie;

    public ScrumBoardKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
        this.vraagStrategie = new ScrumBoardVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("🧠 Welkom in de Scrum Board kamer!");
        System.out.println("Je krijgt een opdracht om een bord correct in te richten met taken, user stories en epics.");
        System.out.println("Geef per item aan of het een Epic, User Story of Taak is.\n");

        List<Vraag> vragen = vraagStrategie.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }

            char antwoord = vraagAntwoord();
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!\n");
                speler.notifyGameObservers("goed");
            } else {
                System.out.println("❌ Fout antwoord.\n");
                speler.notifyGameObservers("fout");
                foutBeantwoordeVragen.add(i);
            }
        }

        while (!foutBeantwoordeVragen.isEmpty()) {
            System.out.println("Je hebt enkele vragen fout beantwoord.");
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

            List<Integer> nogFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println(vraag.getVraag());
                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }

                char antwoord = vraagAntwoord();
                if (antwoord == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!\n");
                    speler.notifyGameObservers("goed");
                } else {
                    System.out.println("❌ Nog steeds fout.\n");
                    speler.notifyGameObservers("fout");
                    nogFout.add(index);
                }
            }
            foutBeantwoordeVragen = nogFout;
        }

        System.out.println("🎉 Goed gedaan! Je hebt het Scrum Board succesvol ingericht.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.voegVoltooideKamerToe(gebruikersnaam, 3);
            dbManager.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }
        return true;
    }

    private char vraagAntwoord() {
        while (true) {
            System.out.print("Kies het juiste antwoord (A-C): ");
            String antwoord = scanner.nextLine().trim().toUpperCase();
            if (antwoord.length() == 1) {
                char c = antwoord.charAt(0);
                if (c >= 'A' && c <= 'C') {
                    return c;
                }
            }
            System.out.println("Ongeldige invoer. Voer A, B of C in.");
        }
    }
}
