package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.AbilityRegistry;

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

        for (Entity entity : Utils.rayCastEntitiesInRange((Player) player)) {
            if (entity instanceof LivingEntity) {
                Player player1 = (Player) player;
                player1.displayClientMessage(Component.literal("Entity  reached"), true);
                igniteEntity((LivingEntity) entity, 120 + (Beyonder.getSequence() % 9) * 20);
            }
        }
        return true;
    }

    private void igniteEntity(LivingEntity entity, int fireTicks) {
        entity.setSecondsOnFire(fireTicks);
    }

}
