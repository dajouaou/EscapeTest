package org.example;

//hintstrategy jatochhhh we gon get that A++++

public interface HintStrategy {
    String geefHint();
}

class SimpeleHint implements HintStrategy {
    public String geefHint() {
        return "💡 Denk aan de Scrum-rollen en hun verantwoordelijkheden.";
    }
}

class GeenHint implements HintStrategy {
    public String geefHint() {
        return "Geen hint beschikbaar.";
    }
}
