package com.aetherteam.aether.entity.monster.dungeon.boss.ai;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Optional;

public class Crush extends Behavior<Slider> {
    public Crush() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
        return slider.isAwake() && !slider.isDeadOrDying() && (slider.horizontalCollision || slider.verticalCollision || this.blocksBetween(slider));
    }

    @Override
    protected void start(ServerLevel level, Slider slider, long gameTime) {
        boolean crushed = false;
        if (ForgeEventFactory.getMobGriefingEvent(level, slider)) {
            AABB crushBox = slider.getBoundingBox().inflate(0.2);
            for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(crushBox.minX), Mth.floor(crushBox.minY), Mth.floor(crushBox.minZ), Mth.floor(crushBox.maxX), Mth.floor(crushBox.maxY), Mth.floor(crushBox.maxZ))) {
                if (slider.getDungeon() == null || slider.getDungeon().roomBounds().contains(pos.getCenter())) {
                    BlockState blockState = slider.level.getBlockState(pos);
                    if (this.isBreakable(blockState)) {
                        crushed = slider.level.destroyBlock(pos, true, slider) || crushed;
                        EntityUtil.spawnRemovalParticles(slider.getLevel(), pos);
                    }
                }
            }
        }
        if (crushed) {
            slider.level.playSound(null, slider.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 3.0F, (0.625F + (slider.getRandom().nextFloat() - slider.getRandom().nextFloat()) * 0.2F) * 0.7F);
            slider.playSound(slider.getCollideSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
            slider.getBrain().setMemoryWithExpiry(AetherMemoryModuleTypes.MOVE_DELAY.get(), Unit.INSTANCE, slider.calculateMoveDelay());
            slider.setDeltaMovement(Vec3.ZERO);
        }
    }

    private boolean blocksBetween(Slider slider) {
        Brain<?> brain = slider.getBrain();
        Optional<LivingEntity> attackTarget = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        return attackTarget.filter(livingEntity -> slider.getLevel().getBlockStates(AABB.of(BoundingBox.fromCorners(livingEntity.blockPosition(), slider.blockPosition()))).anyMatch(this::isBreakable)).isPresent();
    }

    private boolean isBreakable(BlockState blockState) {
        return !blockState.isAir() && !blockState.is(AetherTags.Blocks.SLIDER_UNBREAKABLE);
    }
}
