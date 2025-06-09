package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;



class DailyScrumKamer extends Kamer {
    private final VraagStrategieen vraagStrategie;

    public DailyScrumKamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
        this.vraagStrategie = new DailyScrumVragen();
    }

    @Override
    public boolean start() {
        toonHint();
        System.out.println("Welkom in de Daily Scrum kamer!");

        // ➤ Joker prompt voor KeyJoker of ReviewKeyJoker bovenaan (1x per kamer)
        Joker actieveJoker = speler.getJoker();
        if ((actieveJoker instanceof KeyJoker || actieveJoker instanceof ReviewKeyJoker) && !speler.isJokerGebruikt()) {
            System.out.print("Wil je je KeyJoker gebruiken voor deze kamer? (ja/nee): ");
            String gebruik = scanner.nextLine().trim().toLowerCase();
            if (gebruik.equals("ja")) {
                speler.gebruikJoker(this); // activeert accept() of useIn()
            }
        }

        List<Vraag> vragen = vraagStrategie.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());

            // ➤ HintJoker prompt per vraag
            if (actieveJoker instanceof HintJoker && !speler.isJokerGebruikt()) {
                System.out.print("Wil je je HintJoker gebruiken voor deze vraag? (ja/nee): ");
                String gebruik = scanner.nextLine().trim().toLowerCase();
                if (gebruik.equals("ja")) {
                    speler.gebruikJoker(this); // activeert Hint tonen
                }
            }

            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }

            char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!");
                speler.notifyGameObservers("goed");
                vragenCorrect[i] = true;
            } else {
                System.out.println("❌ Fout!");
                speler.notifyGameObservers("fout");
                foutBeantwoordeVragen.add(i);
                vraagHintNaFout();
            }
        }

        while (!foutBeantwoordeVragen.isEmpty() && wilOpnieuwProberen(scanner)) {
            List<Integer> nogFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println(vraag.getVraag());

                if (actieveJoker instanceof HintJoker && !speler.isJokerGebruikt()) {
                    System.out.print("Wil je je HintJoker gebruiken voor deze vraag? (ja/nee): ");
                    String gebruik = scanner.nextLine().trim().toLowerCase();
                    if (gebruik.equals("ja")) {
                        speler.gebruikJoker(this);
                    }
                }

                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }

                char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
                if (antwoord == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!");
                    speler.notifyGameObservers("goed");
                    vragenCorrect[index] = true;
                } else {
                    System.out.println("❌ Nog steeds fout!");
                    speler.notifyGameObservers("fout");
                    nogFout.add(index);
                    vraagHintNaFout();
                }
            }
            foutBeantwoordeVragen = nogFout;
        }

        for (boolean correct : vragenCorrect) {
            if (!correct) {
                return false;
            }
        }

        System.out.println("🎉 Goed gedaan! Je hebt deze kamer succesvol afgerond.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager db = new DatabaseManager();
            db.voegVoltooideKamerToe(gebruikersnaam, 4);
            db.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }

        return true;
    }

    @Override
    public void accept(KeyJoker joker) {
        System.out.println("🔑 De KeyJoker opent een extra sleutel in deze kamer!");
        speler.voegMuntenToe(1); // of een andere beloning
    }
}


class ScrumBoardKamer extends Kamer {
    private final VraagStrategieen vraagStrategie;

    public ScrumBoardKamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
        this.vraagStrategie = new ScrumBoardVragen();
    }

    @Override
    public boolean start() {
        toonHint();
        System.out.println("Welkom in de Scrum Board kamer!");

        List<Vraag> vragen = vraagStrategie.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }
            char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!");
                speler.notifyGameObservers("goed");
                vragenCorrect[i] = true;
            } else {
                System.out.println("❌ Fout!");
                speler.notifyGameObservers("fout");
                foutBeantwoordeVragen.add(i);
                vraagHintNaFout();
            }
        }

        while (!foutBeantwoordeVragen.isEmpty() && wilOpnieuwProberen(scanner)) {
            List<Integer> nogFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println(vraag.getVraag());
                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }
                char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
                if (antwoord == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!");
                    speler.notifyGameObservers("goed");
                    vragenCorrect[index] = true;
                } else {
                    System.out.println("❌ Nog steeds fout!");
                    speler.notifyGameObservers("fout");
                    nogFout.add(index);
                    vraagHintNaFout();
                }
            }
            foutBeantwoordeVragen = nogFout;
        }

        for (boolean correct : vragenCorrect) {
            if (!correct) {
                return false;
            }
        }

        System.out.println("🎉 Goed gedaan! Je hebt deze kamer succesvol afgerond.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager db = new DatabaseManager();
            db.voegVoltooideKamerToe(gebruikersnaam, 3);
            db.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }

        return true;
    }
}

