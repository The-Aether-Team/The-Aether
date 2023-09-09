package com.aetherteam.aether.item.combat.abilities.weapon;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;

public interface HolystoneWeapon {
    /**
     * Drops an Ambrosium Shard at a target's position if it can drop Ambrosium Shards, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}, with a 1/25 chance.
     * @param target The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @see com.aetherteam.aether.item.combat.HolystoneSwordItem
     */
    default void dropAmbrosium(LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (!target.getType().is(AetherTags.Entities.NO_AMBROSIUM_DROPS) && target.level().getRandom().nextInt(25) == 0) {
                target.spawnAtLocation(AetherItems.AMBROSIUM_SHARD.get());
            }
        }
    }
}
