package org.example;

import java.util.List;

public class SprintRetrospectiveVragen implements VraagStrategie {
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