package org.drachentrix.plugins.lordofthemysteries.common.utils;

import java.util.Objects;

public class BeyonderIngredient extends net.minecraft.world.item.Item {

    private final String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeyonderIngredient that = (BeyonderIngredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public BeyonderIngredient(Properties p_41383_, String name) {
        super(p_41383_);
        this.name = name;
    }
}
