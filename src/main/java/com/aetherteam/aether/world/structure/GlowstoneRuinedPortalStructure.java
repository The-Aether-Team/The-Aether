package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.world.structurepiece.GlowstoneRuinedPortalPiece;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Optional;

public class GlowstoneRuinedPortalStructure extends Structure {
//    private static final String[] STRUCTURE_LOCATION_PORTALS = new String[]{"ruined_portal/portal_1", "ruined_portal/portal_2", "ruined_portal/portal_3", "ruined_portal/portal_4", "ruined_portal/portal_5", "ruined_portal/portal_6", "ruined_portal/portal_7", "ruined_portal/portal_8", "ruined_portal/portal_9", "ruined_portal/portal_10"};
//    private static final String[] STRUCTURE_LOCATION_GIANT_PORTALS = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};

    private final List<GlowstoneRuinedPortalStructure.Setup> setups;
    public static final Codec<GlowstoneRuinedPortalStructure> CODEC = RecordCodecBuilder.create((codec) -> codec.group(settingsCodec(codec),
                    ExtraCodecs.nonEmptyList(GlowstoneRuinedPortalStructure.Setup.CODEC.listOf()).fieldOf("setups").forGetter((structure) -> structure.setups))
            .apply(codec, GlowstoneRuinedPortalStructure::new));

    public GlowstoneRuinedPortalStructure(Structure.StructureSettings settings, List<GlowstoneRuinedPortalStructure.Setup> setups) {
        super(settings);
        this.setups = setups;
    }

    public GlowstoneRuinedPortalStructure(Structure.StructureSettings settings, GlowstoneRuinedPortalStructure.Setup setup) {
        this(settings, List.of(setup));
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return Optional.empty();
//        GlowstoneRuinedPortalPiece.Properties ruinedportalpiece$properties = new GlowstoneRuinedPortalPiece.Properties();
//        WorldgenRandom worldgenrandom = context.random();
//        Setup ruinedportalstructure$setup = null;
//        if (this.setups.size() > 1) {
//            float f = 0.0F;
//
//            for(Setup ruinedportalstructure$setup1 : this.setups) {
//                f += ruinedportalstructure$setup1.weight();
//            }
//
//            float f1 = worldgenrandom.nextFloat();
//
//            for(Setup ruinedportalstructure$setup2 : this.setups) {
//                f1 -= ruinedportalstructure$setup2.weight() / f;
//                if (f1 < 0.0F) {
//                    ruinedportalstructure$setup = ruinedportalstructure$setup2;
//                    break;
//                }
//            }
//        } else {
//            ruinedportalstructure$setup = this.setups.get(0);
//        }
//
//        if (ruinedportalstructure$setup == null) {
//            throw new IllegalStateException();
//        } else {
//            Setup ruinedportalstructure$setup3 = ruinedportalstructure$setup;
//            ruinedportalpiece$properties.airPocket = sample(worldgenrandom, ruinedportalstructure$setup3.airPocketProbability());
//            ruinedportalpiece$properties.mossiness = ruinedportalstructure$setup3.mossiness();
//            ruinedportalpiece$properties.overgrown = ruinedportalstructure$setup3.overgrown();
//            ruinedportalpiece$properties.vines = ruinedportalstructure$setup3.vines();
//            ruinedportalpiece$properties.replaceWithHolystone = ruinedportalstructure$setup3.replaceWithBlackstone();
//            ResourceLocation resourcelocation;
//            if (worldgenrandom.nextFloat() < 0.05F) {
//                resourcelocation = new ResourceLocation(STRUCTURE_LOCATION_GIANT_PORTALS[worldgenrandom.nextInt(STRUCTURE_LOCATION_GIANT_PORTALS.length)]);
//            } else {
//                resourcelocation = new ResourceLocation(STRUCTURE_LOCATION_PORTALS[worldgenrandom.nextInt(STRUCTURE_LOCATION_PORTALS.length)]);
//            }
//
//            StructureTemplate structuretemplate = context.structureTemplateManager().getOrCreate(resourcelocation);
//            Rotation rotation = Util.getRandom(Rotation.values(), worldgenrandom);
//            Mirror mirror = worldgenrandom.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
//            BlockPos blockpos = new BlockPos(structuretemplate.getSize().getX() / 2, 0, structuretemplate.getSize().getZ() / 2);
//            ChunkGenerator chunkgenerator = context.chunkGenerator();
//            LevelHeightAccessor levelheightaccessor = context.heightAccessor();
//            RandomState randomstate = context.randomState();
//            BlockPos blockpos1 = context.chunkPos().getWorldPosition();
//            BoundingBox boundingbox = structuretemplate.getBoundingBox(blockpos1, rotation, blockpos, mirror);
//            BlockPos blockpos2 = boundingbox.getCenter();
//            int i = chunkgenerator.getBaseHeight(blockpos2.getX(), blockpos2.getZ(), RuinedPortalPiece.getHeightMapType(ruinedportalstructure$setup3.placement()), levelheightaccessor, randomstate) - 1;
//            int j = findSuitableY(worldgenrandom, chunkgenerator, ruinedportalstructure$setup3.placement(), ruinedportalpiece$properties.airPocket, i, boundingbox.getYSpan(), boundingbox, levelheightaccessor, randomstate);
//            BlockPos blockpos3 = new BlockPos(blockpos1.getX(), j, blockpos1.getZ());
//            return Optional.of(new GenerationStub(blockpos3, (p_229297_) -> {
//                if (ruinedportalstructure$setup3.canBeCold()) {
//                    ruinedportalpiece$properties.cold = isCold(blockpos3, context.chunkGenerator().getBiomeSource().getNoiseBiome(QuartPos.fromBlock(blockpos3.getX()), QuartPos.fromBlock(blockpos3.getY()), QuartPos.fromBlock(blockpos3.getZ()), randomstate.sampler()));
//                }
//
//                p_229297_.addPiece(new RuinedPortalPiece(context.structureTemplateManager(), blockpos3, ruinedportalstructure$setup3.placement(), ruinedportalpiece$properties, resourcelocation, structuretemplate, rotation, mirror, blockpos));
//            }));
//        }
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.RUINED_PORTAL.get();
    }

