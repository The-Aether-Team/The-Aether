package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface GravititeWeapon {
    /**
     * Launches an entity in the air if the target is launchable, the target is on the ground, and if the attacker attacked with full attack strength if they're a player.
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     */
    default void launchEntity(LivingEntity target, LivingEntity attacker) {
        if ((attacker instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(attacker instanceof Player)) {
            if (!target.getType().is(AetherTags.Entities.UNLAUNCHABLE) && target.isOnGround()) {
                target.push(0.0, 1.0, 0.0);
                if (target instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                }
            }
        }
    }
}
