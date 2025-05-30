package org.example;
import java.util.Scanner;

public interface InteractiefVoorwerp extends Voorwerp {
    void startInteractie(java.util.Scanner scanner);
}

class Puzzel implements InteractiefVoorwerp {
    private final String vraag;
    private final String oplossing;

    public Puzzel(String vraag, String oplossing) {
        this.vraag = vraag;
        this.oplossing = oplossing;
    }

    @Override
    public void startInteractie(Scanner scanner) {
        System.out.println("🧩 Puzzel: " + vraag);
        System.out.print("Jouw antwoord: ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase(oplossing)) {
            System.out.println("✅ Correct!");
        } else {
            System.out.println("❌ Fout. De juiste oplossing was: " + oplossing);
        }
    }
}

