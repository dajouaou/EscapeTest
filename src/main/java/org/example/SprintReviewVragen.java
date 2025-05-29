package org.example;

import java.util.List;

public class SprintReviewVragen implements VraagStrategie {
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