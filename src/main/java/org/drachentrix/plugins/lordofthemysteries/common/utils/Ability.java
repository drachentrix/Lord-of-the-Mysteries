package org.drachentrix.plugins.lordofthemysteries.common.utils;

public abstract class Ability {
    private String name;
    private int spiritualityUse;
    private int sequenceBoost = 1;
    private final int sequence; // on what sequence the ability got acquired

    public Ability(String name, int spiritualityUse, int sequence) {
        setName(name);
        setSpiritualityUse(spiritualityUse);
        this.sequence = sequence;
    }


    public Ability(String name, int spiritualityUse, int sequenceBoost, int sequence) {
        setName(name);
        setSpiritualityUse(spiritualityUse);
        setSequenceBoost(sequenceBoost);
        this.sequence = sequence;
    }

    public abstract void onAbilityUse();

    public void setName(String name) {
        this.name = name;
    }

    public void setSpiritualityUse(int spiritualityUse) {
        this.spiritualityUse = spiritualityUse;
    }

    public void setSequenceBoost(int sequenceBoost) {
        this.sequenceBoost = sequenceBoost;
    }

    public String getName() {
        return name;
    }

    public int getSpiritualityUse() {
        return spiritualityUse;
    }

    public int getSequenceBoost() {
        return sequenceBoost;
    }

    public int getSequence() {
        return sequence;
    }
}
