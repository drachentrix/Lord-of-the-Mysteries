package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Utils;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.AbilityRegistry;

import java.util.List;

public class BurningTarget extends Ability {

    public BurningTarget(String name, int spiritualityUse, int sequence) {
        super(name, spiritualityUse, sequence);
        AbilityRegistry.registerAbility(name, this::fromNBT);
    }

    public BurningTarget(int spiritualityUse, int sequence) {
        super("BurningTarget", spiritualityUse, sequence);
        AbilityRegistry.registerAbility("BurningTarget", this::fromNBT);
    }

    @Override
    public Ability fromNBT(CompoundTag nbt) {
        String name = nbt.getString("name");
        int sequence = nbt.getInt("sequence");
        int spiriualityUse = nbt.getInt("spiritualityUse");
        return new BurningTarget(name, spiriualityUse, sequence);
    }

    @Override
    public boolean onAbilityUse(LivingEntity player) {
        List<Entity> entity = Utils.rayCastEntitiesInRange((Player) player);
        if (!entity.isEmpty()){
            igniteEntity(entity.get(0));
        } else{
            List<BlockPos> blocksInRay = Utils.rayCastBlocksInRange((Player) player, 5 );
            if (!blocksInRay.isEmpty()){
                Level level = Minecraft.getInstance().getSingleplayerServer().getLevel(player.level().dimension());
                BlockState fireBlockState = FireBlock.getState(player.level(), blocksInRay.get(0).above());
                level.setBlock(blocksInRay.get(0).above(), fireBlockState, 2);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, blocksInRay.get(0).above());
            }
        }
        return true;
    }

    private void igniteEntity(Entity entity) {
        entity.setSecondsOnFire(15);
    }

}
