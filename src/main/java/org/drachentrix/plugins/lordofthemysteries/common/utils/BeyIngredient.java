package org.drachentrix.plugins.lordofthemysteries.common.utils;

import net.minecraft.world.item.Item;

import java.util.Objects;

public class BeyIngredient extends Item {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeyIngredient that = (BeyIngredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    private String name;
    public BeyIngredient(Properties p_41383_) {
        super(p_41383_);
    }
}
