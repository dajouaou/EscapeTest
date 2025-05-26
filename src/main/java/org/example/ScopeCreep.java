package org.example;

class ScopeCreep implements MonsterGedrag {
    public void reageer() {
        System.out.println("Scope Creep verschijnt! Je hebt te veel ingepland. Herplan je sprint.");
    }

    public String toString() {
        return "Scope Creep: je hebt te veel werk ingepland.";
    }
}
