package org.example;

import java.util.List;

public class SprintPlanningVragen implements VraagStrategie {
    @Override
    public List<Vraag> getVragen() {
        return List.of(
                new Vraag("Het team heeft een velocity van 20 punten. Welke set taken past het BESTE in deze sprint?",
                        new String[]{
                                "A) Taak A (8 punten), Taak B (5 punten), Taak C (10 punten)",
                                "B) Taak A (8 punten), Taak B (5 punten), Taak C (3 punten), Taak D (5 punten)",
                                "C) Taak A (13 punten), Taak B (12 punten)",
                                "D) Taak A (5 punten), Taak B (5 punten), Taak C (5 punten), Taak D (5 punten)"
                        },
                        'D'),

                new Vraag("Welke van deze taken zou je NIET meenemen in een sprint als je het scrum-monster wilt voorkomen?",
                        new String[]{
                                "A) Een kleine bugfix voor een bestaande feature",
                                "B) Een nieuwe feature zonder duidelijke acceptatiecriteria",
                                "C) Een technische schuld item uit de backlog",
                                "D) Een kleine UI-aanpassing gevraagd door de PO"
                        },
                        'B'),

                new Vraag("Het team heeft 5 dagen om een taak van 8 story points af te ronden. Wat is de beste actie?",
                        new String[]{
                                "A) De taak opsplitsen in kleinere delen",
                                "B) Extra teamleden toevoegen aan de taak",
                                "C) De deadline verlengen",
                                "D) De punten aanpassen naar 5 om het te laten passen"
                        },
                        'A'),

                new Vraag("Wat is het belangrijkste criterium voor het selecteren van taken tijdens sprint planning?",
                        new String[]{
                                "A) Wat het management het meest wil zien",
                                "B) Wat past binnen de teamcapaciteit en bijdraagt aan het sprintdoel",
                                "C) Wat de meeste story points oplevert",
                                "D) Wat het snelst afgerond kan worden"
                        },
                        'B'),

                new Vraag("Een stakeholder vraagt om een extra feature tijdens de sprint. Wat doe je?",
                        new String[]{
                                "A) Onmiddellijk toevoegen om de stakeholder tevreden te stellen",
                                "B) Toevoegen en een andere taak verwijderen",
                                "C) Uitleggen dat nieuwe items eerst geëvalueerd moeten worden",
                                "D) Beloven dat het aan het einde wordt toegevoegd als er tijd is"
                        },
                        'C')
        );
    }
}