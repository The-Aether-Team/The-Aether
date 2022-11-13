package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.mixin.mixins.accessor.AbstractFurnaceBlockEntityAccessor;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractAetherFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private ItemStack containerItem = ItemStack.EMPTY;

    public AbstractAetherFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(type, pos, state, recipeType);
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItem(int index, @Nonnull ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            return this.getBurnDuration(stack) > 0;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        AbstractFurnaceBlockEntityAccessor abstractFurnaceBlockEntityAccessor = (AbstractFurnaceBlockEntityAccessor) this;
        Optional<NonNullList<Ingredient>> ingredient = abstractFurnaceBlockEntityAccessor.getQuickCheck().getRecipeFor(this, this.level).map(AbstractCookingRecipe::getIngredients);
        if (this.containerItem.isEmpty()) {
            ingredient.ifPresent(ing -> this.containerItem = stack.getCraftingRemainingItem());
        }
        if (direction == Direction.DOWN && index == 0 && !this.containerItem.isEmpty()) {
            boolean flag = stack.is(this.containerItem.getItem());
            if (ingredient.isEmpty()) {
                this.containerItem = ItemStack.EMPTY;
            }
            return flag;
        } else {
            return true;
        }
    }

    @Override
    public void awardUsedRecipesAndPopExperience(@Nonnull ServerPlayer player) { }

    @Nonnull
    @Override
    public List<Recipe<?>> getRecipesToAwardAndPopExperience(@Nonnull ServerLevel level, @Nonnull Vec3 experiencePos) {
        return new ArrayList<>();
    }
}
