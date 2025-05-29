package org.example;

// Interface
interface MonsterGedrag {
    void reageer();
    String toString();
}

// 👹 Scope Creep
class ScopeCreep implements MonsterGedrag {
    public void reageer() {
        System.out.println("👹 Scope Creep verschijnt! Te veel werk gepland.");
    }

    public String toString() {
        return "Je hebt te veel ingepland. Houd je sprint realistisch.";
    }
}

// 🐌 Vertraging
class Vertraging implements MonsterGedrag {
    public void reageer() {
        System.out.println("🐌 Vertraging verschijnt! Je vergeet een update.");
    }

    public String toString() {
        return "Iemand heeft zijn status niet gedeeld. Daily Scrum faalt.";
    }
}

// 💾 Technical Debt
class TechnicalDebt implements MonsterGedrag {
    public void reageer() {
        System.out.println("💾 Technical Debt verschijnt! Slechte structuur.");
    }

    public String toString() {
        return "Refactor je board. Alles is een rommel.";
    }
}

// 💬 Miscommunicatie
class Miscommunicatie implements MonsterGedrag {
    public void reageer() {
        System.out.println("💬 Miscommunicatie verschijnt! Je begrijpt feedback verkeerd.");
    }

    public String toString() {
        return "Luister goed naar stakeholders voordat je bouwt.";
    }
}

// 🙈 Blinde Vlek
class BlindeVlek implements MonsterGedrag {
    public void reageer() {
        System.out.println("🙈 Blinde Vlek verschijnt! Je leert niets van je fouten.");
    }

    public String toString() {
        return "Je herhaalt steeds dezelfde fouten. Reflecteer!";
    }
}

// 👾 TIA Monster
class TiaMonster implements MonsterGedrag {
    public void reageer() {
        System.out.println("👾 TIA Monster verschijnt! Laatste test voor ontsnapping.");
    }

    public String toString() {
        return "Beantwoord alles goed en ontsnap aan het CGI-gebouw!";
    }
}
