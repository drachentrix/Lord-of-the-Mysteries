package org.drachentrix.plugins.lordofthemysteries.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Sequences;
import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyIngredient;
import org.drachentrix.plugins.lordofthemysteries.common.utils.CauldronLogger.LogImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CraftingCauldronBlock extends HorizontalDirectionalBlock {

    private final LogImpl logger;
    private static final Block[] heatingBlocks = {Blocks.FIRE, Blocks.MAGMA_BLOCK, Blocks.LAVA};

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CraftingCauldronBlock(Properties properties) {
        super(properties);
        logger = new LogImpl();
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
    public BlockState rotate(BlockState pState, Rotation rotation) {
        return pState.setValue(FACING, rotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING);
    }

    @NotNull
    @ParametersAreNonnullByDefault
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (interactionHand == InteractionHand.OFF_HAND){
            Map<Boolean, Sequences> recipe = checkForValidRecipe();
            if (!recipe.keySet().isEmpty() && validHeatBlock(blockPos)){ //maybe noch adden ob es richtige heat hat
                ResourceLocation itemLoc = new ResourceLocation("lordofthemysteries", recipe.get(true).getPotionName());
                Item item = ForgeRegistries.ITEMS.getValue(itemLoc);
                if (item != null){
                    ItemStack itemStack = new ItemStack(item); //spaeter check obs wirklich geht
                    player.getInventory().add(itemStack);
                }
            } else{
                // konsequenz noch adden (Geplant so ne giftwolke und dann von dem Pathway der am meisten ähnelt ein Artifact
            }
            logger.clearLog();
        } else if (interactionHand == InteractionHand.MAIN_HAND && !player.getMainHandItem().is(ItemStack.EMPTY.getItem())){
            if (player.getMainHandItem().getItem() instanceof BeyIngredient && blockState.getFluidState().is(Fluids.WATER)){
                logger.add((BeyIngredient) player.getMainHandItem().getItem());
                player.getInventory().removeItem(player.getMainHandItem());
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, hitResult);
    }

    private Map<Boolean, Sequences> checkForValidRecipe(){
        boolean check;
        for(Sequences sequences: Sequences.getAllSequences()) {
            check = true;
            for (BeyIngredient ingredient : logger.readLog()) {
                if (!sequences.getIngredientList().contains(ingredient)){
                    check = false;
                    break;
                }
            }
            if (check){
                HashMap<Boolean, Sequences> tmp = new HashMap<>();
                tmp.put(check, sequences);
                return tmp;

            }
        }
        return new HashMap<>();

    }

    private boolean validHeatBlock(BlockPos pos){
        BlockPos beneath = pos.below();
        return Arrays.stream(heatingBlocks).anyMatch(block -> {
            assert Minecraft.getInstance().player != null;
            return defaultBlockState() == Minecraft.getInstance().player.level().getBlockState(beneath);
        });
    }
}
