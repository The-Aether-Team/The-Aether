package com.gildedgames.aether.entity.ai.goal.target;

import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.entity.BossMob;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class InBossRoomTargetGoal<T extends LivingEntity, J extends Mob & BossMob<J>> extends TargetGoal {
    protected final Class<T> targetType;
    @Nullable
    protected LivingEntity target;

    public InBossRoomTargetGoal(J mob, Class<T> targetType) {
        this(mob, targetType, false, false);
    }

    public InBossRoomTargetGoal(J mob, Class<T> targetType, boolean mustSee) {
        this(mob, targetType, mustSee, false);
    }

    public InBossRoomTargetGoal(J mob, Class<T> targetType, boolean mustSee, boolean mustReach) {
        super(mob, mustSee, mustReach);
        this.targetType = targetType;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        this.findTarget();
        return this.target != null;
    }

    protected void findTarget() {
        DungeonTracker<J> dungeon = ((J) this.mob).getDungeon();
        if (dungeon != null && (this.targetType == Player.class || this.targetType == ServerPlayer.class) && !dungeon.dungeonPlayers().isEmpty()) {
            UUID uuid = dungeon.dungeonPlayers().get(this.mob.random.nextInt(dungeon.dungeonPlayers().size()));
            Player player = this.mob.level.getPlayerByUUID(uuid);
            if (player != null && !player.isSpectator() && !player.isCreative()) {
                this.target = player;
            } else {
                this.target = null;
            }
        } else {
            this.target = null;
        }
    }

    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        this.target = pTarget;
    }
}
