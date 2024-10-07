package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.mixin.mixins.common.accessor.ZombifiedPiglinAccessor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class PigSlayerItem extends SwordItem {
    public PigSlayerItem() {
        super(AetherItemTiers.PIG_SLAYER, new Item.Properties().rarity(AetherItems.AETHER_LOOT).attributes(SwordItem.createAttributes(AetherItemTiers.PIG_SLAYER, 3.0F, -2.4F)));
    }

    /**
     * Spawns flame particles around the target when hit and alerts Zombified Piglins.
     *
     * @param stack    The {@link ItemStack} used to hurt the target
     * @param target   The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the enemy was hurt or not, as a {@link Boolean}.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (target.getType().is(AetherTags.Entities.PIGS)) {
                if (target instanceof ZombifiedPiglin zombifiedPiglin) {
                    if (!(attacker instanceof Player player) || !player.isCreative()) {
                        ZombifiedPiglinAccessor zombifiedPiglinAccessor = (ZombifiedPiglinAccessor) zombifiedPiglin;
                        zombifiedPiglin.setTarget(attacker);
                        zombifiedPiglinAccessor.callAlertOthers();
                    }
                }
                if (target.level() instanceof ServerLevel level) {
                    for (int i = 0; i < 20; i++) {
                        double d0 = level.getRandom().nextGaussian() * 0.02;
                        double d1 = level.getRandom().nextGaussian() * 0.02;
                        double d2 = level.getRandom().nextGaussian() * 0.02;
                        double d3 = 5.0;
                        double x = target.getX() + (level.getRandom().nextFloat() * target.getBbWidth() * 2.0) - target.getBbWidth() - d0 * d3;
                        double y = target.getY() + (level.getRandom().nextFloat() * target.getBbHeight()) - d1 * d3;
                        double z = target.getZ() + (level.getRandom().nextFloat() * target.getBbWidth() * 2.0) - target.getBbWidth() - d2 * d3;
                        level.sendParticles(ParticleTypes.FLAME, x, y, z, 1, d0, d1, d2, 0.0);
                    }
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    /**
     * @see Aether#eventSetup()
     * Deals 20-22 hearts of damage to the target if they're a Pig-type entity and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.<br><br>
     */
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource damageSource = event.getSource();
        float damage = event.getAmount();
        if (canPerformAbility(target, damageSource)) {
            event.setAmount(damage + 16.0F);
        }
    }

    /**
     * Basic checks to perform the ability if the source is living, the target is a Pig-type entity, the item is a Pig Slayer, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     *
     * @param target The killed {@link LivingEntity}.
     * @param source The attacking {@link DamageSource}.
     */
    private static boolean canPerformAbility(LivingEntity target, DamageSource source) {
        if (source.getDirectEntity() instanceof LivingEntity attacker) {
            if (EquipmentUtil.isFullStrength(attacker)) {
                if (target.getType().is(AetherTags.Entities.PIGS)) {
                    return attacker.getMainHandItem().is(AetherItems.PIG_SLAYER.get());
                }
            }
        }
        return false;
    }
}
