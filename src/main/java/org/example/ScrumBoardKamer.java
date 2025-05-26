package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrumBoardKamer extends Kamer {

    private final Speler speler;
    private final Scanner scanner;

    public ScrumBoardKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("\uD83E\uDDF0 Welkom in de Scrum Board kamer!");
        System.out.println("Je krijgt een opdracht om een bord correct in te richten met taken, user stories en epics.");
        System.out.println("Geef per item aan of het een Epic, User Story of Taak is.\n");

        String[] vragen = {
                "1. Gebruiker moet kunnen inloggen met twee-factor authenticatie",
                "2. Inlogfunctionaliteit ontwikkelen",
                "3. Beveiliging en authenticatie verbeteren in hele app",
                "4. Wachtwoord vergeten-functionaliteit bouwen",
                "5. Knop ‘Wachtwoord resetten’ toevoegen aan inlogscherm",
                "6. Nieuwe gebruikersinterface voor onboarding",
                "7. Als gebruiker wil ik hulpteksten zien bij velden tijdens onboarding",
                "8. Hulptekstcomponent toevoegen in front-end",
                "9. Checkout-proces optimaliseren voor mobiel",
                "10. Als gebruiker wil ik een mobielvriendelijke betaalknop"
        };

        String[][] opties = new String[vragen.length][3];
        for (int i = 0; i < vragen.length; i++) {
            opties[i][0] = "A) Epic";
            opties[i][1] = "B) User Story";
            opties[i][2] = "C) Taak";
        }

        char[] juisteAntwoorden = {'B', 'C', 'A', 'B', 'C', 'A', 'B', 'C', 'A', 'B'};
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();

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
