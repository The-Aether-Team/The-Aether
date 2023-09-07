package com.aetherteam.aether.item.combat.abilities.armor;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

public interface PhoenixArmor {
    /**
     * Boosts the entity's movement in lava if wearing a full set of Phoenix Armor. The default boost is modified based on duration in lava and whether the boots have Depth Strider.<br><br>
     * Wearing Phoenix Armor also clears any fire from the wearer and spawns flame particles around them.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
    static void boostLavaSwimming(LivingEntity entity) {
        if (EquipmentUtil.hasFullPhoenixSet(entity)) {
            entity.clearFire();
            if (entity.isInLava()) {
                entity.resetFallDistance();
                if (entity instanceof Player player) {
                    AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                        float defaultBoost = boostWithDepthStrider(entity, 1.75F, 1.0F);
                        aetherPlayer.setPhoenixSubmergeLength(Math.min(aetherPlayer.getPhoenixSubmergeLength() + 0.1, 1.0));
                        defaultBoost *= aetherPlayer.getPhoenixSubmergeLength();
                        entity.moveRelative(0.04F * defaultBoost, new Vec3(entity.xxa, entity.yya, entity.zza));
                    });
                } else {
                    float defaultBoost = boostWithDepthStrider(entity, 1.75F, 1.0F);
                    entity.moveRelative(0.04F * defaultBoost, new Vec3(entity.xxa, entity.yya, entity.zza));
                }
            }
            if (entity.getLevel() instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.FLAME,
                        entity.getX() + (level.getRandom().nextGaussian() / 5.0),
                        entity.getY() + (level.getRandom().nextGaussian() / 3.0),
                        entity.getZ() + (level.getRandom().nextGaussian() / 5.0),
                        1, 0.0, 0.0, 0.0, 0.0F);
            }
        }
        if (!EquipmentUtil.hasFullPhoenixSet(entity) || !entity.isInLava()) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> aetherPlayer.setPhoenixSubmergeLength(0.0));
            }
        }
    }

    /**
     * Boosts the entity's vertical movement in lava if wearing a full set of Phoenix Armor. The default boost is modified based on duration in lava and whether the boots have Depth Strider.<br><br>
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
    static void boostVerticalLavaSwimming(LivingEntity entity) {
        if (EquipmentUtil.hasFullPhoenixSet(entity)) {
            entity.clearFire();
            if (entity.isInLava()) {
                entity.resetFallDistance();
                if (entity instanceof Player player) {
                    AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                        float defaultBoost = boostWithDepthStrider(entity, 1.5F, 0.05F);
                        aetherPlayer.setPhoenixSubmergeLength(Math.min(aetherPlayer.getPhoenixSubmergeLength() + 0.1, 1.0));
                        defaultBoost *= aetherPlayer.getPhoenixSubmergeLength();
                        if (entity.getDeltaMovement().y() > 0 || entity.isCrouching()) {
                            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, defaultBoost, 1.0));
                        }
                    });
                } else {
                    float defaultBoost = boostWithDepthStrider(entity, 1.5F, 0.05F);
                    if (entity.getDeltaMovement().y() > 0 || entity.isCrouching()) {
                        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, defaultBoost, 1.0));
                    }
                }
            }
        }
    }

    /**
     * Adds an extra 1.5 to the boost for every Depth Strider level up to Depth Strider 3.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param start The starting value as a {@link Float}.
     * @param increment The increment value as a {@link Float}.
     * @return The modified boost as a {@link Float}.
     */
    private static float boostWithDepthStrider(LivingEntity entity, float start, float increment) {
        float defaultBoost = start;
        float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
        if (depthStriderModifier > 0.0F) {
            defaultBoost += depthStriderModifier * increment;
        }
        return defaultBoost;
    }

