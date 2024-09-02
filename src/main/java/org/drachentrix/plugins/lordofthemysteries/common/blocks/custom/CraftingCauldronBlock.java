package org.drachentrix.plugins.lordofthemysteries.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Sequences;
import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyonderIngredient;
import org.drachentrix.plugins.lordofthemysteries.common.utils.CauldronLogger.CauldronCauldronLogImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class CraftingCauldronBlock extends HorizontalDirectionalBlock {

    private final CauldronCauldronLogImpl logger;
    private final Block[] heatingBlocks = {Blocks.FIRE, Blocks.MAGMA_BLOCK, Blocks.LAVA};

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 1, 2);

    public CraftingCauldronBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1));
        logger = new CauldronCauldronLogImpl();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec((Function<Properties, ? extends HorizontalDirectionalBlock>) CraftingCauldronBlock::new);
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation rotation) {
        return pState.setValue(FACING, rotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(LEVEL);
    }

    @NotNull
    @ParametersAreNonnullByDefault
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (player.getItemInHand(interactionHand).is(Items.WATER_BUCKET) && blockState.getValue(LEVEL).equals(1)) {
            level.setBlock(blockPos, blockState.setValue(LEVEL, 2), 3);
            player.setItemInHand(interactionHand, new ItemStack(Items.BUCKET));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (player.getItemInHand(interactionHand).is(Items.BUCKET) && blockState.getValue(LEVEL).equals(2)) {
            level.setBlock(blockPos, blockState.setValue(LEVEL, 1), 3);
            player.setItemInHand(interactionHand, new ItemStack(Items.WATER_BUCKET));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (interactionHand == InteractionHand.MAIN_HAND && player.getItemInHand(interactionHand).is(Items.GLASS_BOTTLE) && blockState.getValue(LEVEL).equals(2)) {
            Sequences recipe = checkForValidRecipe();
            if (recipe != null && validHeatBlock(blockPos)) { //maybe noch adden ob es richtige heat hat
                ResourceLocation itemLoc = new ResourceLocation("lordofthemysteries", recipe.getPotionName());
                net.minecraft.world.item.Item item = ForgeRegistries.ITEMS.getValue(itemLoc);
                if (item != null) {
                    ItemStack itemStack = new ItemStack(item); //spaeter check obs wirklich geht
                    player.getInventory().add(itemStack);
                    player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                }
            } else {
                // konsequenz noch adden (Geplant so ne giftwolke und dann von dem Pathway der am meisten ähnelt ein Artifact
            }
            logger.clearLog();
        } else if (interactionHand == InteractionHand.MAIN_HAND && !player.getMainHandItem().is(ItemStack.EMPTY.getItem())) {
            if (player.getMainHandItem().getItem() instanceof BeyonderIngredient && blockState.getValue(LEVEL).equals(2)) {
                logger.add((BeyonderIngredient) player.getMainHandItem().getItem());
                player.getInventory().removeItem(player.getMainHandItem());
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, hitResult);
    }

    private Sequences checkForValidRecipe() {
        boolean check;
        List<BeyonderIngredient> ingredientList = logger.readLog();

        if (!ingredientList.isEmpty()) {
            List<Sequences> sequences = Beyonder.isBeyonder() ? Sequences.getSequencesBasedOnPathway(Beyonder.getPathway()) : Sequences.getAllSequences();
            for (Sequences sequence : sequences) {
                check = true;
                for (BeyonderIngredient ingredient : ingredientList) {
                    if (!sequence.getIngredientList().contains(ingredient)) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    return sequence;

                }
            }
        }
        return null;

    }

    private boolean validHeatBlock(BlockPos pos) {
        try {
            BlockState level = Minecraft.getInstance().player.level().getBlockState(pos.below());
            return Arrays.stream(heatingBlocks)
                    .anyMatch(block -> block.defaultBlockState() == level);
        } catch (NullPointerException npe) {
            System.err.println("There was a problem with getting the level of the player." + npe);
            return false;
        }

    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }
}
