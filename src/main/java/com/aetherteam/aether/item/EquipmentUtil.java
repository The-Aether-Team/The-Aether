package com.aetherteam.aether.item;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.integration.AccessoryUtil;
import com.aetherteam.aether.inventory.container.AccessoryContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.List;

public final class EquipmentUtil {
    /**
     * An attack is full strength if either it's a player that attacked with full attack strength, or if the entity is not a player.
     *
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the attack was full strength, as a {@link Boolean}.
     */
    public static boolean isFullStrength(LivingEntity attacker) {
        boolean combatifyLoaded = ModList.get().isLoaded("combatify");
        return !(attacker instanceof Player player) || (combatifyLoaded ? player.getAttackStrengthScale(1.0F) >= 1.95F : player.getAttackStrengthScale(1.0F) >= 1.0F);
    }

    /**
     * Calculates damage increase based on a base value, the amount of damage taken (maximum durability - current durability), and the stack's maximum durability.<br><br>
     * <a href="https://www.desmos.com/calculator/gmaj2zduis">See math visually.</a>
     *
     * @param stack     A {@link ItemStack} to check the durability of.
     * @param baseValue A base {@link Double} value.
     * @return The buffed {@link Double} value.
     */
    public static double calculateZaniteBuff(ItemStack stack, double baseValue) {
        return baseValue * (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
    }

    /**
     * Searches for gloves in an entity's accessory inventory and returns the first {@link ItemStack} instance.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link ItemStack} for the accessory item.
     */
    public static ItemStack getGloves(LivingEntity entity) {
        return AccessoryUtil.getInFirstSlot(entity, AccessoryContainer.SlotType.GLOVES);
    }

    /**
     * Searches for Zanite Rings in an entity's accessory inventory and returns all the {@link ItemStack} instances.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link List} of {@link ItemStack}s for the accessory items.
     */
    public static List<ItemStack> getZaniteRings(LivingEntity entity) {
        return AccessoryUtil.getItems(entity, (stack) -> stack.is(AetherItems.ZANITE_RING));
    }

    /**
     * Searches for a Zanite Pendant in an entity's accessory inventory and returns the first {@link ItemStack} instance.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link ItemStack} for the accessory item.
     */
    public static ItemStack getZanitePendant(LivingEntity entity) {
        return AccessoryUtil.getFirstItem(entity, (stack) -> stack.is(AetherItems.ZANITE_PENDANT));
    }

    /**
     * Checks if an Ice Pendant or Ice Ring exists in an entity's accessory inventory.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFreezingAccessory(LivingEntity entity) {
        return AccessoryUtil.hasItem(entity, (stack) -> stack.is(AetherItems.ICE_PENDANT) || stack.is(AetherItems.ICE_RING));
    }

    /**
     * Checks if an accessory that pacifies Swets exists in an entity's accessory inventory.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasSwetPacifyingAccessory(LivingEntity entity) {
        return AccessoryUtil.hasItem(entity, (stack) -> stack.is(AetherTags.Items.PACIFIES_SWETS));
    }

    /**
     * Checks if a Swet Cape exists in an entity's accessory inventory.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasSwetCape(LivingEntity entity) {
        return AccessoryUtil.hasItem(entity, (stack) -> stack.is(AetherItems.SWET_CAPE));
    }

    /**
     * Checks if an Invisibility Cloak exists in an entity's accessory inventory.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasInvisibilityCloak(LivingEntity entity) {
        return AccessoryUtil.hasItem(entity, (stack) -> stack.is(AetherItems.INVISIBILITY_CLOAK));
    }

    /**
     * Checks if a cape exists in an entity's accessory inventory.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasCape(LivingEntity entity) {
        return AccessoryUtil.hasInSlot(entity, AccessoryContainer.SlotType.CAPE);
    }

    /**
     * Searches for a cape in an entity's accessory inventory and returns the first {@link ItemStack} instance.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The {@link ItemStack} for the accessory item.
     */
    public static ItemStack getCape(LivingEntity entity) {
        return AccessoryUtil.getInFirstSlot(entity, AccessoryContainer.SlotType.CAPE);
    }

    /**
     * Checks if the player is wearing Sentry Boots.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasSentryBoots(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.FEET).is(AetherItems.SENTRY_BOOTS.get());
    }

    /**
     * Checks if the player is wearing the Gravitite Armor set.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullGravititeSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.GRAVITITE_HELMET.get(), AetherItems.GRAVITITE_CHESTPLATE.get(), AetherItems.GRAVITITE_LEGGINGS.get(), AetherItems.GRAVITITE_BOOTS.get(), AetherItems.GRAVITITE_GLOVES.get());
    }

    /**
     * Checks if the player is wearing the Valkyrie Armor set.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullValkyrieSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.VALKYRIE_HELMET.get(), AetherItems.VALKYRIE_CHESTPLATE.get(), AetherItems.VALKYRIE_LEGGINGS.get(), AetherItems.VALKYRIE_BOOTS.get(), AetherItems.VALKYRIE_GLOVES.get());
    }

    /**
     * Checks if the player is wearing the Neptune Armor set.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullNeptuneSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.NEPTUNE_HELMET.get(), AetherItems.NEPTUNE_CHESTPLATE.get(), AetherItems.NEPTUNE_LEGGINGS.get(), AetherItems.NEPTUNE_BOOTS.get(), AetherItems.NEPTUNE_GLOVES.get());
    }

    /**
     * Checks if the player is wearing the Phoenix Armor set.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasFullPhoenixSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.PHOENIX_HELMET.get(), AetherItems.PHOENIX_CHESTPLATE.get(), AetherItems.PHOENIX_LEGGINGS.get(), AetherItems.PHOENIX_BOOTS.get(), AetherItems.PHOENIX_GLOVES.get());
    }

    /**
     * Checks if the player is wearing any Phoenix Armor.
     *
     * @param entity The {@link LivingEntity} wearer.
     * @return The result of the check, as a {@link Boolean}.
     */
    public static boolean hasAnyPhoenixArmor(LivingEntity entity) {
        return hasAnyArmor(entity, AetherItems.PHOENIX_HELMET.get(), AetherItems.PHOENIX_CHESTPLATE.get(), AetherItems.PHOENIX_LEGGINGS.get(), AetherItems.PHOENIX_BOOTS.get(), AetherItems.PHOENIX_GLOVES.get());
    }

    /**
     * Checks if the player is wearing all armor pieces out of a set.
     *
     * @param entity     The {@link LivingEntity} wearer.
     * @param helmet     The helmet {@link Item}.
     * @param chestplate The chestplate {@link Item}.
     * @param leggings   The leggings {@link Item}.
     * @param boots      The boots {@link Item}.
     * @param gloves     The gloves {@link Item}.
     * @return The result of the check, as a {@link Boolean}.
     */
    private static boolean hasArmorSet(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                && entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                && entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                && entity.getItemBySlot(EquipmentSlot.FEET).is(boots)
                && (!AetherConfig.SERVER.require_gloves.get() || EquipmentUtil.getGloves(entity).is(gloves));
    }

    /**
     * Checks if the player is wearing any armor pieces out of a set.
     *
     * @param entity     The {@link LivingEntity} wearer.
     * @param helmet     The helmet {@link Item}.
     * @param chestplate The chestplate {@link Item}.
     * @param leggings   The leggings {@link Item}.
     * @param boots      The boots {@link Item}.
     * @param gloves     The gloves {@link Item}.
     * @return The result of the check, as a {@link Boolean}.
     */
    private static boolean hasAnyArmor(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                || entity.getItemBySlot(EquipmentSlot.FEET).is(boots)
                || EquipmentUtil.getGloves(entity).is(gloves);
    }
}
