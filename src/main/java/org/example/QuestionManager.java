package org.example;

public class QuestionManager {

    public static VraagStrategie getStrategieVoorKamer(int kamerNr) {
        return switch (kamerNr) {
            case 1 -> new SprintPlanningVragen();
            case 2 -> new DailyScrumVragen();
            case 3 -> new ScrumBoardVragen();
            case 4 -> new SprintReviewVragen();
            case 5 -> new SprintRetrospectiveVragen();
            case 6 -> new FinaleTiaVragen();
            default -> throw new IllegalArgumentException("Onbekend kamernummer: " + kamerNr);
        };
    }
}
