package org.example;

public interface Oppakbaar extends Voorwerp {
    void pakOp(Speler speler);
}
class Sleutel implements Oppakbaar {
    private final String naam;

    public Sleutel(String naam) {
        this.naam = naam;
    }

    @Override
    public void pakOp(Speler speler) {
        System.out.println("🔑 Je hebt de sleutel '" + naam + "' opgepakt.");
        speler.voegMuntenToe(50);
    }

    public String getNaam() {
        return naam;
    }
}

