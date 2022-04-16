package com.gildedgames.aether.common.item.combat.abilities.armor;

import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;

public interface ObsidianArmor {
    static float protectUser(LivingEntity entity, float amount) {
        return EquipmentUtil.hasFullObsidianSet(entity) ? (amount / 2.0F) : amount;
    }
}
