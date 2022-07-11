package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.world.structurepiece.LargeAercloudPiece;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import javax.annotation.Nonnull;
import java.util.Optional;

public class LargeAercloudStructure extends Structure {
    public static final Codec<LargeAercloudStructure> CODEC = simpleCodec(LargeAercloudStructure::new);

    public LargeAercloudStructure(Structure.StructureSettings settings) {
        super(settings);
    }

    @Nonnull
    @Override
    public Optional<GenerationStub> findGenerationPoint(@Nonnull Structure.GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> generatePieces(builder, context));
    }

    private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
        builder.addPiece(new LargeAercloudPiece(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()));
    }

    @Nonnull
    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.LARGE_AERCLOUD.get();
    }
}
