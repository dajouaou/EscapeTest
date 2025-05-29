package org.example;

import javax.swing.*;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class FinaleTiakamer extends Kamer {
    private final Speler speler;
    private final javax.swing.Timer timer;
    private int secondenOver = 180;
    private boolean timerGestart = false;
    private JFrame gui;
    private final Scanner scanner;
    private VraagStrategie vraagStrategie;

    public FinaleTiakamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
        this.timer = new javax.swing.Timer(1000, e -> {
            secondenOver--;
            if (secondenOver <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "⏰ Tijd is op! Je bent verslagen door het TIA monster.");
                System.exit(0);
            }
        });
        this.vraagStrategie = new FinaleTiaVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        return launchMinigame();
    }

    private boolean launchMinigame() {
        gui = new JFrame("Ontdek TIA");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(500, 300);
        gui.setLayout(new GridLayout(1, 3));

        final boolean[] resultaat = new boolean[1];
        JButton transparantieBtn = new JButton("Deur 1");
        transparantieBtn.addActionListener(e -> toonUitleg("Transparantie betekent dat iedereen volledige zichtbaarheid heeft in het werkproces."));

        JButton inspectieBtn = new JButton("Deur 2");
        inspectieBtn.addActionListener(e -> toonUitleg("Inspectie houdt in dat het werk regelmatig wordt beoordeeld om voortgang te evalueren."));

        JButton aanpassingBtn = new JButton("Deur 3");
        aanpassingBtn.addActionListener(e -> {
            toonUitleg("Aanpassing betekent dat op basis van inspectie het proces of werk kan worden aangepast.");
            gui.dispose();
            resultaat[0] = startFinaleVragen();
            synchronized (scanner) {
                scanner.notify();
            }
        });

        gui.add(transparantieBtn);
        gui.add(inspectieBtn);
        gui.add(aanpassingBtn);
        gui.setVisible(true);

        synchronized (scanner) {
            try {
                scanner.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultaat[0];
    }

    private void toonUitleg(String uitleg) {
        JOptionPane.showMessageDialog(gui, uitleg);
    }

    private boolean startFinaleVragen() {
        List<Vraag> vragen = vraagStrategie.getVragen();
        int correcteAntwoorden = 0;
        timer.start();
        timerGestart = true;

        for (Vraag v : vragen) {
            System.out.println("\n" + v.getVraag());
            for (int j = 0; j < v.getOpties().length; j++) {
                System.out.println((char) ('A' + j) + ") " + v.getOpties()[j]);
            }

            char antwoord = wachtOpAntwoord(scanner);
            if (antwoord == v.getCorrectAntwoord()) {
                System.out.println("✅ Correct!");
                correcteAntwoorden++;
            } else {
                System.out.println("❌ Fout. Het juiste antwoord was: " + v.getCorrectAntwoord());
            }
        }

        timer.stop();

        if (correcteAntwoorden == vragen.size()) {
            System.out.println("\n🗝️ Je hebt het TIA Monster verslagen en een sleutel ontvangen!");
            System.out.println("🏃‍♂️ Je rent door een lange gang, de muren trillen. Je hoort alarmsignalen...");
            System.out.println("💼 Je ziet het CGI gebouw in de verte. Je bent bijna vrij! 🏢\n");
            speler.voegMuntenToe(300);
            System.out.println("💰 Je hebt 300 munten gekregen! Wissel ze in voor een prijs bij CGI.");

            try {
                String gebruikersnaam = speler.getGebruikersnaam();
                DatabaseManager dbManager = new DatabaseManager();
                dbManager.voegVoltooideKamerToe(gebruikersnaam, 6);
                dbManager.updateSpelerStatus(gebruikersnaam, "");
                int totaalMunten = dbManager.getMunten(gebruikersnaam) + 300;
                dbManager.updateMunten(gebruikersnaam, totaalMunten);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang of munten.");
            }
            return true;
        } else {
            System.out.println("❌ Je hebt niet genoeg vragen juist beantwoord. Probeer opnieuw.");
            return false;
        }
    }

    private char wachtOpAntwoord(Scanner scanner) {
        while (true) {
            System.out.print("Kies A, B, C of D: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                return input.charAt(0);
            }
            System.out.println("Ongeldige invoer. Probeer opnieuw.");
        }
    }
}