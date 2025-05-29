package org.example;

import java.util.List;

 class DailyScrumVragen implements VraagStrategieen {
    @Override
    public List<Vraag> getVragen() {
        return List.of(
                new Vraag("Status-update voor: Jan (Developer)",
                        new String[]{
                                "A) Ik heb gisteren aan ticket PROJ-123 gewerkt en vandaag ga ik PROJ-124 afronden",
                                "B) Ik heb de financiële rapporten gecontroleerd en ga vandaag met klanten bellen",
                                "C) Ik heb de serverkast schoongemaakt en ga vandaag koffie halen",
                                "D) Ik heb de marketingstrategie bepaald en ga vandaag advertenties plaatsen"
                        },
                        'A'),

                new Vraag("Status-update voor: Sarah (Product Owner)",
                        new String[]{
                                "A) Informatie geven over prioriteiten of klantfeedback",
                                "B) Gedetailleerde technische oplossingen voorstellen",
                                "C) Ieders werktijden controleren",
                                "D) De sprintplanning volledig herzien"
                        },
                        'A'),

                new Vraag("Status-update voor: Mark (Scrum Master)",
                        new String[]{
                                "A) Nauwkeurige notulen maken van alles wat gezegd wordt",
                                "B) Zorgen dat het team het event binnen de timebox houdt en zich aan de focus houdt",
                                "C) Technische oplossingen aandragen voor problemen",
                                "D) Individuele teamleden aanspreken op hun prestaties"
                        },
                        'B')
        );
    }
}

class FinaleTiaVragen implements VraagStrategieen {
    @Override
    public List<Vraag> getVragen() {
        return List.of(
                new Vraag("Wat is Sprint Planning?",
                        new String[]{
                                "Een dagelijkse meeting",
                                "Plannen van het werk voor de sprint",
                                "Een terugblik",
                                "Testfase"
                        },
                        'B'),

                new Vraag("Wat is het Scrum Board?",
                        new String[]{
                                "Een spreadsheet",
                                "Een documentatiemap",
                                "Een visueel hulpmiddel voor voortgang",
                                "Een product backlog"
                        },
                        'C'),

                new Vraag("Waar staat TIA voor binnen Scrum?",
                        new String[]{
                                "Taken, Inzichten, Acties",
                                "Testen, Integreren, Afmaken",
                                "Transparantie, Inspectie, Aanpassing",
                                "Teams, Iteraties, Agile"
                        },
                        'C')
        );
    }
}

class ScrumBoardVragen implements VraagStrategieen {
    @Override
    public List<Vraag> getVragen() {
        return List.of(
                new Vraag("Gebruiker moet kunnen inloggen met twee-factor authenticatie",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'B'),

                new Vraag("Inlogfunctionaliteit ontwikkelen",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'C'),

                new Vraag("Beveiliging en authenticatie verbeteren in hele app",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'A'),

                new Vraag("Wachtwoord vergeten-functionaliteit bouwen",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'B'),

                new Vraag("Knop 'Wachtwoord resetten' toevoegen aan inlogscherm",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'C')
        );
    }
}

class SprintPlanningVragen implements VraagStrategieen {
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

class SprintRetrospectiveVragen implements VraagStrategieen {
    @Override
    public List<Vraag> getVragen() {
        return List.of(
                new Vraag("Wat is het hoofddoel van een Sprint Retrospective?",
                        new String[]{
                                "A) Problemen oplossen van vorige sprint",
                                "B) Samen reflecteren en verbeteren als team",
                                "C) Nieuwe stories maken",
                                "D) Plannen van de volgende sprint"
                        },
                        'B'),

                new Vraag("Een teamlid voelde zich niet gehoord tijdens de sprint. Wat kan het team hiervan leren?",
                        new String[]{
                                "A) Er moet een aparte vergadering komen voor feedback",
                                "B) De Scrum Master moet strenger worden",
                                "C) Betere luisterafspraken maken en inclusiviteit bespreken",
                                "D) Diegene moet minder kritisch zijn"
                        },
                        'C'),

                new Vraag("De teamcommunicatie liep regelmatig vast. Wat is een mogelijke les?",
                        new String[]{
                                "A) Meer documenten maken",
                                "B) Slack verbieden",
                                "C) Teamafspraken over communicatie verbeteren",
                                "D) Dagelijks rapporteren"
                        },
                        'C')
        );
    }
}

class SprintReviewVragen implements VraagStrategieen {
    @Override
    public List<Vraag> getVragen() {
        return List.of(
                new Vraag("Wat is het hoofddoel van de Sprint Review?",
                        new String[]{
                                "A) Sprint afsluiten",
                                "B) Documentatie controleren",
                                "C) Feedback verzamelen over het geleverde werk",
                                "D) Nieuwe sprint plannen"
                        },
                        'C'),

                new Vraag("Wat gebeurt er vaak als er tijdens de Sprint Review feedback wordt gegeven?",
                        new String[]{
                                "A) Het team wordt vervangen",
                                "B) De Product Backlog wordt bijgewerkt",
                                "C) Er worden nieuwe KPI's opgesteld",
                                "D) De sprint wordt ongeldig verklaard"
                        },
                        'B'),

                new Vraag("Wie nemen er typisch deel aan een Sprint Review?",
                        new String[]{
                                "A) Alleen het ontwikkelteam",
                                "B) Alleen de Scrum Master",
                                "C) Het team en stakeholders",
                                "D) Alleen de Product Owner"
                        },
                        'C')
        );
    }
}
