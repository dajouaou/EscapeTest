package org.example;


import java.util.Scanner;

public class KamerFactory {
    public static Kamer maakKamer(int nummer, Speler speler, DatabaseManager db, Scanner scanner) {
        return switch (nummer) {
            case 1 -> new SprintPlanningKamer(speler);
            case 2 -> new DailyScrumKamer(speler, db, scanner);
            case 3 -> new ScrumBoardKamer(speler, scanner);
            case 4 -> new SprintReviewKamer(speler, scanner);
            case 5 -> new SprintRetrospectiveKamer(speler, scanner);
            case 6 -> new FinaleTiakamer(speler, scanner);
            default -> throw new IllegalArgumentException("Onbekend kamernummer: " + nummer);
        };
    }
}
