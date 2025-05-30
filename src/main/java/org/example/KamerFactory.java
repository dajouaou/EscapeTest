package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;

public class KamerFactory {
    private static final Map<Integer, BiFunction<Speler, Scanner, Kamer>> kamerMap = new HashMap<>();

    static {
        register(1, (s, sc) -> new SprintPlanningKamer(s));
        register(2, (s, sc) -> new DailyScrumKamer(s, sc));
        register(3, (s, sc) -> new ScrumBoardKamer(s, sc));
        register(4, (s, sc) -> new SprintReviewKamer(s, sc));
        register(5, (s, sc) -> new SprintRetrospectiveKamer(s, sc));
        register(6, (s, sc) -> new VoorwerpenKamer(s, sc));
        register(7, (s, sc) -> new FinaleTiakamer(s, sc));
    }

    public static void register(int nummer, BiFunction<Speler, Scanner, Kamer> creator) {
        kamerMap.put(nummer, creator);
    }

    public static Kamer maakKamer(int nummer, Speler speler, Scanner scanner) {
        if (!kamerMap.containsKey(nummer)) {
            throw new IllegalArgumentException("Onbekend kamernummer: " + nummer);
        }
        Kamer kamer = kamerMap.get(nummer).apply(speler, scanner);
        kamer.setHintStrategy(new SimpeleHint());
        return kamer;
    }
}
