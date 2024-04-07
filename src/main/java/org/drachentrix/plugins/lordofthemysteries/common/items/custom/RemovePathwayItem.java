package org.drachentrix.plugins.lordofthemysteries.common.items.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;

public class RemovePathwayItem extends Item {
    public RemovePathwayItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Beyonder.reset();
        p_41433_.displayClientMessage(Component.literal("You are human again..."), true);
        return super.use(p_41432_, p_41433_, p_41434_);
    }
}
