package org.drachentrix.plugins.lordofthemysteries.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class CraftingCauldron extends CauldronBlock {


    private static final Block[] heatingBlocks = {Blocks.FIRE, Blocks.MAGMA_BLOCK, Blocks.LAVA};
    public CraftingCauldron() {
        super(Properties.of().sound(SoundType.METAL).explosionResistance(1).destroyTime(2.0f));
    }

    @NotNull
    @ParametersAreNonnullByDefault
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (interactionHand == InteractionHand.OFF_HAND){

        }
        return super.use(blockState, level, blockPos, player, interactionHand, hitResult);
    }
}
