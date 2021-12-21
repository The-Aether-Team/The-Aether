package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fox.FoxEatBerriesGoal.class)
public abstract class EatBerriesGoalMixin extends MoveToBlockGoal
{
    protected EatBerriesGoalMixin(PathfinderMob entity, double p_i45888_2_, int p_i45888_4_) {
        super(entity, p_i45888_2_, p_i45888_4_);
    }

    @Inject(at = @At("HEAD"), method = "isValidTarget", cancellable = true)
    private void isValidTarget(LevelReader worldReader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = worldReader.getBlockState(pos);
        if (blockstate.is(AetherBlocks.BERRY_BUSH.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "onReachedTarget", cancellable = true)
    private void onReachedTarget(CallbackInfo ci) {
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.mob.level, this.mob)) {
            BlockState blockstate = this.mob.level.getBlockState(this.blockPos);
            if (blockstate.is(AetherBlocks.BERRY_BUSH.get())) {
                boolean flag = this.mob.level.getBlockState(this.blockPos.below()).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
                int j = 1 + this.mob.level.random.nextInt(3) + (flag ? 1 : 0);
                ItemStack itemstack = this.mob.getItemBySlot(EquipmentSlot.MAINHAND);
                if (itemstack.isEmpty()) {
                    this.mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AetherItems.BLUE_BERRY.get()));
                    --j;
                }

                if (j > 0) {
                    Block.popResource(this.mob.level, this.blockPos, new ItemStack(AetherItems.BLUE_BERRY.get(), j));
                }

                this.mob.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
                this.mob.level.setBlock(this.blockPos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState(), 2);
            }
        }
    }
}
