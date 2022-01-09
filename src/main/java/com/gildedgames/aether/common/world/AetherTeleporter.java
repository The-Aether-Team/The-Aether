package com.gildedgames.aether.common.world;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.block.miscellaneous.AetherPortalBlock;
import com.gildedgames.aether.common.registry.AetherPOI;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.PortalTravelSoundPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;

public class AetherTeleporter implements ITeleporter
{
    protected final ServerLevel world;
    private boolean hasFrame; //Whether to generate a portal frame or not.

    public AetherTeleporter(ServerLevel worldIn, boolean isFrame) {
        this.world = worldIn;
        this.hasFrame = isFrame;
    }

    public Optional<BlockUtil.FoundRectangle> getExistingPortal(BlockPos pos) {
        PoiManager poiManager = this.world.getPoiManager();
        poiManager.ensureLoadedAndValid(this.world, pos, 128);
        Optional<PoiRecord> optional = poiManager.getInSquare((poiType) ->
                poiType == AetherPOI.AETHER_PORTAL.get(), pos, 128, PoiManager.Occupancy.ANY).sorted(Comparator.<PoiRecord>comparingDouble((poi) ->
                poi.getPos().distSqr(pos)).thenComparingInt((poi) ->
                poi.getPos().getY())).filter((poi) ->
                this.world.getBlockState(poi.getPos()).hasProperty(BlockStateProperties.HORIZONTAL_AXIS)).findFirst();
        return optional.map((poi) -> {
            BlockPos blockpos = poi.getPos();
            this.world.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
            BlockState blockstate = this.world.getBlockState(blockpos);
            return BlockUtil.getLargestRectangleAround(blockpos, blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, (posIn) ->
                    this.world.getBlockState(posIn) == blockstate);
        });
    }

