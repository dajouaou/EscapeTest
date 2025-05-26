package org.example;

class DeadlinePressure implements MonsterGedrag {
    public void reageer() {
        System.out.println("Deadline Pressure verschijnt! Je moet je sprint deadline halen!");
    }

    public String toString() {
        return "Deadline Pressure: de tijd dringt.";
    }
}
