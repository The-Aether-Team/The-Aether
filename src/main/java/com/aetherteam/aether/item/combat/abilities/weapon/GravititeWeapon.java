package com.aetherteam.aether.item.combat.abilities.weapon;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.utils.FabricUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public interface GravititeWeapon {
    /**
     * Launches an entity in the air if the target is launchable, the target is on the ground, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     * @param target The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @see com.aetherteam.aether.item.combat.GravititeSwordItem
     */
    default void launchEntity(LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (!target.getType().is(AetherTags.Entities.UNLAUNCHABLE) && (target.onGround() || FabricUtils.isInFluidType(target))) {
                target.push(0.0, 1.0, 0.0);
                if (target instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                }
            }
        }
    }
}
