package org.example;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class CLI {
    private Game game;
    private TimerPopup timerPopup;
    private Scanner scanner;

    public CLI(Game game, Scanner scanner) {
        this.game = game;
        this.scanner = scanner;  // main scanner gebruiken ipv iedere keer nieuwe scanner yurr
    }

    public void leesCommando() {
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                System.out.println("Geen invoer meer. Programma wordt afgesloten.");
                break;
            }
            String input = scanner.nextLine().toLowerCase().trim();

            if (input.equals("stop")) {
                System.out.println("Spel gestopt.");
                break;
            } else if (input.equals("status")) {
                game.toonStatus();
            } else if (input.equals("uitleg")) {
                toonSpelregels();
            } else if (input.equals("start")) {
                System.out.println("Typ 'ga naar kamer <nummer>' om te beginnen.");
            } else if (input.startsWith("ga naar kamer")) {
                try {
                    int kamerNummer = Integer.parseInt(input.replaceAll("[^0-9]", ""));
                    System.out.println("Je betreedt kamer " + kamerNummer + "...");
                    startVraagTimer();
                    game.betreedKamer(kamerNummer);
                    stopVraagTimer();
                } catch (NumberFormatException e) {
                    System.out.println("Ongeldig kamernummer.");
                }
            } else {
                System.out.println("Onbekend commando.");
            }
        }
        // scanner niet sluiten hier plss dat gebeurt in main als programma stopt por favores
    }

    private void toonSpelregels() {
        System.out.println("=== Spelregels ===");
        System.out.println("HELP! JE ZIT VAST IN EEN GEBOUW VOL MET MONSTERS EN JE MOET WEG ZIEN TE KOMEN!");
        System.out.println();
        System.out.println("ZOEK ALLE SLEUTELS VAN DE SCRUM-DEUREN, BEANTWOORD VRAGEN EN VERLAAT HET GEBOUW VOORDAT DE TIMER AFLOOPT!⏳");
        System.out.println();
        System.out.println("JE HEBT 3 MINUTEN VOOR IEDERE KAMER! IEDERE KAMER HEEFT 2-3 VRAGEN OVER 'SCRUM'");
        System.out.println();
        System.out.println("BEANTWOORD JE EEN VRAAG FOUTIEF? REN VOOR JE LEVEN WANT ER VERSCHIJNT EEN ENG MONSTER!🧌👹👺");
        System.out.println();
        System.out.println("Succes..... muhahahhahaha");
        System.out.println("==================");
    }


    public void startVraagTimer() {
        stopVraagTimer();
        timerPopup = new TimerPopup(3 * 60);
    }

    public void stopVraagTimer() {
        if (timerPopup != null) {
            timerPopup.stop();
            timerPopup = null;
        }
    }
}
