package com.gildedgames.aether;

import com.gildedgames.aether.data.resources.AetherDimensions;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class AetherConfig
{
    public static class Common
    {
        public final ConfigValue<Boolean> enable_bed_explosions;
        public final ConfigValue<Boolean> start_with_portal;
        public final ConfigValue<Boolean> enable_startup_loot;
        public final ConfigValue<Boolean> edible_ambrosium;
        public final ConfigValue<Boolean> tools_debuff;
        public final ConfigValue<Boolean> healing_gummy_swets;
        public final ConfigValue<Integer> maximum_life_shards;
        public final ConfigValue<Boolean> repeat_sun_spirit_dialogue;

        public final ConfigValue<Boolean> spawn_golden_feather;
        public final ConfigValue<Boolean> spawn_valkyrie_cape;

        public final ConfigValue<Boolean> generate_tall_grass;
        public final ConfigValue<Boolean> generate_pink_aerclouds;
        public final ConfigValue<Boolean> generate_holiday_tree_always;
        public final ConfigValue<Boolean> generate_holiday_tree_seasonally;

        public final ConfigValue<Boolean> sun_altar_whitelist;

        public final ConfigValue<Boolean> disable_aether_portal;
        public final ConfigValue<Boolean> disable_falling_to_overworld;
        public final ConfigValue<Boolean> disable_eternal_day;
        public final ConfigValue<String> portal_destination_dimension_ID;
        public final ConfigValue<String> portal_return_dimension_ID;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Gameplay");
            enable_bed_explosions = builder
                    .comment("Vanilla's beds will explode in the Aether")
                    .translation("config.aether.common.gameplay.enable_bed_explosions")
                    .define("Beds explode", false);
            start_with_portal = builder
                    .comment("On world creation, the player is given an Aether Portal Frame item to automatically go to the Aether with")
                    .translation("config.aether.common.gameplay.start_with_portal")
                    .define("Gives player Aether Portal Frame item", false);
            enable_startup_loot = builder
                    .comment("When the player enters the Aether, they are given a Book of Lore and Golden Parachutes as starting loot")
                    .translation("config.aether.common.gameplay.enable_startup_loot")
                    .define("Gives starting loot on entry", true);
            tools_debuff = builder
                    .comment("Tools that aren't from the Aether will mine all blocks slower than ones that are from the Aether")
                    .translation("config.aether.common.gameplay.tools_debuff")
                    .define("Debuff non-Aether tools", false);
            edible_ambrosium = builder
                    .comment("Ambrosium Shards can be eaten to restore a half heart of health")
                    .translation("config.aether.common.gameplay.edible_ambrosium")
                    .define("Ambrosium Shards are edible", false);
            healing_gummy_swets = builder
                    .comment("Gummy Swets when eaten restore full health instead of full hunger")
                    .translation("config.aether.common.gameplay.healing_gummy_swets")
                    .define("Gummy Swets restore health", false);
            maximum_life_shards = builder
                    .comment("Determines the limit of the amount of Life Shards a player can consume to increase their health")
                    .translation("config.aether.common.gameplay.maximum_life_shards")
                    .define("Maximum consumable Life Shards", 10);
            repeat_sun_spirit_dialogue = builder
                    .comment("Determines whether the Sun Spirit's dialogue when meeting him should play through every time you meet him")
                    .translation("config.aether.common.gameplay.repeat_sun_spirit_dialogue")
                    .define("Repeat Sun Spirit's battle dialogue", true);
            builder.pop();

            builder.push("Loot");
            spawn_golden_feather = builder
                    .comment("Allows the Golden Feather to spawn in the Silver Dungeon loot table")
                    .translation("config.aether.common.loot.spawn_golden_feather")
                    .define("Golden Feather in loot", false);
            spawn_valkyrie_cape = builder
                    .comment("Allows the Valkyrie Cape to spawn in the Silver Dungeon loot table")
                    .translation("config.aether.common.loot.spawn_valkyrie_cape")
                    .define("Valkyrie Cape in loot", true);
            builder.pop();

            builder.push("World Generation");
            generate_tall_grass = builder
                    .comment("Determines whether the Aether should generate Tall Grass blocks on terrain or not")
                    .translation("config.aether.common.world_generation.generate_tall_grass")
                    .define("Generate Tall Grass in the Aether", false);
            generate_pink_aerclouds = builder
                    .comment("Determines whether Pink Aerclouds should generate in the skies of the Aether along with other Aerclouds")
                    .translation("config.aether.common.world_generation.generate_pink_aerclouds")
                    .define("Generate Pink Aerclouds", false);
            generate_holiday_tree_always = builder
                    .comment("Determines whether Holiday Trees should always be able to generate when exploring new chunks in the Aether, if true, this overrides 'Generate Holiday Trees seasonally'")
                    .translation("config.aether.common.world_generation.generate_holiday_tree_always")
                    .define("Generate Holiday Trees always", false);
            generate_holiday_tree_seasonally = builder
                    .comment("Determines whether Holiday Trees should be able to generate during the time frame of December and January when exploring new chunks in the Aether, only works if 'Generate Holiday Trees always' is set to false")
                    .translation("config.aether.common.world_generation.generate_holiday_tree_seasonally")
                    .define("Generate Holiday Trees seasonally", true);
            builder.pop();

            builder.push("Multiplayer");
            sun_altar_whitelist = builder
                    .comment("Makes it so that only whitelisted users or anyone with permission level 4 can use the Sun Altar on a server")
                    .translation("config.aether.common.multiplayer.sun_altar_whitelist")
                    .define("Only whitelisted users access Sun Altars", false);
            builder.pop();

            builder.push("Modpack");
            disable_aether_portal = builder
                    .comment("Prevents the Aether Portal from being created normally in the mod")
                    .translation("config.aether.common.modpack.disable_aether_portal")
                    .define("Disables Aether Portal creation", false);
            disable_falling_to_overworld = builder
                    .comment("Prevents the player from falling back to the Overworld when they fall out of the Aether")
                    .translation("config.aether.common.modpack.disable_falling_to_overworld")
                    .define("Disables falling into the Overworld", false);
            disable_eternal_day = builder
                    .comment("Removes eternal day so that the Aether has a normal daylight cycle even before defeating the Sun Spirit")
                    .translation("config.aether.common.modpack.disable_eternal_day")
                    .define("Disables eternal day", false);
            portal_destination_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will send the player to")
                    .translation("config.aether.common.modpack.portal_destination_dimension_ID")
                    .define("Sets portal destination dimension", AetherDimensions.AETHER_LEVEL.location().toString());
            portal_return_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will return the player to")
                    .translation("config.aether.common.modpack.portal_return_dimension_ID")
                    .define("Sets portal return dimension", Level.OVERWORLD.location().toString());
            builder.pop();
        }
    }

    public static class Client
    {
        public final ConfigValue<Boolean> legacy_models;
        public final ConfigValue<Boolean> disable_aether_skybox;

        public final ConfigValue<Boolean> enable_aether_menu;
        public final ConfigValue<Boolean> enable_aether_menu_button;
        public final ConfigValue<Boolean> enable_world_preview;
        public final ConfigValue<Boolean> enable_world_preview_button;
        public final ConfigValue<Boolean> enable_quick_load_button;
        public final ConfigValue<Boolean> menu_type_toggles_alignment;
        public final ConfigValue<Boolean> align_vanilla_menu_elements_left;
        public final ConfigValue<Boolean> align_aether_menu_elements_left;
        public final ConfigValue<Boolean> enable_trivia;
        public final ConfigValue<Boolean> enable_silver_hearts;

        public final ConfigValue<Integer> music_backup_min_delay;
        public final ConfigValue<Integer> music_backup_max_delay;
        public final ConfigValue<Boolean> disable_music_manager;
        public final ConfigValue<Boolean> disable_aether_menu_music;
        public final ConfigValue<Boolean> disable_vanilla_world_preview_menu_music;
        public final ConfigValue<Boolean> disable_aether_world_preview_menu_music;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("Rendering");
            legacy_models = builder
                    .comment("Changes Zephyr and Aerwhale rendering to use their old models from the b1.7.3 version of the mod")
                    .translation("config.aether.client.rendering.legacy_models")
                    .define("Switches to legacy mob models", false);
            disable_aether_skybox = builder
                    .comment("Disables the Aether's custom skybox in case you have a shader that is incompatible with custom skyboxes")
                    .translation("config.aether.client.rendering.disable_aether_skybox")
                    .define("Disables Aether custom skybox", false);
            builder.pop();

            builder.push("Gui");
            enable_aether_menu = builder
                    .comment("Changes the vanilla Minecraft menu into the Aether menu")
                    .translation("config.aether.client.gui.enable_aether_menu")
                    .define("Enables Aether menu", false);
            enable_aether_menu_button = builder
                    .comment("Adds a button to the top right of the main menu screen to toggle between the Aether and vanilla menu")
                    .translation("config.aether.client.gui.enable_aether_menu_button")
                    .define("Enables Aether menu button", true);
            enable_world_preview = builder
                    .comment("Changes the background panorama into a preview of the latest played world")
                    .translation("config.aether.client.gui.enable_world_preview")
                    .define("Enables world preview", false);
            enable_world_preview_button = builder
                    .comment("Adds a button to the top right of the main menu screen to toggle between the panorama and world preview")
                    .translation("config.aether.client.gui.enable_world_preview_button")
                    .define("Enables toggle world button", true);
            enable_quick_load_button = builder
                    .comment("Adds a button to the top right of the main menu screen to allow quick loading into a world if the world preview is enabled")
                    .translation("config.aether.client.gui.enable_quick_load_button")
                    .define("Enables quick load button", true);
            menu_type_toggles_alignment = builder
                    .comment("Determines that menu elements will align left if the menu's world preview is active, if true, this overrides 'Align menu elements left'")
                    .translation("config.aether.client.gui.menu_type_toggles_alignment")
                    .define("Align menu elements left with world preview", false);
            align_vanilla_menu_elements_left = builder
                    .comment("Aligns the elements of the vanilla menu to the left, only works if 'Align menu left with world preview' is set to false")
                    .translation("config.aether.client.gui.align_vanilla_menu_elements_left")
                    .define("Align vanilla menu elements left", false);
            align_aether_menu_elements_left = builder
                    .comment("Aligns the elements of the Aether menu to the left, only works if 'Align menu left with world preview' is set to false")
                    .translation("config.aether.client.gui.align_aether_menu_elements_left")
                    .define("Align Aether menu elements left", true);
            enable_trivia = builder
                    .comment("Adds random trivia and tips to the bottom of loading screens")
                    .translation("config.aether.client.gui.enable_trivia")
                    .define("Enables random trivia", true);
            enable_silver_hearts = builder
                    .comment("Makes the extra hearts given by life shards display as silver colored")
                    .translation("config.aether.client.gui.enable_silver_hearts")
                    .define("Enables silver life shard hearts", true);
            builder.pop();

            builder.push("Audio");
            music_backup_min_delay = builder
                    .comment("Sets the minimum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether")
                    .translation("config.aether.client.audio.music_backup_min_delay")
                    .define("Set backup minimum music delay", 12000);
            music_backup_max_delay = builder
                    .comment("Sets the maximum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether")
                    .translation("config.aether.client.audio.music_backup_max_delay")
                    .define("Set backup maximum music delay", 24000);
            disable_music_manager = builder
                    .comment("Disables the Aether's internal music manager, if true, this overrides all other audio configs")
                    .translation("config.aether.client.audio.disable_music_manager")
                    .define("Disables Aether music manager", false);
            disable_aether_menu_music = builder
                    .comment("Disables the Aether's menu music in case another mod implements its own, only works if 'Disables Aether music manager' is false")
                    .translation("config.aether.client.audio.disable_aether_menu_music")
                    .define("Disables Aether menu music", false);
            disable_vanilla_world_preview_menu_music = builder
                    .comment("Disables the menu music on the vanilla world preview menu, only works if 'Disables Aether music manager' is false")
                    .translation("config.aether.client.audio.disable_vanilla_world_preview_menu_music")
                    .define("Disables vanilla world preview menu music", false);
            disable_aether_world_preview_menu_music = builder
                    .comment("Disables the menu music on the Aether world preview menu, only works if 'Disables Aether music manager' is false")
                    .translation("config.aether.client.audio.disable_aether_world_preview_menu_music")
                    .define("Disables Aether world preview menu music", false);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