class SprintPlanningKamer extends Kamer {
    private final VraagStrategieen vraagStrategie;

    public SprintPlanningKamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
        this.vraagStrategie = new SprintPlanningVragen();
    }

    @Override
    public boolean start() {
        toonHint();
        System.out.println("Welkom bij de Sprintplanning Kamer!");
        System.out.println("Beantwoord de vragen juist om door te gaan. Fout? Scope Creep verschijnt!");

        List<Vraag> vragen = vraagStrategie.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println("\nVraag " + (i + 1) + ": " + vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }
            char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!");
                speler.notifyGameObservers("goed");
                vragenCorrect[i] = true;
            } else {
                System.out.println("❌ Fout!");
                speler.notifyGameObservers("fout");
                foutBeantwoordeVragen.add(i);
                vraagHintNaFout();
            }
        }

        while (!foutBeantwoordeVragen.isEmpty() && wilOpnieuwProberen(scanner)) {
            List<Integer> nogFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println("\n" + vraag.getVraag());
                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }
                char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
                if (antwoord == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!");
                    speler.notifyGameObservers("goed");
                    vragenCorrect[index] = true;
                } else {
                    System.out.println("❌ Nog steeds fout!");
                    speler.notifyGameObservers("fout");
                    nogFout.add(index);
                    vraagHintNaFout();
                }
            }
            foutBeantwoordeVragen = nogFout;
        }

        boolean allesGoed = true;
        for (boolean correct : vragenCorrect) {
            if (!correct) {
                allesGoed = false;
                break;
            }
        }

        if (!allesGoed) return false;

        System.out.println("🎉 Alle vragen goed! Je mag door.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager db = new DatabaseManager();
            db.voegVoltooideKamerToe(gebruikersnaam, 1);
            db.updateSpelerStatus(gebruikersnaam, "");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fout bij opslaan voortgang.");
        }

        return true;
    }
}

class SprintRetrospectiveKamer extends Kamer {
    private final VraagStrategieen vraagStrategie;

    public SprintRetrospectiveKamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
        this.vraagStrategie = new SprintRetrospectiveVragen();
    }

    @Override
    public boolean start() {
        toonHint();
        System.out.println("Welkom in de Sprint Retrospective kamer!");

        List<Vraag> vragen = vraagStrategie.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }
            char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!");
                speler.notifyGameObservers("goed");
                vragenCorrect[i] = true;
            } else {
                System.out.println("❌ Fout!");
                speler.notifyGameObservers("fout");
                foutBeantwoordeVragen.add(i);
                vraagHintNaFout();
            }
        }

        while (!foutBeantwoordeVragen.isEmpty() && wilOpnieuwProberen(scanner)) {
            List<Integer> nogFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println(vraag.getVraag());
                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }
                char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
                if (antwoord == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!");
                    speler.notifyGameObservers("goed");
                    vragenCorrect[index] = true;
                } else {
                    System.out.println("❌ Nog steeds fout!");
                    speler.notifyGameObservers("fout");
                    nogFout.add(index);
                    vraagHintNaFout();
                }
            }
            foutBeantwoordeVragen = nogFout;
        }

        for (boolean correct : vragenCorrect) {
            if (!correct) {
                return false;
            }
        }

        System.out.println("🎉 Goed gedaan! Je hebt deze kamer succesvol afgerond.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager db = new DatabaseManager();
            db.voegVoltooideKamerToe(gebruikersnaam, 5);
            db.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }

        return true;
    }
}

class SprintReviewKamer extends Kamer {
    private final VraagStrategieen vraagStrategie;

