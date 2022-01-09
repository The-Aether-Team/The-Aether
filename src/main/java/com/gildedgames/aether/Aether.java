package com.gildedgames.aether;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.block.util.dispenser.DispenseDartBehavior;
import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectileEntity;
import com.gildedgames.aether.common.entity.projectile.weapon.LightningKnifeEntity;
import com.gildedgames.aether.common.entity.tile.AltarTileEntity;
import com.gildedgames.aether.common.entity.tile.FreezerTileEntity;
import com.gildedgames.aether.common.item.materials.util.ISwetBallConversion;
import com.gildedgames.aether.common.item.miscellaneous.bucket.SkyrootWaterBucketItem;
import com.gildedgames.aether.common.registry.*;
import com.gildedgames.aether.common.world.gen.placement.PlacementModifiers;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.data.*;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.util.Random;

@Mod(Aether.MODID)
public class Aether {
    public static final String MODID = "aether";
    public static final Logger LOGGER = LogManager.getLogger();

    public Aether() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::curiosSetup);
        modEventBus.addListener(this::dataSetup);

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
                AetherTileEntityTypes.TILE_ENTITIES,
                AetherRecipes.RECIPE_SERIALIZERS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modEventBus);
        }

        AetherLoot.init();
        AetherAdvancements.init();
        PlacementModifiers.init();

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
        AetherBlocks.registerWoodTypes();
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
        AltarTileEntity.addItemEnchantingTime(AetherItems.AMBROSIUM_SHARD.get(), 500);

        FreezerTileEntity.addItemFreezingTime(AetherBlocks.ICESTONE.get(), 500);
    }
}
