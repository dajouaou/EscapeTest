package org.example;

import java.util.*;

public class Speler {
    private final String naam;
    private int huidigeKamer = 0;
    private final Set<Integer> kamersGehaald = new HashSet<>();
    private Monster actiefMonster;
    private final List<SpelerObserver> observers = new ArrayList<>();
    private Map<Integer, Integer> laatsteVraagPerKamer = new HashMap<>();

    private int munten = 0;

    public Speler(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Naam mag niet leeg zijn");
        }
        this.naam = naam;
    }

    public void setLaatsteVraagVoorKamer(int kamerNummer, int vraagNummer) {
        laatsteVraagPerKamer.put(kamerNummer, vraagNummer);
    }

    public Integer getLaatsteVraagVoorKamer(int kamerNummer) {
        return laatsteVraagPerKamer.get(kamerNummer);
    }

    public String getGebruikersnaam() {
        return naam;
    }

    public interface SpelerObserver {
        void onKamerVeranderd(int nieuweKamer);
        void onMonsterGewijzigd(Monster monster);
        void onKamerVoltooid(int kamerNummer);
    }

    public String getNaam() { return naam; }
    public int getHuidigeKamer() { return huidigeKamer; }
    public Set<Integer> getKamersGehaald() { return new HashSet<>(kamersGehaald); }
    public boolean heeftMonster() { return actiefMonster != null; }
    public Monster getMonster() { return actiefMonster; }
    public String getMonsterNaam() {
        return heeftMonster() ? actiefMonster.getNaam() : "Geen actief monster";
    }

    public void setHuidigeKamer(int kamer) {
        if (kamer < 0) throw new IllegalArgumentException("Ongeldig kamernummer");
        this.huidigeKamer = kamer;
        notifyObservers(o -> o.onKamerVeranderd(kamer));
    }

    public void voegKamerToe(int kamer) {
        if (kamer <= 0) throw new IllegalArgumentException("Ongeldig kamernummer");
        if (kamersGehaald.add(kamer)) {
            notifyObservers(o -> o.onKamerVoltooid(kamer));
        }
    }

    public void setMonster(Monster monster) {
        this.actiefMonster = monster;
        notifyObservers(o -> o.onMonsterGewijzigd(monster));
    }

    public void verwijderMonster() {
        setMonster(null);
    }

    // observer management girlssssssss
    public void addObserver(SpelerObserver observer) {
        if (observer != null) observers.add(observer);
    }

    public void removeObserver(SpelerObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(java.util.function.Consumer<SpelerObserver> notification) {
        for (SpelerObserver observer : observers) {
            notification.accept(observer);
        }
    }

    public boolean heeftKamerGehaald(int kamer) {
        return kamersGehaald.contains(kamer);
    }

    public int getAantalKamersGehaald() {
        return kamersGehaald.size();
    }

    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Naam: ").append(naam).append("\n");
        status.append("Huidige kamer: ").append(huidigeKamer).append("\n");
        status.append("Kamers gehaald: ").append(kamersGehaald).append("\n");
        status.append("Actief monster: ").append(heeftMonster() ? actiefMonster.getNaam() : "Geen").append("\n");
        status.append("Munten: ").append(munten).append("\n");
        return status.toString();
    }

    public void voegMuntenToe(int aantal) {
        if (aantal > 0) {
            this.munten += aantal;
        }
    }

    private final List<GameObserver> gameObservers = new ArrayList<>();

    public void addGameObserver(GameObserver observer) {
        if (observer != null) {
            gameObservers.add(observer);
        }
    }

    public void notifyGameObservers(String resultaat) {
        for (GameObserver observer : gameObservers) {
            observer.update(resultaat);
        }
    }

    public String getKamerNaam() {
        switch (huidigeKamer) {
            case 1: return "Sprint Planning";
            case 2: return "Daily Scrum";
            case 3: return "Scrum Board";
            case 4: return "Sprint Review";
            case 5: return "Sprint Retrospective";
            case 6: return "Finale TIA Kamer";
            default: return "Onbekend";
        }
    }


}
