package com.aetherteam.aether.world.structurepiece;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.world.structure.AetherStructureTypes;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public class GlowstoneRuinedPortalPiece extends TemplateStructurePiece {
    private final GlowstoneRuinedPortalPiece.VerticalPlacement verticalPlacement;
    private final GlowstoneRuinedPortalPiece.Properties properties;

    public GlowstoneRuinedPortalPiece(StructureTemplateManager structureTemplateManager, BlockPos templatePosition, GlowstoneRuinedPortalPiece.VerticalPlacement verticalPlacement, GlowstoneRuinedPortalPiece.Properties properties, ResourceLocation location, StructureTemplate template, Rotation rotation, Mirror mirror, BlockPos pivotPos) {
        super(StructurePieceType.RUINED_PORTAL, 0, structureTemplateManager, location, location.toString(), makeSettings(mirror, rotation, verticalPlacement, pivotPos, properties), templatePosition);
        this.verticalPlacement = verticalPlacement;
        this.properties = properties;
    }

    public GlowstoneRuinedPortalPiece(StructureTemplateManager structureTemplateManager, CompoundTag tag) {
        super(AetherStructurePieceTypes.RUINED_PORTAL.get(), tag, structureTemplateManager, (location) -> makeSettings(structureTemplateManager, tag, location));
        this.verticalPlacement = GlowstoneRuinedPortalPiece.VerticalPlacement.byName(tag.getString("VerticalPlacement"));
        this.properties = GlowstoneRuinedPortalPiece.Properties.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Properties"))).getOrThrow(true, Aether.LOGGER::error);
    }

    public GlowstoneRuinedPortalPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(context.structureTemplateManager(), tag);
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rotation", this.placeSettings.getRotation().name());
        tag.putString("Mirror", this.placeSettings.getMirror().name());
        tag.putString("VerticalPlacement", this.verticalPlacement.getName());
        GlowstoneRuinedPortalPiece.Properties.CODEC.encodeStart(NbtOps.INSTANCE, this.properties).resultOrPartial(Aether.LOGGER::error).ifPresent((propertiesTag) -> tag.put("Properties", propertiesTag));
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager structureTemplateManager, CompoundTag tag, ResourceLocation location) {
        StructureTemplate structuretemplate = structureTemplateManager.getOrCreate(location);
        BlockPos blockpos = new BlockPos(structuretemplate.getSize().getX() / 2, 0, structuretemplate.getSize().getZ() / 2);
        return makeSettings(Mirror.valueOf(tag.getString("Mirror")), Rotation.valueOf(tag.getString("Rotation")), GlowstoneRuinedPortalPiece.VerticalPlacement.byName(tag.getString("VerticalPlacement")), blockpos, GlowstoneRuinedPortalPiece.Properties.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Properties"))).getOrThrow(true, Aether.LOGGER::error));
    }

    private static StructurePlaceSettings makeSettings(Mirror mirror, Rotation rotation, GlowstoneRuinedPortalPiece.VerticalPlacement verticalPlacement, BlockPos pos, GlowstoneRuinedPortalPiece.Properties properties) {
        StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setRotation(rotation).setMirror(mirror).setRotationPivot(pos);
        //todo
        return structureplacesettings;
    }

    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        BoundingBox boundingbox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
        if (box.isInside(boundingbox.getCenter())) {
            box.encapsulate(boundingbox);
            super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
        }
    }

    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) { }

    public static class Properties {
        public static final Codec<GlowstoneRuinedPortalPiece.Properties> CODEC = RecordCodecBuilder.create((codec) ->
                codec.group(
                        Codec.BOOL.fieldOf("cold").forGetter((p_229226_) -> p_229226_.cold),
                        Codec.FLOAT.fieldOf("mossiness").forGetter((p_229224_) -> p_229224_.mossiness),
                        Codec.BOOL.fieldOf("air_pocket").forGetter((p_229222_) -> p_229222_.airPocket),
                        Codec.BOOL.fieldOf("overgrown").forGetter((p_229220_) -> p_229220_.overgrown),
                        Codec.BOOL.fieldOf("vines").forGetter((p_229218_) -> p_229218_.vines),
                        Codec.BOOL.fieldOf("replace_with_holystone").forGetter((p_229216_) -> p_229216_.replaceWithHolystone)
                ).apply(codec, GlowstoneRuinedPortalPiece.Properties::new));
        public boolean cold;
        public float mossiness;
        public boolean airPocket;
        public boolean overgrown;
        public boolean vines;
        public boolean replaceWithHolystone;

        public Properties() { }

        public Properties(boolean cold, float mossiness, boolean airPocket, boolean overgrown, boolean vines, boolean replaceWithHolystone) {
            this.cold = cold;
            this.mossiness = mossiness;
            this.airPocket = airPocket;
            this.overgrown = overgrown;
            this.vines = vines;
            this.replaceWithHolystone = replaceWithHolystone;
        }
    }

    public enum VerticalPlacement implements StringRepresentable {
        ON_LAND_SURFACE("on_land_surface"),
        IN_AETHER("in_aether");

        public static final StringRepresentable.EnumCodec<GlowstoneRuinedPortalPiece.VerticalPlacement> CODEC = StringRepresentable.fromEnum(GlowstoneRuinedPortalPiece.VerticalPlacement::values);
        private final String name;

        VerticalPlacement(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static GlowstoneRuinedPortalPiece.VerticalPlacement byName(String name) {
            return CODEC.byName(name);
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
