package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.world.structurepiece.GlowstoneRuinedPortalPiece;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Optional;

public class GlowstoneRuinedPortalStructure extends Structure {
    private static final String[] STRUCTURE_LOCATION_PORTALS = new String[]{"ruined_portal/portal_1", "ruined_portal/portal_2", "ruined_portal/portal_3", "ruined_portal/portal_4", "ruined_portal/portal_5", "ruined_portal/portal_6", "ruined_portal/portal_7", "ruined_portal/portal_8", "ruined_portal/portal_9", "ruined_portal/portal_10"};
    private static final String[] STRUCTURE_LOCATION_GIANT_PORTALS = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};

    private final List<Setup> setups;
    public static final Codec<GlowstoneRuinedPortalStructure> CODEC = RecordCodecBuilder.create((codec) -> codec.group(settingsCodec(codec),
                    ExtraCodecs.nonEmptyList(Setup.CODEC.listOf()).fieldOf("setups").forGetter((structure) -> structure.setups))
            .apply(codec, GlowstoneRuinedPortalStructure::new));

    public GlowstoneRuinedPortalStructure(Structure.StructureSettings settings, List<Setup> setups) {
        super(settings);
        this.setups = setups;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure#findGenerationPoint(GenerationContext)}.
     */
    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        GlowstoneRuinedPortalPiece.Properties pieceProperties = new GlowstoneRuinedPortalPiece.Properties();
        WorldgenRandom worldGenRandom = context.random();
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        LevelHeightAccessor levelHeightAccessor = context.heightAccessor();
        RandomState randomState = context.randomState();
        BlockPos blockPosChunk = context.chunkPos().getWorldPosition();

        Setup portalSetup = null;
        if (this.setups.size() > 1) {
            float f = 0.0F;

            for (Setup setup1 : this.setups) {
                f += setup1.weight();
            }

            float f1 = worldGenRandom.nextFloat();

            for (Setup setup2 : this.setups) {
                f1 -= setup2.weight() / f;
                if (f1 < 0.0F) {
                    portalSetup = setup2;
                    break;
                }
            }
        } else {
            portalSetup = this.setups.get(0);
        }

        if (portalSetup == null) {
            throw new IllegalStateException();
        } else {
            Setup setup3 = portalSetup;
            pieceProperties.airPocket = sample(worldGenRandom, setup3.airPocketProbability());
            pieceProperties.mossiness = setup3.mossiness();
            pieceProperties.overgrown = setup3.overgrown();
            pieceProperties.vines = setup3.vines();
            pieceProperties.replaceWithHolystone = setup3.replaceWithHolystone();
            ResourceLocation location;
            if (worldGenRandom.nextFloat() < 0.05F) {
                location = new ResourceLocation(Aether.MODID, STRUCTURE_LOCATION_GIANT_PORTALS[worldGenRandom.nextInt(STRUCTURE_LOCATION_GIANT_PORTALS.length)]);
            } else {
                location = new ResourceLocation(Aether.MODID, STRUCTURE_LOCATION_PORTALS[worldGenRandom.nextInt(STRUCTURE_LOCATION_PORTALS.length)]);
            }

            StructureTemplate template = context.structureTemplateManager().getOrCreate(location);
            Rotation rotation = Util.getRandom(Rotation.values(), worldGenRandom);
            Mirror mirror = worldGenRandom.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
            BlockPos blockPos = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
            BoundingBox boundingbox = template.getBoundingBox(blockPosChunk, rotation, blockPos, mirror);
            BlockPos centerPos = boundingbox.getCenter();
            int i = chunkGenerator.getBaseHeight(centerPos.getX(), centerPos.getZ(), GlowstoneRuinedPortalPiece.getHeightMapType(setup3.placement()), levelHeightAccessor, randomState) - 1;
            int j = findSuitableY(worldGenRandom, chunkGenerator, setup3.placement(), i, boundingbox.getYSpan(), boundingbox, levelHeightAccessor, randomState);
            BlockPos spawnPos = new BlockPos(blockPosChunk.getX(), j, blockPosChunk.getZ());

            if (validCorners(chunkGenerator, levelHeightAccessor, chunkPos, randomState, spawnPos.getY())) {
                return Optional.of(new GenerationStub(spawnPos, (builder) -> builder.addPiece(new GlowstoneRuinedPortalPiece(context.structureTemplateManager(), spawnPos, setup3.placement(), pieceProperties, location, rotation, mirror, blockPos))));
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure#sample(WorldgenRandom, float)}.
     */
    private static boolean sample(WorldgenRandom random, float threshold) {
        if (threshold == 0.0F) {
            return false;
        } else if (threshold == 1.0F) {
            return true;
        } else {
            return random.nextFloat() < threshold;
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure#findSuitableY(RandomSource, ChunkGenerator, RuinedPortalPiece.VerticalPlacement, boolean, int, int, BoundingBox, LevelHeightAccessor, RandomState)}.
     */
    private static int findSuitableY(RandomSource random, ChunkGenerator chunkGenerator, GlowstoneRuinedPortalPiece.VerticalPlacement verticalPlacement, int height, int blockCountY, BoundingBox box, LevelHeightAccessor heightAccessor, RandomState randomState) {
        int j = heightAccessor.getMinBuildHeight() + 15;
        int i;
        if (verticalPlacement == GlowstoneRuinedPortalPiece.VerticalPlacement.PARTLY_BURIED) {
            i = height - blockCountY + Mth.randomBetweenInclusive(random, 2, 8);
        } else {
            i = height;
        }

        List<BlockPos> positions = ImmutableList.of(new BlockPos(box.minX(), 0, box.minZ()), new BlockPos(box.maxX(), 0, box.minZ()), new BlockPos(box.minX(), 0, box.maxZ()), new BlockPos(box.maxX(), 0, box.maxZ()));
        List<NoiseColumn> noiseColumns = positions.stream().map((pos) -> chunkGenerator.getBaseColumn(pos.getX(), pos.getZ(), heightAccessor, randomState)).toList();
        Heightmap.Types heightmap$types = verticalPlacement == GlowstoneRuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR : Heightmap.Types.WORLD_SURFACE;

        int l;
        for (l = i; l > j; --l) {
            int i1 = 0;

            for (NoiseColumn noiseColumn : noiseColumns) {
                BlockState blockState = noiseColumn.getBlock(l);
                if (heightmap$types.isOpaque().test(blockState)) {
                    ++i1;
                    if (i1 == 3) {
                        return l;
                    }
                }
            }
        }
        return l;
    }

    /**
     * Checks whether a chunk corner isn't empty, to determine whether a ruined portal can generate at the location it has chosen.
     *
     * @param generator      The {@link ChunkGenerator} for the world.
     * @param heightAccessor The {@link LevelHeightAccessor} for the world.
     * @param chunkPos       The {@link ChunkPos} for the structure generation.
     * @param random         The {@link RandomState} from the world.
     * @param spawnY         The {@link Integer} for the y-position where the structure tries to generate.
     * @return Whether a chunk corner is valid for the structure to generate from.
     */
    private static boolean validCorners(ChunkGenerator generator, LevelHeightAccessor heightAccessor, ChunkPos chunkPos, RandomState random, int spawnY) {
        int minX = chunkPos.getMinBlockX() - 1;
        int minZ = chunkPos.getMinBlockZ() - 1;
        int maxX = chunkPos.getMaxBlockX() + 1;
        int maxZ = chunkPos.getMaxBlockZ() + 1;
        NoiseColumn[] columns = {
                generator.getBaseColumn(minX, minZ, heightAccessor, random),
                generator.getBaseColumn(minX, maxZ, heightAccessor, random),
                generator.getBaseColumn(maxX, minZ, heightAccessor, random),
                generator.getBaseColumn(maxX, maxZ, heightAccessor, random)
        };
        for (NoiseColumn column : columns) {
            if (!column.getBlock(spawnY).isAir() && !column.getBlock(spawnY).is(AetherTags.Blocks.NON_RUINED_PORTAL_SPAWNABLE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.RUINED_PORTAL.get();
    }

    public record Setup(GlowstoneRuinedPortalPiece.VerticalPlacement placement, float airPocketProbability, float mossiness, boolean overgrown, boolean vines, boolean replaceWithHolystone, float weight) {
        public static final Codec<Setup> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
                GlowstoneRuinedPortalPiece.VerticalPlacement.CODEC.fieldOf("placement").forGetter(Setup::placement),
                Codec.floatRange(0.0F, 1.0F).fieldOf("air_pocket_probability").forGetter(Setup::airPocketProbability),
                Codec.floatRange(0.0F, 1.0F).fieldOf("mossiness").forGetter(Setup::mossiness),
                Codec.BOOL.fieldOf("overgrown").forGetter(Setup::overgrown),
                Codec.BOOL.fieldOf("vines").forGetter(Setup::vines),
                Codec.BOOL.fieldOf("replace_with_holystone").forGetter(Setup::replaceWithHolystone),
                ExtraCodecs.POSITIVE_FLOAT.fieldOf("weight").forGetter(Setup::weight)).apply(codec, Setup::new));
    }
}
