package org.drachentrix.plugins.lordofthemysteries.common.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;

public abstract class Ability {
    private String name;
    private int spiritualityUse;
    private int sequenceBoost = 1;
    private final int sequence; // on what sequence the ability got acquired

    public Ability(String name, int spiritualityUse, int sequence) {
        setName(name);
        setSpiritualityUse(spiritualityUse - spiritualityUse % (10 - Beyonder.getSequence()));
        this.sequence = sequence;
    }

    public abstract void onAbilityUse(LivingEntity player);

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
