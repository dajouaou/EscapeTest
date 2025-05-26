package org.example;

class Monster {
    private MonsterGedrag gedrag;

    public Monster(MonsterGedrag gedrag) {
        this.gedrag = gedrag;
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
