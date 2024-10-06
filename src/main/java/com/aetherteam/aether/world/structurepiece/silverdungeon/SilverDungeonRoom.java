package com.aetherteam.aether.world.structurepiece.silverdungeon;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * Different types of rooms in the Silver Dungeon.
 */
public class SilverDungeonRoom extends SilverDungeonPiece {
    public SilverDungeonRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        super(AetherStructurePieceTypes.SILVER_DUNGEON_ROOM.get(), manager, name, SilverDungeonRoom.makeSettings(manager, rotation, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "silver_dungeon/" + name)), pos, processors);
        this.setOrientation(rotation.rotate(Direction.SOUTH));
    }

    public SilverDungeonRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_DUNGEON_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), id -> SilverDungeonRoom.makeSettings(context.structureTemplateManager(), id));
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager manager, Rotation rotation, ResourceLocation id) {
        return SilverDungeonRoom.makeSettings(manager, id).setRotation(rotation);
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager manager, ResourceLocation id) {
        StructureTemplate template = manager.getOrCreate(id);
        BlockPos pivot = new BlockPos(template.getSize().getX() / 2 - 4, 0, template.getSize().getZ() / 2 - 4);
        return new StructurePlaceSettings()
                .setRotationPivot(pivot);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        if (name.equals("Chest")) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            BlockPos.MutableBlockPos chestPos = pos.mutable();
            chestPos.set(this.boundingBox.minX() + random.nextInt(this.boundingBox.getXSpan()), pos.getY(), this.boundingBox.minZ() + random.nextInt(this.boundingBox.getZSpan()));
            // If the random position is outside the chunk, bring it back to the starting spot.
            this.placeChestOrMimic(level, box, random, box.isInside(chestPos) ? chestPos : pos);
        }
    }

    private void placeChestOrMimic(ServerLevelAccessor level, BoundingBox generatingChunk, RandomSource random, BlockPos pos) {
        BlockState state = (random.nextInt(5) > 1 ? Blocks.CHEST : AetherBlocks.CHEST_MIMIC.get()).defaultBlockState();
        Direction facing = Direction.from2DDataValue(random.nextInt(4));
        state.setValue(HorizontalDirectionalBlock.FACING, facing);
        this.createChest(level, generatingChunk, random, pos, AetherLoot.SILVER_DUNGEON, state);
    }
}
