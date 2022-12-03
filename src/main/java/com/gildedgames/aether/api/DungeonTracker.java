package com.gildedgames.aether.api;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.util.ConstantsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public record DungeonTracker<T extends Mob & BossMob<T>>(T boss, Vec3 originCoordinates, AABB roomBounds, List<UUID> dungeonPlayers) {

    //this is a backup if for some reason the boss isnt spawned with a dungeon bounds. might just be used for debugging as well and will be removed.
    public static <T extends Mob & BossMob<T>> void createDebugDungeon(T boss) {
        DungeonTracker<T> dungeon = new DungeonTracker<>(
                boss,
                boss.position(),
                new AABB(boss.position().x() - 7, boss.position().y() - 1, boss.position().z() - 7,
                        boss.position().x() + 7, boss.position().y() + 7, boss.position().z() + 7),
                new ArrayList<>()
        );
        boss.setDungeon(dungeon);
    }

    //debugging
    public void debugBounds() {
        for (int x = (int) Math.floor(this.roomBounds().minX); x <= Math.ceil(this.roomBounds().maxX); x++) {
            for (int y = (int) Math.floor(this.roomBounds().minY); y <= Math.ceil(this.roomBounds().maxY); y++) {
                for (int z = (int) Math.floor(this.roomBounds().minZ); z <= Math.ceil(this.roomBounds().maxZ); z++) {
                    if (x == Math.floor(this.roomBounds().minX) || x == Math.ceil(this.roomBounds().maxX)
                            || y == Math.floor(this.roomBounds().minY) || y == Math.ceil(this.roomBounds().maxY)
                            || z == Math.floor(this.roomBounds().minZ) || z == Math.ceil(this.roomBounds().maxZ)) {
                        this.boss().getLevel().setBlockAndUpdate(new BlockPos(x, y, z), AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get().defaultBlockState());
                    }
                }
            }
        }
    }

    public boolean isBossWithinRoom() {
        return this.roomBounds().contains(this.boss().position());
    }

    public boolean isPlayerWithinRoom(Player player) {
        return this.dungeonPlayers().contains(player.getUUID());
    }

    public void trackPlayers() {
        this.boss().getLevel().getEntities(EntityType.PLAYER, this.roomBounds(), Entity::isAlive).forEach(player -> {
            if (!isPlayerWithinRoom(player)) {
                this.boss().onDungeonPlayerAdded(player);
                this.dungeonPlayers().add(player.getUUID());
            }
        });
        this.dungeonPlayers().removeIf(uuid -> {
            Player player = this.boss().getLevel().getPlayerByUUID(uuid);
            boolean shouldRemove = player != null && (!this.roomBounds().contains(player.position()) || !player.isAlive());
            if (shouldRemove) {
                this.boss().onDungeonPlayerRemoved(player);
            }
            return shouldRemove;
        });
    }

    public void grantAdvancements(@Nonnull DamageSource damageSource) {
        for (UUID uuid : this.dungeonPlayers()) {
            Player player = this.boss().getLevel().getPlayerByUUID(uuid);
            if (player != null) {
                player.awardKillScore(this.boss(), this.boss().getDeathScore(), damageSource);
            }
        }
    }

    /**
     * Iterates on every block within the bounds of the dungeon
     */
    public void modifyRoom(Function<BlockState, BlockState> function) {
        AABB bounds = this.roomBounds();
        Level level = this.boss().getLevel();
        for (BlockPos pos : BlockPos.betweenClosed((int) bounds.minX, (int) bounds.minY, (int) bounds.minZ, (int) bounds.maxX, (int) bounds.maxY, (int) bounds.maxZ)) {
            BlockState state = level.getBlockState(pos);
            BlockState newState = function.apply(state);
            if (newState != null) {
                level.setBlock(pos, newState, 1 | 2);
            }
        }
    }

    public CompoundTag addAdditionalSaveData() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("OriginX", this.originCoordinates().x());
        tag.putDouble("OriginY", this.originCoordinates().y());
        tag.putDouble("OriginZ", this.originCoordinates().z());

        tag.putDouble("RoomBoundsMinX", this.roomBounds().minX);
        tag.putDouble("RoomBoundsMinY", this.roomBounds().minY);
        tag.putDouble("RoomBoundsMinZ", this.roomBounds().minZ);
        tag.putDouble("RoomBoundsMaxX", this.roomBounds().maxX);
        tag.putDouble("RoomBoundsMaxY", this.roomBounds().maxY);
        tag.putDouble("RoomBoundsMaxZ", this.roomBounds().maxZ);

        tag.putInt("DungeonPlayersSize", this.dungeonPlayers().size());
        for (int i = 0; i < this.dungeonPlayers().size(); i++) {
            tag.putUUID("Player" + i, this.dungeonPlayers().get(i));
        }
        return tag;
    }

    public static <T extends Mob & BossMob<T>> DungeonTracker<T> readAdditionalSaveData(@Nonnull CompoundTag tag, T boss) {
        double originX = tag.getDouble("OriginX");
        double originY = tag.getDouble("OriginY");
        double originZ = tag.getDouble("OriginZ");
        Vec3 originCoordinates = new Vec3(originX, originY, originZ);

        double minX = tag.getDouble("RoomBoundsMinX");
        double minY = tag.getDouble("RoomBoundsMinY");
        double minZ = tag.getDouble("RoomBoundsMinZ");
        double maxX = tag.getDouble("RoomBoundsMaxX");
        double maxY = tag.getDouble("RoomBoundsMaxY");
        double maxZ = tag.getDouble("RoomBoundsMaxZ");
        AABB roomBounds = new AABB(minX, minY, minZ, maxX, maxY, maxZ);

        List<UUID> dungeonPlayers = new ArrayList<>();
        int size = tag.getInt("DungeonPlayersSize");
        for (int i = 0; i < size; i++) {
            UUID uuid = tag.getUUID("Player" + i);
            dungeonPlayers.add(uuid);
        }

        return new DungeonTracker<>(boss, originCoordinates, roomBounds, dungeonPlayers);
    }
}
