package com.gildedgames.aether.data.providers;

import com.gildedgames.aether.block.construction.AetherFarmBlock;
import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.dungeon.DoorwayBlock;
import com.gildedgames.aether.block.miscellaneous.UnstableObsidianBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public abstract class AetherBlockStateProvider extends BlockStateProvider {
    public AetherBlockStateProvider(DataGenerator dataGenerator, String modId, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, modId, existingFileHelper);
    }

    public ResourceLocation texture(String name) {
        return this.modLoc("block/" + name);
    }

    public ResourceLocation texture(String name, String location) {
        return this.modLoc("block/" + location + name);
    }

    public ResourceLocation texture(String name, String location, String suffix) {
        return this.modLoc("block/" + location + name + suffix);
    }

    public String name(Supplier<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getKey(block.get()).getPath();
    }

    public void block(Supplier<? extends Block> block, String location) {
        this.simpleBlock(block.get(), this.cubeAll(block, location));
    }

    public void blockDoubleDrops(Supplier<? extends Block> block, String location) {
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(this.cubeAll(block, location)).build(), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void randomBlockDoubleDrops(Supplier<? extends Block> block, String location) {
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.allYRotations(this.cubeAll(block, location), 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void translucentBlock(Supplier<? extends Block> block, String location) {
        this.simpleBlock(block.get(), this.cubeAllTranslucent(block, location));
    }

    public void crossBlock(Supplier<? extends Block> block, String location) {
        this.crossBlock(block, models().cross(this.name(block), this.texture(this.name(block), location)).renderType(new ResourceLocation("cutout")));
    }

    public void crossBlock(Supplier<? extends Block> block, ModelFile model) {
        this.getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }

    public void saplingBlock(Supplier<? extends Block> block, String location) {
        ModelFile sapling = models().cross(this.name(block), this.texture(this.name(block), location)).renderType(new ResourceLocation("cutout"));
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(sapling).build(), SaplingBlock.STAGE);
    }

    public void stairs(Supplier<? extends StairBlock> block, Supplier<? extends Block> baseBlock, String location) {
        this.stairsBlock(block.get(), this.texture(this.name(baseBlock), location));
    }

    public void slab(Supplier<? extends SlabBlock> block, Supplier<? extends Block> baseBlock, String location) {
        this.slabBlock(block.get(), this.texture(this.name(baseBlock)), this.texture(this.name(baseBlock), location));
    }

    public void translucentSlab(Supplier<? extends SlabBlock> block, Supplier<? extends Block> baseBlock, String location) {
        ResourceLocation texture = this.texture(this.name(baseBlock), location);
        this.translucentSlabBlock(block.get(), models().slab(this.name(block), texture, texture, texture).renderType(new ResourceLocation("translucent")),
                this.models().slabTop(this.name(block) + "_top", texture, texture, texture).renderType(new ResourceLocation("translucent")),
                this.models().getExistingFile(this.texture(this.name(baseBlock))));
    }

    public void translucentSlabBlock(SlabBlock block, ModelFile bottom, ModelFile top, ModelFile doubleslab) {
        this.getVariantBuilder(block)
                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(bottom))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(top))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(doubleslab));
    }

    public void buttonBlock(Supplier<? extends ButtonBlock> block, ResourceLocation texture) {
        ModelFile button = this.models().button(this.name(block), texture);
        ModelFile buttonPressed = this.models().buttonPressed(this.name(block) + "_pressed", texture);
        this.buttonBlock(block.get(), button, buttonPressed);
    }

    public void pressurePlateBlock(Supplier<? extends PressurePlateBlock> block, ResourceLocation texture) {
        ModelFile pressurePlate = this.models().pressurePlate(this.name(block), texture);
        ModelFile pressurePlateDown = this.models().pressurePlateDown(this.name(block) + "_down", texture);
        this.pressurePlateBlock(block.get(), pressurePlate, pressurePlateDown);
    }

    public void signBlock(Supplier<? extends StandingSignBlock> signBlock, Supplier<? extends WallSignBlock> wallSignBlock, ResourceLocation texture) {
        ModelFile sign = this.models().sign(this.name(signBlock), texture);
        this.signBlock(signBlock.get(), wallSignBlock.get(), sign);
    }

    public void fence(Supplier<? extends FenceBlock> block, Supplier<? extends Block> baseBlock, String location) {
        this.fenceBlock(block.get(), this.texture(this.name(baseBlock), location));
        this.fenceColumn(block, this.name(baseBlock), location);
    }

    public void fenceColumn(Supplier<? extends FenceBlock> block, String side, String location) {
        String baseName = this.name(block);
        this.fourWayBlock(block.get(),
                this.models().fencePost(baseName + "_post", this.texture(side, location)),
                this.models().fenceSide(baseName + "_side", this.texture(side, location)));
    }

    public void fenceGateBlock(Supplier<? extends FenceGateBlock> block, Supplier<? extends Block> baseBlock, String location) {
        this.fenceGateBlockInternal(block.get(), this.name(block), this.texture(this.name(baseBlock), location));
    }

    public void fenceGateBlockInternal(FenceGateBlock block, String baseName, ResourceLocation texture) {
        ModelFile gate = this.models().fenceGate(baseName, texture);
        ModelFile gateOpen = this.models().fenceGateOpen(baseName + "_open", texture);
        ModelFile gateWall = this.models().fenceGateWall(baseName + "_wall", texture);
        ModelFile gateWallOpen = this.models().fenceGateWallOpen(baseName + "_wall_open", texture);
        this.fenceGateBlock(block, gate, gateOpen, gateWall, gateWallOpen);
    }

    public void doorBlock(Supplier<? extends DoorBlock> block, ResourceLocation bottom, ResourceLocation top) {
        this.doorBlockWithRenderType(block.get(), bottom, top, "cutout");
    }

    public void trapdoorBlock(Supplier<? extends TrapDoorBlock> block, ResourceLocation texture, boolean orientable) {
        this.trapdoorBlockWithRenderType(block.get(), texture, orientable, "cutout");
    }

    public void wallBlock(Supplier<? extends WallBlock> block, Supplier<? extends Block> baseBlock, String location) {
        this.wallBlockInternal(block.get(), this.name(block), this.texture(this.name(baseBlock), location));
    }

    public void wallBlockInternal(WallBlock block, String baseName, ResourceLocation texture) {
        this.wallBlock(block, this.models().wallPost(baseName + "_post", texture),
                this.models().wallSide(baseName + "_side", texture),
                this.models().wallSideTall(baseName + "_side_tall", texture));
    }

    public void portal(Supplier<? extends Block> block) {
        ModelFile portal_ew = this.models().withExistingParent(this.name(block) + "_ew", this.mcLoc("block/nether_portal_ew"))
                .texture("particle", this.modLoc("block/miscellaneous/" + this.name(block)))
                .texture("portal", this.modLoc("block/miscellaneous/" + this.name(block)))
                .renderType(new ResourceLocation("translucent"));
        ModelFile portal_ns = this.models().withExistingParent(this.name(block) + "_ns", this.mcLoc("block/nether_portal_ns"))
                .texture("particle", this.modLoc("block/miscellaneous/" + this.name(block)))
                .texture("portal", this.modLoc("block/miscellaneous/" + this.name(block)))
                .renderType(new ResourceLocation("translucent"));
        this.getVariantBuilder(block.get()).forAllStates(state -> {
            Direction.Axis axis = state.getValue(NetherPortalBlock.AXIS);
            return ConfiguredModel.builder()
                    .modelFile(axis == Direction.Axis.Z ? portal_ew : portal_ns)
                    .build();
        });
    }

    public void grass(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile grass = this.grassBlock(block, dirtBlock);
        ModelFile grassSnowed = this.cubeBottomTop(this.name(block) + "_snow",
                this.extend(this.texture(this.name(block), "natural/"), "_snow"),
                this.texture(this.name(dirtBlock), "natural/"),
                this.extend(this.texture(this.name(block), "natural/"), "_top"));
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void enchantedGrass(Supplier<? extends Block> block, Supplier<? extends Block> grassBlock, Supplier<? extends Block> dirtBlock) {
        ModelFile grass = this.grassBlock(block, dirtBlock);
        ModelFile grassSnowed = this.cubeBottomTop(this.name(grassBlock) + "_snow",
                this.extend(this.texture(this.name(grassBlock), "natural/"), "_snow"),
                this.texture(this.name(dirtBlock), "natural/"),
                this.extend(this.texture(this.name(block), "natural/"), "_top"));
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public ModelFile grassBlock(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        return this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "natural/"), "_side"),
                this.texture(this.name(dirtBlock), "natural/"),
                this.extend(this.texture(this.name(block), "natural/"), "_top"));
    }

    public void dirtPath(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile path = this.models().withExistingParent(this.name(block), this.mcLoc("block/dirt_path"))
                .texture("particle", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block) + "_top"))
                .texture("side", this.modLoc("block/construction/" + this.name(block) + "_side"))
                .texture("bottom", this.modLoc("block/natural/" + this.name(dirtBlock)));
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.allYRotations(path, 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void farmland(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile farmland = this.models().withExistingParent(this.name(block), this.mcLoc("block/template_farmland"))
                .texture("dirt", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block)));
        ModelFile moist = this.models().withExistingParent(this.name(block) + "_moist", mcLoc("block/template_farmland"))
                .texture("dirt", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block) + "_moist"));
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            int moisture = state.getValue(AetherFarmBlock.MOISTURE);
            return ConfiguredModel.builder()
                    .modelFile(moisture < AetherFarmBlock.MAX_MOISTURE ? farmland : moist)
                    .build();
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void log(Supplier<? extends RotatedPillarBlock> block) {
        this.axisBlock(block.get(), this.texture(this.name(block), "natural/"), this.extend(this.texture(this.name(block), "natural/"), "_top"));
    }

    public void enchantedLog(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        this.axisBlock(block.get(), this.texture(this.name(block), "natural/"), this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
    }

    public void wood(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        this.axisBlock(block.get(), this.texture(this.name(baseBlock), "natural/"), this.texture(this.name(baseBlock), "natural/"));
    }

    public void pane(Supplier<? extends IronBarsBlock> block, Supplier<? extends GlassBlock> glass, String location) {
        this.paneBlockWithRenderType(block.get(), this.texture(this.name(glass), location), this.extend(this.texture(this.name(block), location), "_top"), ResourceLocation.tryParse("translucent"));
    }

    public void altar(Supplier<? extends Block> block) {
        ModelFile altar = this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "utility/"), "_side"),
                this.extend(this.texture(this.name(block), "utility/"), "_bottom"),
                this.extend(this.texture(this.name(block), "utility/"), "_bottom"));
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(altar));
    }

    public void freezer(Supplier<? extends Block> block) {
        ModelFile freezer = this.utility(block);
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(freezer));
    }

    public void incubator(Supplier<? extends Block> block) {
        ModelFile incubator = this.utility(block);
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(incubator));
    }

    public ModelFile utility(Supplier<? extends Block> block) {
        return this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "utility/"), "_side"),
                this.extend(this.texture("altar", "utility/"), "_bottom"),
                this.extend(this.texture(this.name(block), "utility/"), "_top"));
    }

    public void torchBlock(Supplier<? extends Block> block, Supplier<? extends Block> wall) {
        ModelFile torch = this.models().torch(this.name(block), this.texture(this.name(block), "utility/")).renderType(new ResourceLocation("cutout"));
        ModelFile torchwall = this.models().torchWall(this.name(wall), this.texture(this.name(block), "utility/")).renderType(new ResourceLocation("cutout"));
        this.simpleBlock(block.get(), torch);
        getVariantBuilder(wall.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(torchwall)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)
                        .build());
    }

    public void berryBush(Supplier<? extends Block> block, Supplier<? extends Block> stem) {
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(this.bush(block, stem)));
    }

    public ModelFile bush(Supplier<? extends Block> block, Supplier<? extends Block> stem) {
        return this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("particle", this.texture(this.name(block), "natural/")).texture("bush", this.texture(this.name(block), "natural/")).texture("stem", this.texture(this.name(stem), "natural/"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).shade(true).allFaces((direction, builder) -> builder.texture("#bush").end()).end()
                .element().from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.NORTH).texture("#stem").end().face(Direction.SOUTH).texture("#stem").end().end()
                .element().from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.WEST).texture("#stem").end().face(Direction.EAST).texture("#stem").end().end()
                .renderType(new ResourceLocation("cutout"));
    }

    public BlockModelBuilder pottedStemModel(Supplier<? extends Block> block, Supplier<? extends Block> stem, String location) {
        return models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("particle", this.mcLoc("block/flower_pot")).texture("stem", this.modLoc("block/" + location + this.name(stem))).texture("dirt", this.mcLoc("block/dirt")).texture("flowerpot", this.mcLoc("block/flower_pot"))
                .element().from(5.0F, 0.0F, 5.0F).to(6.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(10.0F, 0.0F, 5.0F).to(11.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 5.0F).to(10.0F, 6.0F, 6.0F)
                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 10.0F).to(10.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 6.0F).to(10.0F, 4.0F, 10.0F)
                .face(Direction.UP).uvs(6.0F, 6.0F, 10.0F, 10.0F).texture("#dirt").end()
                .face(Direction.DOWN).uvs(6.0F, 12.0F, 10.0F, 16.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(1.0F, 4.0F, 8.0F).to(15.0F, 16.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.NORTH).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end()
                .face(Direction.SOUTH).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end().end()
                .element().from(8.0F, 4.0F, 1.0F).to(8.0F, 16.0F, 15.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.EAST).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end()
                .face(Direction.WEST).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end().end();
    }

    public void pottedStem(Supplier<? extends Block> stem, String location) {
        ModelFile pot = this.pottedStemModel(stem, stem, location).renderType(new ResourceLocation("cutout"));
        this.getVariantBuilder(stem.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedBush(Supplier<? extends Block> bush, Supplier<? extends Block> stem, String location) {
        ModelFile pot = this.pottedStemModel(bush, stem, location)
                .texture("stem", this.modLoc("block/" + location + this.name(stem))).texture("bush", this.modLoc("block/" + location + this.name(bush)))
                .element().from(3.0F, 6.0F, 3.0F).to(13.0F, 16.0F, 13.0F)
                .face(Direction.NORTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.EAST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.SOUTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.WEST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.UP).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.DOWN).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end().end()
                .renderType(new ResourceLocation("cutout"));
        this.getVariantBuilder(bush.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedPlant(Supplier<? extends Block> block, Supplier<? extends Block> flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), this.mcLoc("block/flower_pot_cross")).texture("plant", this.modLoc("block/" + location + this.name(flower))).renderType(new ResourceLocation("cutout"));
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void dungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        ConfiguredModel dungeonBlock = new ConfiguredModel(this.models().cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), "dungeon/")));
        this.getVariantBuilder(block.get()).partialState().setModels(dungeonBlock);
    }

    public void invisibleBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        ModelFile visible = this.models().cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), "dungeon/"));
        ModelFile invisible = this.models().getBuilder(this.name(block));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            if (!state.getValue(DoorwayBlock.INVISIBLE)) {
                return ConfiguredModel.builder().modelFile(visible).build();
            } else {
                return ConfiguredModel.builder().modelFile(invisible).build();
            }
        });
    }

    public void chestMimic(Supplier<? extends Block> block, Supplier<? extends Block> dummyBlock) {
        ModelFile chest = this.models().cubeAll(this.name(block), this.mcLoc("block/" + this.name(dummyBlock)));
        this.chest(block, chest);
    }

    public void treasureChest(Supplier<? extends Block> block, Supplier<? extends Block> dummyBlock) {
        ModelFile chest = this.models().cubeAll(this.name(block), this.texture(this.name(dummyBlock), "dungeon/"));
        this.chest(block, chest);
    }

    public void chest(Supplier<? extends Block> block, ModelFile chest) {
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(chest).build(),
                ChestBlock.TYPE, ChestBlock.WATERLOGGED);
    }

    public void pillar(Supplier<? extends RotatedPillarBlock> block) {
        this.axisBlock(block.get(), this.extend(this.texture(this.name(block), "dungeon/"), "_side"), this.extend(this.texture(this.name(block), "dungeon/"), "_top"));
    }

    public void pillarTop(Supplier<? extends RotatedPillarBlock> block) {
        this.axisBlock(block.get(), this.texture("pillar_carved", "dungeon/"), this.texture(this.name(block), "dungeon/"));
    }

    public void present(Supplier<? extends Block> block) {
        ModelFile present = this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "miscellaneous/"), "_side"),
                this.extend(this.texture(this.name(block), "miscellaneous/"), "_top"),
                this.extend(this.texture(this.name(block), "miscellaneous/"), "_top"));
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(present));
    }

    public void sunAltar(Supplier<? extends Block> block) {
        ModelFile sunAltar = this.cubeBottomTop(name(block),
                this.extend(this.texture(this.name(block), "utility/"), "_side"), this.texture("hellfire_stone", "dungeon/"),
                this.extend(this.texture(this.name(block), "utility/"), "_top"));
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(sunAltar));
    }

    public void bookshelf(Supplier<? extends Block> block, Supplier<? extends Block> endBlock) {
        ModelFile bookshelf = this.models().cubeColumn(this.name(block), this.texture(this.name(block), "construction/"), this.texture(this.name(endBlock), "construction/"));
        this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(bookshelf));
    }

    public void bed(Supplier<? extends BedBlock> block, Supplier<? extends Block> dummyBlock) {
        ModelFile head = this.models().cubeAll(this.name(block) + "_head", this.texture(this.name(dummyBlock), "construction/"));
        ModelFile foot = this.models().cubeAll(this.name(block) + "_foot", this.texture(this.name(dummyBlock), "construction/"));
        this.getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BedPart part = state.getValue(BlockStateProperties.BED_PART);
            return ConfiguredModel.builder()
                    .modelFile(part == BedPart.HEAD ? head : foot)
                    .rotationY((((int) dir.toYRot()) + 180) % 360)
                    .build();
        }, BedBlock.OCCUPIED);
    }

    public void unstableObsidian(Supplier<? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            int age = state.getValue(UnstableObsidianBlock.AGE);
            return ConfiguredModel.builder().modelFile(this.models().cubeAll(this.name(block) + "_" + age, this.texture(this.name(block) + "_" + age, "miscellaneous/"))).build();
        });
    }

    public ModelFile cubeAll(Supplier<? extends Block> block, String location) {
        return this.models().cubeAll(this.name(block), this.texture(this.name(block), location));
    }

    public ModelFile cubeAllTranslucent(Supplier<? extends Block> block, String location) {
        return this.models().cubeAll(this.name(block), this.texture(this.name(block), location)).renderType(new ResourceLocation("translucent"));
    }

    public ModelFile cubeBottomTop(String block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return this.models().cubeBottomTop(block, side, bottom, top);
    }

    public ResourceLocation extend(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }
}
