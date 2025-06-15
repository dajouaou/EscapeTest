package org.example;

import java.util.Scanner;

public class KamerTestMain {
    public static void main(String[] args) {
        System.out.println("🧪 STUB TEST:");
        Kamer stubKamer = new TestKamer(new Speler("stub"), new Scanner("ja\n"));
        stubKamer.setHintProvider(new StubHintProvider());
        stubKamer.vraagHintNaFout();

        System.out.println("\n🧪 MOCK TEST:");
        MockHintProvider mock = new MockHintProvider();
        Kamer mockKamer = new TestKamer(new Speler("mock"), new Scanner("ja\n"));
        mockKamer.setHintProvider(mock);
        mockKamer.vraagHintNaFout();
        if (mock.isAangeroepen()) {
            System.out.println("✅ Mock geslaagd: getHint werd aangeroepen");
        } else {
            System.out.println("❌ Mock gefaald: getHint werd NIET aangeroepen");
        }

        System.out.println("\n🧪 RANDWAARDE TEST (max. 3 hints):");
        Kamer randKamer = new TestKamer(new Speler("rand"), new Scanner("ja\nja\nja\nja\n"));
        randKamer.setHintProvider(new StubHintProvider());
        randKamer.vraagHintNaFout(); // 1
        randKamer.vraagHintNaFout(); // 2
        randKamer.vraagHintNaFout(); // 3
        randKamer.vraagHintNaFout(); // 4 → overschrijding
    }
}
