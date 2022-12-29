package com.gildedgames.aether.perk.types;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.util.PerkUtil;
import com.gildedgames.nitrogen.api.users.Patron;
import com.gildedgames.nitrogen.api.users.User;
import com.google.common.collect.ImmutableMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MoaSkins {
    private static final Map<String, MoaSkin> MOA_SKINS = new LinkedHashMap<>();

    public static void registerMoaSkins() {
        register("blue_moa", new MoaSkin("blue_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.blue_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("blue_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/blue_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/blue_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("white_moa", new MoaSkin("white_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.white_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("white_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/white_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/white_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("black_moa", new MoaSkin("black_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.black_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("black_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/black_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/black_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/black_moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("orange_moa", new MoaSkin("orange_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.orange_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("orange_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/orange_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/orange_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("boko_yellow", new MoaSkin("boko_yellow", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.boko_yellow"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("boko_yellow").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/boko_yellow_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/boko_yellow/boko_yellow.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/boko_yellow/boko_yellow_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("crookjaw_purple", new MoaSkin("crookjaw_purple", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.crookjaw_purple"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("crookjaw_purple").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/crookjaw_purple_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("gharrix_red", new MoaSkin("gharrix_red", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.gharrix_red"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("gharrix_red").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/gharrix_red_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gharrix_red/gharrix_red.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gharrix_red/gharrix_red_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("halcian_pink", new MoaSkin("halcian_pink", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.halcian_pink"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("halcian_pink").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/halcian_pink_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/halcian_pink/halcian_pink.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/halcian_pink/halcian_pink_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("tivalier_green", new MoaSkin("tivalier_green", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.tivalier_green"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAngelMoaSkins("tivalier_green").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/tivalier_green_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/tivalier_green/tivalier_green.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/tivalier_green/tivalier_green_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.ANGEL, true))
        ));
        register("arctic_moa", new MoaSkin("arctic_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.arctic_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins("arctic_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/arctic_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/arctic_moa/arctic_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/arctic_moa/arctic_moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.VALKYRIE, true))
        ));
        register("cockatrice_moa", new MoaSkin("cockatrice_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.cockatrice_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins("cockatrice_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/cockatrice_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.VALKYRIE, true))
        ));
        register("phoenix_moa", new MoaSkin("phoenix_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.phoenix_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins("phoenix_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/phoenix_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.VALKYRIE, true))
        ));
        register("sentry_moa", new MoaSkin("sentry_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.sentry_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins("sentry_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/sentry_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.VALKYRIE, true))
        ));
        register("valkyrie_moa", new MoaSkin("valkyrie_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.valkyrie_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins("valkyrie_moa").test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/valkyrie_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/valkyrie_moa/valkyrie_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/valkyrie_moa/valkyrie_moa_saddle.png"))
                .info(new MoaSkin.Info(Patron.Tier.VALKYRIE, true))
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
        private final ResourceLocation saddleLocation;
        private final Info info;

        protected MoaSkin(String id, Properties properties) {
            this(id, properties.displayName, properties.userPredicate, properties.iconLocation, properties.skinLocation, properties.emissiveLocation, properties.saddleLocation, properties.info);
        }

        protected MoaSkin(String id, Component displayName, Predicate<User> userPredicate, ResourceLocation iconLocation, ResourceLocation skinLocation, ResourceLocation emissiveLocation, ResourceLocation saddleLocation, Info info) {
            this.id = id;
            this.displayName = displayName;
            this.userPredicate = userPredicate;
            this.iconLocation = iconLocation;
            this.skinLocation = skinLocation;
            this.emissiveLocation = emissiveLocation;
            this.saddleLocation = saddleLocation;
            this.info = info;
        }

        public String getId() {
            return this.id;
        }

        public Component getDisplayName() {
            return this.displayName;
        }

        public Predicate<User> getUserPredicate() {
            return this.userPredicate;
        }

        public ResourceLocation getIconLocation() {
            return this.iconLocation;
        }

        public ResourceLocation getSkinLocation() {
            return this.skinLocation;
        }

        @Nullable
        public ResourceLocation getEmissiveLocation() {
            return this.emissiveLocation;
        }

        public ResourceLocation getSaddleLocation() {
            return this.saddleLocation;
        }

        public Info getInfo() {
            return this.info;
        }

        public static MoaSkin read(FriendlyByteBuf buffer) {
            String id = buffer.readUtf();
            return MoaSkins.getMoaSkins().get(id);
        }

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
            private ResourceLocation saddleLocation;
            private Info info;

            public Properties displayName(Component displayName) {
                this.displayName = displayName;
                return this;
            }

            public Properties userPredicate(Predicate<User> userPredicate) {
                this.userPredicate = userPredicate;
                return this;
            }

            public Properties iconLocation(ResourceLocation iconLocation) {
                this.iconLocation = iconLocation;
                return this;
            }

            public Properties skinLocation(ResourceLocation skinLocation) {
                this.skinLocation = skinLocation;
                return this;
            }

            public Properties emissiveLocation(ResourceLocation emissiveLocation) {
                this.emissiveLocation = emissiveLocation;
                return this;
            }

            public Properties saddleLocation(ResourceLocation saddleLocation) {
                this.saddleLocation = saddleLocation;
                return this;
            }

            public Properties info(Info info) {
                this.info = info;
                return this;
            }
        }

        public record Info(Patron.Tier tier, boolean lifetime) { }
    }
}
