package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.joml.Vector3d;

import java.util.List;

public class FreezeTarget extends Ability {
    public FreezeTarget(int spiritualityUse, int sequence) {
        super("Freeze Target", spiritualityUse, sequence);
    }

    @Override
    public void onAbilityUse(LivingEntity player) {
        Minecraft.getInstance().player.displayClientMessage(Component.literal("WELP"), true);
        Vec3 lookVec = player.getLookAngle();
        double rayLength = 50.0;
        Vec3 rayEnd = player.getEyePosition(1.0f).add(lookVec.x * rayLength, lookVec.y * rayLength, lookVec.z * rayLength);

        List<Entity> entitiesInRay = player.level().getEntities(player, player.getBoundingBox().expandTowards(rayEnd), entity -> true);
        for (Entity entity : entitiesInRay) {
            if (entity != player && !(entity instanceof ItemEntity)) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal("Player is looking at entity"), true);
            }
        }
    }
}
