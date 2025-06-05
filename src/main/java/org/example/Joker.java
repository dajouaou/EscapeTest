package org.example;

public interface Joker {
    void useIn(Kamer kamer);
    boolean isUsed();  // elke joker mag maar één keer gebruikt worden
}
