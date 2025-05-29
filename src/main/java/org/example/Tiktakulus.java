package org.example;

class Tiktakulus implements MonsterGedrag {
    @Override
    public void reageer() {
        System.out.println("💀 TikTakulus verspert de weg!");
    }

    @Override
    public String toString() {
        return "💀 TikTakulus verschijnt uit de duisternis.";
    }
}