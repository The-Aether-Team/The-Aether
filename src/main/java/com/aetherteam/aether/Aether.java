package com.aetherteam.aether;

import com.aetherteam.aether.advancement.AetherAdvancementTriggers;
import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.AetherCauldronInteractions;
import com.aetherteam.aether.block.AetherWoodTypes;
import com.aetherteam.aether.block.dispenser.AetherDispenseBehaviors;
import com.aetherteam.aether.block.dispenser.DispenseUsableItemBehavior;
import com.aetherteam.aether.block.dispenser.SkyrootBoatDispenseBehavior;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.CombinedPackResources;
import com.aetherteam.aether.client.TriviaGenerator;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.command.AetherCommands;
import com.aetherteam.aether.command.SunAltarWhitelist;
import com.aetherteam.aether.data.ReloadListeners;
import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.ai.attribute.AetherAttributes;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.event.listeners.DimensionListener;
import com.aetherteam.aether.event.listeners.EntityListener;
import com.aetherteam.aether.event.listeners.PerkListener;
import com.aetherteam.aether.event.listeners.RecipeListener;
import com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener;
import com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener;
import com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener;
import com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener;
import com.aetherteam.aether.event.listeners.capability.AetherPlayerListener;
import com.aetherteam.aether.event.listeners.capability.AetherTimeListener;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.AetherArmorMaterials;
import com.aetherteam.aether.item.combat.ZaniteSwordItem;
import com.aetherteam.aether.item.combat.loot.FlamingSwordItem;
import com.aetherteam.aether.item.combat.loot.HolySwordItem;
import com.aetherteam.aether.item.combat.loot.PigSlayerItem;
import com.aetherteam.aether.item.components.AetherDataComponents;
import com.aetherteam.aether.loot.conditions.AetherLootConditions;
import com.aetherteam.aether.loot.functions.AetherLootFunctions;
import com.aetherteam.aether.loot.modifiers.AetherLootModifiers;
import com.aetherteam.aether.network.packet.AetherPlayerSyncPacket;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.aether.network.packet.PhoenixArrowSyncPacket;
import com.aetherteam.aether.network.packet.clientbound.*;
import com.aetherteam.aether.network.packet.serverbound.*;
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
import com.aetherteam.nitrogen.fabric.NetworkRegisterHelper;
import com.aetherteam.nitrogen.fabric.WrappedInventoryStorage;
import com.aetherteam.nitrogen.fabric.events.AddPackFindersEvent;
import com.aetherteam.nitrogen.fabric.events.ItemAttributeModifierHelper;
import com.aetherteam.nitrogen.fabric.events.LivingEntityEvents;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import com.aetherteam.nitrogen.fabric.registries.datamaps.RegisterDataMapTypesEvent;
import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import io.wispforest.accessories.api.slot.UniqueSlotHandling;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.Container;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class Aether implements ModInitializer {
    public static final String MODID = "aether";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Path DIRECTORY = FabricLoader.getInstance().getConfigDir().resolve(Aether.MODID);

    public static final TriviaGenerator TRIVIA_READER = new TriviaGenerator();

    @Override
    public void onInitialize() {
        DIRECTORY.toFile().mkdirs(); // Ensures the Aether's config folder is generated.
        NeoForgeConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.STARTUP, AetherConfig.STARTUP_SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.SERVER, AetherConfig.SERVER_SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.COMMON, AetherConfig.COMMON_SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.CLIENT, AetherConfig.CLIENT_SPEC);

        DeferredRegister<?>[] registers = {
                AetherEntityTypes.ENTITY_TYPES,
                AetherDataComponents.DATA_COMPONENT_TYPES,
                AetherBlocks.BLOCKS,
                AetherItems.ITEMS,
                AetherAttributes.ATTRIBUTES,
                AetherBlockEntityTypes.BLOCK_ENTITY_TYPES,
                AetherMenuTypes.MENU_TYPES,
                AetherEffects.EFFECTS,
                AetherArmorMaterials.ARMOR_MATERIALS,
                AetherParticleTypes.PARTICLES,
                AetherFeatures.FEATURES,
                AetherFoliagePlacerTypes.FOLIAGE_PLACERS,
                AetherPlacementModifiers.PLACEMENT_MODIFIERS,
                AetherTrunkPlacerTypes.TRUNK_PLACERS,
                AetherTreeDecoratorTypes.TREE_DECORATORS,
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
                AetherAdvancementTriggers.TRIGGERS
        };

        for (DeferredRegister<?> register : registers) {
            register.addEntriesToRegistry();
        }

        AetherDataAttachments.init();

        AetherPoi.init();

        AetherWoodTypes.init();

        this.registerCapabilities();
        this.registerPackets();
        RegisterDataMapTypesEvent.EVENT.register(this::registerDataMaps);
        AddPackFindersEvent.EVENT.register(this::packSetup);
        DynamicRegistries.registerSynced(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY, MoaType.CODEC);

        this.eventSetup();

        AetherBlocks.registerWoodTypes(); // Registered this early to avoid bugs with WoodTypes and signs.

        Reflection.initialize(SunAltarWhitelist.class);
        Reflection.initialize(AetherRecipeBookTypes.class);
        Reflection.initialize(AetherMobCategory.class);
        Reflection.initialize(AetherAdvancementTriggers.class);

        AetherBlocks.registerPots();
        AetherBlocks.registerFlammability();
        AetherBlocks.registerFluidInteractions();

        AetherItems.registerAccessories();
        AetherItems.setupBucketReplacements();

        this.registerDispenserBehaviors();
        this.registerCauldronInteractions();

        UniqueSlotHandling.EVENT.register(AetherAccessorySlots.INSTANCE);
    }

    public void registerPackets() {
        NetworkRegisterHelper registrar = NetworkRegisterHelper.INSTANCE;

        // CLIENTBOUND
        registrar.playToClient(AetherTravelPacket.TYPE, AetherTravelPacket.STREAM_CODEC, AetherTravelPacket::execute);
        registrar.playToClient(BossInfoPacket.Display.TYPE, BossInfoPacket.Display.STREAM_CODEC, BossInfoPacket.Display::execute);
        registrar.playToClient(BossInfoPacket.Remove.TYPE, BossInfoPacket.Remove.STREAM_CODEC, BossInfoPacket.Remove::execute);
        registrar.playToClient(ClientDeveloperGlowPacket.Apply.TYPE, ClientDeveloperGlowPacket.Apply.STREAM_CODEC, ClientDeveloperGlowPacket.Apply::execute);
        registrar.playToClient(ClientDeveloperGlowPacket.Remove.TYPE, ClientDeveloperGlowPacket.Remove.STREAM_CODEC, ClientDeveloperGlowPacket.Remove::execute);
        registrar.playToClient(ClientDeveloperGlowPacket.Sync.TYPE, ClientDeveloperGlowPacket.Sync.STREAM_CODEC, ClientDeveloperGlowPacket.Sync::execute);
        registrar.playToClient(ClientGrabItemPacket.TYPE, ClientGrabItemPacket.STREAM_CODEC, ClientGrabItemPacket::execute);
        registrar.playToClient(ClientHaloPacket.Apply.TYPE, ClientHaloPacket.Apply.STREAM_CODEC, ClientHaloPacket.Apply::execute);
        registrar.playToClient(ClientHaloPacket.Remove.TYPE, ClientHaloPacket.Remove.STREAM_CODEC, ClientHaloPacket.Remove::execute);
        registrar.playToClient(ClientHaloPacket.Sync.TYPE, ClientHaloPacket.Sync.STREAM_CODEC, ClientHaloPacket.Sync::execute);
        registrar.playToClient(ClientMoaSkinPacket.Apply.TYPE, ClientMoaSkinPacket.Apply.STREAM_CODEC, ClientMoaSkinPacket.Apply::execute);
        registrar.playToClient(ClientMoaSkinPacket.Remove.TYPE, ClientMoaSkinPacket.Remove.STREAM_CODEC, ClientMoaSkinPacket.Remove::execute);
        registrar.playToClient(ClientMoaSkinPacket.Sync.TYPE, ClientMoaSkinPacket.Sync.STREAM_CODEC, ClientMoaSkinPacket.Sync::execute);
        registrar.playToClient(CloudMinionPacket.TYPE, CloudMinionPacket.STREAM_CODEC, CloudMinionPacket::execute);
        registrar.playToClient(HealthResetPacket.TYPE, HealthResetPacket.STREAM_CODEC, HealthResetPacket::execute);
        registrar.playToClient(LeavingAetherPacket.TYPE, LeavingAetherPacket.STREAM_CODEC, LeavingAetherPacket::execute);
        registrar.playToClient(MoaInteractPacket.TYPE, MoaInteractPacket.STREAM_CODEC, MoaInteractPacket::execute);
        registrar.playToClient(OpenSunAltarPacket.TYPE, OpenSunAltarPacket.STREAM_CODEC, OpenSunAltarPacket::execute);
        registrar.playToClient(PortalInteractPacket.TYPE, PortalInteractPacket.STREAM_CODEC, PortalInteractPacket::execute);
        registrar.playToClient(PortalTravelSoundPacket.TYPE, PortalTravelSoundPacket.STREAM_CODEC, PortalTravelSoundPacket::execute);
        registrar.playToClient(QueenDialoguePacket.TYPE, QueenDialoguePacket.STREAM_CODEC, QueenDialoguePacket::execute);
        registrar.playToClient(RegisterMoaSkinsPacket.TYPE, RegisterMoaSkinsPacket.STREAM_CODEC, RegisterMoaSkinsPacket::execute);
        registrar.playToClient(RemountAerbunnyPacket.TYPE, RemountAerbunnyPacket.STREAM_CODEC, RemountAerbunnyPacket::execute);
        registrar.playToClient(SetInvisibilityPacket.TYPE, SetInvisibilityPacket.STREAM_CODEC, SetInvisibilityPacket::execute);
        registrar.playToClient(ToolDebuffPacket.TYPE, ToolDebuffPacket.STREAM_CODEC, ToolDebuffPacket::execute);
        registrar.playToClient(ZephyrSnowballHitPacket.TYPE, ZephyrSnowballHitPacket.STREAM_CODEC, ZephyrSnowballHitPacket::execute);

        // SERVERBOUND
        registrar.playToServer(AerbunnyPuffPacket.TYPE, AerbunnyPuffPacket.STREAM_CODEC, AerbunnyPuffPacket::execute);
        registrar.playToServer(ClearItemPacket.TYPE, ClearItemPacket.STREAM_CODEC, ClearItemPacket::execute);
        registrar.playToServer(HammerProjectileLaunchPacket.TYPE, HammerProjectileLaunchPacket.STREAM_CODEC, HammerProjectileLaunchPacket::execute);
        registrar.playToServer(LoreExistsPacket.TYPE, LoreExistsPacket.STREAM_CODEC, LoreExistsPacket::execute);
        registrar.playToServer(NpcPlayerInteractPacket.TYPE, NpcPlayerInteractPacket.STREAM_CODEC, NpcPlayerInteractPacket::execute);
        registrar.playToServer(OpenAccessoriesPacket.TYPE, OpenAccessoriesPacket.STREAM_CODEC, OpenAccessoriesPacket::execute);
        registrar.playToServer(OpenInventoryPacket.TYPE, OpenInventoryPacket.STREAM_CODEC, OpenInventoryPacket::execute);
        registrar.playToServer(ServerDeveloperGlowPacket.Apply.TYPE, ServerDeveloperGlowPacket.Apply.STREAM_CODEC, ServerDeveloperGlowPacket.Apply::execute);
        registrar.playToServer(ServerDeveloperGlowPacket.Remove.TYPE, ServerDeveloperGlowPacket.Remove.STREAM_CODEC, ServerDeveloperGlowPacket.Remove::execute);
        registrar.playToServer(ServerHaloPacket.Apply.TYPE, ServerHaloPacket.Apply.STREAM_CODEC, ServerHaloPacket.Apply::execute);
        registrar.playToServer(ServerHaloPacket.Remove.TYPE, ServerHaloPacket.Remove.STREAM_CODEC, ServerHaloPacket.Remove::execute);
        registrar.playToServer(ServerMoaSkinPacket.Apply.TYPE, ServerMoaSkinPacket.Apply.STREAM_CODEC, ServerMoaSkinPacket.Apply::execute);
        registrar.playToServer(ServerMoaSkinPacket.Remove.TYPE, ServerMoaSkinPacket.Remove.STREAM_CODEC, ServerMoaSkinPacket.Remove::execute);
        registrar.playToServer(StepHeightPacket.TYPE, StepHeightPacket.STREAM_CODEC, StepHeightPacket::execute);
        registrar.playToServer(SunAltarUpdatePacket.TYPE, SunAltarUpdatePacket.STREAM_CODEC, SunAltarUpdatePacket::execute);

        // BOTH
        registrar.playBidirectional(AetherPlayerSyncPacket.TYPE, AetherPlayerSyncPacket.STREAM_CODEC, AetherPlayerSyncPacket::execute);
        registrar.playBidirectional(AetherTimeSyncPacket.TYPE, AetherTimeSyncPacket.STREAM_CODEC, AetherTimeSyncPacket::execute);
        registrar.playBidirectional(PhoenixArrowSyncPacket.TYPE, PhoenixArrowSyncPacket.STREAM_CODEC, PhoenixArrowSyncPacket::execute);
    }

    public void registerCapabilities() {
        ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, side) -> {
            TreasureChestBlockEntity entity = (TreasureChestBlockEntity) blockEntity;
            if (!(state.getBlock() instanceof ChestBlock)) {
                return new WrappedInventoryStorage(InventoryStorage.of(entity, null)){
                    @Override
                    public boolean supportsExtraction() {
                        return entity.getLocked();
                    }
                };
            }
            Container inv = ChestBlock.getContainer((ChestBlock) state.getBlock(), state, level, pos, true);
            return InventoryStorage.of(inv == null ? entity : inv, null);
        }, AetherBlocks.TREASURE_CHEST.get());

        ItemStorage.SIDED.registerForBlockEntity(InventoryStorage::of, AetherBlockEntityTypes.INCUBATOR.get());
    }

    public void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(AetherDataMaps.ALTAR_FUEL);
        event.register(AetherDataMaps.FREEZER_FUEL);
        event.register(AetherDataMaps.INCUBATOR_FUEL);
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
        this.setupAccessoriesOverridePack(event);
        this.setupTemporaryFreezingPack(event);
        this.setupRuinedPortalPack(event);
    }

    /**
     * A built-in resource pack for programmer art based on the 1.2.5 version of the mod.
     */
    private void setupReleasePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/classic_125").orElseThrow();
            this.createCombinedPack(event, resourcePath, "builtin/aether_125_art", "pack.aether.125.title", "pack.aether.125.description");
        }
    }

    /**
     * A built-in resource pack for programmer art based on the b1.7.3 version of the mod.
     */
    private void setupBetaPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/classic_b173").orElseThrow();
            this.createCombinedPack(event, resourcePath, "builtin/aether_b173_art", "pack.aether.b173.title", "pack.aether.b173.description");
        }
    }

    /**
     * Creates a built-in resource pack that combines asset files from two different locations.
     *
     * @param sourcePath  The {@link Path} of the non-base assets.
     * @param name        The {@link String} internal name of the resource pack.
     * @param title       The {@link String} title of the resource pack.
     * @param description The {@link String} description of the resource pack.
     */
    private void createCombinedPack(AddPackFindersEvent event, Path sourcePath, String name, String title, String description) {
        PackLocationInfo locationInfo = new PackLocationInfo(name, Component.translatable(title), PackSource.BUILT_IN, Optional.empty());
        Path baseResourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/classic_base").orElseThrow();
        PathPackResources basePack = new PathPackResources(locationInfo, baseResourcePath);
        PathPackResources pack = new PathPackResources(locationInfo, sourcePath);
        List<PathPackResources> mergedPacks = List.of(pack, basePack);
        Pack.ResourcesSupplier resourcesSupplier = new CombinedPackResources.CombinedResourcesSupplier(new PackMetadataSection(Component.translatable(description), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty()), mergedPacks, sourcePath);
        Pack.Metadata metadata = Pack.readPackMetadata(locationInfo, resourcesSupplier, SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
        if (metadata != null) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            locationInfo,
                            resourcesSupplier,
                            metadata,
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in resource pack to change the model of Quicksoil Glass Panes when using CTM, as CTM's connected textures won't properly work with the normal Quicksoil Glass Pane model.<br><br>
     * The pack is loaded and automatically applied if CTM is installed.
     */
    private void setupCTMFixPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES && FabricLoader.getInstance().isModLoaded("ctm")) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/ctm_fix").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.ctm.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_ctm_fix", Component.translatable("pack.aether.ctm.title"), PackSource.BUILT_IN, Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, true*/),
                            new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in resource pack to include Pro Tips messages in Tips' UI.<br><br>
     * The pack is loaded and automatically applied if Tips is installed through {@link AetherClient#autoApplyPacks()}.
     */
    private void setupTipsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES && FabricLoader.getInstance().isModLoaded("tipsmod") && AetherConfig.STARTUP.enable_trivia.get()) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/tips").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.tips.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_tips", Component.translatable("pack.aether.tips.title"), PackSource.BUILT_IN, Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, false*/),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in resource pack to change textures for color blindness accessibility.
     */
    private void setupColorblindPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/colorblind").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.colorblind.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_colorblind", Component.translatable("pack.aether.colorblind.title"), PackSource.BUILT_IN, Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, false*/),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in resource pack to include ability tooltips for items.<br><br>
     */
    private void setupTooltipsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/tooltips").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.tooltips.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_tooltips", Component.translatable("pack.aether.tooltips.title"), PackSource.BUILT_IN, Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, false*/),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack to set up the compatibility for Immersive Portals.<br><br>
     * The pack is loaded and automatically applied if Immersive Portals is installed and if the {@link AetherConfig.Common#enable_immersive_portals_compatibility} config is enabled.
     */
    private void setupImmersivePortalsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && FabricLoader.getInstance().isModLoaded("immersive_portals_core") && AetherConfig.COMMON.enable_immersive_portals_compatibility.get()) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/imm_ptl_compat").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.imm_ptl_compat.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), Optional.empty());
            event.addRepositorySource((source) ->
                source.accept(new Pack(
                        new PackLocationInfo("builtin/aether_imm_ptl_compat", Component.translatable("pack.aether.imm_ptl_compat.title"), PackSource.BUILT_IN, Optional.empty()),
                        new PathPackResources.PathResourcesSupplier(resourcePath),
                        new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, true*/),
                        new PackSelectionConfig(true, Pack.Position.TOP, false)
                    )
                )
            );
        }
    }

    /**
     * A built-in data pack to set up the default slots for accessories.<br><br>
     * The pack is loaded and automatically applied if the {@link AetherConfig.Common#use_default_accessories_menu} config isn't enabled.
     */
    private void setupAccessoriesPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && !AetherConfig.COMMON.use_default_accessories_menu.get()) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/accessories").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.aether_accessories.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_accessories", Component.translatable("pack.aether.aether_accessories.title"), PackSource.BUILT_IN, Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, true*/),
                            new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack to empty the Aether's accessory slot tags and use the default accessory slot tags instead, as well as register the default accessories slots.<br><br>
     * The pack is loaded and automatically applied if the {@link AetherConfig.Common#use_default_accessories_menu} config is enabled.
     */
    private void setupAccessoriesOverridePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && AetherConfig.COMMON.use_default_accessories_menu.get()) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/accessories_override").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.default_accessories.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_accessories_override", Component.translatable("pack.aether.default_accessories.title"), PackSource.BUILT_IN, Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, true*/),
                            new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack to make ice accessories create temporary blocks instead of permanent blocks when freezing liquids.
     */
    private void setupTemporaryFreezingPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/temporary_freezing").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.freezing.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_temporary_freezing", Component.translatable("pack.aether.freezing.title"), create(decorateWithSource("pack.source.builtin"), AetherConfig.COMMON.add_temporary_freezing_automatically.get()), Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, false*/),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack for generating ruined Aether Portals.
     */
    private void setupRuinedPortalPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow().findPath("packs/ruined_portal").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether.ruined_portal.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), Optional.empty());
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_ruined_portal", Component.translatable("pack.aether.ruined_portal.title"), create(decorateWithSource("pack.source.builtin"), AetherConfig.COMMON.add_ruined_portal_automatically.get()), Optional.empty()),
                            new PathPackResources.PathResourcesSupplier(resourcePath),
                            new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()/*, false*/),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    public void eventSetup() {
        AccessoryAbilityListener.listen();
        ArmorAbilityListener.listen();
        ToolAbilityListener.listen();
        WeaponAbilityListener.listen();
        AetherPlayerListener.listen();
        AetherTimeListener.listen();
        DimensionListener.listen();
        EntityListener.listen();
        //ItemListener.listen();
        PerkListener.listen();
        RecipeListener.listen();

        CommandRegistrationCallback.EVENT.register(AetherCommands::registerCommands);
        ReloadListeners.reloadListenerSetup(ResourceManagerHelper.get(PackType.SERVER_DATA));
        ItemAttributeModifierHelper.ON_MODIFICATION.register(ZaniteSwordItem::onModifyAttributes);
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> FlamingSwordItem.onLivingDamage(entity, source));
        LivingEntityEvents.ON_DAMAGE.register((entity, source, originalDamage, newDamage) -> {
            HolySwordItem.onLivingDamage(entity, source, newDamage);
            PigSlayerItem.onLivingDamage(entity, source, newDamage);
        });

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(AetherCreativeTabs::buildCreativeModeTabs);
        AetherEntityTypes.registerSpawnPlacements();
        AetherEntityTypes.registerEntityAttributes();

        AbilityHooks.ToolHooks.STRIPPABLES.forEach(StrippableBlockRegistry::register);
        AbilityHooks.ToolHooks.FLATTENABLES.forEach((block, flattenedBlock) -> FlattenableBlockRegistry.register(block, flattenedBlock.defaultBlockState()));
        AbilityHooks.ToolHooks.TILLABLES.forEach((block, tilledBlock) -> TillableBlockRegistry.register(block, ctx -> ctx.getLevel().getBlockState(ctx.getClickedPos().above()).isAir(), tilledBlock.defaultBlockState()));
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
        DispenserBlock.registerBehavior(AetherItems.GOLDEN_DART.get(), new ProjectileDispenseBehavior(AetherItems.GOLDEN_DART.get()));
        DispenserBlock.registerBehavior(AetherItems.POISON_DART.get(), new ProjectileDispenseBehavior(AetherItems.POISON_DART.get()));
        DispenserBlock.registerBehavior(AetherItems.ENCHANTED_DART.get(), new ProjectileDispenseBehavior(AetherItems.ENCHANTED_DART.get()));
        DispenserBlock.registerBehavior(AetherItems.LIGHTNING_KNIFE.get(), new ProjectileDispenseBehavior(AetherItems.LIGHTNING_KNIFE.get()));
        DispenserBlock.registerBehavior(AetherItems.HAMMER_OF_KINGBDOGZ.get(), AetherDispenseBehaviors.DISPENSE_KINGBDOGZ_HAMMER_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_DISPENSE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_PICKUP_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.AMBROSIUM_SHARD.get(), new DispenseUsableItemBehavior<>(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get()));
        DispenserBlock.registerBehavior(AetherItems.SWET_BALL.get(), new DispenseUsableItemBehavior<>(AetherRecipeTypes.SWET_BALL_CONVERSION.get()));
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_BOAT.get(), new SkyrootBoatDispenseBehavior());
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_CHEST_BOAT.get(), new SkyrootBoatDispenseBehavior(true));
    }

    private void registerCauldronInteractions() {
        CauldronInteraction.EMPTY.map().put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.WATER.map().put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.LAVA.map().put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.POWDER_SNOW.map().put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.EMPTY.map().put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.map().put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.LAVA.map().put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.POWDER_SNOW.map().put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.map().put(AetherItems.SKYROOT_BUCKET.get(), AetherCauldronInteractions.EMPTY_WATER);
        CauldronInteraction.POWDER_SNOW.map().put(AetherItems.SKYROOT_BUCKET.get(), AetherCauldronInteractions.EMPTY_POWDER_SNOW);
        CauldronInteraction.WATER.map().put(AetherItems.LEATHER_GLOVES.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.map().put(AetherItems.RED_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteraction.WATER.map().put(AetherItems.BLUE_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteraction.WATER.map().put(AetherItems.YELLOW_CAPE.get(), AetherCauldronInteractions.CAPE);
    }
}
