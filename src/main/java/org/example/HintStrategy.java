package org.example;

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

class ScopeCreepHint implements HintStrategy{
    public String geefHint() {return "lol";}
}

class TIAHint implements HintStrategy {
    public String geefHint() {return "tia";}
}

