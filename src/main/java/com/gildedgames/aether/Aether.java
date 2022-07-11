package com.gildedgames.aether;

import com.gildedgames.aether.advancement.AetherAdvancementTriggers;
import com.gildedgames.aether.blockentity.AetherBlockEntityTypes;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.block.AetherCauldronInteractions;
import com.gildedgames.aether.block.dispenser.AetherDispenseBehaviors;
import com.gildedgames.aether.client.particle.AetherParticleTypes;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.blockentity.IncubatorBlockEntity;
import com.gildedgames.aether.block.dispenser.DispenseDartBehavior;
import com.gildedgames.aether.blockentity.AltarBlockEntity;
import com.gildedgames.aether.blockentity.FreezerBlockEntity;
import com.gildedgames.aether.data.generators.*;
import com.gildedgames.aether.data.generators.tags.*;
import com.gildedgames.aether.data.resources.*;
import com.gildedgames.aether.effect.AetherEffects;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.inventory.menu.AetherMenuTypes;
import com.gildedgames.aether.inventory.AetherRecipeBookTypes;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.loot.conditions.AetherLootConditions;
import com.gildedgames.aether.loot.functions.AetherLootFunctions;
import com.gildedgames.aether.loot.modifiers.AetherLootModifiers;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.world.AetherPOI;
import com.gildedgames.aether.world.foliageplacer.AetherFoliagePlacerTypes;
import com.gildedgames.aether.world.feature.AetherFeatures;
import com.gildedgames.aether.world.placementmodifier.AetherPlacementModifiers;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.client.CombinedResourcePack;
import com.gildedgames.aether.api.SunAltarWhitelist;
import com.gildedgames.aether.api.TriviaGenerator;
import com.gildedgames.aether.world.structure.AetherStructureTypes;
import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import com.gildedgames.aether.world.treedecorator.AetherTreeDecoratorTypes;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.PathResourcePack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.nio.file.Path;
import java.util.List;

