package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class HolySwordItem extends SwordItem {
    public HolySwordItem() {
        super(AetherItemTiers.HOLY, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT));
    }

    /**
     * Reduces the item's durability by 10 when attacking  undead mobs or mobs that treat healing and harming effects as inverted. This occurs if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     *
     * @param stack    The {@link ItemStack} used to hurt the target
     * @param target   The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the enemy was hurt or not, as a {@link Boolean}.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (target.getMobType() == MobType.UNDEAD || target.isInvertedHealAndHarm()) {
                stack.hurtAndBreak(10, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    /**
     * Deals a base 15 damage to undead mobs or mobs that treat healing and harming effects as inverted, with an extra 2.5 damage for every level of Smite the item has, in addition to the weapon's default damage. This occurs if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource damageSource = event.getSource();
        float damage = event.getAmount();
        if (canPerformAbility(target, damageSource)) {
            ItemStack itemStack = target.getMainHandItem();
            float bonus = 8.25F;
            int smiteModifier = itemStack.getEnchantmentLevel(Enchantments.SMITE);
            if (smiteModifier > 0) {
                bonus += (smiteModifier * 2.5);
            }
            event.setAmount(damage + bonus); // Default ~7 + bonus 8 at minimum.
        }
    }

    /**
     * Basic checks to perform the ability if the source is living, the target is an undead entity, the item is a Holy Sword, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     *
     * @param target The killed {@link LivingEntity}.
     * @param source The attacking {@link DamageSource}.
     */
    private static boolean canPerformAbility(LivingEntity target, DamageSource source) {
        if (source.getDirectEntity() instanceof LivingEntity attacker) {
            if (EquipmentUtil.isFullStrength(attacker)) {
                if (target.getMobType() == MobType.UNDEAD || target.isInvertedHealAndHarm()) {
                    return attacker.getMainHandItem().is(AetherItems.HOLY_SWORD.get());
                }
            }
        }
        return false;
    }
}
