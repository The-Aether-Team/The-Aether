package com.gildedgames.aether.core.util;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class LevelUtil {
    public static boolean isPortalDestination(Level level) {
        return getPortalDestination().equals(level.dimension());
    }

    public static ResourceKey<Level> getPortalDestination() {
        return AetherDimensions.AETHER_LEVEL; // TODO Config
    }

    public static boolean sunSpiritControlsDaycycle(Level level) {
        return isHostileParadise(level); // TODO AetherTags.Dimensions.SUN_SPIRIT_CONTROLS_DAYCYCLE
    }

    public static boolean isDispenserIgnitionDisabled(Level level) {
        return isHostileParadise(level); // TODO AetherTags.Dimensions.DISPENSER_IGNITION_DISABLED
    }

    public static boolean canUseAetherItemBan(Level level) {
        return isHostileParadise(level); // TODO AetherTags.Dimensions.USES_AETHER_ITEM_BAN
    }

    public static boolean shouldRemoveWheatSeedDrop(Level level) {
        return isHostileParadise(level); // TODO AetherTags.Dimensions.REMOVES_WHEAT_SEED_DROP
    }

    public static boolean hasAerogelFreezing(Level level) {
        return isHostileParadise(level); // TODO AetherTags.Dimensions.HAS_AEROGEL_FREEZING
    }

    public static boolean shouldReturnPlayerToOverworld(Level level) {
        return isHostileParadise(level); // TODO AetherTags.Dimensions.AETHER_PORTALS_RETURN_PLAYER
    }

    public static boolean isPortalFormingAllowed(Level level) {
        // TODO AetherTags.Dimensions.AETHER_PORTALS_CAN_FORM
        //  This is the only tag that features both the Aether and Overworld
        return isHostileParadise(level) || Level.OVERWORLD.equals(level.dimension());
    }

    public static boolean shouldSendFallingPlayerToOverworld(Level level) {
        return isHostileParadise(level); // TODO AetherTags.Dimensions.PLAYER_FALLS_INTO_OVERWORLD
    }

    public static boolean isHostileParadise(Level level) {
        // To be open-ended until Dimension Tags are implemented
        return Aether.MODID.equals(level.dimension().location().getNamespace()); // TODO AetherTags.Dimensions.HOSTILE_PARADISE
    }

    private LevelUtil() {
    }
}
