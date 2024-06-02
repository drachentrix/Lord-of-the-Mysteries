package org.drachentrix.plugins.lordofthemysteries.common.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;

import java.util.Objects;

public abstract class Ability {
    private String name;
    private int spiritualityUse;
    private int sequenceBoost = 1; //ka fur was ich den wollte
    private final int sequence; // on what sequence the ability got acquired

    public Ability(String name, int spiritualityUse, int sequence) {
        setName(name);
        setSpiritualityUse(spiritualityUse - spiritualityUse % (10 - Beyonder.getSequence()));
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "Equipped: " + name + '\'' +
                " with spiritualityUse of " + spiritualityUse;
    }

    public CompoundTag toNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("name", this.name);
        nbt.putInt("sequenceBoost", this.sequence);
        nbt.putInt("spiritualityUse", this.spiritualityUse);
        return nbt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability ability = (Ability) o;
        return Objects.equals(name, ability.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public abstract Ability fromNBT(CompoundTag nbt);

    public abstract boolean onAbilityUse(LivingEntity player);

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
