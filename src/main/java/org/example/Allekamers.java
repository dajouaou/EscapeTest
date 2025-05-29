package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.GridLayout;

 class DailyScrumKamer extends Kamer {
    private final DatabaseManager dbManager;
    private final Speler speler;
    private final int kamerNummer = 4;
    private Scanner scanner;
    private VraagStrategieen vraagStrategieen;

    public DailyScrumKamer(Speler speler, DatabaseManager dbManager, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.dbManager = dbManager;
        this.scanner = scanner;
        this.vraagStrategieen = new DailyScrumVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("Welkom bij de Daily Scrum kamer!");
        System.out.println("Je krijgt een lijst met teamleden en mogelijke status-updates.");
        System.out.println("Kies per teamlid de juiste status-update die zij het meest waarschijnlijk zouden geven.");
        System.out.println("Een vergeten of fout antwoord roept het monster 'Vertraging' op.\n");

        List<Vraag> vragen = vraagStrategieen.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }

            char antwoordChar = vraagAntwoord();
            if (antwoordChar == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!\n");
                speler.notifyGameObservers("goed");
            } else {
                System.out.println("❌ Fout!");
                speler.notifyGameObservers("fout");
                foutBeantwoordeVragen.add(i);
            }
        }

        while (!foutBeantwoordeVragen.isEmpty()) {
            System.out.println("\n💀 Je hebt niet alle vragen correct beantwoord.");
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

            List<Integer> nogSteedsFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println(vraag.getVraag());
                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }

                char antwoordChar = vraagAntwoord();
                if (antwoordChar == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!\n");
                    speler.notifyGameObservers("goed");
                } else {
                    System.out.println("❌ Nog steeds fout.");
                    speler.notifyGameObservers("fout");
                    nogSteedsFout.add(index);
                }
            }
            foutBeantwoordeVragen = nogSteedsFout;
        }

        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            dbManager.voegVoltooideKamerToe(gebruikersnaam, kamerNummer);
            System.out.println("🎉 Goed gedaan! Alle status-updates zijn correct gegeven.");
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
//finaletiakamer


class FinaleTiakamer extends Kamer {
    private final Speler speler;
    private final javax.swing.Timer timer;
    private int secondenOver = 180;
    private boolean timerGestart = false;
    private JFrame gui;
    private final Scanner scanner;
    private VraagStrategieen vraagStrategie;

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
                speler.notifyGameObservers("goed");  // Observer pattern: monster weg, deur open
                correcteAntwoorden++;
            } else {
                System.out.println("❌ Fout. Het juiste antwoord was: " + v.getCorrectAntwoord());
                speler.notifyGameObservers("fout");  // Observer pattern: monster verschijnt, deur blijft dicht
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
//scrumboardkamer

 class ScrumBoardKamer extends Kamer {
    private final Speler speler;
    private final Scanner scanner;
    private VraagStrategieen vraagStrategie;

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

//sprintplanningkamer

 class SprintPlanningKamer extends Kamer {
    private List<Integer> foutBeantwoordeVragen = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private VraagStrategieen vraagStrategie;

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
//retrospectivekamer

 class SprintRetrospectiveKamer extends Kamer {
    private final Speler speler;
    private final Scanner scanner;
    private VraagStrategieen vraagStrategie;

    public SprintRetrospectiveKamer(Speler speler, Scanner scanner) {
        super(speler);
        this.speler = speler;
        this.scanner = scanner;
        this.vraagStrategie = new SprintRetrospectiveVragen();
    }

    @Override
    public boolean start() {
        setHintStrategy(new SimpeleHint());
        toonHint();
        System.out.println("💡 Welkom in de Sprint Retrospective kamer!");
        System.out.println("Je krijgt situaties die zich in een team voordoen en moet aangeven wat het team hiervan kan leren.\n");

        List<Vraag> vragen = vraagStrategie.getVragen();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }

            char antwoord = vraagAntwoord();
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!\n");
                vragenCorrect[i] = true;
                speler.notifyGameObservers("goed");
            } else {
                System.out.println("❌ Fout antwoord.\n");
                vragenCorrect[i] = false;
                speler.notifyGameObservers("fout");
            }
        }

        while (bevatFouteAntwoorden(vragenCorrect)) {
            System.out.println("Niet alle antwoorden waren correct.");
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

            for (int i = 0; i < vragen.size(); i++) {
                if (!vragenCorrect[i]) {
                    Vraag vraag = vragen.get(i);
                    System.out.println(vraag.getVraag());
                    for (String optie : vraag.getOpties()) {
                        System.out.println(optie);
                    }

                    char antwoord = vraagAntwoord();
                    if (antwoord == vraag.getCorrectAntwoord()) {
                        System.out.println("✅ Correct!\n");
                        vragenCorrect[i] = true;
                        speler.notifyGameObservers("goed");
                    } else {
                        System.out.println("❌ Nog steeds fout.\n");
                        speler.notifyGameObservers("fout");
                    }
                }
            }
        }

        System.out.println("🎉 Goed gedaan! Je hebt de Sprint Retrospective succesvol afgerond.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.voegVoltooideKamerToe(gebruikersnaam, 5);
            dbManager.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }
        return true;
    }

    private boolean bevatFouteAntwoorden(boolean[] antwoorden) {
        for (boolean correct : antwoorden) {
            if (!correct) {
                return true;
            }
        }
        return false;
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

//sprintreviewkamer

 class SprintReviewKamer extends Kamer {
    private final Speler speler;
    private final Scanner scanner;
    private VraagStrategieen vraagStrategie;

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

