package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public interface GravititeWeapon {
    /**
     * Launches an entity in the air if the target is launchable, the target is on the ground, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     */
    default void launchEntity(LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (!target.getType().is(AetherTags.Entities.UNLAUNCHABLE) && target.isOnGround()) {
                target.push(0.0, 1.0, 0.0);
                if (target instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                }
            }
        }
    }
}
