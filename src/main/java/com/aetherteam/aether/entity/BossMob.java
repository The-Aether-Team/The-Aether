package com.aetherteam.aether.entity;

import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Interface for applying names to Aether bosses without displaying a name tag.
 * This also handles dungeon tracking.
 */
public interface BossMob<T extends Mob & BossMob<T>> {
    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }
    TargetingConditions NON_COMBAT = TargetingConditions.forNonCombat();
    Component getBossName();
    void setBossName(Component component);

    boolean isBossFight();
    void setBossFight(boolean isFighting);

    BossRoomTracker<T> getDungeon();
    void setDungeon(BossRoomTracker<T> dungeon);

    int getDeathScore();

    default void trackDungeon() {
        if (this.getDungeon() != null) {
            this.getDungeon().trackPlayers();
            if (this.isBossFight() && (this.getDungeon().dungeonPlayers().isEmpty() || !this.getDungeon().isBossWithinRoom())) {
                this.reset();
            }
        }
    }

    default void displayTooFarMessage(Player player) {
        player.sendSystemMessage(Component.translatable("gui.aether.boss.message.far"));
    }

    default void onDungeonPlayerAdded(@Nullable Player player) { }

    default void onDungeonPlayerRemoved(@Nullable Player player) { }

    void reset();

    /**
     * Called when the boss is defeated to change all blocks to unlocked blocks.
     */
    default void tearDownRoom() {
        this.getDungeon().modifyRoom(this::convertBlock);
    }

    default void closeRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof DoorwayBlock) {
                return state.setValue(DoorwayBlock.INVISIBLE, false);
            } else {
                return null;
            }
        });
    }

    default void openRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof DoorwayBlock) {
                return state.setValue(DoorwayBlock.INVISIBLE, true);
            } else {
                return null;
            }
        });
    }

    @Nullable
    BlockState convertBlock(BlockState state);

    default void addBossSaveData(CompoundTag tag) {
        tag.putString("BossName", Component.Serializer.toJson(this.getBossName()));
        tag.putBoolean("BossFight", this.isBossFight());
        if (this.getDungeon() != null) {
            tag.put("Dungeon", this.getDungeon().addAdditionalSaveData());
        }
    }

    default void readBossSaveData(CompoundTag tag) {
        if (tag.contains("BossName")) {
            Component name = Component.Serializer.fromJson(tag.getString("BossName"));
            if (name != null) {
                this.setBossName(name);
            }
        }
        if (tag.contains("BossFight")) {
            this.setBossFight(tag.getBoolean("BossFight"));
        }
        if (tag.contains("Dungeon") && tag.get("Dungeon") instanceof CompoundTag dungeonTag) {
            this.setDungeon(BossRoomTracker.readAdditionalSaveData(dungeonTag, self()));
        }
    }
}
