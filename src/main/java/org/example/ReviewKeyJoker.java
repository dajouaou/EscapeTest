package org.example;

public class ReviewKeyJoker implements Joker {
    private boolean used = false;

    @Override
    public void useIn(Kamer kamer) {
        String naam = kamer.getClass().getSimpleName();
        if ((naam.contains("DailyScrum") || naam.contains("SprintReview")) && !used) {
            System.out.println("🗝️ De KeyJoker geeft je een extra sleutel in deze kamer!");
            used = true;
        } else {
            System.out.println("🚫 De KeyJoker werkt niet in deze kamer.");
        }
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}
