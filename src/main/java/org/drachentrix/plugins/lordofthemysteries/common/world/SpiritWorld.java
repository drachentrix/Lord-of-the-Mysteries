package org.drachentrix.plugins.lordofthemysteries.common.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;

public class SpiritWorld {

    public static final ResourceKey<LevelStem> SPIRIT_WORLD_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world"));
    public static final ResourceKey<Level> SPIRIT_WORLD_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world"));
    public static final ResourceKey<DimensionType> SPIRIT_WORLD_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world_type"));

    public static void register(){}
}