package org.drachentrix.plugins.lordofthemysteries.common.world;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.Executor;

public class SpiritWorld {

    public static final ResourceKey<LevelStem> SPIRIT_WORLD_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world"));
    public static final ResourceKey<Level> SPIRIT_WORLD_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world"));
    public static final ResourceKey<DimensionType> SPIRIT_WORLD_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(LordOfTheMysteries.MODID, "spirit_world_type"));


    public static void register(){

    }

}
