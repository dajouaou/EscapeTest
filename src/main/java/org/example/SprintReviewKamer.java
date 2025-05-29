package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SprintReviewKamer extends Kamer {
    private final Speler speler;
    private final Scanner scanner;
    private VraagStrategie vraagStrategie;

    public SprintReviewKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
        this.vraagStrategie = new SprintReviewVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("📋 Welkom in de Sprint Review kamer!");
        System.out.println("Je moet feedback van stakeholders interpreteren en de impact ervan inschatten.\n");

        boolean alleAntwoordenCorrect;
        List<Integer> foutBeantwoordeVragen;
        List<Vraag> vragen = vraagStrategie.getVragen();

        do {
            alleAntwoordenCorrect = true;
            foutBeantwoordeVragen = new ArrayList<>();

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
