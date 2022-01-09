package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

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

    public void fence(Supplier<? extends FenceBlock> block, Supplier<? extends Block> baseBlock, String location) {
        fenceBlock(block.get(), texture(name(baseBlock), location));
        fenceColumn(block, name(baseBlock), location);
    }

    private void fenceColumn(Supplier<? extends FenceBlock> block, String side, String location) {
        String baseName = block.get().getRegistryName().toString();
        fourWayBlock(block.get(),
                models().fencePost(baseName + "_post", texture(side, location)),
                models().fenceSide(baseName + "_side", texture(side, location)));
    }

    public void fenceGateBlock(Supplier<? extends FenceGateBlock> block, Supplier<? extends Block> baseBlock, String location) {
        fenceGateBlockInternal(block.get(), block.get().getRegistryName().toString(), texture(name(baseBlock), location));
    }

    private void fenceGateBlockInternal(FenceGateBlock block, String baseName, ResourceLocation texture) {
        ModelFile gate = models().fenceGate(baseName, texture);
        ModelFile gateOpen = models().fenceGateOpen(baseName + "_open", texture);
        ModelFile gateWall = models().fenceGateWall(baseName + "_wall", texture);
        ModelFile gateWallOpen = models().fenceGateWallOpen(baseName + "_wall_open", texture);
        fenceGateBlock(block, gate, gateOpen, gateWall, gateWallOpen);
    }

    public void wallBlock(Supplier<? extends WallBlock> block, Supplier<? extends Block> baseBlock, String location) {
        wallBlockInternal(block.get(), block.get().getRegistryName().toString(), texture(name(baseBlock), location));
    }

    private void wallBlockInternal(WallBlock block, String baseName, ResourceLocation texture) {
        wallBlock(block, models().wallPost(baseName + "_post", texture), models().wallSide(baseName + "_side", texture), models().wallSideTall(baseName + "_side_tall", texture));
    }

    public void grass(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile grass = cubeBottomTop(name(block), extend(texture(name(block), "natural/"), "_side"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        ModelFile grassSnowed = cubeBottomTop(name(block) + "_snow", extend(texture(name(block), "natural/"), "_snow"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            Boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void enchantedGrass(Supplier<? extends Block> block, Supplier<? extends Block> grassBlock, Supplier<? extends Block> dirtBlock) {
        ModelFile grass = cubeBottomTop(name(block), extend(texture(name(block), "natural/"), "_side"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        ModelFile grassSnowed = cubeBottomTop(name(grassBlock) + "_snow", extend(texture(name(grassBlock), "natural/"), "_snow"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            Boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void log(Supplier<? extends RotatedPillarBlock> block) {
        axisBlock(block.get(), texture(name(block), "natural/"), extend(texture(name(block), "natural/"), "_top"));
    }

    public void enchantedLog(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        axisBlock(block.get(), texture(name(block), "natural/"), extend(texture(name(baseBlock), "natural/"), "_top"));
    }

    public void wood(Supplier<? extends Block> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        ConfiguredModel wood = new ConfiguredModel(models().cubeAll(name(block), texture(name(baseBlock), "natural/")));
        getVariantBuilder(block.get()).partialState().setModels(wood);
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

    public void dungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        ConfiguredModel dungeonBlock = new ConfiguredModel(models().cubeAll(name(baseBlock), texture(name(baseBlock), "dungeon/")));
        getVariantBuilder(block.get()).partialState().setModels(dungeonBlock);
    }

    public void chest(Supplier<? extends Block> block, Supplier<? extends Block> dummyBlock)
    {
        ModelFile chest = models().cubeAll(name(block), texture(name(dummyBlock), "dungeon/"));
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

    public void bed(Supplier<? extends BedBlock> block, Supplier<? extends Block> dummyBlock)
    {
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