    /**
     * Slowly increments a timer to convert a player's Phoenix Armor if they're in water, rain, or a bubble column.<br><br>
     * This is done by looping through the armor {@link EquipmentSlot}s and also checking with {@link top.theillusivec4.curios.common.CuriosHelper#findFirstCurio(LivingEntity, Item)} for the gloves.<br><br>
     * The methods used for this are {@link PhoenixArmor#breakPhoenixArmor(LivingEntity, ItemStack, ItemStack, EquipmentSlot)} and {@link PhoenixArmor#breakPhoenixGloves(LivingEntity, SlotResult, ItemStack)}.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
    static void damageArmor(LivingEntity entity) {
        if (entity instanceof Player player) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (EquipmentUtil.hasAnyPhoenixArmor(entity) && entity.isInWaterRainOrBubble()) {
                    if (entity.getLevel().getGameTime() % 15 == 0) {
                        aetherPlayer.setObsidianConversionTime(aetherPlayer.getObsidianConversionTime() + 1);
                        entity.getLevel().levelEvent(1501, entity.blockPosition(), 0);
                    }
                } else {
                    aetherPlayer.setObsidianConversionTime(0);
                }
                if (aetherPlayer.getObsidianConversionTime() >= aetherPlayer.getObsidianConversionTimerMax()) {
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
                    SlotResult slotResult = EquipmentUtil.getCurio(entity, AetherItems.PHOENIX_GLOVES.get());
                    if (slotResult != null) {
                        breakPhoenixGloves(entity, slotResult, new ItemStack(AetherItems.OBSIDIAN_GLOVES.get()));
                    }
                }
            });
        }
    }

    /**
     * Replaces the armor stack and copies over its tags and enchantments.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param equippedStack The worn {@link ItemStack}.
     * @param outcomeStack The replacement {@link ItemStack}.
     * @param slot The {@link EquipmentSlot} of the armor item.
     */
    private static void breakPhoenixArmor(LivingEntity entity, ItemStack equippedStack, ItemStack outcomeStack, EquipmentSlot slot) {
        EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(equippedStack), outcomeStack);
        if (equippedStack.hasTag()) {
            outcomeStack.setTag(equippedStack.getTag());
        }
        entity.setItemSlot(slot, outcomeStack);
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.INVENTORY_CHANGED.trigger(serverPlayer, serverPlayer.getInventory(), outcomeStack);
        }
    }

    /**
     * Replaces the gloves stack and copies over its tags and enchantments.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param slotResult The {@link SlotResult} of the Curio item.
     * @param outcomeStack The replacement {@link ItemStack}.
     */
    private static void breakPhoenixGloves(LivingEntity entity, SlotResult slotResult, ItemStack outcomeStack) {
        EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(slotResult.stack()), outcomeStack);
        if (slotResult.stack().hasTag()) {
            outcomeStack.setTag(slotResult.stack().getTag());
        }
        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(iCuriosItemHandler -> {
            Map<String, ICurioStacksHandler> curios = iCuriosItemHandler.getCurios(); // Map of Curio slot names -> slot stack handlers.
            ICurioStacksHandler inv = curios.get(slotResult.slotContext().identifier()); // Stack handler for the Curio slot, gotten using the identifier through slotResult.
            if (inv != null) {
                IDynamicStackHandler stackHandler = inv.getStacks();
                stackHandler.setStackInSlot(slotResult.slotContext().index(), outcomeStack); // Changes stack in slot using stack handler.
            }
        });
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.INVENTORY_CHANGED.trigger(serverPlayer, serverPlayer.getInventory(), outcomeStack);
        }
    }

    /**
     * Prevents a user from receiving fire damage if wearing a full set of Phoenix Armor.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param source The attacking {@link DamageSource}.
     * @return Whether the fire damage should be cancelled, as a {@link Boolean}.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityAttack(LivingAttackEvent)
     */
    static boolean extinguishUser(LivingEntity entity, DamageSource source) {
        return EquipmentUtil.hasFullPhoenixSet(entity) && source.is(DamageTypeTags.IS_FIRE);
    }
}
