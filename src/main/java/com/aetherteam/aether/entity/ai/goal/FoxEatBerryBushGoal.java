package com.aetherteam.aether.entity.ai.goal;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;

public class FoxEatBerryBushGoal extends MoveToBlockGoal {
    private final Fox fox;
    private int ticksWaited;

    public FoxEatBerryBushGoal(Fox fox, double speedModifier, int searchRange, int verticalSearchRange) {
        super(fox, speedModifier, searchRange, verticalSearchRange);
        this.fox = fox;
    }

    @Override
    public boolean canUse() {
        return !this.fox.isSleeping() && super.canUse();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        return blockState.is(AetherBlocks.BERRY_BUSH.get());
    }

    @Override
    public void start() {
        this.ticksWaited = 0;
        this.fox.setSitting(false);
        super.start();
    }

    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            if (this.ticksWaited >= 40) {
                this.onReachedTarget();
            } else {
                ++this.ticksWaited;
            }
        } else if (!this.isReachedTarget() && this.fox.getRandom().nextFloat() < 0.05F) {
            this.fox.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
        }
        super.tick();
    }

    protected void onReachedTarget() {
        if (ForgeEventFactory.getMobGriefingEvent(this.fox.getLevel(), this.fox)) {
            BlockState blockState = this.mob.getLevel().getBlockState(this.blockPos);
            if (blockState.is(AetherBlocks.BERRY_BUSH.get())) {
                this.pickBlueBerries();
            }
        }
    }

    private void pickBlueBerries() {
        boolean onEnchantedGrass = this.mob.getLevel().getBlockState(this.blockPos.below()).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
        int j = 1 + this.mob.getLevel().getRandom().nextInt(3) + (onEnchantedGrass ? 1 : 0);
        ItemStack itemStack = this.mob.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty()) {
            this.mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AetherItems.BLUE_BERRY.get()));
            --j;
        }
        if (j > 0) {
            Block.popResource(this.mob.getLevel(), this.blockPos, new ItemStack(AetherItems.BLUE_BERRY.get(), j));
        }
        this.mob.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        this.mob.getLevel().setBlock(this.blockPos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState(), 2);
    }

    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 100 == 0;
    }
}
