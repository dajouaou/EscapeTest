package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();

        System.out.println("Welkom bij Scrum Escape Building!");

        boolean ingelogd = false;
        String gebruikersnaam = null;  // hier declareren so that hij ook buiten de while bekend is guyss :33

        while (!ingelogd) {
            System.out.println("Typ 'registreer' om een account aan te maken of 'login' om in te loggen:");
            if (!scanner.hasNextLine()) {
                System.out.println("Geen invoer meer. Programma wordt afgesloten.");
                break;
            }
            String keuze = scanner.nextLine().trim();

            System.out.print("Gebruikersnaam: ");
            if (!scanner.hasNextLine()) break;
            gebruikersnaam = scanner.nextLine();  // we fill dat ho in

            System.out.print("Wachtwoord: ");
            if (!scanner.hasNextLine()) break;
            String wachtwoord = scanner.nextLine();

            if (keuze.equalsIgnoreCase("registreer")) {
                if (db.registreerSpeler(gebruikersnaam, wachtwoord)) {
                    System.out.println("Account succesvol aangemaakt. Je bent nu ingelogd.");
                    ingelogd = true;
                } else {
                    System.out.println("Registratie mislukt. Gebruikersnaam bestaat al.");
                }
            } else if (keuze.equalsIgnoreCase("login")) {
                if (db.controleerLogin(gebruikersnaam, wachtwoord)) {
                    System.out.println("Succesvol ingelogd.");
                    ingelogd = true;
                } else {
                    System.out.println("Login mislukt. Controleer je gegevens.");
                }
            } else {
                System.out.println("Ongeldige keuze. Typ 'registreer' of 'login'.");
            }
        }

        if (ingelogd) {
            Game spel = new Game(gebruikersnaam, db, scanner); // gebruikersnaam is nu valid
            CLI cli = new CLI(spel, scanner);
            spel.setCLI(cli);

            System.out.println("");
            System.out.println("Van harte welkom bij ons geweldig scrumspel!");
            System.out.println("Dit is je begin van het leren wat 'Scrum' inhoudt.");
            System.out.println("Dit gaan we doen door een enorm educatieve en geweldig spel te spelen.");
            System.out.println("");
            System.out.println("Typ 'uitleg' als je de spelregels wilt doornemen.");
            System.out.println("Wil je het spel gewoon starten? Typ dan 'start'.");
            System.out.println("");
            System.out.println("Typ 'status' om je voortgang te zien.");
            System.out.println("Typ 'stop' om het spel af te sluiten.");

            cli.leesCommando();
        }

        scanner.close();
    }
}

