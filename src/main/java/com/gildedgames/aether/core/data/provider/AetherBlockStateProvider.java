package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.construction.AetherFarmlandBlock;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public abstract class AetherBlockStateProvider extends BlockStateProvider
{
    public AetherBlockStateProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, Aether.MODID, fileHelper);
    }

    protected ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    protected ResourceLocation texture(String name, String location) {
        return modLoc("block/" + location + name);
    }

    protected ResourceLocation texture(String name, String location, String suffix) { return modLoc("block/" + location + name + suffix); }

    protected String name(Supplier<? extends Block> block) {
        return block.get().getRegistryName().getPath();
    }

    public void block(Supplier<? extends Block> block, String location) {
        simpleBlock(block.get(), cubeAll(block, location));
    }

    public void blockDoubleDrops(Supplier<? extends Block> block, String location) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(cubeAll(block, location)).build(), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void randomBlockDoubleDrops(Supplier<? extends Block> block, String location) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.allYRotations(cubeAll(block, location), 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void crossBlock(Supplier<? extends Block> block, String location) {
        crossBlock(block, models().cross(name(block), texture(name(block), location)));
    }

    private void crossBlock(Supplier<? extends Block> block, ModelFile model) {
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }

    public void saplingBlock(Supplier<? extends Block> block, String location) {
        ModelFile sapling = models().cross(name(block), texture(name(block), location));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(sapling).build(),
                SaplingBlock.STAGE);
    }

    public void stairs(Supplier<? extends StairBlock> block, Supplier<? extends Block> baseBlock, String location) {
        stairsBlock(block.get(), texture(name(baseBlock), location));
    }

    public void slab(Supplier<? extends SlabBlock> block, Supplier<? extends Block> baseBlock, String location) {
        slabBlock(block.get(), texture(name(baseBlock)), texture(name(baseBlock), location));
    }

    public void buttonBlock(Supplier<? extends ButtonBlock> block, ResourceLocation texture) {
        ModelFile button = models().button(name(block), texture);
        ModelFile buttonPressed = models().buttonPressed(name(block) + "_pressed", texture);
        buttonBlock(block.get(), button, buttonPressed);
    }

    public void pressurePlateBlock(Supplier<? extends PressurePlateBlock> block, ResourceLocation texture) {
        ModelFile pressurePlate = models().pressurePlate(name(block), texture);
        ModelFile pressurePlateDown = models().pressurePlateDown(name(block) + "_down", texture);
        pressurePlateBlock(block.get(), pressurePlate, pressurePlateDown);
    }

    public void signBlock(Supplier<? extends StandingSignBlock> signBlock, Supplier<? extends WallSignBlock> wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        signBlock(signBlock.get(), wallSignBlock.get(), sign);
    }

    public void fence(Supplier<? extends FenceBlock> block, Supplier<? extends Block> baseBlock, String location) {
        fenceBlock(block.get(), texture(name(baseBlock), location));
        fenceColumn(block, name(baseBlock), location);
    }

    private void fenceColumn(Supplier<? extends FenceBlock> block, String side, String location) {
        String baseName = name(block);
        fourWayBlock(block.get(),
                models().fencePost(baseName + "_post", texture(side, location)),
                models().fenceSide(baseName + "_side", texture(side, location)));
    }

    public void fenceGateBlock(Supplier<? extends FenceGateBlock> block, Supplier<? extends Block> baseBlock, String location) {
        fenceGateBlockInternal(block.get(), name(block), texture(name(baseBlock), location));
    }

    private void fenceGateBlockInternal(FenceGateBlock block, String baseName, ResourceLocation texture) {
        ModelFile gate = models().fenceGate(baseName, texture);
        ModelFile gateOpen = models().fenceGateOpen(baseName + "_open", texture);
        ModelFile gateWall = models().fenceGateWall(baseName + "_wall", texture);
        ModelFile gateWallOpen = models().fenceGateWallOpen(baseName + "_wall_open", texture);
        fenceGateBlock(block, gate, gateOpen, gateWall, gateWallOpen);
    }

    public void wallBlock(Supplier<? extends WallBlock> block, Supplier<? extends Block> baseBlock, String location) {
        wallBlockInternal(block.get(), name(block), texture(name(baseBlock), location));
    }

    private void wallBlockInternal(WallBlock block, String baseName, ResourceLocation texture) {
        wallBlock(block, models().wallPost(baseName + "_post", texture), models().wallSide(baseName + "_side", texture), models().wallSideTall(baseName + "_side_tall", texture));
    }

    public void portal(Supplier<? extends Block> block) {
        ModelFile portal_ew = models().withExistingParent(name(block) + "_ew", mcLoc("block/nether_portal_ew"))
                .texture("particle", modLoc("block/miscellaneous/" + name(block)))
                .texture("portal", modLoc("block/miscellaneous/" + name(block)));
        ModelFile portal_ns = models().withExistingParent(name(block) + "_ns", mcLoc("block/nether_portal_ns"))
                .texture("particle", modLoc("block/miscellaneous/" + name(block)))
                .texture("portal", modLoc("block/miscellaneous/" + name(block)));
        getVariantBuilder(block.get()).forAllStates(state -> {
            Direction.Axis axis = state.getValue(NetherPortalBlock.AXIS);
            return ConfiguredModel.builder()
                    .modelFile(axis == Direction.Axis.Z ? portal_ew : portal_ns)
                    .build();
        });
    }

    public void grass(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile grass = cubeBottomTop(name(block), extend(texture(name(block), "natural/"), "_side"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        ModelFile grassSnowed = cubeBottomTop(name(block) + "_snow", extend(texture(name(block), "natural/"), "_snow"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void enchantedGrass(Supplier<? extends Block> block, Supplier<? extends Block> grassBlock, Supplier<? extends Block> dirtBlock) {
        ModelFile grass = cubeBottomTop(name(block), extend(texture(name(block), "natural/"), "_side"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        ModelFile grassSnowed = cubeBottomTop(name(grassBlock) + "_snow", extend(texture(name(grassBlock), "natural/"), "_snow"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void dirtPath(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile path = models().withExistingParent(name(block), mcLoc("block/dirt_path"))
                .texture("particle", modLoc("block/natural/" + name(dirtBlock)))
                .texture("top", modLoc("block/construction/" + name(block) + "_top"))
                .texture("side", modLoc("block/construction/" + name(block) + "_side"))
                .texture("bottom", modLoc("block/natural/" + name(dirtBlock)));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.allYRotations(path, 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void farmland(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile farmland = models().withExistingParent(name(block), mcLoc("block/template_farmland"))
                .texture("dirt", modLoc("block/natural/" + name(dirtBlock)))
                .texture("top", modLoc("block/construction/" + name(block)));
        ModelFile moist = models().withExistingParent(name(block) + "_moist", mcLoc("block/template_farmland"))
                .texture("dirt", modLoc("block/natural/" + name(dirtBlock)))
                .texture("top", modLoc("block/construction/" + name(block) + "_moist"));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            int moisture = state.getValue(AetherFarmlandBlock.MOISTURE);
            return ConfiguredModel.builder()
                    .modelFile(moisture < AetherFarmlandBlock.MAX_MOISTURE ? farmland : moist)
                    .build();
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void log(Supplier<? extends RotatedPillarBlock> block) {
        axisBlock(block.get(), texture(name(block), "natural/"), extend(texture(name(block), "natural/"), "_top"));
    }

    public void enchantedLog(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        axisBlock(block.get(), texture(name(block), "natural/"), extend(texture(name(baseBlock), "natural/"), "_top"));
    }

    public void wood(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        axisBlock(block.get(), texture(name(baseBlock), "natural/"), texture(name(baseBlock), "natural/"));
    }

    public void altar(Supplier<? extends Block> block) {
        ModelFile altar = cubeBottomTop(name(block), extend(texture(name(block), "utility/"), "_side"), extend(texture(name(block), "utility/"), "_bottom"), extend(texture(name(block), "utility/"), "_bottom"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(altar));
    }

    public void freezer(Supplier<? extends Block> block) {
        ModelFile freezer = cubeBottomTop(name(block), extend(texture(name(block), "utility/"), "_side"), extend(texture("altar", "utility/"), "_bottom"), extend(texture(name(block), "utility/"), "_top"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(freezer));
    }

    public void incubator(Supplier<? extends Block> block) {
        ModelFile incubator = cubeBottomTop(name(block), extend(texture(name(block), "utility/"), "_side"), extend(texture("altar", "utility/"), "_bottom"), extend(texture(name(block), "utility/"), "_top"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(incubator));
    }

    public void torchBlock(Supplier<? extends Block> block, Supplier<? extends Block> wall) {
        ModelFile torch = models().torch(name(block), texture(name(block), "utility/"));
        ModelFile torchwall = models().torchWall(name(wall), texture(name(block), "utility/"));
        simpleBlock(block.get(), torch);
        getVariantBuilder(wall.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(torchwall)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)
                        .build());
    }

    public void berryBush(Supplier<? extends Block> block, Supplier<? extends Block> stem) {
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(bush(block, stem)));
    }

    public ModelFile bush(Supplier<? extends Block> block, Supplier<? extends Block> stem) {
        return this.models().withExistingParent(name(block), mcLoc("block/block"))
                .texture("particle", texture(name(block), "natural/")).texture("bush", texture(name(block), "natural/")).texture("stem", texture(name(stem), "natural/"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).shade(true).allFaces((direction, builder) -> builder.texture("#bush").end()).end()
                .element().from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.NORTH).texture("#stem").end().face(Direction.SOUTH).texture("#stem").end().end()
                .element().from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.WEST).texture("#stem").end().face(Direction.EAST).texture("#stem").end().end();
    }

    public BlockModelBuilder pottedStemModel(Supplier<? extends Block> block, Supplier<? extends Block> stem, String location) {
        return models().withExistingParent(name(block), mcLoc("block/block"))
                .texture("particle", mcLoc("block/flower_pot")).texture("stem", modLoc("block/" + location + name(stem))).texture("dirt", mcLoc("block/dirt")).texture("flowerpot", mcLoc("block/flower_pot"))
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
        ModelFile pot = pottedStemModel(stem, stem, location);
        getVariantBuilder(stem.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedBush(Supplier<? extends Block> bush, Supplier<? extends Block> stem, String location) {
        ModelFile pot = pottedStemModel(bush, stem, location)
                .texture("stem", modLoc("block/" + location + name(stem))).texture("bush", modLoc("block/" + location + name(bush)))
                .element().from(3.0F, 6.0F, 3.0F).to(13.0F, 16.0F, 13.0F)
                    .face(Direction.NORTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                    .face(Direction.EAST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                    .face(Direction.SOUTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                    .face(Direction.WEST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                    .face(Direction.UP).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                    .face(Direction.DOWN).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end().end();
        getVariantBuilder(bush.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedPlant(Supplier<? extends Block> block, Supplier<? extends Block> flower, String location) {
        ModelFile pot = models().withExistingParent(name(block), mcLoc("block/flower_pot_cross")).texture("plant", modLoc("block/" + location + name(flower)));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void dungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        ConfiguredModel dungeonBlock = new ConfiguredModel(models().cubeAll(name(baseBlock), texture(name(baseBlock), "dungeon/")));
        getVariantBuilder(block.get()).partialState().setModels(dungeonBlock);
    }

    public void chestMimic(Supplier<? extends Block> block, Supplier<? extends Block> dummyBlock) {
        ModelFile chest = models().cubeAll(name(block), mcLoc("block/" + name(dummyBlock)));
        chest(block, chest);
    }

    public void treasureChest(Supplier<? extends Block> block, Supplier<? extends Block> dummyBlock) {
        ModelFile chest = models().cubeAll(name(block), texture(name(dummyBlock), "dungeon/"));
        chest(block, chest);
    }

    public void chest(Supplier<? extends Block> block, ModelFile chest) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(chest).build(),
                ChestBlock.TYPE, ChestBlock.WATERLOGGED);
    }

    public void pillar(Supplier<? extends RotatedPillarBlock> block) {
        axisBlock(block.get(), extend(texture(name(block), "dungeon/"), "_side"), extend(texture(name(block), "dungeon/"), "_top"));
    }

    public void pillarTop(Supplier<? extends RotatedPillarBlock> block) {
        axisBlock(block.get(), texture("pillar_carved", "dungeon/"), texture(name(block), "dungeon/"));
    }

    public void present(Supplier<? extends Block> block) {
        ModelFile present = cubeBottomTop(name(block), extend(texture(name(block), "miscellaneous/"), "_side"), extend(texture(name(block), "miscellaneous/"), "_top"), extend(texture(name(block), "miscellaneous/"), "_top"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(present));
    }

    public void sunAltar(Supplier<? extends Block> block) {
        ModelFile sunAltar = cubeBottomTop(name(block), extend(texture(name(block), "utility/"), "_side"), texture("hellfire_stone", "dungeon/"), extend(texture(name(block), "utility/"), "_top"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(sunAltar));
    }

    public void bookshelf(Supplier<? extends Block> block, Supplier<? extends Block> endBlock) {
        ModelFile bookshelf = models().cubeColumn(name(block), texture(name(block), "construction/"), texture(name(endBlock), "construction/"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(bookshelf));
    }

    public void bed(Supplier<? extends BedBlock> block, Supplier<? extends Block> dummyBlock) {
        ModelFile head = models().cubeAll(name(block) + "_head", texture(name(dummyBlock), "construction/"));
        ModelFile foot = models().cubeAll(name(block) + "_foot", texture(name(dummyBlock), "construction/"));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BedPart part = state.getValue(BlockStateProperties.BED_PART);
            return ConfiguredModel.builder()
                    .modelFile(part == BedPart.HEAD ? head : foot)
                    .rotationY((((int) dir.toYRot()) + 180) % 360)
                    .build();
        }, BedBlock.OCCUPIED);
    }

    public ModelFile cubeAll(Supplier<? extends Block> block, String location) {
        return models().cubeAll(name(block), texture(name(block), location));
    }

    private ModelFile cubeBottomTop(String block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().cubeBottomTop(block, side, bottom, top);
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }
}
