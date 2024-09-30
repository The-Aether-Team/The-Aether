package com.aetherteam.aether;

import com.aetherteam.aether.advancement.AetherAdvancementTriggers;
import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.AetherCauldronInteractions;
import com.aetherteam.aether.block.dispenser.AetherDispenseBehaviors;
import com.aetherteam.aether.block.dispenser.DispenseDartBehavior;
import com.aetherteam.aether.block.dispenser.DispenseSkyrootBoatBehavior;
import com.aetherteam.aether.block.dispenser.DispenseUsableItemBehavior;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.CombinedPackResources;
import com.aetherteam.aether.client.TriviaGenerator;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.command.SunAltarWhitelist;
import com.aetherteam.aether.data.AetherData;
import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.ai.AetherBlockPathTypes;
import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.conditions.AetherLootConditions;
import com.aetherteam.aether.loot.functions.AetherLootFunctions;
import com.aetherteam.aether.loot.modifiers.AetherLootModifiers;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.perk.types.MoaSkins;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.world.AetherPoi;
import com.aetherteam.aether.world.feature.AetherFeatures;
import com.aetherteam.aether.world.foliageplacer.AetherFoliagePlacerTypes;
import com.aetherteam.aether.world.placementmodifier.AetherPlacementModifiers;
import com.aetherteam.aether.world.processor.AetherStructureProcessors;
import com.aetherteam.aether.world.structure.AetherStructureTypes;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import com.aetherteam.aether.world.treedecorator.AetherTreeDecoratorTypes;
import com.aetherteam.aether.world.trunkplacer.AetherTrunkPlacerTypes;
import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.function.UnaryOperator;

@Mod(Aether.MODID)
public class Aether {
    public static final String MODID = "aether";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Path DIRECTORY = FMLPaths.CONFIGDIR.get().resolve(Aether.MODID);

    public static final TriviaGenerator TRIVIA_READER = new TriviaGenerator();

    public Aether(IEventBus bus, Dist dist) {
        bus.addListener(AetherData::dataSetup);
        bus.addListener(this::commonSetup);
        bus.addListener(this::packSetup);
        bus.addListener(NewRegistryEvent.class, event -> event.register(AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY));
        bus.addListener(NewRegistryEvent.class, event -> event.register(AetherMoaTypes.MOA_TYPE_REGISTRY));

        DeferredRegister<?>[] registers = {
                AetherBlocks.BLOCKS,
                AetherItems.ITEMS,
                AetherEntityTypes.ENTITY_TYPES,
                AetherBlockEntityTypes.BLOCK_ENTITY_TYPES,
                AetherMenuTypes.MENU_TYPES,
                AetherEffects.EFFECTS,
                AetherParticleTypes.PARTICLES,
                AetherFeatures.FEATURES,
                AetherFoliagePlacerTypes.FOLIAGE_PLACERS,
                AetherPlacementModifiers.PLACEMENT_MODIFIERS,
                AetherTrunkPlacerTypes.TRUNK_PLACERS,
                AetherTreeDecoratorTypes.TREE_DECORATORS,
                AetherPoi.POI,
                AetherStructureTypes.STRUCTURE_TYPES,
                AetherStructurePieceTypes.STRUCTURE_PIECE_TYPES,
                AetherStructureProcessors.STRUCTURE_PROCESSOR_TYPES,
                AetherRecipeTypes.RECIPE_TYPES,
                AetherRecipeSerializers.RECIPE_SERIALIZERS,
                AetherLootFunctions.LOOT_FUNCTION_TYPES,
                AetherLootConditions.LOOT_CONDITION_TYPES,
                AetherLootModifiers.GLOBAL_LOOT_MODIFIERS,
                AetherSoundEvents.SOUNDS,
                AetherGameEvents.GAME_EVENTS,
                AetherCreativeTabs.CREATIVE_MODE_TABS,
                AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDES,
                AetherMoaTypes.MOA_TYPES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        if (dist == Dist.CLIENT) {
            AetherMenus.MENUS.register(bus);
        }

        AetherBlocks.registerWoodTypes(); // Registered this early to avoid bugs with WoodTypes and signs.

        DIRECTORY.toFile().mkdirs(); // Ensures the Aether's config folder is generated.
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, AetherConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AetherConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AetherConfig.CLIENT_SPEC);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        AetherPacketHandler.register();

        Reflection.initialize(SunAltarWhitelist.class);
        Reflection.initialize(AetherRecipeBookTypes.class);
        Reflection.initialize(AetherBlockPathTypes.class);
        Reflection.initialize(AetherMobCategory.class);
        Reflection.initialize(AetherAdvancementTriggers.class);

        MoaSkins.registerMoaSkins();

        event.enqueueWork(() -> {
            AetherBlocks.registerFuels();
            AetherBlocks.registerPots();
            AetherBlocks.registerFlammability();
            AetherBlocks.registerFluidInteractions();

            AetherItems.setupBucketReplacements();

            this.registerDispenserBehaviors();
            this.registerCauldronInteractions();
            this.registerComposting();
        });
    }

