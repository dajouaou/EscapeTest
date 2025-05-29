package org.example;

public class Deur implements GameObserver {
    @Override
    public void update(String resultaat) {
        if ("goed".equalsIgnoreCase(resultaat)) {
            System.out.println("🔓 Je hebt de sleutel gevonden! Deur opent✨");
        } else {
            System.out.println("🚪 Deur blijft gesloten.");
        }
    }
}