@Mod(Aether.MODID)
@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Aether
{
    public static final String MODID = "aether";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Path DIRECTORY = FMLPaths.CONFIGDIR.get().resolve("aether");

    public static TriviaGenerator TRIVIA_READER;

    public Aether() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::curiosSetup);
        modEventBus.addListener(this::dataSetup);
        modEventBus.addListener(this::packSetup);

        DeferredRegister<?>[] registers = {
                AetherBlocks.BLOCKS,
                AetherItems.ITEMS,
                AetherEntityTypes.ENTITIES,
                AetherBlockEntityTypes.BLOCK_ENTITIES,
                AetherMenuTypes.MENU_TYPES,
                AetherEffects.EFFECTS,
                AetherParticleTypes.PARTICLES,
                AetherFeatures.FEATURES,
                AetherFoliagePlacerTypes.FOLIAGE_PLACERS,
                AetherTreeDecoratorTypes.TREE_DECORATORS,
                AetherPOI.POI,
                AetherStructureTypes.STRUCTURE_TYPES,
                AetherStructurePieceTypes.STRUCTURE_PIECE_TYPES,
                AetherRecipeTypes.RECIPE_TYPES,
                AetherRecipeSerializers.RECIPE_SERIALIZERS,
                AetherLootFunctions.LOOT_FUNCTION_TYPES,
                AetherLootConditions.LOOT_CONDITION_TYPES,
                AetherLootModifiers.GLOBAL_LOOT_MODIFIERS,
                AetherSoundEvents.SOUNDS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modEventBus);
        }

        AetherBlocks.registerWoodTypes();

        DIRECTORY.toFile().mkdirs();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AetherConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AetherConfig.CLIENT_SPEC);

        TRIVIA_READER = new TriviaGenerator();
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        AetherItems.registerAbilities();
        AetherPacketHandler.register();

        AetherAdvancementTriggers.init();
        AetherPlacementModifiers.init();
        AetherRecipeBookTypes.init();

        SunAltarWhitelist.initialize();

        registerFuels();

        event.enqueueWork(() -> {
            AetherBlocks.registerPots();
            AetherBlocks.registerFlammability();
            AetherBlocks.registerFreezables();

            AetherEntityTypes.registerSpawnPlacements();

            registerDispenserBehaviors();
            registerCauldronInteractions();
            registerComposting();
        });
    }

    public void curiosSetup(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("aether_pendant").icon(new ResourceLocation(Aether.MODID, "gui/slots/pendant")).hide().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("aether_cape").icon(new ResourceLocation(Aether.MODID, "gui/slots/cape")).hide().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("aether_ring").icon(new ResourceLocation(Aether.MODID, "gui/slots/ring")).size(2).hide().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("aether_shield").icon(new ResourceLocation(Aether.MODID, "gui/slots/shield")).hide().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("aether_gloves").icon(new ResourceLocation(Aether.MODID, "gui/slots/gloves")).hide().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("aether_accessory").icon(new ResourceLocation(Aether.MODID, "gui/slots/misc")).size(2).hide().build());
    }

    public void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new AetherBlockStateData(generator, helper));
        generator.addProvider(event.includeClient(), new AetherItemModelData(generator, helper));
        generator.addProvider(event.includeClient(), new AetherLanguageData(generator));
        generator.addProvider(event.includeClient(), new AetherSoundData(generator, helper));

        generator.addProvider(event.includeServer(), new AetherRecipeData(generator));
        generator.addProvider(event.includeServer(), new AetherLootTableData(generator));
        generator.addProvider(event.includeServer(), new AetherLootModifierData(generator));
        generator.addProvider(event.includeServer(), new AetherAdvancementData(generator, helper));
        AetherBlockTagData blockTags = new AetherBlockTagData(generator, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new AetherItemTagData(generator, blockTags, helper));
        generator.addProvider(event.includeServer(), new AetherEntityTagData(generator, helper));
        generator.addProvider(event.includeServer(), new AetherFluidTagData(generator, helper));
        generator.addProvider(event.includeServer(), new AetherBiomeTagData(generator, helper));
        generator.addProvider(event.includeServer(), new AetherDimensionTagData(generator, helper));
        generator.addProvider(event.includeServer(), new AetherDataGenerators<ConfiguredFeature<?, ?>>().create(generator, helper, AetherConfiguredFeatures.CONFIGURED_FEATURES, Registry.CONFIGURED_FEATURE_REGISTRY));
        generator.addProvider(event.includeServer(), new AetherDataGenerators<PlacedFeature>().create(generator, helper, AetherPlacedFeatures.PLACED_FEATURES, Registry.PLACED_FEATURE_REGISTRY));
        generator.addProvider(event.includeServer(), new AetherDataGenerators<Structure>().create(generator, helper, AetherStructures.STRUCTURES, Registry.STRUCTURE_REGISTRY));
        generator.addProvider(event.includeServer(), new AetherDataGenerators<Biome>().create(generator, helper, AetherBiomes.BIOMES, ForgeRegistries.Keys.BIOMES));
        generator.addProvider(event.includeServer(), new AetherDataGenerators<DimensionType>().create(generator, helper, AetherDimensions.DIMENSION_TYPES, Registry.DIMENSION_TYPE_REGISTRY));
        generator.addProvider(event.includeServer(), new AetherDataGenerators<NoiseGeneratorSettings>().create(BuiltinRegistries.ACCESS, generator, helper, AetherNoiseGeneratorSettings.NOISE_GENERATOR_SETTINGS, Registry.NOISE_GENERATOR_SETTINGS_REGISTRY));
        generator.addProvider(event.includeServer(), new AetherDataGenerators<LevelStem>().levelStem(generator, helper));
    }

    public void packSetup(AddPackFindersEvent event) {
        setupReleasePack(event);
        setupBetaPack(event);
        setupCTMFixPack(event);
    }

    private void setupReleasePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/classic_125");
            PathResourcePack pack = new PathResourcePack(ModList.get().getModFileById(Aether.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath);
            createCombinedPack(event, resourcePath, pack, "builtin/aether_125_art", "Aether 1.2.5 Textures", "The classic look of the Aether from 1.2.5");
        }
    }

    private void setupBetaPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/classic_b173");
            PathResourcePack pack = new PathResourcePack(ModList.get().getModFileById(Aether.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath);
            createCombinedPack(event, resourcePath, pack, "builtin/aether_b173_art", "Aether b1.7.3 Textures", "The original look of the Aether from b1.7.3");
        }
    }

    private void createCombinedPack(AddPackFindersEvent event, Path sourcePath, PathResourcePack pack, String name, String title, String description) {
        Path baseResourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/classic_base");
        PathResourcePack basePack = new PathResourcePack(ModList.get().getModFileById(Aether.MODID).getFile().getFileName() + ":" + baseResourcePath, baseResourcePath);
        List<PathResourcePack> mergedPacks = List.of(pack, basePack);
        event.addRepositorySource((packConsumer, packConstructor) ->
                packConsumer.accept(Pack.create(
                        name, false,
                        () -> new CombinedResourcePack(name, title, new PackMetadataSection(Component.literal(description), PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion())), mergedPacks, sourcePath),
                        packConstructor, Pack.Position.TOP, PackSource.BUILT_IN)
                ));
    }

    private void setupCTMFixPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES && ModList.get().isLoaded("ctm")) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/ctm_fix");
            PathResourcePack pack = new PathResourcePack(ModList.get().getModFileById(Aether.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath);
            event.addRepositorySource((packConsumer, packConstructor) ->
                packConsumer.accept(packConstructor.create(
                        "builtin/aether_ctm_fix", Component.literal("Aether CTM Fix"), true, () -> pack,
                        new PackMetadataSection(Component.literal("Fixes Quicksoil Glass Panes when using CTM"), PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion())),
                        Pack.Position.TOP, PackSource.BUILT_IN, false)
                ));
        }
    }

    private void registerDispenserBehaviors() {
        AetherDispenseBehaviors.DEFAULT_FIRE_CHARGE_BEHAVIOR = DispenserBlock.DISPENSER_REGISTRY.get(Items.FIRE_CHARGE);
        AetherDispenseBehaviors.DEFAULT_FLINT_AND_STEEL_BEHAVIOR = DispenserBlock.DISPENSER_REGISTRY.get(Items.FLINT_AND_STEEL);
        DispenserBlock.registerBehavior(AetherItems.GOLDEN_DART.get(), new DispenseDartBehavior(AetherItems.GOLDEN_DART));
        DispenserBlock.registerBehavior(AetherItems.POISON_DART.get(), new DispenseDartBehavior(AetherItems.POISON_DART));
        DispenserBlock.registerBehavior(AetherItems.ENCHANTED_DART.get(), new DispenseDartBehavior(AetherItems.ENCHANTED_DART));
        DispenserBlock.registerBehavior(AetherItems.LIGHTNING_KNIFE.get(), AetherDispenseBehaviors.DISPENSE_LIGHTNING_KNIFE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.HAMMER_OF_NOTCH.get(), AetherDispenseBehaviors.DISPENSE_NOTCH_HAMMER_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_DISPENSE_BEHAVIOR);
		DispenserBlock.registerBehavior(AetherItems.SKYROOT_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_PICKUP_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.AMBROSIUM_SHARD.get(), AetherDispenseBehaviors.DISPENSE_AMBROSIUM_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SWET_BALL.get(), AetherDispenseBehaviors.DISPENSE_SWET_BALL_BEHAVIOR);
        DispenserBlock.registerBehavior(Items.FIRE_CHARGE, AetherDispenseBehaviors.DISPENSE_FIRE_CHARGE_BEHAVIOR);
        DispenserBlock.registerBehavior(Items.FLINT_AND_STEEL, AetherDispenseBehaviors.DISPENSE_FLINT_AND_STEEL);
    }

    private void registerCauldronInteractions() {
        CauldronInteraction.EMPTY.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.WATER.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.LAVA.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.POWDER_SNOW.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteraction.WATER.put(AetherItems.SKYROOT_BUCKET.get(), AetherCauldronInteractions.EMPTY_WATER);
        CauldronInteraction.WATER.put(AetherItems.LEATHER_GLOVES.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(AetherItems.RED_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteraction.WATER.put(AetherItems.BLUE_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteraction.WATER.put(AetherItems.YELLOW_CAPE.get(), AetherCauldronInteractions.CAPE);
    }

    private void registerComposting() {
        ComposterBlock.add(0.3F, AetherBlocks.SKYROOT_LEAVES.get());
        ComposterBlock.add(0.3F, AetherBlocks.SKYROOT_SAPLING.get());
        ComposterBlock.add(0.3F, AetherBlocks.GOLDEN_OAK_LEAVES.get());
        ComposterBlock.add(0.3F, AetherBlocks.GOLDEN_OAK_SAPLING.get());
        ComposterBlock.add(0.3F, AetherBlocks.CRYSTAL_LEAVES.get());
        ComposterBlock.add(0.3F, AetherBlocks.CRYSTAL_FRUIT_LEAVES.get());
        ComposterBlock.add(0.3F, AetherBlocks.HOLIDAY_LEAVES.get());
        ComposterBlock.add(0.3F, AetherBlocks.DECORATED_HOLIDAY_LEAVES.get());
        ComposterBlock.add(0.3F, AetherItems.BLUE_BERRY.get());
        ComposterBlock.add(0.5F, AetherItems.ENCHANTED_BERRY.get());
        ComposterBlock.add(0.5F, AetherBlocks.BERRY_BUSH.get());
        ComposterBlock.add(0.5F, AetherBlocks.BERRY_BUSH_STEM.get());
        ComposterBlock.add(0.65F, AetherBlocks.WHITE_FLOWER.get());
        ComposterBlock.add(0.65F, AetherBlocks.PURPLE_FLOWER.get());
        ComposterBlock.add(0.65F, AetherItems.WHITE_APPLE.get());
    }

    private void registerFuels() {
        AltarBlockEntity.addItemEnchantingTime(AetherItems.AMBROSIUM_SHARD.get(), 500);
        FreezerBlockEntity.addItemFreezingTime(AetherBlocks.ICESTONE.get(), 500);
        IncubatorBlockEntity.addItemIncubatingTime(AetherBlocks.AMBROSIUM_TORCH.get(), 1000);
    }
}
