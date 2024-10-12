package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.advancement.AetherAdvancementTriggers;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import com.aetherteam.aether.inventory.menu.IncubatorMenu;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

/**
 * [CODE COPY] - {@link AbstractFurnaceBlockEntity}.<br><br>
 * Has heavy modifications for Incubator-specific behavior.
 */
public class IncubatorBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private static final int[] SLOTS_NS = {0};
    private static final int[] SLOTS_EW = {1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private ServerPlayer player; // The last player to put an item in the egg slot.
    private int litTime; // The current fuel burning progress time.
    private int litDuration; // Total time it takes a fuel item to burn.
    private int incubationProgress; // The current incubation progress time.
    private int incubationTotalTime; // Total time a recipe takes to incubate.
    private int x; // The x position of the block entity.
    private int y; // The y position of the block entity.
    private int z; // The z position of the block entity.
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> IncubatorBlockEntity.this.litTime;
                case 1 -> IncubatorBlockEntity.this.litDuration;
                case 2 -> IncubatorBlockEntity.this.incubationProgress;
                case 3 -> IncubatorBlockEntity.this.incubationTotalTime;
                case 4 -> IncubatorBlockEntity.this.x;
                case 5 -> IncubatorBlockEntity.this.y;
                case 6 -> IncubatorBlockEntity.this.z;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> IncubatorBlockEntity.this.litTime = value;
                case 1 -> IncubatorBlockEntity.this.litDuration = value;
                case 2 -> IncubatorBlockEntity.this.incubationProgress = value;
                case 3 -> IncubatorBlockEntity.this.incubationTotalTime = value;
                case 4 -> IncubatorBlockEntity.this.x = value;
                case 5 -> IncubatorBlockEntity.this.y = value;
                case 6 -> IncubatorBlockEntity.this.z = value;
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInput, IncubationRecipe> quickCheck;

    public IncubatorBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, AetherRecipeTypes.INCUBATION.get());
    }

    public IncubatorBlockEntity(BlockPos pos, BlockState state, RecipeType<IncubationRecipe> recipeType) {
        super(AetherBlockEntityTypes.INCUBATOR.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(recipeType);
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new IncubatorMenu(id, playerInventory, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, IncubatorBlockEntity blockEntity) {
        boolean flag = blockEntity.isLit();
        boolean flag1 = false;

        if (blockEntity.isLit()) {
            --blockEntity.litTime;
        }

        ItemStack itemstack = blockEntity.items.get(1);
        ItemStack itemstack1 = blockEntity.items.get(0);
        boolean flag2 = !itemstack1.isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2) {
            RecipeHolder<IncubationRecipe> recipe;
            if (flag2) {
                recipe = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(itemstack1), level).orElse(null);
            } else {
                recipe = null;
            }

            if (!blockEntity.isLit() && blockEntity.canIncubate(recipe, blockEntity.items)) {
                blockEntity.litTime = blockEntity.getBurnDuration(itemstack);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    flag1 = true;
                    if (itemstack.hasCraftingRemainingItem()) {
                        blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                    } else if (flag3) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && blockEntity.canIncubate(recipe, blockEntity.items)) {
                ++blockEntity.incubationProgress;
                if (blockEntity.incubationProgress == blockEntity.incubationTotalTime) {
                    blockEntity.incubationProgress = 0;
                    blockEntity.incubationTotalTime = getTotalIncubationTime(level, blockEntity);
                    if (blockEntity.incubate(recipe, blockEntity.items)) {
                        blockEntity.setRecipeUsed(recipe);
                    }
                    flag1 = true;
                }
            } else {
                blockEntity.incubationProgress = 0;
            }
        } else if (!blockEntity.isLit() && blockEntity.incubationProgress > 0) {
            blockEntity.incubationProgress = Mth.clamp(blockEntity.incubationProgress - 2, 0, blockEntity.incubationTotalTime);
        }

        if (flag != blockEntity.isLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
            level.setBlock(pos, state, 1 | 2);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }

        if (blockEntity.x != pos.getX()) {
            blockEntity.x = pos.getX();
        }
        if (blockEntity.y != pos.getY()) {
            blockEntity.y = pos.getY();
        }
        if (blockEntity.z != pos.getZ()) {
            blockEntity.z = pos.getZ();
        }
    }

    /**
     * Spawns an entity on top of the incubator with the recipe's NBT data and the item's custom name.
     *
     * @param recipe The {@link IncubationRecipe} being incubated.
     * @param stacks The {@link NonNullList NonNullList<ItemStack>} of items in the menu.
     * @return A {@link Boolean} for whether the item successfully incubated.
     */
    private boolean incubate(@Nullable RecipeHolder<IncubationRecipe> recipe, NonNullList<ItemStack> stacks) {
        if (recipe != null && this.canIncubate(recipe, stacks)) {
            ItemStack itemStack = stacks.get(0);
            EntityType<?> entityType = recipe.value().getEntity();
            BlockPos spawnPos = this.getBlockPos().above();
            if (this.level != null && !this.level.isClientSide() && this.level instanceof ServerLevel serverLevel) {
                CompoundTag tag = null;
                if (recipe.value().getTag().isPresent()) {
                    tag = recipe.value().getTag().get();
                }
                Component customName = itemStack.has(DataComponents.CUSTOM_NAME) ? itemStack.getHoverName() : null;
                Entity entity = entityType.spawn(serverLevel, tag, null, spawnPos, MobSpawnType.TRIGGERED, true, false);
                if (entity != null) {
                    entity.setCustomName(customName);
                    if (this.player != null) {
                        AetherAdvancementTriggers.INCUBATION_TRIGGER.get().trigger(this.player, itemStack);
                    }
                }
            }
            itemStack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    private boolean canIncubate(@Nullable RecipeHolder<IncubationRecipe> recipe, NonNullList<ItemStack> stacks) {
        return !stacks.get(0).isEmpty() && recipe != null;
    }

    protected int getBurnDuration(ItemStack fuelStack) {
        if (!fuelStack.isEmpty()) {
            var datamap = fuelStack.getItem().builtInRegistryHolder().getData(AetherDataMaps.INCUBATOR_FUEL);
            if (datamap != null) {
                return datamap.burnTime();
            }
        }
        return 0;
    }

    private static int getTotalIncubationTime(Level level, IncubatorBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.items.getFirst()), level).map((recipe) -> recipe.value().getIncubationTime()).orElse(5700);
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    public void setPlayer(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        if (index == 0 && !flag) {
            this.incubationTotalTime = getTotalIncubationTime(this.level, this);
            this.incubationProgress = 0;
            this.setChanged();
        }
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void fillStackedContents(StackedContents helper) {
        for (ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            return SLOTS_NS;
        } else {
            return SLOTS_EW;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 1) {
            return this.getBurnDuration(stack) > 0;
        } else {
            return true;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.id();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.getBlockPos()) != this) {
            return false;
        } else {
            return player.distanceToSqr(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5) <= 64.0;
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + Aether.MODID + ".incubator");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.litTime = tag.getInt("LitTime");
        this.litDuration = this.getBurnDuration(this.items.get(1));
        this.incubationProgress = tag.getInt("IncubationProgress");
        this.incubationTotalTime = tag.getInt("IncubationTotalTime");
        CompoundTag compoundtag = tag.getCompound("RecipesUsed");
        for (String string : compoundtag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.withDefaultNamespace(string), compoundtag.getInt(string));
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("LitTime", this.litTime);
        tag.putInt("IncubationProgress", this.incubationProgress);
        tag.putInt("IncubationTotalTime", this.incubationTotalTime);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        CompoundTag compoundTag = new CompoundTag();
        this.recipesUsed.forEach((location, integer) -> compoundTag.putInt(location.toString(), integer));
        tag.put("RecipesUsed", compoundTag);
    }
}
