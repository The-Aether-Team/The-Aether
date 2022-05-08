package com.gildedgames.aether.common.item.combat.abilities.armor;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

public interface PhoenixArmor {
    static void boostLavaSwimming(LivingEntity entity) {
        if (EquipmentUtil.hasFullPhoenixSet(entity)) {
            entity.clearFire();
            if (entity.isInLava()) {
                entity.resetFallDistance();
                float defaultBoost = 10.5F;
                float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
                if (depthStriderModifier > 0.0F) {
                    defaultBoost += depthStriderModifier * 1.5F;
                }
                Vec3 movement = entity.getDeltaMovement().multiply(defaultBoost, 0.25F, defaultBoost);
                entity.move(MoverType.SELF, movement);
            }
            if (entity.level instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.FLAME,
                        entity.getX() + (level.getRandom().nextGaussian() / 5.0D),
                        entity.getY() + (level.getRandom().nextGaussian() / 3.0D),
                        entity.getZ() + (level.getRandom().nextGaussian() / 5.0D),
                        1, 0.0D, 0.0D, 0.0D, 0.0F);
            }
        }
    }

    static void damageArmor(LivingEntity entity) {
        if (entity.isInWaterRainOrBubble()) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack equippedStack = entity.getItemBySlot(equipmentSlot);
                    if (equippedStack.is(AetherItems.PHOENIX_HELMET.get())) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_HELMET.get()), equipmentSlot);
                    } else if (equippedStack.is(AetherItems.PHOENIX_CHESTPLATE.get())) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_CHESTPLATE.get()), equipmentSlot);
                    } else if (equippedStack.is(AetherItems.PHOENIX_LEGGINGS.get())) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_LEGGINGS.get()), equipmentSlot);
                    } else if (equippedStack.is(AetherItems.PHOENIX_BOOTS.get())) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_BOOTS.get()), equipmentSlot);
                    }
                }
            }
            CuriosApi.getCuriosHelper().findFirstCurio(entity, AetherItems.PHOENIX_GLOVES.get()).ifPresent((slotResult) -> breakPhoenixGloves(entity, slotResult, new ItemStack(AetherItems.OBSIDIAN_GLOVES.get())));
        }
    }

    private static void breakPhoenixArmor(LivingEntity entity, ItemStack equippedStack, ItemStack outcomeStack, EquipmentSlot slot) {
        if (entity.level.getGameTime() % 10 == 0) {
            equippedStack.hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(slot));
            if (entity.getItemBySlot(slot).isEmpty()) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(equippedStack), outcomeStack);
                if (equippedStack.hasTag()) {
                    outcomeStack.setTag(equippedStack.getTag());
                }
                entity.setItemSlot(slot, outcomeStack);
            }
        }
    }

    private static void breakPhoenixGloves(LivingEntity entity, SlotResult slotResult, ItemStack outcomeStack) {
        if (entity.level.getGameTime() % 10 == 0) {
            slotResult.stack().hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            if (CuriosApi.getCuriosHelper().findFirstCurio(entity, AetherItems.PHOENIX_GLOVES.get()).isEmpty()) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(slotResult.stack()), outcomeStack);
                if (slotResult.stack().hasTag()) {
                    outcomeStack.setTag(slotResult.stack().getTag());
                }
                CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(iCuriosItemHandler -> {
                    Map<String, ICurioStacksHandler> curios = iCuriosItemHandler.getCurios();
                    ICurioStacksHandler inv = curios.get(slotResult.slotContext().identifier());
                    if (inv != null) {
                        IDynamicStackHandler stackHandler = inv.getStacks();
                        stackHandler.setStackInSlot(slotResult.slotContext().index(), outcomeStack);
                    }
                });
            }
        }
    }

    static boolean extinguishUser(LivingEntity entity, DamageSource source) {
        return EquipmentUtil.hasFullPhoenixSet(entity) && source.isFire();
    }
}
