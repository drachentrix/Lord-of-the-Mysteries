package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Utils;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.AbilityRegistry;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Scheduler;

public class FreezeTarget extends Ability {
    private static boolean isFreezed = false;

    public static boolean getFreezed() {
        return isFreezed;
    }

    public FreezeTarget(String name, int spiritualityUse, int sequence) {
        super(name, spiritualityUse, sequence);
        AbilityRegistry.registerAbility(name, this::fromNBT);
    }

    public FreezeTarget(int spiritualityUse, int sequence) {
        super("Freeze Target", spiritualityUse, sequence);
        AbilityRegistry.registerAbility("Freeze Target", this::fromNBT);
    }

    @Override
    public Ability fromNBT(CompoundTag nbt) {
        String name = nbt.getString("name");
        int sequence = nbt.getInt("sequence");
        int spiriualityUse = nbt.getInt("spiritualityUse");
        return new FreezeTarget(name, spiriualityUse, sequence);
    }

    @Override
    public boolean onAbilityUse(LivingEntity player) {
        for (Entity entity : Utils.rayCastEntitiesInRange((Player) player)) {
            if (entity instanceof LivingEntity) {
                Player player1 = (Player) player;
                player1.displayClientMessage(Component.literal("Entity  reached"), true);
                stopMovement((LivingEntity) entity, 20 + (Beyonder.getSequence() % 9) + 20);
            }
        }
        return true;
    }

    public void stopMovement(LivingEntity entity, int durationInTicks) {
        if (entity instanceof Player) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Integer.MAX_VALUE, 255));
        } else {
            isFreezed = true;
        }

        Scheduler.schedule(() -> {
            entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            isFreezed = false;
        }, durationInTicks);
    }
}
