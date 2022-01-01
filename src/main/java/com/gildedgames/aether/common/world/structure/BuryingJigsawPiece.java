package com.gildedgames.aether.common.world.structure;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.EmptyPoolElement;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BuryingJigsawPiece extends PoolElementStructurePiece {
    public BuryingJigsawPiece(StructureManager structureManager, StructurePoolElement element, BlockPos pos, int elevationDelta, Rotation rotation, BoundingBox box) {
        super(/*AetherStructurePieces.BURYING_JIGSAW_PIECE, */structureManager, element, pos, elevationDelta, rotation, box);
        this.type = AetherStructurePieces.BURYING_JIGSAW_PIECE;
    }

    public BuryingJigsawPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(/*AetherStructurePieces.BURYING_JIGSAW_PIECE, */context, tag);
    }

    @Override
    public NoiseEffect getNoiseEffect() {
        return NoiseEffect.BURY;
    }

    // [VANILLACOPY] JigsawPlacement.addPieces
    public static Optional<PieceGenerator<JigsawConfiguration>> addPieces(PieceGeneratorSupplier.Context<JigsawConfiguration> context, JigsawPlacement.PieceFactory factory, BlockPos pos, boolean complicatedThing, boolean adjustToTerrain) {
        WorldgenRandom worldgenrandom = new WorldgenRandom(new XoroshiroRandomSource(0L));
        worldgenrandom.setLargeFeatureSeed(context.seed(), context.chunkPos().x, context.chunkPos().z);
        RegistryAccess registryaccess = context.registryAccess();
        JigsawConfiguration jigsawconfiguration = context.config();
        ChunkGenerator chunkgenerator = context.chunkGenerator();
        StructureManager structuremanager = context.structureManager();
        LevelHeightAccessor levelheightaccessor = context.heightAccessor();
        Predicate<Biome> predicate = context.validBiome();
        StructureFeature.bootstrap();
        Registry<StructureTemplatePool> registry = registryaccess.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
        Rotation rotation = Rotation.getRandom(worldgenrandom);
        StructureTemplatePool structuretemplatepool = jigsawconfiguration.startPool().get();

        StructurePoolElement structurepoolelement = structuretemplatepool.getRandomTemplate(worldgenrandom);

        if (structurepoolelement == EmptyPoolElement.INSTANCE) return Optional.empty();

        PoolElementStructurePiece jigsawPiece = factory.create(structuremanager, structurepoolelement, pos, structurepoolelement.getGroundLevelDelta(), rotation, structurepoolelement.getBoundingBox(structuremanager, pos, rotation));
        BoundingBox boundingbox = jigsawPiece.getBoundingBox();
        int i = (boundingbox.maxX() + boundingbox.minX()) / 2;
        int j = (boundingbox.maxZ() + boundingbox.minZ()) / 2;
        int k;
        if (adjustToTerrain) {
            k = pos.getY() + chunkgenerator.getFirstFreeHeight(i, j, Heightmap.Types.WORLD_SURFACE_WG, levelheightaccessor);
        } else {
            k = pos.getY();
        }

        if (!predicate.test(chunkgenerator.getNoiseBiome(QuartPos.fromBlock(i), QuartPos.fromBlock(k), QuartPos.fromBlock(j)))) {
            return Optional.empty();
        } else {
            int l = boundingbox.minY() + jigsawPiece.getGroundLevelDelta();
            jigsawPiece.move(0, k - l, 0);
            return Optional.of((structurePiecesBuilder, jigsawConfigurationContext) -> {
                List<PoolElementStructurePiece> list = Lists.newArrayList();
                list.add(jigsawPiece);
                if (jigsawconfiguration.maxDepth() > 0) {
                    AABB aabb = new AABB(i - 80, k - 80, j - 80, i + 80 + 1, k + 80 + 1, j + 80 + 1);
                    JigsawPlacement.Placer jigsawplacement$placer = new JigsawPlacement.Placer(registry, jigsawconfiguration.maxDepth(), factory, chunkgenerator, structuremanager, list, worldgenrandom);
                    jigsawplacement$placer.placing.addLast(new JigsawPlacement.PieceState(jigsawPiece, new MutableObject<>(Shapes.join(Shapes.create(aabb), Shapes.create(AABB.of(boundingbox)), BooleanOp.ONLY_FIRST)), 0));

                    while(!jigsawplacement$placer.placing.isEmpty()) {
                        JigsawPlacement.PieceState jigsawplacement$piecestate = jigsawplacement$placer.placing.removeFirst();
                        jigsawplacement$placer.tryPlacingChildren(jigsawplacement$piecestate.piece, jigsawplacement$piecestate.free, jigsawplacement$piecestate.depth, complicatedThing, levelheightaccessor);
                    }

                    list.forEach(structurePiecesBuilder::addPiece);
                }
            });
        }
    }
}
