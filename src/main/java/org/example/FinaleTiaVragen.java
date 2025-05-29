package org.example;

import java.util.List;

public class FinaleTiaVragen implements VraagStrategie {
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