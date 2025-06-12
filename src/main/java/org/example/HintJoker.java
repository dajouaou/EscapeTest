package org.example;

public class HintJoker implements Joker {
    private boolean used = false;

    @Override
    public void useIn(Kamer kamer) {
        if (used) return;
        System.out.println("💡 HintJoker activeert: " + kamer.getHintProvider().getHint());
        used = true;
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}