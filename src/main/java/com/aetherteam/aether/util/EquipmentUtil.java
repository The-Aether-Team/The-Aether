package com.aetherteam.aether.util;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.Optional;

public class EquipmentUtil {
    /**
     * An attack is full strength if either it's a player that attacked with full attack strength, or if the entity is not a player.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the attack was full strength, as a {@link Boolean}.
     */
    public static boolean isFullStrength(LivingEntity attacker) {
        return !(attacker instanceof Player player) || player.getAttackStrengthScale(1.0F) == 1.0F;
    }

    /**
     * Calculates damage increase based on a base value, the amount of damage taken (maximum durability - current durability), and the stack's maximum durability.<br><br>
     * <a href="https://www.desmos.com/calculator/gmaj2zduis">See math visually.</a>
     * @param stack A {@link ItemStack} to check the durability of.
     * @param baseValue A base {@link Double} value.
     * @return The buffed {@link Double} value.
     */
    public static double calculateZaniteBuff(ItemStack stack, double baseValue) {
        return baseValue * (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
    }

    public static SlotResult getGloves(LivingEntity entity) {
        Optional<SlotResult> slotResultOptional = CuriosApi.getCuriosHelper().findFirstCurio(entity, (stack) -> stack.getItem() instanceof GlovesItem);
        return slotResultOptional.orElse(null);
    }

    public static List<SlotResult> getZaniteRings(LivingEntity entity) {
        return getCurios(entity, AetherItems.ZANITE_RING.get());
    }

    public static SlotResult getZanitePendant(LivingEntity entity) {
        return getCurio(entity, AetherItems.ZANITE_PENDANT.get());
    }

    public static boolean hasFreezingAccessory(LivingEntity entity) {
        return hasCurio(entity, AetherItems.ICE_PENDANT.get()) || hasCurio(entity, AetherItems.ICE_RING.get());
    }

    public static boolean hasSwetCape(LivingEntity entity) {
        return hasCurio(entity, AetherItems.SWET_CAPE.get());
    }

    public static boolean hasInvisibilityCloak(LivingEntity entity) {
        return hasCurio(entity, AetherItems.INVISIBILITY_CLOAK.get());
    }

    public static boolean hasCurio(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, item).isPresent();
    }

    public static SlotResult getCurio(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, item).orElse(null);
    }

    public static List<SlotResult> getCurios(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findCurios(entity, item);
    }

    public static boolean hasSentryBoots(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.FEET).is(AetherItems.SENTRY_BOOTS.get());
    }

    public static boolean hasFullGravititeSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.GRAVITITE_HELMET.get(), AetherItems.GRAVITITE_CHESTPLATE.get(), AetherItems.GRAVITITE_LEGGINGS.get(), AetherItems.GRAVITITE_BOOTS.get(), AetherItems.GRAVITITE_GLOVES.get());
    }

    public static boolean hasFullValkyrieSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.VALKYRIE_HELMET.get(), AetherItems.VALKYRIE_CHESTPLATE.get(), AetherItems.VALKYRIE_LEGGINGS.get(), AetherItems.VALKYRIE_BOOTS.get(), AetherItems.VALKYRIE_GLOVES.get());
    }

    public static boolean hasFullNeptuneSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.NEPTUNE_HELMET.get(), AetherItems.NEPTUNE_CHESTPLATE.get(), AetherItems.NEPTUNE_LEGGINGS.get(), AetherItems.NEPTUNE_BOOTS.get(), AetherItems.NEPTUNE_GLOVES.get());
    }

    public static boolean hasFullPhoenixSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.PHOENIX_HELMET.get(), AetherItems.PHOENIX_CHESTPLATE.get(), AetherItems.PHOENIX_LEGGINGS.get(), AetherItems.PHOENIX_BOOTS.get(), AetherItems.PHOENIX_GLOVES.get());
    }

    public static boolean hasAnyPhoenixArmor(LivingEntity entity) {
        return hasAnyArmor(entity, AetherItems.PHOENIX_HELMET.get(), AetherItems.PHOENIX_CHESTPLATE.get(), AetherItems.PHOENIX_LEGGINGS.get(), AetherItems.PHOENIX_BOOTS.get(), AetherItems.PHOENIX_GLOVES.get());
    }

    private static boolean hasArmorSet(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                && entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                && entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                && entity.getItemBySlot(EquipmentSlot.FEET).is(boots)
                && CuriosApi.getCuriosHelper().findFirstCurio(entity, gloves).isPresent();
    }

    private static boolean hasAnyArmor(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                || entity.getItemBySlot(EquipmentSlot.FEET).is(boots)
                || CuriosApi.getCuriosHelper().findFirstCurio(entity, gloves).isPresent();
    }
}
