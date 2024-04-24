package org.drachentrix.plugins.lordofthemysteries.common.world;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenProvider extends DatapackBuiltinEntriesProvider {

   // public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
       //     .add(Registries.DIMENSION_TYPE, SpiritWorld::bootstapType)
      //      .add(Registries.LEVEL_STEM, SpiritWorld::bootstapStem);

    public WorldGenProvider(PackOutput output, CompletableFuture<RegistrySetBuilder.PatchedRegistries> registries, Set<String> modIds) {
        super(output, registries, modIds);
    }
}
