package com.gildedgames.aether;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.block.entity.IncubatorBlockEntity;
import com.gildedgames.aether.common.block.util.dispenser.DispenseDartBehavior;
import com.gildedgames.aether.common.block.entity.AltarBlockEntity;
import com.gildedgames.aether.common.block.entity.FreezerBlockEntity;
import com.gildedgames.aether.common.registry.*;
import com.gildedgames.aether.common.world.gen.placement.PlacementModifiers;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.data.*;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.resource.CombinedResourcePack;
import net.minecraft.SharedConstants;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.resource.PathResourcePack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.nio.file.Path;
import java.util.List;

@Mod(Aether.MODID)
public class Aether
{
    public static final String MODID = "aether";
    public static final Logger LOGGER = LogManager.getLogger();

    public Aether() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::curiosSetup);
        modEventBus.addListener(this::dataSetup);
        modEventBus.addListener(this::packSetup);

        DeferredRegister<?>[] registers = {
                AetherBlocks.BLOCKS,
                AetherEntityTypes.ENTITIES,
                AetherEffects.EFFECTS,
                AetherItems.ITEMS,
                AetherFeatures.FEATURES,
                AetherParticleTypes.PARTICLES,
                AetherPOI.POI,
                AetherSoundEvents.SOUNDS,
                AetherContainerTypes.CONTAINERS,
                AetherBlockEntityTypes.BLOCK_ENTITIES,
                AetherRecipes.RECIPE_SERIALIZERS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modEventBus);
        }

        AetherLoot.init();
        AetherAdvancements.init();
        PlacementModifiers.init();

        AetherBlocks.registerWoodTypes();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AetherConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AetherConfig.CLIENT_SPEC);

        AetherStructureIngress.registerEvents(modEventBus);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        AetherPacketHandler.register();

        AetherBlocks.registerPots();
        AetherBlocks.registerAxeStrippingBlocks();
        AetherBlocks.registerHoeTillingBlocks();
        AetherBlocks.registerFlammability();
        AetherBlocks.registerFreezables();

        AetherFeatures.registerConfiguredFeatures();

        AetherEntityTypes.registerSpawnPlacements();

        AetherItems.registerAbilities();

        registerDispenserBehaviors();
        registerCauldronInteractions();
        registerComposting();
        registerFuels();
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
        if (event.includeClient()) {
            generator.addProvider(new AetherBlockStateData(generator, helper));
            generator.addProvider(new AetherItemModelData(generator, helper));
            generator.addProvider(new AetherLangData(generator));
            generator.addProvider(new AetherSoundData(generator, helper));
        }
        if (event.includeServer()) {
            generator.addProvider(new AetherRecipeData(generator));
            generator.addProvider(new AetherLootTableData(generator));
            AetherBlockTagData blockTags = new AetherBlockTagData(generator, helper);
            generator.addProvider(blockTags);
            generator.addProvider(new AetherItemTagData(generator, blockTags, helper));
            generator.addProvider(new AetherEntityTagData(generator, helper));
            generator.addProvider(new AetherFluidTagData(generator, helper));
            generator.addProvider(new AetherAdvancementData(generator, helper));
            generator.addProvider(new AetherWorldData(generator));
        }
    }

    public void packSetup(AddPackFindersEvent event) {
        setupReleasePack(event);
        setupBetaPack(event);
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
                        () -> new CombinedResourcePack(name, title, new PackMetadataSection(new TextComponent(description), PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion())), mergedPacks, sourcePath),
                        packConstructor, Pack.Position.TOP, PackSource.BUILT_IN)
                ));
    }

    private void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(AetherItems.GOLDEN_DART.get(), new DispenseDartBehavior(AetherItems.GOLDEN_DART));
        DispenserBlock.registerBehavior(AetherItems.POISON_DART.get(), new DispenseDartBehavior(AetherItems.POISON_DART));
        DispenserBlock.registerBehavior(AetherItems.ENCHANTED_DART.get(), new DispenseDartBehavior(AetherItems.ENCHANTED_DART));
        DispenserBlock.registerBehavior(AetherItems.LIGHTNING_KNIFE.get(), AetherDispenseBehaviors.DISPENSE_LIGHTNING_KNIFE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.HAMMER_OF_NOTCH.get(), AetherDispenseBehaviors.DISPENSE_NOTCH_HAMMER_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherDispenseBehaviors.DISPENSE_WATER_BEHAVIOR);
		DispenserBlock.registerBehavior(AetherItems.SKYROOT_BUCKET.get(), AetherDispenseBehaviors.PICKUP_WATER_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.AMBROSIUM_SHARD.get(), AetherDispenseBehaviors.DISPENSE_AMBROSIUM_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SWET_BALL.get(), AetherDispenseBehaviors.DISPENSE_SWET_BALL_BEHAVIOR);
        DispenseItemBehavior dispenseSpawnEgg = AetherDispenseBehaviors.DISPENSE_SPAWN_EGG_BEHAVIOR;
        for (RegistryObject<Item> item : AetherItems.ITEMS.getEntries()) {
            if (item.get() instanceof SpawnEggItem) {
                DispenserBlock.registerBehavior(item.get(), dispenseSpawnEgg);
            }
        }
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
