package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Aether.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IcestoneBlockEntity>> ICESTONE = BLOCK_ENTITY_TYPES.register("icestone", () ->
            BlockEntityType.Builder.of(IcestoneBlockEntity::new, AetherBlocks.ICESTONE.get(), AetherBlocks.ICESTONE_SLAB.get(), AetherBlocks.ICESTONE_STAIRS.get(), AetherBlocks.ICESTONE_WALL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITY_TYPES.register("altar", () ->
            BlockEntityType.Builder.of(AltarBlockEntity::new, AetherBlocks.ALTAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FreezerBlockEntity>> FREEZER = BLOCK_ENTITY_TYPES.register("freezer", () ->
            BlockEntityType.Builder.of(FreezerBlockEntity::new, AetherBlocks.FREEZER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IncubatorBlockEntity>> INCUBATOR = BLOCK_ENTITY_TYPES.register("incubator", () ->
            BlockEntityType.Builder.of(IncubatorBlockEntity::new, AetherBlocks.INCUBATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChestMimicBlockEntity>> CHEST_MIMIC = BLOCK_ENTITY_TYPES.register("chest_mimic", () ->
            BlockEntityType.Builder.of(ChestMimicBlockEntity::new, AetherBlocks.CHEST_MIMIC.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TreasureChestBlockEntity>> TREASURE_CHEST = BLOCK_ENTITY_TYPES.register("treasure_chest", () ->
            BlockEntityType.Builder.of(TreasureChestBlockEntity::new, AetherBlocks.TREASURE_CHEST.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootBedBlockEntity>> SKYROOT_BED = BLOCK_ENTITY_TYPES.register("skyroot_bed", () ->
            BlockEntityType.Builder.of(SkyrootBedBlockEntity::new, AetherBlocks.SKYROOT_BED.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootSignBlockEntity>> SKYROOT_SIGN = BLOCK_ENTITY_TYPES.register("skyroot_sign", () ->
            BlockEntityType.Builder.of(SkyrootSignBlockEntity::new, AetherBlocks.SKYROOT_WALL_SIGN.get(), AetherBlocks.SKYROOT_SIGN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootHangingSignBlockEntity>> SKYROOT_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("skyroot_hanging_sign", () ->
            BlockEntityType.Builder.of(SkyrootHangingSignBlockEntity::new, AetherBlocks.SKYROOT_WALL_HANGING_SIGN.get(), AetherBlocks.SKYROOT_HANGING_SIGN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SunAltarBlockEntity>> SUN_ALTAR = BLOCK_ENTITY_TYPES.register("sun_altar", () ->
            BlockEntityType.Builder.of(SunAltarBlockEntity::new, AetherBlocks.SUN_ALTAR.get()).build(null));
}
