package org.example;

public class Deur implements GameObserver {
    @Override
    public void update(String resultaat) {
        if ("goed".equalsIgnoreCase(resultaat)) {
            System.out.println("🔓 Deur opent automatisch!");
        } else {
            System.out.println("🚪 Deur blijft gesloten.");
        }
    }
}
