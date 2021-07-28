package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxEntity.EatBerriesGoal.class)
public abstract class EatBerriesGoalMixin extends MoveToBlockGoal
{
    protected EatBerriesGoalMixin(CreatureEntity entity, double p_i45888_2_, int p_i45888_4_) {
        super(entity, p_i45888_2_, p_i45888_4_);
    }

    @Inject(at = @At("HEAD"), method = "isValidTarget", cancellable = true)
    private void isValidTarget(IWorldReader worldReader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
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
                ItemStack itemstack = this.mob.getItemBySlot(EquipmentSlotType.MAINHAND);
                if (itemstack.isEmpty()) {
                    this.mob.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(AetherItems.BLUE_BERRY.get()));
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