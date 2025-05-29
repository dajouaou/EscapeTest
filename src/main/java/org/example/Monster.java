package org.example;

public class Monster implements GameObserver {
    private final MonsterGedrag gedrag;

    public Monster(MonsterGedrag gedrag) {
        this.gedrag = gedrag;
    }

    public void versperWeg() {
        gedrag.reageer();  // Monster laat zijn gedrag zien
    }

    public String getNaam() {
        return gedrag.getClass().getSimpleName();
    }

    public String toonFoutmelding() {
        return gedrag.toString();
    }

    @Override
    public void update(String resultaat) {
        if ("fout".equalsIgnoreCase(resultaat)) {
            versperWeg(); // Monster verschijnt automatisch
        } else {
            System.out.println("👹 Monster verdwijnt.");
        }
    }
}
