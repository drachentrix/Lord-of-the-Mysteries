package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;

import java.util.Random;

public class PotionForm extends Item {
    Sequences sequences;

    public PotionForm(Sequences sequences) {
        super(sequences.getProperties());
        this.sequences = sequences;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            if (!Beyonder.isBeyonder()) {
                if (sequences.getSequence() != 9) {
                    player.displayClientMessage(
                            Component.literal("Pathetic for you Human to think, you could skip Sequences!"), true);
                    Beyonder.loseSanity(new Random().nextInt(10, 60));
                    return super.use(level, player, interactionHand);
                }
                Beyonder.setPathway(sequences.getPathway());
                Beyonder.setSequence(sequences.getSequence());
                Beyonder.setBeyonderStatusActive();
                sequences.getAbilityList().forEach(Beyonder::addAbility);
                Beyonder.setSelectedAbility(sequences.getAbilityList().get(0));
                player.displayClientMessage(Component.literal("You successfully became a sequence " + sequences.getSequence() + " Beyonder of the Pathway " + sequences.getPathway()), true);
            } else {
                if (!sequences.getPathway().equals(Beyonder.getPathway())) {
                    player.displayClientMessage(
                            Component.literal("You feel the Madness devouring you.\n You shouldn't have done that!"), true);
                    Beyonder.loseSanity(new Random().nextInt(0, 16));
                    return super.use(level, player, interactionHand);
                } else {
                    if (sequences.getSequence() < Beyonder.getSequence()) {
                        if (sequences.getSequence() + 1 == Beyonder.getSequence()) {
                            Beyonder.setSequence(sequences.getSequence());
                            sequences.getAbilityList().forEach(Beyonder::addAbility);
                            player.displayClientMessage(Component.literal("You successfully became a sequence " + sequences.getSequence() + " Beyonder of the Pathway " + sequences.getPathway()), true);
                        } else {
                            if (new Random().nextInt(0, 101) < 5) {
                                player.displayClientMessage(
                                        Component.literal("You're craziness has paid off, " +
                                                "you succeeded in advancing from Sequence " + Beyonder.getSequence() + " to " + sequences.getSequence() + "!\n" +
                                                "But you have changed..."), true);
                                Beyonder.loseSanity(Beyonder.getSanity() - new Random().nextInt(3, 10) * Math.abs(Beyonder.getSequence() - sequences.getSequence()));
                                Beyonder.setSequence(sequences.getSequence());
                            } else {
                                player.displayClientMessage(Component.literal("You mere mortal really thought that skipping sequences would end well..."), true);
                                Beyonder.loseSanity(Beyonder.getSanity() - new Random().nextInt(13, 23) * Math.abs(Beyonder.getSequence() - sequences.getSequence()));
                            }
                        }
                    }
                }
            }
            Beyonder.setSpirituality(sequences.getSpirituality());
            Beyonder.setMaxSpirituality(sequences.getSpirituality());
        }
        return super.use(level, player, interactionHand);
    }
}
