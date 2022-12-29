package com.gildedgames.aether.util;

import com.gildedgames.aether.perk.types.MoaPacks;
import com.gildedgames.aether.perk.types.MoaSkins;
import com.gildedgames.nitrogen.api.users.Patron;
import com.gildedgames.nitrogen.api.users.Ranked;
import com.gildedgames.nitrogen.api.users.User;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PerkUtil {
    public static Predicate<User> hasLifetimeValkyrieMoaSkins(String string) {
        return (user) -> hasAllSkins().test(user) || hasLifetimeSkin(user, string);
    }

    public static Predicate<User> hasLifetimeAngelMoaSkins(String string) {
        return (user) -> hasAllSkins().test(user) || hasBaseSkins().test(user) || hasLifetimeSkin(user, string);
    }
    
    public static Predicate<User> hasValkyrieMoaSkins(String string) {
        return (user) -> hasAllSkins().test(user) || (user.isPledging() && user.getPatronTierLevel() >= Patron.Tier.VALKYRIE.getLevel()) || hasLifetimeSkin(user, string);
    }

    public static Predicate<User> hasAngelMoaSkins(String string) {
        return (user) -> hasAllSkins().test(user) || hasBaseSkins().test(user)  || (user.isPledging() && user.getPatronTierLevel() >= Patron.Tier.ANGEL.getLevel()) || hasLifetimeSkin(user, string);
    }

    public static Predicate<User> hasBaseSkins() {
        return (user) -> isContributor().test(user) || user.hasRank(Ranked.Rank.TRANSLATOR) || user.hasRank(Ranked.Rank.CELEBRITY);
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
        return (user) -> isDeveloper().test(user) || user.hasRank(Ranked.Rank.STAFF);
    }

    public static Predicate<User> isDeveloper() {
        return (user) -> user.hasRank(Ranked.Rank.GILDED_GAMES) || user.hasRank(Ranked.Rank.MODDING_LEGACY);
    }

    public static Predicate<User> isContributor() {
        return (user) -> user.hasRank(Ranked.Rank.CONTRIBUTOR) || user.hasRank(Ranked.Rank.LEGACY_CONTRIBUTOR);
    }

    public static boolean hasLifetimeSkin(User user, String string) {
        return !hasLifetimeSkinFromPacks(user, string).isEmpty() || hasLifetimeSkinSpecific(user, string);
    }

    public static List<Component> hasLifetimeSkinFromPacks(User user, String string) {
        List<Component> packNames = new ArrayList<>();
        List<MoaPacks.MoaPack> moaPacks = MoaPacks.getMoaPacks();
        for (MoaPacks.MoaPack moaPack : moaPacks) {
            if (user.getLifetimeSkins().contains(moaPack.id()) && moaPack.hasSkin(string)) {
                packNames.add(moaPack.displayName());
            }
        }
        return packNames;
    }

    public static boolean hasLifetimeSkinSpecific(User user, String string) {
        Map<String, MoaSkins.MoaSkin> moaSkins = MoaSkins.getMoaSkins();
        for (Map.Entry<String, MoaSkins.MoaSkin> moaSkinsEntry : moaSkins.entrySet()) {
            MoaSkins.MoaSkin moaSkin = moaSkinsEntry.getValue();
            if (user.getLifetimeSkins().contains(moaSkin.getId()) && moaSkin.getId().equals(string)) {
                return true;
            }
        }
        return false;
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
