package com.aetherteam.aether.data.providers;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.construction.AetherFarmBlock;
import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.block.miscellaneous.AetherFrostedIceBlock;
import com.aetherteam.aether.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aether.block.miscellaneous.UnstableObsidianBlock;
import com.aetherteam.nitrogen.data.providers.NitrogenBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AetherBlockStateProvider extends NitrogenBlockStateProvider {
    public AetherBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public void grass(Block block, Block dirtBlock) {
        this.grassBlock(block, block, dirtBlock);
    }

    public void enchantedGrass(Block block, Block grassBlock, Block dirtBlock) {
        this.grassBlock(block, grassBlock, dirtBlock);
    }

    public void grassBlock(Block baseBlock, Block blockForSnow, Block blockForDirt) {
        ModelFile grass = this.grassBlock(baseBlock, blockForDirt);
        ModelFile grassSnowed = this.cubeBottomTop(this.name(blockForSnow) + "_snow",
                this.extend(this.texture(this.name(blockForSnow), "natural/"), "_snow"),
                this.texture(this.name(blockForDirt), "natural/"),
                this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
        this.getVariantBuilder(baseBlock).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public ModelFile grassBlock(Block block, Block dirtBlock) {
        return this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "natural/"), "_side"),
                this.texture(this.name(dirtBlock), "natural/"),
                this.extend(this.texture(this.name(block), "natural/"), "_top"));
    }

    public void dirtPath(Block block, Block dirtBlock) {
        ModelFile path = this.models().withExistingParent(this.name(block), this.mcLoc("block/dirt_path"))
                .texture("particle", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block) + "_top"))
                .texture("side", this.modLoc("block/construction/" + this.name(block) + "_side"))
                .texture("bottom", this.modLoc("block/natural/" + this.name(dirtBlock)));
        this.getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.allYRotations(path, 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void aercloudAll(Block block, String location) {
        ResourceLocation texture = this.texture(this.name(block), location);
        this.aercloud(block, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture);
    }

    public void aercloud(Block block,
                         ResourceLocation upInside, ResourceLocation upOutside,
                         ResourceLocation downOutside, ResourceLocation downInside,
                         ResourceLocation northOutside, ResourceLocation northInside,
                         ResourceLocation southInside, ResourceLocation southOutside,
                         ResourceLocation westOutside, ResourceLocation westInside,
                         ResourceLocation eastInside, ResourceLocation eastOutside,
                         ResourceLocation particle) {
        ModelFile model = this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("up_inside", upInside)
                .texture("up_outside", upOutside)
                .texture("down_outside", downOutside)
                .texture("down_inside", downInside)
                .texture("north_outside", northOutside)
                .texture("north_inside", northInside)
                .texture("south_inside", southInside)
                .texture("south_outside", southOutside)
                .texture("west_outside", westOutside)
                .texture("west_inside", westInside)
                .texture("east_inside", eastInside)
                .texture("east_outside", eastOutside)
                .texture("particle", particle)
                .renderType(ResourceLocation.withDefaultNamespace("translucent"))
                .element().from(0.0F, 15.998F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.DOWN).texture("#up_inside").uvs(0, 16, 16, 0).cullface(Direction.UP).end()
                .face(Direction.UP).texture("#up_outside").uvs(0, 0, 16, 16).cullface(Direction.UP).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 0.002F, 16.0F)
                .face(Direction.DOWN).texture("#down_outside").uvs(0, 0, 16, 16).cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#down_inside").uvs(0, 16, 16, 0).cullface(Direction.DOWN).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 0.002F)
                .face(Direction.NORTH).texture("#north_outside").uvs(0, 0, 16, 16).cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#north_inside").uvs(16, 0, 0, 16).cullface(Direction.NORTH).end()
                .end()
                .element().from(0.0F, 0.0F, 15.998F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.NORTH).texture("#south_inside").uvs(16, 0, 0, 16).cullface(Direction.SOUTH).end()
                .face(Direction.SOUTH).texture("#south_outside").uvs(0, 0, 16, 16).cullface(Direction.SOUTH).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(0.002F, 16.0F, 16.0F)
                .face(Direction.WEST).texture("#west_outside").uvs(0, 0, 16, 16).cullface(Direction.WEST).end()
                .face(Direction.EAST).texture("#west_inside").uvs(16, 0, 0, 16).cullface(Direction.WEST).end()
                .end()
                .element().from(15.998F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.WEST).texture("#east_inside").uvs(16, 0, 0, 16).cullface(Direction.EAST).end()
                .face(Direction.EAST).texture("#east_outside").uvs(0, 0, 16, 16).cullface(Direction.EAST).end()
                .end();
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
    }

    public void farmland(Block block, Block dirtBlock) {
        ModelFile farmland = this.models().withExistingParent(this.name(block), this.mcLoc("block/template_farmland"))
                .texture("dirt", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block)));
        ModelFile moist = this.models().withExistingParent(this.name(block) + "_moist", mcLoc("block/template_farmland"))
                .texture("dirt", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block) + "_moist"));
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            int moisture = state.getValue(AetherFarmBlock.MOISTURE);
            return ConfiguredModel.builder()
                    .modelFile(moisture < AetherFarmBlock.MAX_MOISTURE ? farmland : moist)
                    .build();
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void randomBlockDoubleDrops(Block block, String location) {
        this.getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.allYRotations(this.cubeAll(block, location), 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void blockDoubleDrops(Block block, String location) {
        this.getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(this.cubeAll(block, location)).build(), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void enchantedLog(RotatedPillarBlock block, RotatedPillarBlock baseBlock) {
        this.axisBlock(block, this.texture(this.name(block), "natural/"), this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
    }

    public void altar(Block block) {
        ModelFile altar = this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "utility/"), "_side"),
                this.extend(this.texture(this.name(block), "utility/"), "_bottom"),
                this.extend(this.texture(this.name(block), "utility/"), "_bottom"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(altar));
    }

    public void freezer(Block block) {
        ModelFile freezer = this.utility(block);
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(freezer));
    }

    public void incubator(Block block) {
        ModelFile incubator = this.utility(block);
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(incubator));
    }

    public ModelFile utility(Block block) {
        return this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "utility/"), "_side"),
                this.extend(this.texture("altar", "utility/"), "_bottom"),
                this.extend(this.texture(this.name(block), "utility/"), "_top"));
    }

    public void hangingSignBlock(CeilingHangingSignBlock signBlock, WallHangingSignBlock wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    public void berryBush(Block block, Block stem) {
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(this.bush(block, stem)));
    }

    public ModelFile bush(Block block, Block stem) {
        return this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("particle", this.texture(this.name(block), "natural/")).texture("bush", this.texture(this.name(block), "natural/")).texture("stem", this.texture(this.name(stem), "natural/"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).shade(true).allFaces((direction, builder) -> builder.texture("#bush").end()).end()
                .element().from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.NORTH).texture("#stem").end().face(Direction.SOUTH).texture("#stem").end().end()
                .element().from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.WEST).texture("#stem").end().face(Direction.EAST).texture("#stem").end().end()
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
    }

    public BlockModelBuilder pottedStemModel(Block block, Block stem, String location) {
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
                .element().from(7.0F, 4.0F, 8.0F).to(9.0F, 6.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.NORTH).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end()
                .face(Direction.SOUTH).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end().end()
                .element().from(1.0F, 6.0F, 8.0F).to(15.0F, 16.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.NORTH).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end()
                .face(Direction.SOUTH).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end().end()
                .element().from(8.0F, 4.0F, 7.0F).to(8.0F, 6.0F, 9.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.EAST).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end()
                .face(Direction.WEST).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end().end()
                .element().from(8.0F, 6.0F, 1.0F).to(8.0F, 16.0F, 15.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.EAST).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end()
                .face(Direction.WEST).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end().end();
    }

    public void pottedStem(Block stem, String location) {
        ModelFile pot = this.pottedStemModel(stem, stem, location).renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(stem).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedBush(Block bush, Block stem, String location) {
        ModelFile pot = this.pottedStemModel(bush, stem, location)
                .texture("stem", this.modLoc("block/" + location + this.name(stem))).texture("bush", this.modLoc("block/" + location + this.name(bush)))
                .element().from(3.0F, 6.0F, 3.0F).to(13.0F, 16.0F, 13.0F)
                .face(Direction.NORTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.EAST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.SOUTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.WEST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.UP).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.DOWN).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end().end()
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(bush).partialState().addModels(new ConfiguredModel(pot));
    }

    public void dungeonBlock(Block block, Block baseBlock) {
        ConfiguredModel dungeonBlock = new ConfiguredModel(this.models().cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), "dungeon/")));
        this.getVariantBuilder(block).partialState().setModels(dungeonBlock);
    }

    public void invisibleBlock(Block block, Block baseBlock) {
        ModelFile visible = this.models().cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), "dungeon/"));
        ModelFile invisible = this.models().getBuilder(this.name(block));
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            if (!state.getValue(DoorwayBlock.INVISIBLE)) {
                return ConfiguredModel.builder().modelFile(visible).build();
            } else {
                return ConfiguredModel.builder().modelFile(invisible).build();
            }
        });
    }

    public void chestMimic(Block block, Block dummyBlock) {
        ModelFile chest = this.models().cubeAll(this.name(block), this.mcLoc("block/" + this.name(dummyBlock)));
        this.chest(block, chest);
    }

    public void treasureChest(Block block, Block dummyBlock) {
        ModelFile chest = this.models().cubeAll(this.name(block), this.texture(this.name(dummyBlock), "dungeon/"));
        this.chest(block, chest);
    }

    public void pillar(RotatedPillarBlock block) {
        this.axisBlock(block, this.extend(this.texture(this.name(block), "dungeon/"), "_side"), this.extend(this.texture(this.name(block), "dungeon/"), "_top"));
    }

    public void pillarTop(FacingPillarBlock block) {
        ResourceLocation side = this.texture("pillar_carved", "dungeon/");
        ResourceLocation end = this.texture(this.name(block), "dungeon/");
        ModelFile vertical = this.models().cubeColumn(this.name(block), side, end);
        ModelFile horizontal = this.models().cubeColumnHorizontal(this.name(block) + "_horizontal", side, end);
        this.getVariantBuilder(block)
                .partialState().with(FacingPillarBlock.FACING, Direction.DOWN).modelForState().modelFile(vertical).rotationX(180).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.EAST).modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.NORTH).modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.SOUTH).modelForState().modelFile(horizontal).rotationX(90).rotationY(180).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.UP).modelForState().modelFile(vertical).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.WEST).modelForState().modelFile(horizontal).rotationX(90).rotationY(270).addModel();
    }

    public void present(Block block) {
        ModelFile present = this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "miscellaneous/"), "_side"),
                this.extend(this.texture(this.name(block), "miscellaneous/"), "_top"),
                this.extend(this.texture(this.name(block), "miscellaneous/"), "_top"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(present));
    }

    public void sunAltar(Block block) {
        ModelFile sunAltar = this.cubeBottomTop(name(block),
                this.extend(this.texture(this.name(block), "utility/"), "_side"), this.texture("hellfire_stone", "dungeon/"),
                this.extend(this.texture(this.name(block), "utility/"), "_top"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(sunAltar));
    }

    public void bed(Block block, Block dummyBlock) {
        ModelFile head = this.models().cubeAll(this.name(block) + "_head", this.texture(this.name(dummyBlock), "construction/"));
        ModelFile foot = this.models().cubeAll(this.name(block) + "_foot", this.texture(this.name(dummyBlock), "construction/"));
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BedPart part = state.getValue(BlockStateProperties.BED_PART);
            return ConfiguredModel.builder()
                    .modelFile(part == BedPart.HEAD ? head : foot)
                    .rotationY((((int) dir.toYRot()) + 180) % 360)
                    .build();
        }, BedBlock.OCCUPIED);
    }

    public void frostedIce(Block block, Block base) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(AetherFrostedIceBlock.AGE);
            return ConfiguredModel.builder().modelFile(this.models().cubeAll(this.name(block) + "_" + age, this.mcLoc("block/" + this.name(base) + "_" + age)).renderType(ResourceLocation.withDefaultNamespace("translucent"))).build();
        });
    }

    public void unstableObsidian(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(UnstableObsidianBlock.AGE);
            return ConfiguredModel.builder().modelFile(this.models().cubeAll(this.name(block) + "_" + age, this.texture(this.name(block) + "_" + age, "miscellaneous/"))).build();
        });
    }
}
