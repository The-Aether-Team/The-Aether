package com.aetherteam.aether.perk.types;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.nitrogen.api.users.User;
import com.google.common.collect.ImmutableMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MoaSkins {
    private static final Map<String, MoaSkin> MOA_SKINS = new LinkedHashMap<>();

    public static void registerMoaSkins() {
        for (MoaType moaType : AetherMoaTypes.MOA_TYPES.getEntries().stream().map(RegistryObject::get).toList()) {
            String name = (moaType.getId().getNamespace().equals(Aether.MODID) ? moaType.getId().getPath() : moaType.getId().toString().replace(":", ".")) + "_moa";
            register(name, new MoaSkin(name, new MoaSkin.Properties()
                    .displayName(Component.translatable("gui.aether.moa_skins.skin." + name))
                    .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                    .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/" + name + "_icon.png"))
                    .skinLocation(moaType.getMoaTexture())
                    .saddleLocation(moaType.getSaddleTexture())
                    .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
            ));
        }
        register("classic_moa", new MoaSkin("classic_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.classic_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/classic_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/classic_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("boko_yellow", new MoaSkin("boko_yellow", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.boko_yellow"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/boko_yellow_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/boko_yellow/boko_yellow.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/boko_yellow/boko_yellow_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("crookjaw_purple", new MoaSkin("crookjaw_purple", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.crookjaw_purple"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/crookjaw_purple_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/crookjaw_purple/crookjaw_purple_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("gharrix_red", new MoaSkin("gharrix_red", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.gharrix_red"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/gharrix_red_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gharrix_red/gharrix_red.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/gharrix_red/gharrix_red_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("halcian_pink", new MoaSkin("halcian_pink", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.halcian_pink"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/halcian_pink_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/halcian_pink/halcian_pink.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/halcian_pink/halcian_pink_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("tivalier_green", new MoaSkin("tivalier_green", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.tivalier_green"))
                .userPredicate((user) -> PerkUtil.hasLifetimeAscentanMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/tivalier_green_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/tivalier_green/tivalier_green.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/tivalier_green/tivalier_green_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.ASCENTAN, true))
        ));
        register("arctic_moa", new MoaSkin("arctic_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.arctic_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/arctic_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/arctic_moa/arctic_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/arctic_moa/arctic_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("cockatrice_moa", new MoaSkin("cockatrice_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.cockatrice_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/cockatrice_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/cockatrice_moa/cockatrice_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("phoenix_moa", new MoaSkin("phoenix_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.phoenix_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/phoenix_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/phoenix_moa/phoenix_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("sentry_moa", new MoaSkin("sentry_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.sentry_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/sentry_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa.png"))
                .emissiveLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa_emissive.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/sentry_moa/sentry_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
        ));
        register("valkyrie_moa", new MoaSkin("valkyrie_moa", new MoaSkin.Properties()
                .displayName(Component.translatable("gui.aether.moa_skins.skin.valkyrie_moa"))
                .userPredicate((user) -> PerkUtil.hasLifetimeValkyrieMoaSkins().test(user))
                .iconLocation(new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/icons/valkyrie_moa_icon.png"))
                .skinLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/valkyrie_moa/valkyrie_moa.png"))
                .saddleLocation(new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/skins/valkyrie_moa/valkyrie_moa_saddle.png"))
                .info(new MoaSkin.Info(User.Tier.VALKYRIE, true))
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
         * @return The {@link ResourceLocation} of the {@link MoaSkin}'s saddle texture.
         */
        public ResourceLocation getSaddleLocation() {
            return this.saddleLocation;
        }

        /**
         * @return The Patreon {@link com.aetherteam.nitrogen.api.users.User.Tier} and lifetime {@link Boolean} {@link Info} of the {@link MoaSkin}.
         */
        public Info getInfo() {
            return this.info;
        }

        /**
         * Reads a {@link MoaSkin} from a {@link FriendlyByteBuf} network buffer.
         * @param buffer The {@link FriendlyByteBuf} buffer.
         * @return A {@link MoaSkin}.
         */
        public static MoaSkin read(FriendlyByteBuf buffer) {
            String id = buffer.readUtf();
            return MoaSkins.getMoaSkins().get(id);
        }

        /**
         * Writes a {@link MoaSkin} to a {@link FriendlyByteBuf} network buffer.
         * @param buffer The {@link FriendlyByteBuf} buffer.
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
            private ResourceLocation saddleLocation;
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
             * @see MoaSkin#getSaddleLocation()
             */
            public Properties saddleLocation(ResourceLocation saddleLocation) {
                this.saddleLocation = saddleLocation;
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
