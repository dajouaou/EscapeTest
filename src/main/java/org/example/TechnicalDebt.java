package org.example;

class TechnicalDebt implements MonsterGedrag {
    public void reageer() {
        System.out.println("Technical Debt verschijnt! Je hebt technische schulden die je moet oplossen.");
    }

    public String toString() {
        return "Technical Debt: je moet je technische schuld inhalen.";
    }
}
