package com.aetherteam.aether.perk;

import com.aetherteam.nitrogen.api.users.User;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public final class PerkUtil {
    /**
     * @return A {@link Predicate} to check if a {@link User} has access to Valkyrie-tier Moa skins for a lifetime period.
     */
    public static Predicate<User> hasLifetimeValkyrieMoaSkins() {
        return (user) -> hasAllSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.VALKYRIE.getLevel() || (user.getCurrentTier() == null && user.getHighestPastTierLevel() >= User.Tier.VALKYRIE.getLevel());
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to Ascentan-tier Moa skins for a lifetime period.
     */
    public static Predicate<User> hasLifetimeAscentanMoaSkins() {
        return (user) -> hasAllSkins().test(user) || hasBaseSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.ASCENTAN.getLevel() || (user.getCurrentTier() == null && user.getHighestPastTierLevel() >= User.Tier.ASCENTAN.getLevel());
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to Valkyrie-tier Moa skins.
     */
    public static Predicate<User> hasValkyrieMoaSkins() {
        return (user) -> hasAllSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.VALKYRIE.getLevel();
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to Ascentan-tier Moa skins.
     */
    public static Predicate<User> hasAscentanMoaSkins() {
        return (user) -> hasAllSkins().test(user) || hasBaseSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.ASCENTAN.getLevel();
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to Human-tier Moa skins.
     */
    public static Predicate<User> hasHumanMoaSkins() {
        return (user) -> hasAllSkins().test(user) || hasBaseSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.HUMAN.getLevel();
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to a base set of Moa skins.
     */
    public static Predicate<User> hasBaseSkins() {
        return (user) -> isContributor().test(user) || user.getHighestGroup() == User.Group.TRANSLATOR || user.getHighestGroup() == User.Group.CELEBRITY;
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to all Moa skins.
     */
    public static Predicate<User> hasAllSkins() {
        return (user) -> isDeveloperOrStaff().test(user);
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to developer glow.
     */
    public static Predicate<User> hasDeveloperGlow() {
        return (user) -> isDeveloper().test(user);
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} has access to a halo.
     */
    public static Predicate<User> hasHalo() {
        return (user) -> isDeveloperOrStaff().test(user) || isContributor().test(user);
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} is a developer is staff.
     */
    public static Predicate<User> isDeveloperOrStaff() {
        return (user) -> isDeveloper().test(user) || user.getHighestGroup() == User.Group.STAFF;
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} is a developer.
     */
    public static Predicate<User> isDeveloper() {
        return (user) -> user.getHighestGroup() == User.Group.AETHER_TEAM || user.getHighestGroup() == User.Group.MODDING_LEGACY;
    }

    /**
     * @return A {@link Predicate} to check if a {@link User} is a contributor.
     */
    public static Predicate<User> isContributor() {
        return (user) -> user.getHighestGroup() == User.Group.CONTRIBUTOR || user.getHighestGroup() == User.Group.LEGACY_CONTRIBUTOR;
    }

    /**
     * Converts a hex code to RGB values stored as decimals from 0 to 1.
     *
     * @param hex The color hex code as a {@link String}.
     * @return A {@link Triple} storing three {@link Float}s for RGB values.
     */
    @Nullable
    public static Triple<Float, Float, Float> getPerkColor(@Nullable String hex) {
        if (hex != null && !hex.isEmpty()) {
            try {
                int decimal = Integer.parseInt(hex, 16);
                int r = (decimal & 16711680) >> 16;
                int g = (decimal & '\uff00') >> 8;
                int b = (decimal & 255);
                return Triple.of((float) r / 255.0F, (float) g / 255.0F, (float) b / 255.0F);
            } catch (NumberFormatException exception) {
                return null;
            }
        } else {
            return null;
        }
    }
}
