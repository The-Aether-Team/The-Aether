package com.gildedgames.aether.item.combat.abilities.armor;

import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface ObsidianArmor {
    /**
     * Halves the damage taken by an entity if wearing a full set of Obsidian Armor.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param amount The original damage amount as a {@link Float}.
     * @return The new damage amount as a {@link Float}.
     * @see com.gildedgames.aether.event.listeners.abilities.ArmorAbilityListener#onEntityHurt(LivingHurtEvent)
     */
    static float protectUser(LivingEntity entity, float amount) {
        return EquipmentUtil.hasFullObsidianSet(entity) ? (amount / 2.0F) : amount;
    }
}
