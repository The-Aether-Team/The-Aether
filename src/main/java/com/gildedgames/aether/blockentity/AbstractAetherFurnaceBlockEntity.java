package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.mixin.mixins.accessor.AbstractFurnaceBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractAetherFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected ItemStack containerItem = ItemStack.EMPTY;

    public AbstractAetherFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(type, pos, state, recipeType);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractAetherFurnaceBlockEntity blockEntity) {
        AbstractFurnaceBlockEntityAccessor abstractFurnaceBlockEntityAccessor = (AbstractFurnaceBlockEntityAccessor) blockEntity;
        boolean flag = abstractFurnaceBlockEntityAccessor.callIsLit();
        boolean flag1 = false;

        if (abstractFurnaceBlockEntityAccessor.callIsLit()) {
            abstractFurnaceBlockEntityAccessor.setLitTime(abstractFurnaceBlockEntityAccessor.getLitTime() - 1);
        }

        ItemStack itemstack = abstractFurnaceBlockEntityAccessor.getItems().get(1);
        boolean flag2 = !abstractFurnaceBlockEntityAccessor.getItems().get(0).isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (abstractFurnaceBlockEntityAccessor.callIsLit() || flag3 && flag2) {
            Recipe<?> recipe;
            if (flag2) {
                recipe = abstractFurnaceBlockEntityAccessor.getQuickCheck().getRecipeFor(blockEntity, level).orElse(null);
            } else {
                recipe = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!abstractFurnaceBlockEntityAccessor.callIsLit() && abstractFurnaceBlockEntityAccessor.callCanBurn(recipe, abstractFurnaceBlockEntityAccessor.getItems(), i)) {
                abstractFurnaceBlockEntityAccessor.setLitTime(abstractFurnaceBlockEntityAccessor.callGetBurnDuration(itemstack));
                abstractFurnaceBlockEntityAccessor.setLitDuration(abstractFurnaceBlockEntityAccessor.getLitTime());
                if (abstractFurnaceBlockEntityAccessor.callIsLit()) {
                    flag1 = true;
                    if (itemstack.hasCraftingRemainingItem())
                        abstractFurnaceBlockEntityAccessor.getItems().set(1, itemstack.getCraftingRemainingItem());
                    else
                    if (flag3) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            abstractFurnaceBlockEntityAccessor.getItems().set(1, itemstack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (abstractFurnaceBlockEntityAccessor.callIsLit() && abstractFurnaceBlockEntityAccessor.callCanBurn(recipe, abstractFurnaceBlockEntityAccessor.getItems(), i)) {
                abstractFurnaceBlockEntityAccessor.setCookingProgress(abstractFurnaceBlockEntityAccessor.getCookingProgress() + 1);
                if (abstractFurnaceBlockEntityAccessor.getCookingProgress() == abstractFurnaceBlockEntityAccessor.getCookingTotalTime()) {
                    abstractFurnaceBlockEntityAccessor.setCookingProgress(0);
                    abstractFurnaceBlockEntityAccessor.setCookingTotalTime(AbstractFurnaceBlockEntityAccessor.callGetTotalCookTime(level, blockEntity));
                    if (blockEntity.burn(recipe, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            } else {
                abstractFurnaceBlockEntityAccessor.setCookingProgress(0);
            }
        } else if (!abstractFurnaceBlockEntityAccessor.callIsLit() && abstractFurnaceBlockEntityAccessor.getCookingProgress() > 0) {
            abstractFurnaceBlockEntityAccessor.setCookingProgress(Mth.clamp(abstractFurnaceBlockEntityAccessor.getCookingProgress() - 2, 0, abstractFurnaceBlockEntityAccessor.getCookingTotalTime()));
        }

        if (flag != abstractFurnaceBlockEntityAccessor.callIsLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, abstractFurnaceBlockEntityAccessor.callIsLit());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }

        if (abstractFurnaceBlockEntityAccessor.getItems().get(0).isEmpty() && abstractFurnaceBlockEntityAccessor.getItems().get(2).isEmpty()) {
            blockEntity.containerItem = ItemStack.EMPTY;
        }
    }

    private boolean burn(@Nullable Recipe<?> recipe, NonNullList<ItemStack> stacks, int stackSize) {
        AbstractFurnaceBlockEntityAccessor abstractFurnaceBlockEntityAccessor = (AbstractFurnaceBlockEntityAccessor) this;
        if (recipe != null && abstractFurnaceBlockEntityAccessor.callCanBurn(recipe, stacks, stackSize)) {
            ItemStack inputSlotStack = stacks.get(0);
            ItemStack resultStack = ((Recipe<WorldlyContainer>) recipe).assemble(this);
            ItemStack resultSlotStack = stacks.get(2);

            if (inputSlotStack.is(resultStack.getItem()) || resultStack.is(AetherTags.Items.SAVE_NBT_IN_RECIPE)) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(inputSlotStack), resultStack);
                if (inputSlotStack.hasTag()) {
                    resultStack.setTag(inputSlotStack.getTag());
                }
            }
            if (inputSlotStack.is(resultStack.getItem())) {
                resultStack.setDamageValue(0);
            }

            if (resultSlotStack.isEmpty()) {
                stacks.set(2, resultStack.copy());
            } else if (resultSlotStack.is(resultStack.getItem())) {
                resultSlotStack.grow(resultStack.getCount());
            }

            if (inputSlotStack.hasCraftingRemainingItem()) {
                stacks.set(0, inputSlotStack.getCraftingRemainingItem());
            } else {
                inputSlotStack.shrink(1);
            }
            return true;
        } else {
            return false;
        }
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
        if (direction == Direction.DOWN && index == 0) {
            if (!this.containerItem.isEmpty()) {
                return stack.is(this.containerItem.getItem());
            } else {
                return false;
            }
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
