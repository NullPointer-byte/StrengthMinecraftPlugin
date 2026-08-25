package me.qwert.strenght.perk;

public class Perk {

    private final String id;
    private final String name;
    private final double requiredStrength;

    public Perk(
            String id,
            String name,
            double requiredStrength
    ) {
        this.id = id;
        this.name = name;
        this.requiredStrength = requiredStrength;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRequiredStrength() {
        return requiredStrength;
    }
}