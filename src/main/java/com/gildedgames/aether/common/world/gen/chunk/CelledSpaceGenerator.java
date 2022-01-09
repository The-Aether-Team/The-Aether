package com.gildedgames.aether.common.world.gen.chunk;

import com.gildedgames.aether.core.util.math.Matrix3x3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@SuppressWarnings("unused")
public class CelledSpaceGenerator extends DelegatedChunkGenerator {
    public static final Codec<CelledSpaceGenerator> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ChunkGenerator.CODEC.fieldOf("delegate").forGetter(o -> o.delegate),
            Matrix3x3.CODEC.fieldOf("transformation").orElseGet(() -> Matrix3x3.identityScaled(256)).forGetter(o -> o.nodeMatrix),
            BlockPos.CODEC.fieldOf("unit_scale").forGetter(o -> o.unitScale),
            SurfaceRules.RuleSource.CODEC.fieldOf("surface_rule").forGetter(o -> o.ruleSource),
            BlockState.CODEC.listOf().fieldOf("blockstates").forGetter(o -> o.blocks)
    ).apply(inst, CelledSpaceGenerator::new));

    private final ConcurrentHashMap<BlockPos, BlockPos> nodes = new ConcurrentHashMap<>();

    private final SurfaceRules.RuleSource ruleSource;
    private final List<BlockState> blocks;
    private final Matrix3x3 nodeMatrix;
    private final BlockPos unitScale;

    @SuppressWarnings("FieldCanBeLocal")
    private final int SPAN = 1; // TODO Config

    //private final float halfUnitX;
    //private final float halfUnitY;
    //private final float halfUnitZ;

    private static final BlockState EMPTY_DEFAULT = Blocks.AIR.defaultBlockState();

    public CelledSpaceGenerator(ChunkGenerator delegate, Matrix3x3 matrix3f, BlockPos unitScale, SurfaceRules.RuleSource ruleSource, List<BlockState> blocks) {
        super(delegate, delegate.getBiomeSource(), delegate.getSettings(), delegate.strongholdSeed);
        this.nodeMatrix = matrix3f;
        this.unitScale = unitScale;
        this.ruleSource = ruleSource;
        this.blocks = blocks;

        //this.halfUnitX = this.unitScale.getX() / 2f;
        //this.halfUnitY = this.unitScale.getY() / 2f;
        //this.halfUnitZ = this.unitScale.getZ() / 2f;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunkAccess) {
        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("aether_vector_fill", () -> this.doFill(blender, structureFeatureManager, chunkAccess)), executor);
    }

    private ChunkAccess doFill(Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunk) {
        Beardifier beardifier = new Beardifier(structureFeatureManager, chunk);
        Heightmap oceanHeightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap worldHeightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

        ChunkPos chunkPos = chunk.getPos();
        int xPos = chunkPos.getMinBlockX();
        int zPos = chunkPos.getMinBlockZ();

        for (int y = chunk.getMaxBuildHeight() - 1; y >= chunk.getMinBuildHeight(); y--)
            for (int z = 0; z < 16; z++)
                for (int x = 0; x < 16; x++)
                    this.place(x, y, z, this.test(xPos + x, y, zPos + z, beardifier), oceanHeightmap, worldHeightmap, chunk);

        return chunk;
    }

    protected BlockState test(int x, int y, int z, Beardifier beardifier) {
        return this.test(x, y, z, (float) beardifier.calculateNoise(x, y, z));
    }

    protected BlockState test(int blockX, int blockY, int blockZ, float structureContribution) {
        float nodeSpaceX = this.nodeMatrix.multiplyXRow(blockX, blockY, blockZ) / (float) this.unitScale.getX();
        float nodeSpaceY = this.nodeMatrix.multiplyYRow(blockX, blockY, blockZ) / (float) this.unitScale.getY();
        float nodeSpaceZ = this.nodeMatrix.multiplyZRow(blockX, blockY, blockZ) / (float) this.unitScale.getZ();

        BlockPos node = this.getNearestNode(nodeSpaceX, nodeSpaceY, nodeSpaceZ);

        float rX = Math.abs((nodeSpaceX - node.getX() - 0.5f) * 2f);
        float rY = Math.abs((nodeSpaceY - node.getY() - 0.5f) * 2f);
        float rZ = Math.abs((nodeSpaceZ - node.getZ() - 0.5f) * 2f);

        //float fraction = rX * rY + rX * rZ + rY * rZ - structureContribution * 25;
        float fraction = rX * rX + rY * rY + rZ * rZ - structureContribution * 25;

        return fraction <= 1f ? this.getBlockI(node.getX() + node.getY() + node.getZ()) : EMPTY_DEFAULT;
    }

    protected BlockState getBlockF(float fractional) {
        //if (fractional < 0 || this.blocks.isEmpty()) return EMPTY_DEFAULT;
        return this.getBlockI((int) fractional * this.blocks.size());
    }

    protected BlockState getBlockI(int index) {
        return this.blocks.get(Mth.positiveModulo(index, this.blocks.size()));
    }

    protected void place(int x, int y, int z, BlockState state, Heightmap oceanHeightmap, Heightmap worldHeightmap, ChunkAccess chunk) {
        chunk.setBlockState(new BlockPos(x, y, z), state, false);
        oceanHeightmap.update(x, y, z, state);
        worldHeightmap.update(x, y, z, state);
    }

    // FIXME Very placeholder-y
    @SuppressWarnings({"ConstantConditions", "unused", "RedundantSuppression"})
    protected BlockPos getNearestNode(float vX, float vY, float vZ) {
        // TODO y-storage projection
        // FIXME Mutable BlockPos instead of new BlockPos constantly (not good)
        // TODO Vec3i (mutable) -> Node pooling system
        return this.nodes.computeIfAbsent(new BlockPos(vX, vY, vZ), vec3i -> vec3i);
    }

    // (Mth.positiveModulo(x, this.blocksToUnitScale) - this.blockHalfScale) / (float) this.blockHalfScale

    //protected int nearestXZ(int xzPos) {
    //    return xzPos / this.blocksToUnitScale;
    //}

    //protected int nearestY(int yPos) {
    //    return Mth.clamp(0, yPos / this.verticalScale, this.verticalUnitSpan - 1);
    //}

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }
}
