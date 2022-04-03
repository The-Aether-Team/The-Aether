package com.gildedgames.aether.common.item.combat.abilities.armor;

import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface GravititeArmor {
    static void boostedJump(LivingEntity entity) {
        if (EquipmentUtil.hasFullGravititeSet(entity)) {
            if (entity instanceof Player player) {
                if (!player.isShiftKeyDown()) {
                    player.push(0.0, 1.0, 0.0);
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                    }
                }
            }
        }
    }
}
