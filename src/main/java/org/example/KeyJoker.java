package org.example;

public class KeyJoker implements Joker {
    private boolean used = false;

    @Override
    public void useIn(Kamer kamer) {
        if (used) return;
        kamer.accept(this);  // Kamer bepaalt of deze joker werkt
        used = true;
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}