    public SprintReviewKamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
        this.vraagStrategie = new SprintReviewVragen();
    }

    @Override
    public boolean start() {
        toonHint();
        System.out.println("Welkom in de Sprint Review kamer!");

        List<Vraag> vragen = vraagStrategie.getVragen();
        List<Integer> foutBeantwoordeVragen = new ArrayList<>();
        boolean[] vragenCorrect = new boolean[vragen.size()];

        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            System.out.println(vraag.getVraag());
            for (String optie : vraag.getOpties()) {
                System.out.println(optie);
            }
            char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
            if (antwoord == vraag.getCorrectAntwoord()) {
                System.out.println("✅ Correct!");
                speler.notifyGameObservers("goed");
                vragenCorrect[i] = true;
            } else {
                System.out.println("❌ Fout!");
                speler.notifyGameObservers("fout");
                foutBeantwoordeVragen.add(i);
                vraagHintNaFout();
            }
        }

        while (!foutBeantwoordeVragen.isEmpty() && wilOpnieuwProberen(scanner)) {
            List<Integer> nogFout = new ArrayList<>();
            for (int index : foutBeantwoordeVragen) {
                Vraag vraag = vragen.get(index);
                System.out.println(vraag.getVraag());
                for (String optie : vraag.getOpties()) {
                    System.out.println(optie);
                }
                char antwoord = vraagAntwoord(scanner, vraag.getOpties().length);
                if (antwoord == vraag.getCorrectAntwoord()) {
                    System.out.println("✅ Correct!");
                    speler.notifyGameObservers("goed");
                    vragenCorrect[index] = true;
                } else {
                    System.out.println("❌ Nog steeds fout!");
                    speler.notifyGameObservers("fout");
                    nogFout.add(index);
                    vraagHintNaFout();
                }
            }
            foutBeantwoordeVragen = nogFout;
        }

        for (boolean correct : vragenCorrect) {
            if (!correct) {
                return false;
            }
        }

        System.out.println("🎉 Goed gedaan! Je hebt deze kamer succesvol afgerond.");
        try {
            String gebruikersnaam = speler.getGebruikersnaam();
            DatabaseManager db = new DatabaseManager();
            db.voegVoltooideKamerToe(gebruikersnaam, 4);
            db.updateSpelerStatus(gebruikersnaam, "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Er is een fout opgetreden bij het opslaan van je voortgang.");
        }

        return true;
    }
    @Override
    public void accept(KeyJoker joker) {
        System.out.println("🔓 Je gebruikt de KeyJoker en krijgt toegang tot een verborgen item!");
        speler.voegMuntenToe(1); // of iets unieks
    }

}

class FinaleTiakamer extends Kamer {
    private final javax.swing.Timer timer;
    private int secondenOver = 180;
    private boolean timerGestart = false;
    private JFrame gui;
    private final VraagStrategieen vraagStrategie;

    public FinaleTiakamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
        this.vraagStrategie = new FinaleTiaVragen();
        this.timer = new javax.swing.Timer(1000, e -> {
            secondenOver--;
            if (secondenOver <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "⏰ Tijd is op! Je bent verslagen door het TIA monster.");
                System.exit(0);
            }
        });
    }

    @Override
    public boolean start() {
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

            char antwoord = vraagAntwoord(scanner, v.getOpties().length);
            if (antwoord == v.getCorrectAntwoord()) {
                System.out.println("✅ Correct!");
                speler.notifyGameObservers("goed");
                correcteAntwoorden++;
            } else {
                System.out.println("❌ Fout. Het juiste antwoord was: " + v.getCorrectAntwoord());
                speler.notifyGameObservers("fout");
                vraagHintNaFout();
            }
        }

        timer.stop();

        if (correcteAntwoorden == vragen.size()) {
            System.out.println("\n🗝️ Je hebt het TIA Monster verslagen en een sleutel ontvangen!");
            speler.voegMuntenToe(300);

            try {
                String gebruikersnaam = speler.getGebruikersnaam();
                DatabaseManager db = new DatabaseManager();
                db.voegVoltooideKamerToe(gebruikersnaam, 6);
                db.updateSpelerStatus(gebruikersnaam, "");
                int totaalMunten = db.getMunten(gebruikersnaam) + 300;
                db.updateMunten(gebruikersnaam, totaalMunten);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Fout bij opslaan van voortgang of munten.");
            }
            return true;
        } else {
            System.out.println("❌ Je hebt niet genoeg vragen juist beantwoord. Probeer opnieuw.");
            return false;
        }
    }
}

class VoorwerpenKamer extends Kamer {
    private final Oppakbaar sleutel;
    private final InteractiefVoorwerp puzzel;

    public VoorwerpenKamer(Speler speler, Scanner scanner) {
        super(speler, scanner);
        this.sleutel = new Sleutel("Escape Sleutel");
        this.puzzel = new Puzzel("Wat is de derde Scrum waarde na Focus en Openheid?", "Respect");
    }

    @Override
    public boolean start() {
        toonHint();

        System.out.println("🧩 Je komt een raadsel tegen op een bord...");
        puzzel.startInteractie(scanner);

        System.out.println("🔒 Na het oplossen van de puzzel, vind je een sleutel op de grond.");
        sleutel.pakOp(speler);

        return true;
    }

}

