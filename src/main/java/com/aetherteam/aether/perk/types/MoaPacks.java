package com.aetherteam.aether.perk.types;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;

import java.util.LinkedList;
import java.util.List;

public class MoaPacks {
    private static final List<MoaPack> MOA_PACKS = new LinkedList<>();
    
    public static void registerMoaPacks() {
        register("lifetime_angel_moa_skins", Component.translatable("gui.aether.moa_skins.pack.lifetime_angel_moa_skins"), List.of("blue_moa", "white_moa", "black_moa", "orange_moa", "boko_yellow", "crookjaw_purple", "gharrix_red", "halcian_pink", "tivalier_green"));
        register("lifetime_valkyrie_moa_skins", Component.translatable("gui.aether.moa_skins.pack.lifetime_valkyrie_moa_skins"), List.of("arctic_moa", "cockatrice_moa", "phoenix_moa", "sentry_moa", "valkyrie_moa"));

        register("natural_moa_skins", Component.translatable("gui.aether.moa_skins.pack.natural_moa_skins"), AetherMoaTypes.MOA_TYPES.getEntries().stream().map((entry) -> (entry.getId().getNamespace().equals(Aether.MODID) ? entry.getId().getPath() : entry.getId().toString().replace(":", ".")) + "_moa").toList());
        register("color_theme_moa_skins", Component.translatable("gui.aether.moa_skins.pack.natural_moa_skins"), List.of("boko_yellow", "crookjaw_purple", "gharrix_red", "halcian_pink", "tivalier_green"));
        register("gotv_moa_skins", Component.translatable("gui.aether.moa_skins.pack.natural_moa_skins"), List.of("arctic_moa", "cockatrice_moa", "phoenix_moa", "sentry_moa", "valkyrie_moa"));
    }

    private static void register(String id, Component displayName, List<String> moaSkins) {
        MOA_PACKS.add(new MoaPack(id, displayName, moaSkins));
    }

    public static List<MoaPack> getMoaPacks() {
        return ImmutableList.copyOf(MOA_PACKS);
    }

    public record MoaPack(String id, Component displayName, List<String> moaSkins) {
        public boolean hasSkin(String skin) {
            return moaSkins().contains(skin);
        }
    }
}
