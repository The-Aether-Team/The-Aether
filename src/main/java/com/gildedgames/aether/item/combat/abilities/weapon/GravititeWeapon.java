package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface GravititeWeapon {
    /**
     * Launches an entity in the air if the stack is a gravitite weapon, the target is launchable, the target is on the ground, and if the attacker attacked with full attack strength if they're a player.
     * @param stack The stack used to hurt the target.
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     */
    default void launchEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.is(AetherTags.Items.GRAVITITE_WEAPONS) && !target.getType().is(AetherTags.Entities.UNLAUNCHABLE) && target.isOnGround()
                && ((attacker instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(attacker instanceof Player))) {
            target.push(0.0, 1.0, 0.0);
            if (target instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
            }
        }
    }
}
