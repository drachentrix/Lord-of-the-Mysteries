package org.drachentrix.plugins.lordofthemysteries.common.utils;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AbilityRegistry {
    private static final Map<String, Function<CompoundTag, Ability>> registry = new HashMap<>();

    public static void registerAbility(String type, Function<CompoundTag, Ability> constructor) {
        if(!registry.containsKey(type)){
            registry.put(type, constructor);
        }
    }

    public static Ability fromNBT(CompoundTag nbt) {
        if (nbt != null) {
            String type = nbt.getString("name");
            Function<CompoundTag, Ability> constructor = registry.get(type);
            if (constructor != null) {
                return constructor.apply(nbt);
            }
            throw new IllegalArgumentException("Unknown ability type: " + type);
        }
        return null;
    }
}
