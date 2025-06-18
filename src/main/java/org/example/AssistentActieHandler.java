package org.example;

public class AssistentActieHandler {

    public static void activeerAssistent(Kamer kamer) {
        toonHint(kamer);
        toonEducatiefHulpmiddel(kamer);
        toonMotivatie(kamer);
    }

    private static void toonHint(Kamer kamer) {
        HintProvider hintProvider = kamer.getHintProvider();
        if (hintProvider != null) {
            String hint = hintProvider.getHint();
            if (hint != null && !hint.isEmpty()) {
                System.out.println("💡 Hint: " + hint);
            } else {
                System.out.println("💡 Er is momenteel geen hint beschikbaar.");
            }
        }
    }

    static void toonEducatiefHulpmiddel(Kamer kamer) {
        System.out.println("📘 Hulpmiddel: Lees de vraag goed, evalueer de opties en pas je Scrum-kennis toe.");
    }

    static void toonMotivatie(Kamer kamer) {
        System.out.println("💪 Je denkt als een echte Product Owner!");
    }
}
