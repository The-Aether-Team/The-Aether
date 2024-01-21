package com.aetherteam.aether.item;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public final class EquipmentUtil {
    /**
     * An attack is full strength if either it's a player that attacked with full attack strength, or if the entity is not a player.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the attack was full strength, as a {@link Boolean}.
     */
    public static boolean isFullStrength(LivingEntity attacker) {
        return !(attacker instanceof Player player) || player.getAttackStrengthScale(1.0F) >= 1.0F;
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

    /**
     * Searches for gloves in an entity's accessory inventory and returns the first {@link SlotResult} instance.
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link SlotResult} for the curio item.
     */
    @Nullable
    public static SlotResult getGloves(LivingEntity entity) {
        Optional<SlotResult> slotResultOptional = CuriosApi.getCuriosHelper().findFirstCurio(entity, (stack) -> stack.getItem() instanceof GlovesItem);
        return slotResultOptional.orElse(null);
    }

    /**
     * Searches for Zanite Rings in an entity's accessory inventory and returns all the {@link SlotResult} instances.
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link List} of {@link SlotResult}s for the curio items.
     */
    public static List<SlotResult> getZaniteRings(LivingEntity entity) {
        return getCurios(entity, AetherItems.ZANITE_RING.get());
    }

    /**
     * Searches for a Zanite Pendant in an entity's accessory inventory and returns the first {@link SlotResult} instance.
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link SlotResult} for the curio item.
     */
    @Nullable
    public static SlotResult getZanitePendant(LivingEntity entity) {
        return getCurio(entity, AetherItems.ZANITE_PENDANT.get());
    }

    /**
     * Checks if an Ice Pendant or Ice Ring exists in an entity's accessory inventory.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFreezingAccessory(LivingEntity entity) {
        return hasCurio(entity, AetherItems.ICE_PENDANT.get()) || hasCurio(entity, AetherItems.ICE_RING.get());
    }

    /**
     * Checks if a Swet Cape exists in an entity's accessory inventory.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasSwetCape(LivingEntity entity) {
        return hasCurio(entity, AetherItems.SWET_CAPE.get());
    }

    /**
     * Checks if an Invisibility Cloak exists in an entity's accessory inventory.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasInvisibilityCloak(LivingEntity entity) {
        return hasCurio(entity, AetherItems.INVISIBILITY_CLOAK.get());
    }

    /**
     * Checks if a {@link CapeItem} exists in an entity's accessory inventory.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasCape(LivingEntity entity) {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, stack -> stack.getItem() instanceof CapeItem).isPresent();
    }

    /**
     * Searches for a {@link CapeItem} in an entity's accessory inventory and returns the first {@link SlotResult} instance.
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link SlotResult} for the curio item.
     */
    @Nullable
    public static SlotResult getCape(LivingEntity entity) {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, stack -> stack.getItem() instanceof CapeItem).orElse(null);
    }

    /**
     * Checks if a curio {@link Item} exists in an entity's accessory inventory.
     * @param entity The {@link LivingEntity} wearer.
     * @param item The curio {@link Item} to look for.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasCurio(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, item).isPresent();
    }

    /**
     * Searches for a curio {@link Item} in an entity's accessory inventory and returns the first {@link SlotResult} instance.
     * @param entity The {@link LivingEntity} wearer.
     * @param item The curio {@link Item} to look for.
     * @return The {@link SlotResult} for the curio item.
     */
    @Nullable
    public static SlotResult getCurio(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, item).orElse(null);
    }

    /**
     * Searches for a curio {@link Item} in an entity's accessory inventory and returns all the {@link SlotResult} instances.
     * @param entity The {@link LivingEntity} wearer.
     * @param item The curio {@link Item} to look for.
     * @return The {@link List} of {@link SlotResult}s for the curio items.
     */
    public static List<SlotResult> getCurios(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findCurios(entity, item);
    }

    /**
     * Checks if the player is wearing Sentry Boots.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasSentryBoots(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.FEET).is(AetherItems.SENTRY_BOOTS.get());
    }

    /**
     * Checks if the player is wearing the Gravitite Armor set.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullGravititeSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.GRAVITITE_HELMET.get(), AetherItems.GRAVITITE_CHESTPLATE.get(), AetherItems.GRAVITITE_LEGGINGS.get(), AetherItems.GRAVITITE_BOOTS.get(), AetherItems.GRAVITITE_GLOVES.get());
    }

    /**
     * Checks if the player is wearing the Valkyrie Armor set.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullValkyrieSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.VALKYRIE_HELMET.get(), AetherItems.VALKYRIE_CHESTPLATE.get(), AetherItems.VALKYRIE_LEGGINGS.get(), AetherItems.VALKYRIE_BOOTS.get(), AetherItems.VALKYRIE_GLOVES.get());
    }

    /**
     * Checks if the player is wearing the Neptune Armor set.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullNeptuneSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.NEPTUNE_HELMET.get(), AetherItems.NEPTUNE_CHESTPLATE.get(), AetherItems.NEPTUNE_LEGGINGS.get(), AetherItems.NEPTUNE_BOOTS.get(), AetherItems.NEPTUNE_GLOVES.get());
    }

    /**
     * Checks if the player is wearing the Phoenix Armor set.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullPhoenixSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.PHOENIX_HELMET.get(), AetherItems.PHOENIX_CHESTPLATE.get(), AetherItems.PHOENIX_LEGGINGS.get(), AetherItems.PHOENIX_BOOTS.get(), AetherItems.PHOENIX_GLOVES.get());
    }

    /**
     * Checks if the player is wearing any Phoenix Armor.
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasAnyPhoenixArmor(LivingEntity entity) {
        return hasAnyArmor(entity, AetherItems.PHOENIX_HELMET.get(), AetherItems.PHOENIX_CHESTPLATE.get(), AetherItems.PHOENIX_LEGGINGS.get(), AetherItems.PHOENIX_BOOTS.get(), AetherItems.PHOENIX_GLOVES.get());
    }

    /**
     * Checks if the player is wearing all armor pieces out of a set.
     * @param entity The {@link LivingEntity} wearer.
     * @param helmet The helmet {@link Item}.
     * @param chestplate The chestplate {@link Item}.
     * @param leggings The leggings {@link Item}.
     * @param boots The boots {@link Item}.
     * @param gloves The gloves {@link Item}.
     * @return The result of the check, as a {@link Boolean}.
     */
    private static boolean hasArmorSet(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                && entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                && entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                && entity.getItemBySlot(EquipmentSlot.FEET).is(boots)
                && (!AetherConfig.SERVER.require_gloves.get() || CuriosApi.getCuriosHelper().findFirstCurio(entity, gloves).isPresent());
    }

    /**
     * Checks if the player is wearing any armor pieces out of a set.
     * @param entity The {@link LivingEntity} wearer.
     * @param helmet The helmet {@link Item}.
     * @param chestplate The chestplate {@link Item}.
     * @param leggings The leggings {@link Item}.
     * @param boots The boots {@link Item}.
     * @param gloves The gloves {@link Item}.
     * @return The result of the check, as a {@link Boolean}.
     */
    private static boolean hasAnyArmor(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                || entity.getItemBySlot(EquipmentSlot.FEET).is(boots)
                || CuriosApi.getCuriosHelper().findFirstCurio(entity, gloves).isPresent();
    }
}
