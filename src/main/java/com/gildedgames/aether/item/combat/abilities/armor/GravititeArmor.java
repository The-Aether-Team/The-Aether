package com.gildedgames.aether.item.combat.abilities.armor;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface GravititeArmor {
    static void boostedJump(LivingEntity entity) {
        if (EquipmentUtil.hasFullGravititeSet(entity)) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.isGravititeJumpActive()) {
                        player.push(0.0, 1.0, 0.0);
                        if (player instanceof ServerPlayer serverPlayer) {
                            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                        }
                    }
                });
            }
        }
    }
}
