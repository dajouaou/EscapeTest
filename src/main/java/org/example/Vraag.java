package org.example;

public class Vraag {
    private String vraag;
    private String[] opties;
    private char correctAntwoord;

    public Vraag(String vraag, String[] opties, char correctAntwoord) {
        this.vraag = vraag;
        this.opties = opties;
        this.correctAntwoord = correctAntwoord;
    }

    public String getVraag() {
        return vraag;
    }

    public String[] getOpties() {
        return opties;
    }

    public char getCorrectAntwoord() {
        return correctAntwoord;
    }
}
