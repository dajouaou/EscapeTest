
package org.example;

import java.util.Scanner;

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
        spelerKanAanvallen();
    }

    public void spelerKanAanvallen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("😈 Het monster valt je aan!");
        System.out.println("🗡️ Wil je terugvechten met je zwaard? (ja/nee)");

        String antwoord = scanner.nextLine().trim().toLowerCase();
        if (antwoord.equals("ja")) {
            Weapon zwaard = new Zwaard();
            zwaard.attack();
            System.out.println("✅ Je hebt het monster verslagen!");
            speler.verwijderMonster();
        } else {
            System.out.println("💀 Je hebt niets gedaan... Het monster blijft op je loeren.");
        }
    }

    public String getNaam() {
        return gedrag.getClass().getSimpleName();
    }

    public String toonFoutmelding() {
        return gedrag.toString();
    }
}
