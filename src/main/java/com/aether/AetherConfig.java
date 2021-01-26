package com.aether;

import net.minecraftforge.common.ForgeConfigSpec;

public class AetherConfig {

    public static CommonConfig COMMON;
    public static ClientConfig CLIENT;

    public static class CommonConfig {

        public final Gameplay gameplay = new Gameplay();
        public final WorldGen worldGen = new WorldGen();

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("These options affect gameplay, and how some of the content works.").push("Gameplay Changes");
            {
                gameplay.aetherStart = builder.comment("Determines if the player will get an Aether Portal Frame item when first joining the world.").define("aether_start", false);
                gameplay.edibleAmbrosium = builder.comment("Makes it so you have to eat Ambrosium, instead of just right clicking to heal.").define("edible_ambrosium", false);
                gameplay.disableEternalDay = builder.comment("Disables eternal day making time cycle in the Aether without having to kill the Sun Spirit. This is mainly intended for use in modpacks.").define("disable_eternal_day", false);
                gameplay.disablePortal = builder.comment("Disables spawn of the Aether portal for use with portal being provided by another mod.").define("disable_portal", false);
                gameplay.disableStartupLoot = builder.comment("Disables startup loot when entering the Aether.").define("disable_startup_loot", false);
                gameplay.goldenFeather = builder.comment("Enables the Golden Feather in dungeon loot.").define("golden_feather", false);
                gameplay.maxLifeShards = builder.comment("The max amount of life shards that can be used per player.").defineInRange("max_life_shards", 10, 0, Integer.MAX_VALUE);
                gameplay.repeatSunSpiritDialogue = builder.comment("If disabed, the Sun Spirit's dialog will only show once per world.").define("repeat_sun_spirit_dialogue", true);
                gameplay.skyrootBucketOnly = builder.comment("If enabled, Aether Portals can only be lit by Skyroot Buckets.").define("skyroot_bucket_only", false);
                gameplay.sunAltarMultiplayer = builder.comment("Removes the requirement for a player to be an operator to use the Sun Altar in multiplayer.").define("sun_altar_multiplayer", false);
                gameplay.valkyrieCape = builder.comment("Enables the Valkyrie Cape in dungeon loot.").define("valkyrie_cape", true);
            }
            builder.pop();

            builder.comment("These options affect world generation.").push("World Generation Options");
            {
                worldGen.holidayGeneration = builder.comment("Enables natural christmas decor.").define("christmas_time", false);
                worldGen.pinkAercloudGeneration = builder.comment("Enables natural Pink Aercloud generation.").define("pink_aercloud_generation", false);
                worldGen.tallGrassEnabled = builder.comment("Enables naturally generating tallgrass").define("tallgrass_enabled", false);
            }
            builder.pop();
        }

        public static class Gameplay {
            public ForgeConfigSpec.BooleanValue aetherStart;
            public ForgeConfigSpec.BooleanValue edibleAmbrosium;
            public ForgeConfigSpec.BooleanValue disableEternalDay;
            public ForgeConfigSpec.BooleanValue disablePortal;
            public ForgeConfigSpec.BooleanValue disableStartupLoot;
            public ForgeConfigSpec.BooleanValue goldenFeather;
            public ForgeConfigSpec.IntValue maxLifeShards;
            public ForgeConfigSpec.BooleanValue repeatSunSpiritDialogue;
            public ForgeConfigSpec.BooleanValue skyrootBucketOnly;
            public ForgeConfigSpec.BooleanValue sunAltarMultiplayer;
            public ForgeConfigSpec.BooleanValue valkyrieCape;
        }

        public static class WorldGen {
            public ForgeConfigSpec.BooleanValue holidayGeneration;
            public ForgeConfigSpec.BooleanValue pinkAercloudGeneration;
            public ForgeConfigSpec.BooleanValue tallGrassEnabled;
        }
    }

    public static class ClientConfig {
        public final Visual visual = new Visual();

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("These options do not affect gameplay, and are purely visual.").push("Visual Options");
            {
                visual.installResourcePack = builder.comment("Determines whether the Aether b1.7.3 resource pack should be generated.").define("install_resourcepack", true);
                visual.legacyAltarName = builder.comment("Changes whether the Altar should be named Enchanter or not.").define("legacy_altar_name", false);
                visual.legacyModels = builder.comment("Changes Aether mobs to use their old models, if applicable.").define("legacy_models", false);
                visual.menuButton = builder.comment("Enables the Aether Menu toggle button.").define("menu_button", true);
                visual.menuEnabled = builder.comment("Enables the Aether Menu.").define("menu_enabled", false);
                visual.triviaDisabled = builder.comment("Disables the random trivia/tips you see during loading screens.").define("trivia_disabled", false);
                visual.updatedAercloudColors = builder.comment("Aerclouds will use their more saturated colors from later updates.").define("updated_aercloud_colors", false);
            }
            builder.pop();
        }

        public static class Visual {
            public ForgeConfigSpec.BooleanValue installResourcePack;
            public ForgeConfigSpec.BooleanValue legacyAltarName;
            public ForgeConfigSpec.BooleanValue legacyModels;
            public ForgeConfigSpec.BooleanValue menuButton;
            public ForgeConfigSpec.BooleanValue menuEnabled;
            public ForgeConfigSpec.BooleanValue triviaDisabled;
            public ForgeConfigSpec.BooleanValue updatedAercloudColors;
        }
    }
}
