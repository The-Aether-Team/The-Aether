package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityAccessor {
    @Accessor
    RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> getQuickCheck();

    @Invoker
    boolean callCanBurn(@Nullable Recipe<?> recipe, NonNullList<ItemStack> stacks, int stackSize);

    @Accessor
    int getLitTime();

    @Accessor
    void setLitTime(int litTime);

    @Accessor
    void setLitDuration(int litDuration);

    @Accessor
    int getCookingProgress();

    @Accessor
    void setCookingProgress(int cookingProgress);

    @Accessor
    int getCookingTotalTime();

    @Accessor
    void setCookingTotalTime(int cookingTotalTime);

    @Invoker
    boolean callIsLit();

    @Invoker
    static int callGetTotalCookTime(Level level, AbstractFurnaceBlockEntity blockEntity) {
        throw new AssertionError();
    }

    @Accessor
    NonNullList<ItemStack> getItems();

    @Accessor
    void setItems(NonNullList<ItemStack> items);

    @Invoker
    int callGetBurnDuration(ItemStack pFuel);
}