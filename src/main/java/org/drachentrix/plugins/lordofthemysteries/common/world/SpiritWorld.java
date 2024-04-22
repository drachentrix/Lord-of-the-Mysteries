package org.drachentrix.plugins.lordofthemysteries.common.world;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;

import java.util.List;
import java.util.OptionalLong;

public class SpiritWorld {

    public static final ResourceKey<LevelStem> SPIRIT_WORLD_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world"));
    public static final ResourceKey<Level> SPIRIT_WORLD_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world"));
    public static final ResourceKey<DimensionType> SPIRIT_WORLD_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world_type"));


    public static void bootstapType(BootstapContext<DimensionType> context) {
        context.register(SPIRIT_WORLD_DIMENSION_TYPE,  new DimensionType(
                OptionalLong.empty(),
                false,
                false,
                false,
                false,
                1.0,
                false,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(true, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTyper = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList(List.of(Pair.of(
                                Climate.parameters(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f), biomeHolderGetter.getOrThrow(Biomes.THE_VOID)
                        ))
                )), noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED)
        );
        LevelStem stem = new LevelStem(dimTyper.getOrThrow(SPIRIT_WORLD_DIMENSION_TYPE), noiseBasedChunkGenerator);

    }

}
