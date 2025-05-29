package org.example;

import java.util.List;

public class ScrumBoardVragen implements VraagStrategie {
    @Override
    public List<Vraag> getVragen() {
        return List.of(
                new Vraag("Gebruiker moet kunnen inloggen met twee-factor authenticatie",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'B'),

                new Vraag("Inlogfunctionaliteit ontwikkelen",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'C'),

                new Vraag("Beveiliging en authenticatie verbeteren in hele app",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'A'),

                new Vraag("Wachtwoord vergeten-functionaliteit bouwen",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'B'),

                new Vraag("Knop 'Wachtwoord resetten' toevoegen aan inlogscherm",
                        new String[]{"A) Epic", "B) User Story", "C) Taak"},
                        'C')
        );
    }
}