package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.PhoenixArrowAttachment;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class PhoenixBowItem extends BowItem {
    public PhoenixBowItem() {
        super(new Item.Properties().durability(384).rarity(AetherItems.AETHER_LOOT));
    }

    /**
     * Marks any Arrow shot from the bow as a Phoenix Arrow with a default fire infliction time of 20 seconds, and 40 seconds if the bow has Flame.<br><br>
     * This uses {@link PhoenixArrowAttachment#setPhoenixArrow(boolean)} and {@link PhoenixArrowAttachment#setFireTime(int)} to track these values.
     *
     * @param arrow The {@link AbstractArrow} created by the Bow.
     * @return The original {@link AbstractArrow} (the Phoenix Bow doesn't modify it).
     */
    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean isCrit) {
        var arrow = super.createProjectile(level, shooter, weaponStack, projectileStack, isCrit);
        var data = arrow.getAttachedOrCreate(AetherDataAttachments.PHOENIX_ARROW);
        data.setPhoenixArrow(true);
        int defaultTime = 20;
        if (arrow.getOwner() instanceof LivingEntity livingEntity && EnchantmentHelper.getEnchantmentLevel(livingEntity.level().registryAccess().nitrogen_fabric$holderOrThrow(Enchantments.FLAME), livingEntity) > 0) {
            defaultTime = 40;
        }
        data.setFireTime(defaultTime);
        return arrow;
    }


}
