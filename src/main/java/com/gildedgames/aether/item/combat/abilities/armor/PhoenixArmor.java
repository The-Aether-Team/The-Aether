package com.gildedgames.aether.item.combat.abilities.armor;

import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
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
     * Boosts the entity's movement in lava if wearing a full set of Phoenix Armor. The default boost is a multiplier of 10.5, but an extra 1.5 is added for every Depth Strider level up to Depth Strider 3.<br><br>
     * Wearing Phoenix Armor also clears any fire from the wearer and spawns flame particles around them.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.gildedgames.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
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
            if (entity.getLevel() instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.FLAME,
                        entity.getX() + (level.getRandom().nextGaussian() / 5.0D),
                        entity.getY() + (level.getRandom().nextGaussian() / 3.0D),
                        entity.getZ() + (level.getRandom().nextGaussian() / 5.0D),
                        1, 0.0D, 0.0D, 0.0D, 0.0F);
            }
        }
    }

    /**
     * Slowly damages the wearer's Phoenix Armor if they're in water, rain, or a bubble column.<br><br>
     * This is done by looping through the armor {@link EquipmentSlot}s and also checking with {@link top.theillusivec4.curios.common.CuriosHelper#findFirstCurio(LivingEntity, Item)} for the gloves.<br><br>
     * The methods used for this are {@link PhoenixArmor#breakPhoenixArmor(LivingEntity, ItemStack, ItemStack, EquipmentSlot)} and {@link PhoenixArmor#breakPhoenixGloves(LivingEntity, SlotResult, ItemStack)}.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.gildedgames.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
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

    /**
     * Damages the armor stack every 10 server ticks. Once broken, the stack will be replaced and the enchantments and tags will be copied over.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param equippedStack The worn {@link ItemStack}.
     * @param outcomeStack The replacement {@link ItemStack}.
     * @param slot The {@link EquipmentSlot} of the armor item.
     */
    private static void breakPhoenixArmor(LivingEntity entity, ItemStack equippedStack, ItemStack outcomeStack, EquipmentSlot slot) {
        if (entity.getLevel().getGameTime() % 10 == 0) {
            equippedStack.hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(slot));
            if (entity.getItemBySlot(slot).isEmpty()) { // Slot is empty if item breaks.
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(equippedStack), outcomeStack);
                if (equippedStack.hasTag()) {
                    outcomeStack.setTag(equippedStack.getTag());
                }
                entity.setItemSlot(slot, outcomeStack);
            }
        }
    }

    /**
     * Damages the glove stack every 10 server ticks. Once broken, the stack will be replaced and the enchantments and tags will be copied over.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param slotResult The {@link SlotResult} of the Curio item.
     * @param outcomeStack The replacement {@link ItemStack}.
     */
    private static void breakPhoenixGloves(LivingEntity entity, SlotResult slotResult, ItemStack outcomeStack) {
        if (entity.getLevel().getGameTime() % 10 == 0) {
            slotResult.stack().hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            if (CuriosApi.getCuriosHelper().findFirstCurio(entity, AetherItems.PHOENIX_GLOVES.get()).isEmpty()) { // Can't find Curio anymore if it broke.
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
            }
        }
    }

    /**
     * Prevents a user from receiving fire damage if wearing a full set of Phoenix Armor.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @param source The attacking {@link DamageSource}.
     * @return Whether the fire damage should be cancelled, as a {@link Boolean}.
     * @see com.gildedgames.aether.event.listeners.abilities.ArmorAbilityListener#onEntityAttack(LivingAttackEvent)
     */
    static boolean extinguishUser(LivingEntity entity, DamageSource source) {
        return EquipmentUtil.hasFullPhoenixSet(entity) && source.isFire();
    }
}
