package com.gildedgames.aether.block.entity;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class AbstractAetherFurnaceBlockEntity extends AbstractFurnaceBlockEntity
{
    public AbstractAetherFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(type, pos, state, recipeType);
    }

    @Override
    public boolean canPlaceItem(int p_94041_1_, @Nonnull ItemStack stack) {
        if (p_94041_1_ == 2) {
            return false;
        } else if (p_94041_1_ != 1) {
            return true;
        } else {
            return this.getBurnDuration(stack) > 0;
        }
    }

    public void awardUsedRecipesAndPopExperience(@Nonnull ServerPlayer player) { }

    @Nonnull
    public List<Recipe<?>> getRecipesToAwardAndPopExperience(@Nonnull ServerLevel level, @Nonnull Vec3 experiencePos) {
        return Lists.newArrayList();
    }
}
