package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.AbilityRegistry;

import java.util.List;

public class FreezeTarget extends Ability {
    public FreezeTarget(String name, int spiritualityUse, int sequence) {
        super(name, spiritualityUse, sequence);
        AbilityRegistry.registerAbility(name, this::fromNBT);
    }

    public FreezeTarget( int spiritualityUse, int sequence) {
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
    public void onAbilityUse(LivingEntity player) {
        Vec3 lookVec = player.getLookAngle();
        double rayLength = 35.0;
        Vec3 rayEnd = player.getEyePosition(1.5f).add(lookVec.x * rayLength, lookVec.y * rayLength, lookVec.z * rayLength);

        List<Entity> entitiesInRay = player.level().getEntities(player, player.getBoundingBox().expandTowards(rayEnd), entity -> true);
        for (Entity entity : entitiesInRay) {
            if (entity != player && entity instanceof LivingEntity) {
                Vec3 playerToEntity = entity.position().subtract(player.position()).normalize();
                double dotProduct = lookVec.dot(playerToEntity);

                if (dotProduct > 0.97) {
                    Float speed = ((LivingEntity) entity).getSpeed();
                    ((LivingEntity) entity).setSpeed(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ((LivingEntity) entity).setSpeed(speed);
                }
            }
        }
    }
}
