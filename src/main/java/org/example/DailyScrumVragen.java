package org.example;

import java.util.List;

public class DailyScrumVragen implements VraagStrategie {
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