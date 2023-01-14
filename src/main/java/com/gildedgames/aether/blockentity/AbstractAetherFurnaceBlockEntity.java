package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.mixin.mixins.common.accessor.AbstractFurnaceBlockEntityAccessor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Based on {@link AbstractFurnaceBlockEntity}.
 */
public abstract class AbstractAetherFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected ItemStack remainderItem = ItemStack.EMPTY;

    public AbstractAetherFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(type, pos, state, recipeType);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractAetherFurnaceBlockEntity blockEntity) {
        AbstractFurnaceBlockEntityAccessor abstractFurnaceBlockEntityAccessor = (AbstractFurnaceBlockEntityAccessor) blockEntity;
        boolean flag = abstractFurnaceBlockEntityAccessor.callIsLit();
        boolean flag1 = false;

        if (abstractFurnaceBlockEntityAccessor.callIsLit()) {
            abstractFurnaceBlockEntityAccessor.aether$setLitTime(abstractFurnaceBlockEntityAccessor.aether$getLitTime() - 1);
        }

        ItemStack itemstack = abstractFurnaceBlockEntityAccessor.aether$getItems().get(1);
        boolean flag2 = !abstractFurnaceBlockEntityAccessor.aether$getItems().get(0).isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (abstractFurnaceBlockEntityAccessor.callIsLit() || flag3 && flag2) {
            Recipe<?> recipe;
            if (flag2) {
                recipe = abstractFurnaceBlockEntityAccessor.aether$getQuickCheck().getRecipeFor(blockEntity, level).orElse(null);
            } else {
                recipe = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!abstractFurnaceBlockEntityAccessor.callIsLit() && abstractFurnaceBlockEntityAccessor.callCanBurn(recipe, abstractFurnaceBlockEntityAccessor.aether$getItems(), i)) {
                abstractFurnaceBlockEntityAccessor.aether$setLitTime(abstractFurnaceBlockEntityAccessor.callGetBurnDuration(itemstack));
                abstractFurnaceBlockEntityAccessor.aether$setLitDuration(abstractFurnaceBlockEntityAccessor.aether$getLitTime());
                if (abstractFurnaceBlockEntityAccessor.callIsLit()) {
                    flag1 = true;
                    if (itemstack.hasCraftingRemainingItem())
                        abstractFurnaceBlockEntityAccessor.aether$getItems().set(1, itemstack.getCraftingRemainingItem());
                    else
                    if (flag3) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            abstractFurnaceBlockEntityAccessor.aether$getItems().set(1, itemstack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (abstractFurnaceBlockEntityAccessor.callIsLit() && abstractFurnaceBlockEntityAccessor.callCanBurn(recipe, abstractFurnaceBlockEntityAccessor.aether$getItems(), i)) {
                abstractFurnaceBlockEntityAccessor.aether$setCookingProgress(abstractFurnaceBlockEntityAccessor.aether$getCookingProgress() + 1);
                if (abstractFurnaceBlockEntityAccessor.aether$getCookingProgress() == abstractFurnaceBlockEntityAccessor.aether$getCookingTotalTime()) {
                    abstractFurnaceBlockEntityAccessor.aether$setCookingProgress(0);
                    abstractFurnaceBlockEntityAccessor.aether$setCookingTotalTime(AbstractFurnaceBlockEntityAccessor.callGetTotalCookTime(level, blockEntity));
                    if (blockEntity.burn(recipe, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            } else {
                abstractFurnaceBlockEntityAccessor.aether$setCookingProgress(0);
            }
        } else if (!abstractFurnaceBlockEntityAccessor.callIsLit() && abstractFurnaceBlockEntityAccessor.aether$getCookingProgress() > 0) {
            abstractFurnaceBlockEntityAccessor.aether$setCookingProgress(Mth.clamp(abstractFurnaceBlockEntityAccessor.aether$getCookingProgress() - 2, 0, abstractFurnaceBlockEntityAccessor.aether$getCookingTotalTime()));
        }

        if (flag != abstractFurnaceBlockEntityAccessor.callIsLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, abstractFurnaceBlockEntityAccessor.callIsLit());
            level.setBlock(pos, state, 1 | 2);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }

        if (abstractFurnaceBlockEntityAccessor.aether$getItems().get(0).isEmpty() && abstractFurnaceBlockEntityAccessor.aether$getItems().get(2).isEmpty()) {
            blockEntity.remainderItem = ItemStack.EMPTY; // Resets the remainder item variable used for hopper extraction at the end of the tick loop. This is necessary so that it actually has enough time to get extracted.
        }
    }

    /**
     * Ensures that NBT is carried over between input and result stacks.<br><br>
     * Warning for "unchecked" is suppressed because casting {@link Recipe}<{@link WorldlyContainer}> is fine and done by vanilla.
     * @param recipe The {@link Recipe Recipe<?>} being burned.
     * @param stacks The {@link NonNullList NonNullList<ItemStack>} of items in the menu.
     * @param stackSize The max stack size as an {@link Integer}.
     * @return A {@link Boolean} for whether the item successfully burnt.
     */
    @SuppressWarnings("unchecked")
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

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            return this.getBurnDuration(stack) > 0;
        }
    }

    /**
     * Allows the Aether's furnaces to have remaining crafting byproducts extracted (like buckets) alongside product items.
     * @param index The {@link Integer} for the slot index.
     * @param stack The {@link ItemStack} trying to be taken from the block.
     * @param direction The {@link Direction} for a face.
     * @return Whether the item can be taken by a hopper through the face, as a {@link Boolean}.
     */
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        AbstractFurnaceBlockEntityAccessor abstractFurnaceBlockEntityAccessor = (AbstractFurnaceBlockEntityAccessor) this;
        Optional<NonNullList<Ingredient>> ingredient = abstractFurnaceBlockEntityAccessor.aether$getQuickCheck().getRecipeFor(this, this.getLevel()).map(AbstractCookingRecipe::getIngredients);
        if (this.remainderItem.isEmpty()) {
            ingredient.ifPresent(ing -> this.remainderItem = stack.getCraftingRemainingItem()); // Stores the correlating crafting remainder item.
        }
        if (direction == Direction.DOWN && index == 0) {
            if (!this.remainderItem.isEmpty()) {
                return stack.is(this.remainderItem.getItem()); // An item can be taken as long as it matches the stored crafting remainder item.
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void awardUsedRecipesAndPopExperience(ServerPlayer player) { }

    @Override
    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 experiencePos) {
        return new ArrayList<>();
    }
}
