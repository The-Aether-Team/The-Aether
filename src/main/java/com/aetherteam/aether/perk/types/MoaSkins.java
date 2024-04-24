package com.aetherteam.aether.perk.types;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.nitrogen.api.users.User;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MoaSkins {
    private static final Map<String, MoaSkin> MOA_SKINS = new LinkedHashMap<>();

    public static void registerMoaSkins(Level level) {
        if (!MOA_SKINS.isEmpty()) {
            MOA_SKINS.clear();
        }
        if (level != null) {
            RegistryAccess registryAccess = level.registryAccess();
            Registry<MoaType> registry = registryAccess.registryOrThrow(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY);
            List<ResourceKey<MoaType>> moaTypes = registry.registryKeySet().stream().sorted((current, next) -> {
                MoaType currentType = AetherMoaTypes.getMoaType(registryAccess, current.location());
                MoaType nextType = AetherMoaTypes.getMoaType(registryAccess, next.location());
                if (currentType != null && nextType != null) {
                    return Integer.compare(currentType.maxJumps(), nextType.maxJumps());
                } else {
                    return 0;
                }
            }).toList();
            for (ResourceKey<MoaType> moaTypeKey : moaTypes) {
                MoaType moaType = registry.get(moaTypeKey);
                if (moaType != null) {
                    String name = (moaTypeKey.location().getNamespace().equals(Aether.MODID) ? moaTypeKey.location().getPath() : moaTypeKey.location().toString().replace(":", ".")) + "_moa";
                    register(name, new MoaSkin(name, new MoaSkin.Properties()
                        .displayName(Component.translatable("gui.aether.moa_skins.skin." + name))
                        .userPredicate((user) -> PerkUtil.hasHumanMoaSkins().test(user))
                        .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/" + name + "_icon"))
                        .skinLocation(moaType.moaTexture())
                        .saddleLocation(moaType.saddleTexture())
                        .info(new MoaSkin.Info(User.Tier.HUMAN, false))
                    ));
                }
            }
        }
        register("orange_moa", new MoaSkin("orange_moa", new MoaSkin.Properties()
            .displayName(Component.translatable("gui.aether.moa_skins.skin.orange_moa"))
            .userPredicate((user) -> PerkUtil.hasHumanMoaSkins().test(user))
            .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/orange_moa_icon"))
            .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/orange_moa/orange_moa.png"))
            .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
            .info(new MoaSkin.Info(User.Tier.HUMAN, false))
        ));
        register("brown_moa", new MoaSkin("brown_moa", new MoaSkin.Properties()
            .displayName(Component.translatable("gui.aether.moa_skins.skin.brown_moa"))
            .userPredicate((user) -> PerkUtil.hasHumanMoaSkins().test(user))
            .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/brown_moa_icon"))
            .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/brown_moa/brown_moa.png"))
            .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/black_moa_saddle.png"))
            .info(new MoaSkin.Info(User.Tier.HUMAN, false))
        ));
        register("red_moa", new MoaSkin("red_moa", new MoaSkin.Properties()
            .displayName(Component.translatable("gui.aether.moa_skins.skin.red_moa"))
            .userPredicate((user) -> PerkUtil.hasHumanMoaSkins().test(user))
            .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/red_moa_icon"))
            .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/red_moa/red_moa.png"))
            .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
            .info(new MoaSkin.Info(User.Tier.HUMAN, false))
        ));
        register("green_moa", new MoaSkin("green_moa", new MoaSkin.Properties()
            .displayName(Component.translatable("gui.aether.moa_skins.skin.green_moa"))
            .userPredicate((user) -> PerkUtil.hasHumanMoaSkins().test(user))
            .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/green_moa_icon"))
            .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/green_moa/green_moa.png"))
            .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
            .info(new MoaSkin.Info(User.Tier.HUMAN, false))
        ));
        register("purple_moa", new MoaSkin("purple_moa", new MoaSkin.Properties()
            .displayName(Component.translatable("gui.aether.moa_skins.skin.purple_moa"))
            .userPredicate((user) -> PerkUtil.hasHumanMoaSkins().test(user))
            .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/purple_moa_icon"))
            .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/purple_moa/purple_moa.png"))
            .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
            .info(new MoaSkin.Info(User.Tier.HUMAN, false))
        ));
        register("boko_yellow", new MoaSkin("boko_yellow", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.boko_yellow"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/boko_yellow_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/boko_yellow/boko_yellow.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/boko_yellow/boko_yellow_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/boko_yellow/boko_yellow_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("crookjaw_purple", new MoaSkin("crookjaw_purple", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.crookjaw_purple"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/crookjaw_purple_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple_emissive.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("gharrix_red", new MoaSkin("gharrix_red", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.gharrix_red"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/gharrix_red_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gharrix_red/gharrix_red.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gharrix_red/gharrix_red_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("halcian_pink", new MoaSkin("halcian_pink", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.halcian_pink"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/halcian_pink_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/halcian_pink/halcian_pink.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/halcian_pink/halcian_pink_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/halcian_pink/halcian_pink_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("tivalier_green", new MoaSkin("tivalier_green", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.tivalier_green"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/tivalier_green_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/tivalier_green/tivalier_green.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/tivalier_green/tivalier_green_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("gilded_gharrix", new MoaSkin("gilded_gharrix", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.gilded_gharrix"))
                .userPredicate((user) -> PerkUtil.hasAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/gilded_gharrix_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gilded_gharrix/gilded_gharrix.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gilded_gharrix/gilded_gharrix_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gilded_gharrix/gilded_gharrix_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, false))
        ));
        register("gargoyle_moa", new MoaSkin("gargoyle_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.gargoyle_moa"))
                .userPredicate((user) -> PerkUtil.hasAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/gargoyle_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gargoyle_moa/gargoyle_moa.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gargoyle_moa/gargoyle_moa_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gargoyle_moa/gargoyle_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, false))
        ));
        register("construction_bot", new MoaSkin("construction_bot", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.construction_bot"))
                .userPredicate((user) -> PerkUtil.hasAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/construction_bot_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/construction_bot/construction_bot.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/construction_bot/construction_bot_emissive.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/construction_bot/construction_bot_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/construction_bot/construction_bot_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, false))
        ));
        register("mossy_statue_moa", new MoaSkin("mossy_statue_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.mossy_statue_moa"))
                .userPredicate((user) -> PerkUtil.hasAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/mossy_statue_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/mossy_statue_moa/mossy_statue_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/mossy_statue_moa/mossy_statue_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, false))
        ));
        register("chicken_moa", new MoaSkin("chicken_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.chicken_moa"))
                .userPredicate((user) -> PerkUtil.hasAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/chicken_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/chicken_moa/chicken_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/chicken_moa/chicken_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, false))
        ));
        register("arctic_moa", new MoaSkin("arctic_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.arctic_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/arctic_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/arctic_moa/arctic_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/arctic_moa/arctic_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("cockatrice_moa", new MoaSkin("cockatrice_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.cockatrice_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/cockatrice_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("phoenix_moa", new MoaSkin("phoenix_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.phoenix_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/phoenix_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("sentry_moa", new MoaSkin("sentry_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.sentry_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/sentry_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("valkyrie_moa", new MoaSkin("valkyrie_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.valkyrie_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/valkyrie_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/valkyrie_moa/valkyrie_moa.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/valkyrie_moa/valkyrie_moa_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/valkyrie_moa/valkyrie_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("battle_sentry_moa", new MoaSkin("battle_sentry_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.battle_sentry_moa"))
                .userPredicate((user) -> PerkUtil.hasValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/battle_sentry_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/battle_sentry_moa/battle_sentry_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/battle_sentry_moa/battle_sentry_moa_emissive.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/battle_sentry_moa/battle_sentry_moa_hat.png"))
                .hatEmissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/battle_sentry_moa/battle_sentry_moa_hat_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/battle_sentry_moa/battle_sentry_moa_saddle.png"))
                .saddleEmissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/battle_sentry_moa/battle_sentry_moa_saddle_emissive.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, false))
        ));
        register("frozen_phoenix", new MoaSkin("frozen_phoenix", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.frozen_phoenix"))
                .userPredicate((user) -> PerkUtil.hasValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/frozen_phoenix_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/frozen_phoenix/frozen_phoenix.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/frozen_phoenix/frozen_phoenix_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/frozen_phoenix/frozen_phoenix_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, false))
        ));
        register("molten_moa", new MoaSkin("molten_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.molten_moa"))
                .userPredicate((user) -> PerkUtil.hasValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/molten_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/molten_moa/molten_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/molten_moa/molten_moa_emissive.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/molten_moa/molten_moa_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/molten_moa/molten_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, false))
        ));
        register("undead_moa", new MoaSkin("undead_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.undead_moa"))
                .userPredicate((user) -> PerkUtil.hasValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/undead_moa_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/undead_moa/undead_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/undead_moa/undead_moa_emissive.png"))
                .hatLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/undead_moa/undead_moa_hat.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/undead_moa/undead_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, false))
        ));
        register("stratus", new MoaSkin("stratus", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.stratus"))
                .userPredicate((user) -> PerkUtil.hasValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "skins/icons/stratus_icon"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/stratus/stratus.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/stratus/stratus_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, false))
        ));
    }

    private static void register(String id, MoaSkin moaSkin) {
        MOA_SKINS.put(id, moaSkin);
    }

    public static Map<String, MoaSkin> getMoaSkins() {
        return ImmutableMap.copyOf(MOA_SKINS);
    }

    public static class MoaSkin {
        private final String id;
        private final Component displayName;
        private final Predicate<User> userPredicate;
        private final ResourceLocation iconLocation;
        private final ResourceLocation skinLocation;
        @Nullable
        private final ResourceLocation emissiveLocation;
        @Nullable
        private final ResourceLocation hatLocation;
        @Nullable
        private final ResourceLocation hatEmissiveLocation;
        private final ResourceLocation saddleLocation;
        @Nullable
        private final ResourceLocation saddleEmissiveLocation;
        private final Info info;

        protected MoaSkin(String id, Properties properties) {
            this(id, properties.displayName, properties.userPredicate, properties.iconLocation, properties.skinLocation, properties.emissiveLocation, properties.hatLocation, properties.hatEmissiveLocation, properties.saddleLocation, properties.saddleEmissiveLocation, properties.info);
        }

        protected MoaSkin(String id, Component displayName, Predicate<User> userPredicate, ResourceLocation iconLocation, ResourceLocation skinLocation, ResourceLocation emissiveLocation, ResourceLocation hatLocation, ResourceLocation hatEmissiveLocation, ResourceLocation saddleLocation, ResourceLocation saddleEmissiveLocation, Info info) {
            this.id = id;
            this.displayName = displayName;
            this.userPredicate = userPredicate;
            this.iconLocation = iconLocation;
            this.skinLocation = skinLocation;
            this.emissiveLocation = emissiveLocation;
            this.hatLocation = hatLocation;
            this.hatEmissiveLocation = hatEmissiveLocation;
            this.saddleLocation = saddleLocation;
            this.saddleEmissiveLocation = saddleEmissiveLocation;
            this.info = info;
        }

        /**
         * @return The {@link String} ID for the name of a {@link MoaSkin}.
         */
        public String getId() {
            return this.id;
        }

        /**
         * @return The {@link Component} for the {@link MoaSkin}'s display name in GUIs.
         */
        public Component getDisplayName() {
            return this.displayName;
        }

        /**
         * @return The {@link User} {@link Predicate} to check whether a player has access to a {@link MoaSkin}.
         */
        public Predicate<User> getUserPredicate() {
            return this.userPredicate;
        }

        /**
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s GUI icon.
         */
        public ResourceLocation getIconLocation() {
            return this.iconLocation;
        }

        /**
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s texture.
         */
        public ResourceLocation getSkinLocation() {
            return this.skinLocation;
        }

        /**
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s emissive overlay texture.
         */
        @Nullable
        public ResourceLocation getEmissiveLocation() {
            return this.emissiveLocation;
        }

        /**
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s hat texture.
         */
        public ResourceLocation getHatLocation() {
            return this.hatLocation;
        }

        /**
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s hat emissive overlay texture.
         */
        public ResourceLocation getHatEmissiveLocation() {
            return this.hatEmissiveLocation;
        }

        /**
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s saddle texture.
         */
        public ResourceLocation getSaddleLocation() {
            return this.saddleLocation;
        }

        /**
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s saddle emissive overlay texture.
         */
        @Nullable
        public ResourceLocation getSaddleEmissiveLocation() {
            return this.saddleEmissiveLocation;
        }

        /**
         * @return The Patreon {@link com.aetherteam.nitrogen.api.users.User.Tier} and lifetime {@link Boolean} {@link Info} of the {@link MoaSkin}.
         */
        public Info getInfo() {
            return this.info;
        }

        /**
         * Reads a {@link MoaSkin} from a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer The {@link FriendlyByteBuf} buffer.
         * @return A {@link MoaSkin}.
         */
        public static MoaSkin read(FriendlyByteBuf buffer) {
            String id = buffer.readUtf();
            return MoaSkins.getMoaSkins().get(id);
        }

        /**
         * Writes a {@link MoaSkin} to a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer  The {@link FriendlyByteBuf} buffer.
         * @param moaSkin A {@link MoaSkin}.
         */
        public static void write(FriendlyByteBuf buffer, MoaSkin moaSkin) {
            buffer.writeUtf(moaSkin.getId());
        }

        public static class Properties {
            private Component displayName;
            private Predicate<User> userPredicate;
            private ResourceLocation iconLocation;
            private ResourceLocation skinLocation;
            @Nullable
            private ResourceLocation emissiveLocation = null;
            @Nullable
            private ResourceLocation hatLocation = null;
            @Nullable
            private ResourceLocation hatEmissiveLocation = null;
            private ResourceLocation saddleLocation;
            @Nullable
            private ResourceLocation saddleEmissiveLocation = null;
            private Info info;

            /**
             * @see MoaSkin#getDisplayName()
             */
            public Properties displayName(Component displayName) {
                this.displayName = displayName;
                return this;
            }

            /**
             * @see MoaSkin#getUserPredicate()
             */
            public Properties userPredicate(Predicate<User> userPredicate) {
                this.userPredicate = userPredicate;
                return this;
            }

            /**
             * @see MoaSkin#getIconLocation()
             */
            public Properties iconLocation(ResourceLocation iconLocation) {
                this.iconLocation = iconLocation;
                return this;
            }

            /**
             * @see MoaSkin#getSkinLocation()
             */
            public Properties skinLocation(ResourceLocation skinLocation) {
                this.skinLocation = skinLocation;
                return this;
            }

            /**
             * @see MoaSkin#getEmissiveLocation()
             */
            public Properties emissiveLocation(@Nullable ResourceLocation emissiveLocation) {
                this.emissiveLocation = emissiveLocation;
                return this;
            }

            /**
             * @see MoaSkin#getHatLocation()
             */
            public Properties hatLocation(ResourceLocation hatLocation) {
                this.hatLocation = hatLocation;
                return this;
            }

            /**
             * @see MoaSkin#getHatEmissiveLocation()
             */
            public Properties hatEmissiveLocation(ResourceLocation hatEmissiveLocation) {
                this.hatEmissiveLocation = hatEmissiveLocation;
                return this;
            }

            /**
             * @see MoaSkin#getSaddleLocation()
             */
            public Properties saddleLocation(ResourceLocation saddleLocation) {
                this.saddleLocation = saddleLocation;
                return this;
            }

            /**
             * @see MoaSkin#getSaddleEmissiveLocation()
             */
            public Properties saddleEmissiveLocation(ResourceLocation saddleEmissiveLocation) {
                this.saddleEmissiveLocation = saddleEmissiveLocation;
                return this;
            }

            /**
             * @see MoaSkin#getInfo()
             */
            public Properties info(Info info) {
                this.info = info;
                return this;
            }
        }

        /**
         * Stores the Patreon {@link com.aetherteam.nitrogen.api.users.User.Tier} and a {@link Boolean} for whether the skin is lifetime or not.
         */
        public record Info(User.Tier tier, boolean lifetime) { }
    }
}
