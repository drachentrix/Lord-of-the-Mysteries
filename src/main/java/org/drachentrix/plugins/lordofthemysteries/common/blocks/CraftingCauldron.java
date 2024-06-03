package org.drachentrix.plugins.lordofthemysteries.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Sequences;
import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyIngredient;
import org.drachentrix.plugins.lordofthemysteries.common.utils.CauldronLogger.LogImpl;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

public class CraftingCauldron extends CauldronBlock {

    private final LogImpl logger;
    private static final Block[] heatingBlocks = {Blocks.FIRE, Blocks.MAGMA_BLOCK, Blocks.LAVA};
    public CraftingCauldron() {
        super(Properties.of().sound(SoundType.METAL).explosionResistance(1).destroyTime(2.0f));
        logger = new LogImpl();
    }

    @NotNull
    @ParametersAreNonnullByDefault
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (interactionHand == InteractionHand.OFF_HAND){
            Map<Boolean, Sequences> tmp = checkForValidRecipe();
            if (!tmp.keySet().isEmpty()){
                logger.clearLog();
                ResourceLocation itemLoc = new ResourceLocation("lordofthemysteries", tmp.get(true).getPotionName());
                Item item = ForgeRegistries.ITEMS.getValue(itemLoc);
                if (item != null){
                    ItemStack itemStack = new ItemStack(item); //spaeter check obs wirklich geht
                    player.getInventory().add(itemStack);
                }
            } else{
                logger.clearLog();
                // konsequenz noch adden
            }
        } else if (interactionHand == InteractionHand.MAIN_HAND && !player.getMainHandItem().is(ItemStack.EMPTY.getItem())){
            if (player.getMainHandItem().getItem() instanceof BeyIngredient){
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
        HashMap<Boolean, Sequences> tmp = new HashMap<>();
        return tmp;
        //kann maybe logikFehler sein
    }
}
