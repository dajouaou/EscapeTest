package org.example;

public class Monster implements GameObserver {
    private final MonsterGedrag gedrag;
    private final String kamerNaam;
    private final Speler speler;

    public Monster(MonsterGedrag gedrag, String kamerNaam, Speler speler) {
        this.gedrag = gedrag;
        this.kamerNaam = kamerNaam;
        this.speler = speler;
    }

    @Override
    public void update(String resultaat) {
        if ("fout".equalsIgnoreCase(resultaat)) {
            String huidigeKamerNaam = speler.getKamerNaam();
            if (kamerNaam.equalsIgnoreCase(huidigeKamerNaam)) {
                versperWeg();
            }
        } else if ("goed".equalsIgnoreCase(resultaat)) {
            System.out.println("👹 Monster verdwijnt.");
        }
    }

    public void versperWeg() {
        gedrag.reageer();
    }

    public String getNaam() {
        return gedrag.getClass().getSimpleName();
    }

    public String toonFoutmelding() {
        return gedrag.toString();
    }
}
