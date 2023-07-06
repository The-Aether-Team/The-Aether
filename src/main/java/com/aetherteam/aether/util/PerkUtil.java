package com.aetherteam.aether.util;

import com.aetherteam.nitrogen.api.users.User;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Predicate;

public class PerkUtil {
    public static Predicate<User> hasLifetimeValkyrieMoaSkins() {
        return (user) -> hasAllSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.VALKYRIE.getLevel() || (user.getCurrentTier() == null && user.getHighestPastTierLevel() >= User.Tier.VALKYRIE.getLevel());
    }

    public static Predicate<User> hasLifetimeAscentanMoaSkins() {
        return (user) -> hasAllSkins().test(user) || hasBaseSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.ASCENTAN.getLevel() || (user.getCurrentTier() == null && user.getHighestPastTierLevel() >= User.Tier.ASCENTAN.getLevel());
    }
    
    public static Predicate<User> hasValkyrieMoaSkins() {
        return (user) -> hasAllSkins().test(user) || user.getCurrentTierLevel() >= User.Tier.VALKYRIE.getLevel();
    }

    public static Predicate<User> hasAscentanMoaSkins() {
        return (user) -> hasAllSkins().test(user) || hasBaseSkins().test(user)  || user.getCurrentTierLevel() >= User.Tier.ASCENTAN.getLevel();
    }

    public static Predicate<User> hasBaseSkins() {
        return (user) -> isContributor().test(user) || user.getHighestGroup() == User.Group.TRANSLATOR || user.getHighestGroup() == User.Group.CELEBRITY;
    }

    public static Predicate<User> hasAllSkins() {
        return (user) -> isDeveloperOrStaff().test(user);
    }

    public static Predicate<User> hasDeveloperGlow() {
        return (user) -> isDeveloper().test(user);
    }

    public static Predicate<User> hasHalo() {
        return (user) -> isDeveloperOrStaff().test(user) || isContributor().test(user);
    }

    public static Predicate<User> isDeveloperOrStaff() {
        return (user) -> isDeveloper().test(user) || user.getHighestGroup() == User.Group.STAFF;
    }

    public static Predicate<User> isDeveloper() {
        return (user) -> user.getHighestGroup() == User.Group.AETHER_TEAM || user.getHighestGroup() == User.Group.MODDING_LEGACY;
    }

    public static Predicate<User> isContributor() {
        return (user) -> user.getHighestGroup() == User.Group.CONTRIBUTOR || user.getHighestGroup() == User.Group.LEGACY_CONTRIBUTOR;
    }

    public static Triple<Float, Float, Float> getPerkColor(String hex) {
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