    public void packSetup(AddPackFindersEvent event) {
        // Resource Packs
        this.setupReleasePack(event);
        this.setupBetaPack(event);
        this.setupCTMFixPack(event);
        this.setupTipsPack(event);
        this.setupColorblindPack(event);
        this.setupTooltipsPack(event);

        // Data Packs
        this.setupAccessoriesPack(event);
        this.setupCuriosOverridePack(event);
        this.setupTemporaryFreezingPack(event);
        this.setupRuinedPortalPack(event);
    }

    /**
     * A built-in resource pack for programmer art based on the 1.2.5 version of the mod.
     */
    private void setupReleasePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/classic_125");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(Aether.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath, false);
            this.createCombinedPack(event, resourcePath, pack, "builtin/aether_125_art", "pack.aether.125.title", "pack.aether.125.description");
        }
    }

    /**
     * A built-in resource pack for programmer art based on the b1.7.3 version of the mod.
     */
    private void setupBetaPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/classic_b173");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(Aether.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath, false);
            this.createCombinedPack(event, resourcePath, pack, "builtin/aether_b173_art", "pack.aether.b173.title", "pack.aether.b173.description");
        }
    }

    /**
     * Creates a built-in resource pack that combines asset files from two different locations.
     * @param sourcePath The {@link Path} of the non-base assets.
     * @param pack The {@link PathPackResources} that handles the non-base asset path for the resource pack.
     * @param name The {@link String} internal name of the resource pack.
     * @param title The {@link String} title of the resource pack.
     * @param description The {@link String} description of the resource pack.
     */
    private void createCombinedPack(AddPackFindersEvent event, Path sourcePath, PathPackResources pack, String name, String title, String description) {
        Path baseResourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/classic_base");
        PathPackResources basePack = new PathPackResources(ModList.get().getModFileById(Aether.MODID).getFile().getFileName() + ":" + baseResourcePath, baseResourcePath, false);
        List<PathPackResources> mergedPacks = List.of(pack, basePack);
        Pack.ResourcesSupplier resourcesSupplier = new CombinedPackResources.CombinedResourcesSupplier(new PackMetadataSection(Component.translatable(description), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)), mergedPacks, sourcePath);
        Pack.Info info = Pack.readPackInfo(name, resourcesSupplier, SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
        if (info != null) {
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                    name,
                    Component.translatable(title),
                    false,
                    resourcesSupplier,
                    info,
                    Pack.Position.TOP,
                    false,
                    PackSource.BUILT_IN)
                ));
        }
    }

    /**
     * A built-in resource pack to change the model of Quicksoil Glass Panes when using CTM, as CTM's connected textures won't properly work with the normal Quicksoil Glass Pane model.<br><br>
     * The pack is loaded and automatically applied if CTM is installed.
     */
    private void setupCTMFixPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES && ModList.get().isLoaded("ctm")) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/ctm_fix");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.ctm.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                "builtin/aether_ctm_fix",
                    Component.translatable("pack.aether.ctm.title"),
                    true,
                    new PathPackResources.PathResourcesSupplier(resourcePath, true),
                    new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), true),
                    Pack.Position.TOP,
                    false,
                    PackSource.BUILT_IN)
                )
            );
        }
    }

    /**
     * A built-in resource pack to include Pro Tips messages in Tips' UI.<br><br>
     * The pack is loaded and automatically applied if Tips is installed through {@link AetherClient#autoApplyPacks()}.
     */
    private void setupTipsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES && ModList.get().isLoaded("tipsmod")) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/tips");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.tips.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/aether_tips",
                            Component.translatable("pack.aether.tips.title"),
                            false,
                            new PathPackResources.PathResourcesSupplier(resourcePath, true),
                            new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                            Pack.Position.TOP,
                            false,
                            PackSource.BUILT_IN)
                    )
            );
        }
    }

    /**
     * A built-in resource pack to change textures for color blindness accessibility.
     */
    private void setupColorblindPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/colorblind");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.colorblind.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                    "builtin/aether_colorblind",
                    Component.translatable("pack.aether.colorblind.title"),
                    false,
                    new PathPackResources.PathResourcesSupplier(resourcePath, true),
                    new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                    Pack.Position.TOP,
                    false,
                    PackSource.BUILT_IN)
                )
            );
        }
    }

    /**
     * A built-in resource pack to include ability tooltips for items.<br><br>
     */
    private void setupTooltipsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/tooltips");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.tooltips.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/aether_tooltips",
                            Component.translatable("pack.aether.tooltips.title"),
                            ModList.get().isLoaded("aether_genesis"),
                            new PathPackResources.PathResourcesSupplier(resourcePath, true),
                            new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                            Pack.Position.TOP,
                            false,
                            PackSource.BUILT_IN)
                    )
            );
        }
    }

    /**
     * A built-in data pack to set up the default slots for Curios.<br><br>
     * The pack is loaded and automatically applied if the {@link AetherConfig.Common#use_curios_menu} config isn't enabled.
     */
    private void setupAccessoriesPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && !AetherConfig.COMMON.use_curios_menu.get()) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/accessories");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.accessories.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/aether_accessories",
                            Component.translatable("pack.aether.accessories.title"),
                            true,
                            new PathPackResources.PathResourcesSupplier(resourcePath, true),
                            new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), true),
                            Pack.Position.TOP,
                            false,
                            PackSource.BUILT_IN)
                    )
            );
        }
    }

    /**
     * A built-in data pack to empty the Aether's curio slot tags and use the default curio slot tags instead, as well as register the default Curios slots.<br><br>
     * The pack is loaded and automatically applied if the {@link AetherConfig.Common#use_curios_menu} config is enabled.
     */
    private void setupCuriosOverridePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && AetherConfig.COMMON.use_curios_menu.get()) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/curios_override");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.curios.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                    "builtin/aether_curios_override",
                    Component.translatable("pack.aether.curios.title"),
                    true,
                    new PathPackResources.PathResourcesSupplier(resourcePath, true),
                    new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), true),
                    Pack.Position.TOP,
                    false,
                    PackSource.BUILT_IN)
                )
            );
        }
    }

    /**
     * A built-in data pack to make ice accessories create temporary blocks instead of permanent blocks when freezing liquids.
     */
    private void setupTemporaryFreezingPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/temporary_freezing");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.freezing.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                    "builtin/aether_temporary_freezing",
                    Component.translatable("pack.aether.freezing.title"),
                    false,
                    new PathPackResources.PathResourcesSupplier(resourcePath, true),
                    new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                    Pack.Position.TOP,
                    false,
                    create(decorateWithSource("pack.source.builtin"), AetherConfig.COMMON.add_temporary_freezing_automatically.get()))
                )
            );
        }
    }

    /**
     * A built-in data pack for generating ruined Aether Portals.
     */
    private void setupRuinedPortalPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/ruined_portal");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.ruined_portal.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/aether_ruined_portal",
                            Component.translatable("pack.aether.ruined_portal.title"),
                            false,
                            new PathPackResources.PathResourcesSupplier(resourcePath, true),
                            new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                            Pack.Position.TOP,
                            false,
                            create(decorateWithSource("pack.source.builtin"), AetherConfig.COMMON.add_ruined_portal_automatically.get()))
                    )
            );
        }
    }

    /**
     * [CODE COPY] - {@link PackSource#create(UnaryOperator, boolean)}.
     */
    static PackSource create(final UnaryOperator<Component> decorator, final boolean shouldAddAutomatically) {
        return new PackSource() {
            public Component decorate(Component component) {
                return decorator.apply(component);
            }

            public boolean shouldAddAutomatically() {
                return shouldAddAutomatically;
            }
        };
    }

    /**
     * [CODE COPY] - {@link PackSource#decorateWithSource(String)}.
     */
    private static UnaryOperator<Component> decorateWithSource(String translationKey) {
        Component component = Component.translatable(translationKey);
        return (name) -> Component.translatable("pack.nameAndSource", name, component).withStyle(ChatFormatting.GRAY);
    }

    private void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(AetherItems.GOLDEN_DART.get(), new DispenseDartBehavior(AetherItems.GOLDEN_DART));
        DispenserBlock.registerBehavior(AetherItems.POISON_DART.get(), new DispenseDartBehavior(AetherItems.POISON_DART));
        DispenserBlock.registerBehavior(AetherItems.ENCHANTED_DART.get(), new DispenseDartBehavior(AetherItems.ENCHANTED_DART));
        DispenserBlock.registerBehavior(AetherItems.LIGHTNING_KNIFE.get(), AetherDispenseBehaviors.DISPENSE_LIGHTNING_KNIFE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.HAMMER_OF_KINGBDOGZ.get(), AetherDispenseBehaviors.DISPENSE_KINGBDOGZ_HAMMER_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_DISPENSE_BEHAVIOR);
		DispenserBlock.registerBehavior(AetherItems.SKYROOT_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_PICKUP_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.AMBROSIUM_SHARD.get(), new DispenseUsableItemBehavior<>(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get()));
        DispenserBlock.registerBehavior(AetherItems.SWET_BALL.get(), new DispenseUsableItemBehavior<>(AetherRecipeTypes.SWET_BALL_CONVERSION.get()));
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_BOAT.get(), new DispenseSkyrootBoatBehavior());
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_CHEST_BOAT.get(), new DispenseSkyrootBoatBehavior(true));
    }

    private void registerCauldronInteractions() {
        CauldronInteraction.EMPTY.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.WATER.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.LAVA.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.POWDER_SNOW.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.EMPTY.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.LAVA.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.POWDER_SNOW.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.put(AetherItems.SKYROOT_BUCKET.get(), AetherCauldronInteractions.EMPTY_WATER);
        CauldronInteraction.POWDER_SNOW.put(AetherItems.SKYROOT_BUCKET.get(), AetherCauldronInteractions.EMPTY_POWDER_SNOW);
        CauldronInteraction.WATER.put(AetherItems.LEATHER_GLOVES.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(AetherItems.RED_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteraction.WATER.put(AetherItems.BLUE_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteraction.WATER.put(AetherItems.YELLOW_CAPE.get(), AetherCauldronInteractions.CAPE);
    }

    private void registerComposting() {
        this.addCompost(0.3F, AetherBlocks.SKYROOT_LEAVES.get().asItem());
        this.addCompost(0.3F, AetherBlocks.SKYROOT_SAPLING.get());
        this.addCompost(0.3F, AetherBlocks.GOLDEN_OAK_LEAVES.get());
        this.addCompost(0.3F, AetherBlocks.GOLDEN_OAK_SAPLING.get());
        this.addCompost(0.3F, AetherBlocks.CRYSTAL_LEAVES.get());
        this.addCompost(0.3F, AetherBlocks.CRYSTAL_FRUIT_LEAVES.get());
        this.addCompost(0.3F, AetherBlocks.HOLIDAY_LEAVES.get());
        this.addCompost(0.3F, AetherBlocks.DECORATED_HOLIDAY_LEAVES.get());
        this.addCompost(0.3F, AetherItems.BLUE_BERRY.get());
        this.addCompost(0.5F, AetherItems.ENCHANTED_BERRY.get());
        this.addCompost(0.5F, AetherBlocks.BERRY_BUSH.get());
        this.addCompost(0.5F, AetherBlocks.BERRY_BUSH_STEM.get());
        this.addCompost(0.65F, AetherBlocks.WHITE_FLOWER.get());
        this.addCompost(0.65F, AetherBlocks.PURPLE_FLOWER.get());
        this.addCompost(0.65F, AetherItems.WHITE_APPLE.get());
    }

    /**
     * [CODE COPY] - {@link ComposterBlock#add(float, ItemLike)}.
     * @param chance Chance (as a {@link Float}) to fill a compost layer.
     * @param item The {@link ItemLike} that can be composted.
     */
    private void addCompost(float chance, ItemLike item) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }
}
