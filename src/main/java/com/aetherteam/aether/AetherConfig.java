package com.aetherteam.aether;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class AetherConfig {
    public static class Server {
        public final ConfigValue<Boolean> enable_bed_explosions;
        public final ConfigValue<Boolean> tools_debuff;
        public final ConfigValue<Boolean> edible_ambrosium;
        public final ConfigValue<Boolean> berry_bush_consistency;
        public final ConfigValue<Boolean> crystal_leaves_consistency;
        public final ConfigValue<Boolean> healing_gummy_swets;
        public final ConfigValue<Integer> hammer_of_kingbdogz_cooldown;
        public final ConfigValue<Integer> cloud_staff_cooldown;
        public final ConfigValue<Integer> maximum_life_shards;
        public final ConfigValue<Boolean> require_gloves;

        public final ConfigValue<Boolean> spawn_golden_feather;
        public final ConfigValue<Boolean> spawn_valkyrie_cape;

        public final ConfigValue<Boolean> generate_tall_grass;
        public final ConfigValue<Boolean> generate_holiday_tree_always;
        public final ConfigValue<Boolean> generate_holiday_tree_seasonally;

        public final ConfigValue<Boolean> balance_invisibility_cloak;
        public final ConfigValue<Integer> invisibility_visibility_time;
        public final ConfigValue<Boolean> sun_altar_whitelist;

        public final ConfigValue<Boolean> spawn_in_aether;
        public final ConfigValue<Boolean> disable_aether_portal;
        public final ConfigValue<Boolean> disable_falling_to_overworld;
        public final ConfigValue<Boolean> disable_eternal_day;
        public final ConfigValue<String> portal_destination_dimension_ID;
        public final ConfigValue<String> portal_return_dimension_ID;

        public Server(ModConfigSpec.Builder builder) {
            builder.push("Gameplay");
            enable_bed_explosions = builder
                    .comment("Vanilla's beds will explode in the Aether")
                    .translation("config.aether.server.gameplay.enable_bed_explosions")
                    .define("Beds explode", false);
            tools_debuff = builder
                    .comment("Tools that aren't from the Aether will mine Aether blocks slower than tools that are from the Aether")
                    .translation("config.aether.server.gameplay.tools_debuff")
                    .define("Debuff non-Aether tools", true);
            edible_ambrosium = builder
                    .comment("Ambrosium Shards can be eaten to restore a half heart of health")
                    .translation("config.aether.server.gameplay.edible_ambrosium")
                    .define("Ambrosium Shards are edible", true);
            berry_bush_consistency = builder
                    .comment("Makes Berry Bushes and Bush Stems behave consistently with Sweet Berry Bushes")
                    .translation("config.aether.server.gameplay.berry_bush_consistency")
                    .define("Berry Bush consistency", false);
            crystal_leaves_consistency = builder
                    .comment("Makes Crystal Fruit Leaves behave consistently with Sweet Berry Bushes")
                    .translation("config.aether.server.gameplay.crystal_leaves_consistency")
                    .define("Crystal Fruit Leaves consistency", false);
            healing_gummy_swets = builder
                    .comment("Gummy Swets when eaten restore full health instead of full hunger")
                    .translation("config.aether.server.gameplay.healing_gummy_swets")
                    .define("Gummy Swets restore health", false);
            maximum_life_shards = builder
                    .comment("Determines the limit of the amount of Life Shards a player can consume to increase their health")
                    .translation("config.aether.server.gameplay.maximum_life_shards")
                    .define("Maximum consumable Life Shards", 10);
            hammer_of_kingbdogz_cooldown = builder
                    .comment("Determines the cooldown in ticks for the Hammer of Kingbdogz's ability")
                    .translation("config.aether.server.gameplay.hammer_of_kingbdogz_cooldown")
                    .define("Cooldown for the Hammer of Kingbdogz projectile", 50);
            cloud_staff_cooldown = builder
                    .comment("Determines the cooldown in ticks for the Cloud Staff's ability")
                    .translation("config.aether.server.gameplay.cloud_staff_cooldown")
                    .define("Cooldown for the Cloud Staff", 40);
            require_gloves = builder
                    .comment("Makes armor abilities depend on wearing the respective gloves belonging to an armor set")
                    .translation("config.aether.server.gameplay.require_gloves")
                    .define("Require gloves for set abilities", true);
            builder.pop();

            builder.push("Loot");
            spawn_golden_feather = builder
                    .comment("Allows the Golden Feather to spawn in the Silver Dungeon loot table")
                    .translation("config.aether.server.loot.spawn_golden_feather")
                    .define("Golden Feather in loot", false);
            spawn_valkyrie_cape = builder
                    .comment("Allows the Valkyrie Cape to spawn in the Silver Dungeon loot table")
                    .translation("config.aether.server.loot.spawn_valkyrie_cape")
                    .define("Valkyrie Cape in loot", true);
            builder.pop();

            builder.push("World Generation");
            generate_tall_grass = builder
                    .comment("Determines whether the Aether should generate Tall Grass blocks on terrain or not")
                    .translation("config.aether.server.world_generation.generate_tall_grass")
                    .define("Generate Tall Grass in the Aether", true);
            generate_holiday_tree_always = builder
                    .comment("Determines whether Holiday Trees should always be able to generate when exploring new chunks in the Aether, if true, this overrides 'Generate Holiday Trees seasonally'")
                    .translation("config.aether.server.world_generation.generate_holiday_tree_always")
                    .define("Generate Holiday Trees always", false);
            generate_holiday_tree_seasonally = builder
                    .comment("Determines whether Holiday Trees should be able to generate during the time frame of December and January when exploring new chunks in the Aether, only works if 'Generate Holiday Trees always' is set to false")
                    .translation("config.aether.server.world_generation.generate_holiday_tree_seasonally")
                    .define("Generate Holiday Trees seasonally", true);
            builder.pop();

            builder.push("Multiplayer");
            balance_invisibility_cloak = builder
                    .comment("Makes the Invisibility Cloak more balanced in PVP by disabling equipment invisibility temporarily after attacks")
                    .translation("config.aether.server.multiplayer.balance_invisibility_cloak")
                    .define("Balance Invisibility Cloak for PVP", false);
            invisibility_visibility_time = builder
                    .comment("Sets the time in ticks that it takes for the player to become fully invisible again after attacking when wearing an Invisibility Cloak; only works with 'Balance Invisibility Cloak for PVP'")
                    .translation("config.aether.server.multiplayer.invisibility_visibility_time")
                    .define("Invisibility Cloak visibility timer", 50);
            sun_altar_whitelist = builder
                    .comment("Makes it so that only whitelisted users or anyone with permission level 4 can use the Sun Altar on a server")
                    .translation("config.aether.server.multiplayer.sun_altar_whitelist")
                    .define("Only whitelisted users access Sun Altars", false);
            builder.pop();

            builder.push("Modpack");
            spawn_in_aether = builder
                    .comment("Spawns the player in the Aether dimension; this is best enabled alongside other modpack configuration to avoid issues")
                    .translation("config.aether.server.modpack.spawn_in_aether")
                    .define("Spawns the player in the Aether", false);
            disable_aether_portal = builder
                    .comment("Prevents the Aether Portal from being created normally in the mod")
                    .translation("config.aether.server.modpack.disable_aether_portal")
                    .define("Disables Aether Portal creation", false);
            disable_falling_to_overworld = builder
                    .comment("Prevents the player from falling back to the Overworld when they fall out of the Aether")
                    .translation("config.aether.server.modpack.disable_falling_to_overworld")
                    .define("Disables falling into the Overworld", false);
            disable_eternal_day = builder
                    .comment("Removes eternal day so that the Aether has a normal daylight cycle even before defeating the Sun Spirit")
                    .translation("config.aether.server.modpack.disable_eternal_day")
                    .define("Disables eternal day", false);
            portal_destination_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will send the player to")
                    .translation("config.aether.server.modpack.portal_destination_dimension_ID")
                    .define("Sets portal destination dimension", AetherDimensions.AETHER_LEVEL.location().toString());
            portal_return_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will return the player to")
                    .translation("config.aether.server.modpack.portal_return_dimension_ID")
                    .define("Sets portal return dimension", Level.OVERWORLD.location().toString());
            builder.pop();
        }
    }

    public static class Common {
        public final ConfigValue<Boolean> use_curios_menu;
        public final ConfigValue<Boolean> start_with_portal;
        public final ConfigValue<Boolean> enable_startup_loot;
        public final ConfigValue<Boolean> reposition_slider_message;
        public final ConfigValue<Boolean> repeat_sun_spirit_dialogue;
        public final ConfigValue<Boolean> show_patreon_message;

        public final ConfigValue<Boolean> add_temporary_freezing_automatically;
        public final ConfigValue<Boolean> add_ruined_portal_automatically;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("Gameplay");
            use_curios_menu = builder
                    .worldRestart()
                    .comment("Use the default Curios menu instead of the Aether's Accessories Menu. WARNING: Do not enable this without emptying your equipped accessories")
                    .translation("config.aether.common.gameplay.use_curios_menu")
                    .define("Use default Curios' menu", false);
            start_with_portal = builder
                    .comment("On world creation, the player is given an Aether Portal Frame item to automatically go to the Aether with")
                    .translation("config.aether.common.gameplay.start_with_portal")
                    .define("Gives player Aether Portal Frame item", false);
            enable_startup_loot = builder
                    .comment("When the player enters the Aether, they are given a Book of Lore and Golden Parachutes as starting loot")
                    .translation("config.aether.common.gameplay.enable_startup_loot")
                    .define("Gives starting loot on entry", true);
            reposition_slider_message = builder
                .comment("Moves the message for when a player attacks the Slider with an incorrect item to be above the hotbar instead of in chat")
                .translation("config.aether.common.gameplay.reposition_slider_message")
                .define("Reposition attack message above hotbar", false);
            repeat_sun_spirit_dialogue = builder
                    .comment("Determines whether the Sun Spirit's dialogue when meeting him should play through every time you meet him")
                    .translation("config.aether.common.gameplay.repeat_sun_spirit_dialogue")
                    .define("Repeat Sun Spirit's battle dialogue", true);
            show_patreon_message = builder
                    .comment("Determines if a message that links The Aether mod's Patreon should show")
                    .translation("config.aether.common.gameplay.show_patreon_message")
                    .define("Show Patreon message", true);
            builder.pop();

            builder.push("Data Pack");
            add_temporary_freezing_automatically = builder
                    .worldRestart()
                    .comment("Sets the Aether Temporary Freezing data pack to be added to new worlds automatically")
                    .translation("config.aether.common.data_pack.add_temporary_freezing_automatically")
                    .define("Add Temporary Freezing automatically", false);
            add_ruined_portal_automatically = builder
                    .worldRestart()
                    .comment("Sets the Aether Ruined Portals data pack to be added to new worlds automatically")
                    .translation("config.aether.common.data_pack.add_ruined_portal_automatically")
                    .define("Add Ruined Portals automatically", false);
            builder.pop();
        }
    }

    public static class Client {
        public final ConfigValue<Boolean> legacy_models;
        public final ConfigValue<Boolean> disable_aether_skybox;
        public final ConfigValue<Boolean> colder_lightmap;
        public final ConfigValue<Boolean> green_sunset;

        public final ConfigValue<Boolean> enable_aether_menu_button;
        public final ConfigValue<Boolean> enable_world_preview;
        public final ConfigValue<Boolean> enable_world_preview_button;
        public final ConfigValue<Boolean> enable_quick_load_button;
        public final ConfigValue<Boolean> menu_type_toggles_alignment;
        public final ConfigValue<String> default_aether_menu;
        public final ConfigValue<String> default_minecraft_menu;
        public final ConfigValue<Boolean> enable_trivia;
        public final ConfigValue<Boolean> enable_silver_hearts;
        public final ConfigValue<Boolean> disable_accessory_button;
        public final ConfigValue<Integer> portal_text_y;
        public final ConfigValue<Integer> button_inventory_x;
        public final ConfigValue<Integer> button_inventory_y;
        public final ConfigValue<Integer> button_creative_x;
        public final ConfigValue<Integer> button_creative_y;
        public final ConfigValue<Integer> button_accessories_x;
        public final ConfigValue<Integer> button_accessories_y;
        public final ConfigValue<Integer> layout_perks_x;
        public final ConfigValue<Integer> layout_perks_y;
        public final ConfigValue<Boolean> enable_hammer_cooldown_overlay;

        public final ConfigValue<Integer> music_backup_min_delay;
        public final ConfigValue<Integer> music_backup_max_delay;
        public final ConfigValue<Boolean> disable_music_manager;
        public final ConfigValue<Boolean> disable_aether_menu_music;
        public final ConfigValue<Boolean> disable_vanilla_world_preview_menu_music;
        public final ConfigValue<Boolean> disable_aether_world_preview_menu_music;

        public final ConfigValue<Boolean> should_disable_cumulus_button;
        public final ConfigValue<Boolean> enable_server_button;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Rendering");
            legacy_models = builder
                    .comment("Changes Zephyr and Aerwhale rendering to use their old models from the b1.7.3 version of the mod")
                    .translation("config.aether.client.rendering.legacy_models")
                    .define("Switches to legacy mob models", false);
            disable_aether_skybox = builder
                    .comment("Disables the Aether's custom skybox in case you have a shader that is incompatible with custom skyboxes")
                    .translation("config.aether.client.rendering.disable_aether_skybox")
                    .define("Disables Aether custom skybox", false);
            colder_lightmap = builder
                    .comment("Removes warm-tinting of the lightmap in the Aether, giving the lighting a colder feel")
                    .translation("config.aether.client.rendering.colder_lightmap")
                    .define("Makes lightmap colder", false);
            green_sunset = builder
                    .comment("Enables a green-tinted sunrise and sunset in the Aether, similar to the original mod")
                    .translation("config.aether.client.rendering.green_sunset")
                    .define("Enables green sunrise/sunset", false);
            builder.pop();

            builder.push("Gui");
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
            default_aether_menu = builder
                    .comment("Determines the default Aether menu style to switch to with the menu theme button")
                    .translation("config.aether.client.gui.default_aether_menu")
                    .define("Default Aether menu style", "aether:the_aether_left");
            default_minecraft_menu = builder
                    .comment("Determines the default Minecraft menu style to switch to with the menu theme button")
                    .translation("config.aether.client.gui.default_minecraft_menu")
                    .define("Default Minecraft menu style", "cumulus_menus:minecraft");
            enable_trivia = builder
                    .comment("Adds random trivia and tips to the bottom of loading screens")
                    .translation("config.aether.client.gui.enable_trivia")
                    .define("Enables random trivia", true);
            enable_silver_hearts = builder
                    .comment("Makes the extra hearts given by life shards display as silver colored")
                    .translation("config.aether.client.gui.enable_silver_hearts")
                    .define("Enables silver life shard hearts", true);
            disable_accessory_button = builder
                    .comment("Disables the Aether's accessories button from appearing in GUIs")
                    .translation("config.aether.client.gui.disable_accessory_button")
                    .define("Disables the accessories button", false);
            portal_text_y = builder
                    .comment("The y-coordinate of the Ascending to the Aether and Descending from the Aether text in loading screens")
                    .translation("config.aether.client.gui.portal_text_y")
                    .define("Portal text y-coordinate in loading screens", 50);
            button_inventory_x = builder
                    .comment("The x-coordinate of the accessories button in the inventory and curios menus")
                    .translation("config.aether.client.gui.button_inventory_x")
                    .define("Button x-coordinate in inventory menus", 27);
            button_inventory_y = builder
                    .comment("The y-coordinate of the accessories button in the inventory and curios menus")
                    .translation("config.aether.client.gui.button_inventory_y")
                    .define("Button y-coordinate in inventory menus", 68);
            button_creative_x = builder
                    .comment("The x-coordinate of the accessories button in the creative menu")
                    .translation("config.aether.client.gui.button_creative_x")
                    .define("Button x-coordinate in creative menu", 74);
            button_creative_y = builder
                    .comment("The y-coordinate of the accessories button in the creative menu")
                    .translation("config.aether.client.gui.button_creative_y")
                    .define("Button y-coordinate in creative menu", 40);
            button_accessories_x = builder
                    .comment("The x-coordinate of the accessories button in the accessories menu")
                    .translation("config.aether.client.gui.button_accessories_x")
                    .define("Button x-coordinate in accessories menu", 9);
            button_accessories_y = builder
                    .comment("The y-coordinate of the accessories button in the accessories menu")
                    .translation("config.aether.client.gui.button_accessories_y")
                    .define("Button y-coordinate in accessories menu", 68);
            layout_perks_x = builder
                    .comment("The x-coordinate of the perks button layout when in the pause menu")
                    .translation("config.aether.client.gui.layout_perks_x")
                    .define("Layout x-coordinate in pause menu", -116);
            layout_perks_y = builder
                    .comment("The y-coordinate of the perks button layout when in the pause menu")
                    .translation("config.aether.client.gui.layout_perks_y")
                    .define("Layout y-coordinate in pause menu", 0);
            enable_hammer_cooldown_overlay = builder
                    .comment("Enables the overlay at the top of the screen for the Hammer of Kingbdogz' cooldown")
                    .translation("config.aether.client.gui.enable_hammer_cooldown_overlay")
                    .define("Enables Hammer of Kingbdogz' cooldown overlay", true);
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

            builder.push("Miscellaneous");
            should_disable_cumulus_button = builder
                    .comment("Disables the Cumulus menu selection screen button on launch")
                    .translation("config.aether.client.miscellaneous.should_disable_cumulus_button")
                    .define("Disable Cumulus button", true);
            enable_server_button = builder
                    .comment("Enables a direct join button for the official server")
                    .translation("config.aether.client.miscellaneous.enable_server_button")
                    .define("Enables server button", false);
            builder.pop();
        }
    }

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Server, ModConfigSpec> serverSpecPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();

        final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