    public record Setup(GlowstoneRuinedPortalPiece.VerticalPlacement placement, float airPocketProbability, float mossiness, boolean overgrown, boolean vines, boolean canBeCold, boolean replaceWithBlackstone, float weight) {
        public static final Codec<GlowstoneRuinedPortalStructure.Setup> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
                GlowstoneRuinedPortalPiece.VerticalPlacement.CODEC.fieldOf("placement").forGetter(GlowstoneRuinedPortalStructure.Setup::placement),
                Codec.floatRange(0.0F, 1.0F).fieldOf("air_pocket_probability").forGetter(GlowstoneRuinedPortalStructure.Setup::airPocketProbability),
                Codec.floatRange(0.0F, 1.0F).fieldOf("mossiness").forGetter(GlowstoneRuinedPortalStructure.Setup::mossiness),
                Codec.BOOL.fieldOf("overgrown").forGetter(GlowstoneRuinedPortalStructure.Setup::overgrown), Codec.BOOL.fieldOf("vines").forGetter(GlowstoneRuinedPortalStructure.Setup::vines),
                Codec.BOOL.fieldOf("can_be_cold").forGetter(GlowstoneRuinedPortalStructure.Setup::canBeCold), Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(GlowstoneRuinedPortalStructure.Setup::replaceWithBlackstone),
                ExtraCodecs.POSITIVE_FLOAT.fieldOf("weight").forGetter(GlowstoneRuinedPortalStructure.Setup::weight)).apply(codec, GlowstoneRuinedPortalStructure.Setup::new));
    }
}
