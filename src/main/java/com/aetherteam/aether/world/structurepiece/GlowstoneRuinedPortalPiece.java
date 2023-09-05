package com.aetherteam.aether.world.structurepiece;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.processor.GlowstonePortalAgeProcessor;
import com.aetherteam.aether.world.processor.HolystoneReplaceProcessor;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public class GlowstoneRuinedPortalPiece extends TemplateStructurePiece {
    private final VerticalPlacement verticalPlacement;
    private final Properties properties;

    public GlowstoneRuinedPortalPiece(StructureTemplateManager structureTemplateManager, BlockPos templatePosition, VerticalPlacement verticalPlacement, Properties properties, ResourceLocation location, Rotation rotation, Mirror mirror, BlockPos pivotPos) {
        super(AetherStructurePieceTypes.RUINED_PORTAL.get(), 0, structureTemplateManager, location, location.toString(), makeSettings(mirror, rotation, pivotPos, properties), templatePosition);
        this.verticalPlacement = verticalPlacement;
        this.properties = properties;
    }

    public GlowstoneRuinedPortalPiece(StructureTemplateManager structureTemplateManager, CompoundTag tag) {
        super(AetherStructurePieceTypes.RUINED_PORTAL.get(), tag, structureTemplateManager, (location) -> makeSettings(structureTemplateManager, tag, location));
        this.verticalPlacement = VerticalPlacement.byName(tag.getString("VerticalPlacement"));
        this.properties = Properties.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Properties"))).getOrThrow(true, Aether.LOGGER::error);
    }

    public GlowstoneRuinedPortalPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(context.structureTemplateManager(), tag);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#addAdditionalSaveData(StructurePieceSerializationContext, CompoundTag)}.
     */
    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rotation", this.placeSettings.getRotation().name());
        tag.putString("Mirror", this.placeSettings.getMirror().name());
        tag.putString("VerticalPlacement", this.verticalPlacement.getName());
        Properties.CODEC.encodeStart(NbtOps.INSTANCE, this.properties).resultOrPartial(Aether.LOGGER::error).ifPresent((propertiesTag) -> tag.put("Properties", propertiesTag));
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#makeSettings(StructureTemplateManager, CompoundTag, ResourceLocation)}.
     */
    private static StructurePlaceSettings makeSettings(StructureTemplateManager structureTemplateManager, CompoundTag tag, ResourceLocation location) {
        StructureTemplate structuretemplate = structureTemplateManager.getOrCreate(location);
        BlockPos blockpos = new BlockPos(structuretemplate.getSize().getX() / 2, 0, structuretemplate.getSize().getZ() / 2);
        return makeSettings(Mirror.valueOf(tag.getString("Mirror")), Rotation.valueOf(tag.getString("Rotation")), blockpos, Properties.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Properties"))).getOrThrow(true, Aether.LOGGER::error));
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#makeSettings(Mirror, Rotation, RuinedPortalPiece.VerticalPlacement, BlockPos, RuinedPortalPiece.Properties)}.<br><br>
     * Modified for ruined Aether portals, including replacement for Cobblestone and Holystone Bricks, and applying the double drops property.
     */
    private static StructurePlaceSettings makeSettings(Mirror mirror, Rotation rotation, BlockPos pos, Properties properties) {
        BlockIgnoreProcessor blockIgnoreProcessor = properties.airPocket ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
        List<ProcessorRule> list = Lists.newArrayList();
        StructurePlaceSettings structurePlaceSettings = new StructurePlaceSettings().setRotation(rotation).setMirror(mirror).setRotationPivot(pos)
                .addProcessor(blockIgnoreProcessor)
                .addProcessor(new RuleProcessor(list))
                .addProcessor(new GlowstonePortalAgeProcessor(properties.mossiness))
                .addProcessor(new DoubleDropsProcessor())
                .addProcessor(new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE));
        if (properties.replaceWithHolystone) {
            structurePlaceSettings.addProcessor(HolystoneReplaceProcessor.INSTANCE);
        }
        return structurePlaceSettings;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#postProcess(WorldGenLevel, StructureManager, ChunkGenerator, RandomSource, BoundingBox, ChunkPos, BlockPos)}.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to call.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        BoundingBox boundingbox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
        if (box.isInside(boundingbox.getCenter())) {
            box.encapsulate(boundingbox);
            super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
            this.spreadAetherGrass(random, level);
            this.addDirtBuryingBelowPortal(random, level);
            if (this.properties.vines || this.properties.overgrown) {
                BlockPos.betweenClosedStream(this.getBoundingBox()).forEach((p_229127_) -> {
                    if (this.properties.vines) {
                        this.maybeAddVines(random, level, p_229127_);
                    }
                    if (this.properties.overgrown) {
                        this.maybeAddLeavesAbove(random, level, p_229127_);
                    }
                });
            }
        }
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) { }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#maybeAddVines(RandomSource, LevelAccessor, BlockPos)}.
     */
    private void maybeAddVines(RandomSource random, LevelAccessor level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (!blockState.isAir() && !blockState.is(Blocks.VINE)) {
            Direction direction = getRandomHorizontalDirection(random);
            BlockPos blockPos = pos.relative(direction);
            BlockState relativeState = level.getBlockState(blockPos);
            if (relativeState.isAir()) {
                if (Block.isFaceFull(blockState.getCollisionShape(level, pos), direction)) {
                    BooleanProperty property = VineBlock.getPropertyForFace(direction.getOpposite());
                    level.setBlock(blockPos, Blocks.VINE.defaultBlockState().setValue(property, true), 3);
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#maybeAddLeavesAbove(RandomSource, LevelAccessor, BlockPos)}.
     */
    private void maybeAddLeavesAbove(RandomSource random, LevelAccessor level, BlockPos pos) {
        if (random.nextFloat() < 0.5F && level.getBlockState(pos).is(AetherBlocks.AETHER_GRASS_BLOCK.get()) && level.getBlockState(pos.above()).isAir()) {
            level.setBlock(pos.above(), Blocks.JUNGLE_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 3);
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#addNetherrackDripColumnsBelowPortal(RandomSource, LevelAccessor)}.<br><br>
     * Modified to add Aether Grass Blocks instead of Netherrack.
     */
    private void addDirtBuryingBelowPortal(RandomSource random, LevelAccessor level) {
        for (int i = this.boundingBox.minX() + 1; i < this.boundingBox.maxX(); ++i) {
            for (int j = this.boundingBox.minZ() + 1; j < this.boundingBox.maxZ(); ++j) {
                BlockPos blockPos = new BlockPos(i, this.boundingBox.minY(), j);
                if (level.getBlockState(blockPos).is(AetherBlocks.AETHER_GRASS_BLOCK.get())) {
                    this.addDirtBuryingColumn(random, level, blockPos.below());
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#addNetherrackDripColumn(RandomSource, LevelAccessor, BlockPos)}.<br><br>
     * Modified to add Aether Grass Blocks instead of Netherrack.
     */
    private void addDirtBuryingColumn(RandomSource random, LevelAccessor level, BlockPos pos) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
        this.placeAetherDirtOrGrass(random, level, mutableBlockPos);
        int i = 8;
        while (i > 0 && random.nextFloat() < 0.5F) {
            mutableBlockPos.move(Direction.DOWN);
            --i;
            this.placeAetherDirtOrGrass(random, level, mutableBlockPos);
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#spreadNetherrack(RandomSource, LevelAccessor)}.<br><br>
     * Modified to add Aether Grass Blocks instead of Netherrack.
     */
    private void spreadAetherGrass(RandomSource random, LevelAccessor level) {
        boolean flag = this.verticalPlacement == VerticalPlacement.ON_LAND_SURFACE || this.verticalPlacement == VerticalPlacement.ON_OCEAN_FLOOR;
        BlockPos blockPos = this.boundingBox.getCenter();
        int i = blockPos.getX();
        int j = blockPos.getZ();
        float[] afloat = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
        int k = afloat.length;
        int l = (this.boundingBox.getXSpan() + this.boundingBox.getZSpan()) / 2;
        int i1 = random.nextInt(Math.max(1, 8 - l / 2));
        BlockPos.MutableBlockPos mutablePos = BlockPos.ZERO.mutable();

        for (int k1 = i - k; k1 <= i + k; ++k1) {
            for (int l1 = j - k; l1 <= j + k; ++l1) {
                int i2 = Math.abs(k1 - i) + Math.abs(l1 - j);
                int j2 = Math.max(0, i2 + i1);
                if (j2 < k) {
                    float f = afloat[j2];
                    if (random.nextDouble() < (double) f) {
                        int k2 = getSurfaceY(level, k1, l1, this.verticalPlacement);
                        int l2 = flag ? k2 : Math.min(this.boundingBox.minY(), k2);
                        mutablePos.set(k1, l2, l1);
                        if (Math.abs(l2 - this.boundingBox.minY()) <= 3 && this.canBlockBeReplacedByAetherGrass(level, mutablePos)) {
                            this.placeAetherDirtOrGrass(random, level, mutablePos);
                            if (this.properties.overgrown) {
                                this.maybeAddLeavesAbove(random, level, mutablePos);
                            }
                            this.addDirtBuryingColumn(random, level, mutablePos.below());
                        }
                    }
                }
            }
        }
    }

    /**
     * @param level The {@link Level} that the block is in.
     * @param pos The {@link BlockPos} to check.
     * @return Whether the {@link BlockState} at the given position can be replaced by Aether Grass or Aether Dirt.
     */
    private boolean canBlockBeReplacedByAetherGrass(LevelAccessor level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return !blockstate.is(Blocks.AIR) && !blockstate.is(Blocks.GLOWSTONE) && !blockstate.is(BlockTags.FEATURES_CANNOT_REPLACE) && !blockstate.is(Blocks.WATER)
                && !blockstate.is(Blocks.COBBLESTONE) && !blockstate.is(Blocks.MOSSY_COBBLESTONE)
                && !blockstate.is(Blocks.COBBLESTONE_STAIRS) && !blockstate.is(Blocks.MOSSY_COBBLESTONE_STAIRS)
                && !blockstate.is(Blocks.COBBLESTONE_SLAB) && !blockstate.is(Blocks.MOSSY_COBBLESTONE_SLAB)
                && !blockstate.is(Blocks.COBBLESTONE_WALL) && !blockstate.is(Blocks.MOSSY_COBBLESTONE_WALL)
                && !blockstate.is(AetherBlocks.HOLYSTONE_BRICKS.get())
                && !blockstate.is(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get())
                && !blockstate.is(AetherBlocks.HOLYSTONE_BRICK_SLAB.get())
                && !blockstate.is(AetherBlocks.HOLYSTONE_BRICK_WALL.get());
    }

    /**
     * Places Aether Grass Blocks below air (i.e. on the surface), and Aether Dirt blocks anywhere else.
     * @param random The {@link RandomSource} for the structure piece.
     * @param level The {@link Level} to place in.
     * @param pos The {@link BlockPos} to place at.
     */
    private void placeAetherDirtOrGrass(RandomSource random, LevelAccessor level, BlockPos pos) {
        if (level.isEmptyBlock(pos.above())) {
            level.setBlock(pos, AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), 3);
            this.growGrassAndFlowers(random, level, pos.above());
        } else {
            level.setBlock(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), 3);
        }
    }

    /**
     * Places grass or flowers from the Aether with a 1/5 chance.<br>
     * Flowers are chosen with a 1/10 chance, while grass has a 9/10 chance.<br>
     * Choosing between a Purple Flower or a White Flower is 50/50 chance.<br>
     * Choosing Tall Grass is a 1/10 chance, while Grass is a 9/10 chance.
     * @param random The {@link RandomSource} for the structure piece.
     * @param level The {@link Level} to place in.
     * @param pos The {@link BlockPos} to place at.
     */
    private void growGrassAndFlowers(RandomSource random, LevelAccessor level, BlockPos pos) {
        int featureType = random.nextInt(50);
        if (random.nextInt(100) < 20 && level.isEmptyBlock(pos)) {
            if (featureType < 5 && level.getBlockState(pos.below()).is(AetherTags.Blocks.AETHER_DIRT)) {
                Block flower = random.nextBoolean() ? AetherBlocks.PURPLE_FLOWER.get() : AetherBlocks.WHITE_FLOWER.get();
                level.setBlock(pos, flower.defaultBlockState(), 2);
            } else {
                if (random.nextInt(50) > 5) {
                    level.setBlock(pos, Blocks.GRASS.defaultBlockState(), 2);
                } else {
                    DoublePlantBlock.placeAt(level, Blocks.TALL_GRASS.defaultBlockState(), pos, 2);
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#getSurfaceY(LevelAccessor, int, int, RuinedPortalPiece.VerticalPlacement)}.
     */
    private static int getSurfaceY(LevelAccessor level, int x, int z, VerticalPlacement verticalPlacement) {
        return level.getHeight(getHeightMapType(verticalPlacement), x, z) - 1;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece#getHeightMapType(RuinedPortalPiece.VerticalPlacement)}.
     */
    public static Heightmap.Types getHeightMapType(VerticalPlacement verticalPlacement) {
        return verticalPlacement == VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR : Heightmap.Types.WORLD_SURFACE;
    }

    public static class Properties {
        public static final Codec<Properties> CODEC = RecordCodecBuilder.create((codec) ->
                codec.group(
                        Codec.FLOAT.fieldOf("mossiness").forGetter((properties) -> properties.mossiness),
                        Codec.BOOL.fieldOf("air_pocket").forGetter((properties) -> properties.airPocket),
                        Codec.BOOL.fieldOf("overgrown").forGetter((properties) -> properties.overgrown),
                        Codec.BOOL.fieldOf("vines").forGetter((properties) -> properties.vines),
                        Codec.BOOL.fieldOf("replace_with_holystone").forGetter((properties) -> properties.replaceWithHolystone)
                ).apply(codec, Properties::new));
        public float mossiness;
        public boolean airPocket;
        public boolean overgrown;
        public boolean vines;
        public boolean replaceWithHolystone;

        public Properties() { }

        public Properties(float mossiness, boolean airPocket, boolean overgrown, boolean vines, boolean replaceWithHolystone) {
            this.mossiness = mossiness;
            this.airPocket = airPocket;
            this.overgrown = overgrown;
            this.vines = vines;
            this.replaceWithHolystone = replaceWithHolystone;
        }
    }

    public enum VerticalPlacement implements StringRepresentable {
        ON_LAND_SURFACE("on_land_surface"),
        PARTLY_BURIED("partly_buried"),
        ON_OCEAN_FLOOR("on_ocean_floor");

        /**
         * Warning for "deprecation" is suppressed because using {@link StringRepresentable.EnumCodec} is necessary.
         */
        @SuppressWarnings("deprecation")
        public static final StringRepresentable.EnumCodec<VerticalPlacement> CODEC = StringRepresentable.fromEnum(VerticalPlacement::values);
        private final String name;

        VerticalPlacement(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static VerticalPlacement byName(String name) {
            return CODEC.byName(name);
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