    public Optional<BlockUtil.FoundRectangle> makePortal(BlockPos pos, Direction.Axis axis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d0 = -1.0D;
        BlockPos blockpos = null;
        double d1 = -1.0D;
        BlockPos blockpos1 = null;
        WorldBorder worldborder = this.world.getWorldBorder();
        int dimensionLogicalHeight = this.world.getHeight() - 1;
        BlockPos.MutableBlockPos mutablePos = pos.mutable();

        for (BlockPos.MutableBlockPos blockpos$mutable1 : BlockPos.spiralAround(pos, 16, Direction.EAST, Direction.SOUTH)) {
            int j = Math.min(dimensionLogicalHeight, this.world.getHeight(Heightmap.Types.MOTION_BLOCKING, blockpos$mutable1.getX(), blockpos$mutable1.getZ()));
            if (worldborder.isWithinBounds(blockpos$mutable1) && worldborder.isWithinBounds(blockpos$mutable1.move(direction, 1))) {
                blockpos$mutable1.move(direction.getOpposite(), 1);

                for (int l = j; l >= 0; --l) {
                    blockpos$mutable1.setY(l);
                    if (this.world.isEmptyBlock(blockpos$mutable1)) {
                        int i1;
                        for (i1 = l; l > 0 && this.world.isEmptyBlock(blockpos$mutable1.move(Direction.DOWN)); --l) {
                        }

                        if (l + 4 <= dimensionLogicalHeight) {
                            int j1 = i1 - l;
                            if (j1 <= 0 || j1 >= 3) {
                                blockpos$mutable1.setY(l);
                                if (this.checkRegionForPlacement(blockpos$mutable1, mutablePos, direction, 0)) {
                                    double d2 = pos.distSqr(blockpos$mutable1);
                                    if (this.checkRegionForPlacement(blockpos$mutable1, mutablePos, direction, -1) && this.checkRegionForPlacement(blockpos$mutable1, mutablePos, direction, 1) && (d0 == -1.0D || d0 > d2)) {
                                        d0 = d2;
                                        blockpos = blockpos$mutable1.immutable();
                                    }

                                    if (d0 == -1.0D && (d1 == -1.0D || d1 > d2)) {
                                        d1 = d2;
                                        blockpos1 = blockpos$mutable1.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (d0 == -1.0D && d1 != -1.0D) {
            blockpos = blockpos1;
            d0 = d1;
        }

        if (d0 == -1.0D) {
            blockpos = (new BlockPos(pos.getX(), Mth.clamp(pos.getY(), 70, this.world.getHeight() - 10), pos.getZ())).immutable();
            Direction direction1 = direction.getClockWise();
            if (!worldborder.isWithinBounds(blockpos)) {
                return Optional.empty();
            }

            for (int l1 = -1; l1 < 2; ++l1) {
                for (int k2 = 0; k2 < 2; ++k2) {
                    for (int i3 = -1; i3 < 3; ++i3) {
                        BlockState blockstate1 = i3 < 0 ? Blocks.GLOWSTONE.defaultBlockState() : Blocks.AIR.defaultBlockState();
                        mutablePos.setWithOffset(blockpos, k2 * direction.getStepX() + l1 * direction1.getStepX(), i3, k2 * direction.getStepZ() + l1 * direction1.getStepZ());
                        this.world.setBlockAndUpdate(mutablePos, blockstate1);
                    }
                }
            }
        }

        for (int k1 = -1; k1 < 3; ++k1) {
            for (int i2 = -1; i2 < 4; ++i2) {
                if (k1 == -1 || k1 == 2 || i2 == -1 || i2 == 3) {
                    mutablePos.setWithOffset(blockpos, k1 * direction.getStepX(), i2, k1 * direction.getStepZ());
                    this.world.setBlock(mutablePos, Blocks.GLOWSTONE.defaultBlockState(), 3);
                }
            }
        }

        BlockState aetherPortal = AetherBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(AetherPortalBlock.AXIS, axis);

        for (int j2 = 0; j2 < 2; ++j2) {
            for (int l2 = 0; l2 < 3; ++l2) {
                mutablePos.setWithOffset(blockpos, j2 * direction.getStepX(), l2, j2 * direction.getStepZ());
                this.world.setBlock(mutablePos, aetherPortal, 18);
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(blockpos.immutable(), 2, 3));
    }

    private boolean checkRegionForPlacement(BlockPos originalPos, BlockPos.MutableBlockPos offsetPos, Direction directionIn, int offsetScale) {
        Direction direction = directionIn.getClockWise();

        for (int i = -1; i < 3; ++i) {
            for (int j = -1; j < 4; ++j) {
                offsetPos.setWithOffset(originalPos, directionIn.getStepX() * i + direction.getStepX() * offsetScale, j, directionIn.getStepZ() * i + direction.getStepZ() * offsetScale);
                if (j < 0 && !this.world.getBlockState(offsetPos).getMaterial().isSolid()) {
                    return false;
                }

                if (j >= 0 && !this.world.isEmptyBlock(offsetPos)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        boolean isAether = destWorld.dimension() == AetherDimensions.AETHER_WORLD;
        if (entity.level.dimension() != AetherDimensions.AETHER_WORLD && !isAether) {
            return null;
        }
        else if(!this.hasFrame) {
            return new PortalInfo(new Vec3(entity.getX(), destWorld.getHeight(), entity.getZ()), Vec3.ZERO, entity.getYRot(), entity.getXRot()); //For falling out of the Aether
        }
        else {
            WorldBorder border = destWorld.getWorldBorder();
            double minX = Math.max(-2.9999872E7D, border.getMinX() + 16.0D);
            double minZ = Math.max(-2.9999872E7D, border.getMinZ() + 16.0D);
            double maxX = Math.min(2.9999872E7D, border.getMaxX() - 16.0D);
            double maxZ = Math.min(2.9999872E7D, border.getMaxZ() - 16.0D);
            double coordinateDifference = DimensionType.getTeleportationScale(entity.level.dimensionType(), destWorld.dimensionType());
            BlockPos blockpos = new BlockPos(Mth.clamp(entity.getX() * coordinateDifference, minX, maxX), entity.getY(), Mth.clamp(entity.getZ() * coordinateDifference, minZ, maxZ));
            return this.getOrMakePortal(entity, blockpos).map((result) -> {
                BlockState blockstate = entity.level.getBlockState(entity.portalEntrancePos);
                Direction.Axis axis;
                Vec3 vector3d;
                if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                    axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                    BlockUtil.FoundRectangle rectangle = BlockUtil.getLargestRectangleAround(entity.portalEntrancePos, axis, 21, Direction.Axis.Y, 21, (pos) -> entity.level.getBlockState(pos) == blockstate);
                    vector3d = entity.getRelativePortalPosition(axis, rectangle);
                } else {
                    axis = Direction.Axis.X;
                    vector3d = new Vec3(0.5D, 0.0D, 0.0D);
                }

                return PortalShape.createPortalInfo(destWorld, result, axis, vector3d, entity.getDimensions(entity.getPose()), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
            }).orElse(null);
        }
    }

    protected Optional<BlockUtil.FoundRectangle> getOrMakePortal(Entity entity, BlockPos pos) {
        Optional<BlockUtil.FoundRectangle> existingPortal = this.getExistingPortal(pos);
        if (existingPortal.isPresent()) {
            return existingPortal;
        }
        else {
            Direction.Axis portalAxis = this.world.getBlockState(entity.portalEntrancePos).getOptionalValue(AetherPortalBlock.AXIS).orElse(Direction.Axis.X);
            Optional<BlockUtil.FoundRectangle> makePortal = this.makePortal(pos, portalAxis);
            if (!makePortal.isPresent()) {
                //Uh oh!
            }

            return makePortal;
        }
    }

    @Override
    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
        if (this.hasFrame) {
            AetherPacketHandler.sendToPlayer(new PortalTravelSoundPacket(), player);
        }
        return false;
    }
}