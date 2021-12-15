package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.registry.AetherContainerTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;

import javax.annotation.Nonnull;
import java.util.Map;

public class AccessoriesContainer extends PlayerContainer
{
    private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[] {
            PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS,
            PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS,
            PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE,
            PlayerContainer.EMPTY_ARMOR_SLOT_HELMET
    };
    private static final EquipmentSlotType[] VALID_EQUIPMENT_SLOTS = new EquipmentSlotType[] {
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET
    };
    private static final String[] AETHER_IDENTIFIERS = new String[] {
            "aether_pendant",
            "aether_cape",
            "aether_shield",
            "aether_ring",
            "aether_gloves",
            "aether_accessory"
    };

    public final LazyOptional<ICuriosItemHandler> curiosHandler;

    private final PlayerEntity player;

    private final CraftingInventory craftMatrix = new CraftingInventory(this, 2, 2);
    private final CraftResultInventory craftResult = new CraftResultInventory();

    public boolean hasButton;

    public AccessoriesContainer(int containerId, PlayerInventory playerInventory) {
        this(containerId, playerInventory, true);
    }

    public AccessoriesContainer(int containerId, PlayerInventory playerInventory, boolean hasButton) {
        super(playerInventory, playerInventory.player.level.isClientSide, playerInventory.player);
        this.menuType = AetherContainerTypes.ACCESSORIES.get();
        this.containerId = containerId;
        this.lastSlots.clear();
        this.slots.clear();
        this.player = playerInventory.player;
        this.curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(this.player);
        this.hasButton = hasButton;

        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 28));

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.addSlot(new Slot(this.craftMatrix, j + i * 2, 116 + j * 18, 18 + i * 18));
            }
        }

        for (int k = 0; k < 4; ++k) {
            final EquipmentSlotType equipmentslottype = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(playerInventory, 36 + (3 - k), 59, 8 + k * 18)
            {
                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack p_75214_1_) {
                    return p_75214_1_.canEquip(equipmentslottype, AccessoriesContainer.this.player);
                }

                @Override
                public boolean mayPickup(PlayerEntity p_82869_1_) {
                    ItemStack itemstack = this.getItem();
                    return (itemstack.isEmpty() || p_82869_1_.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.mayPickup(p_82869_1_);
                }

                @Override
                @OnlyIn(Dist.CLIENT)
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(PlayerContainer.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
                }
            });
        }

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

        this.addSlot(new Slot(playerInventory, 40, 116, 62)
        {
            @Override
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        this.curiosHandler.ifPresent(curios -> {
            Map<String, ICurioStacksHandler> curioMap = curios.getCurios();
            int slots = 0;
            int xOffset = 77;
            int yOffset = 8;
            for (String identifier : AETHER_IDENTIFIERS) {
                ICurioStacksHandler stacksHandler = curioMap.get(identifier);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                if (!stacksHandler.isVisible()) {
                    for (int i = 0; i < stackHandler.getSlots(); i++) {
                        if (!identifier.equals("aether_accessory")) {
                            this.addSlot(new CurioSlot(this.player, stackHandler, i, identifier, xOffset, yOffset, stacksHandler.getRenders()));
                            slots++;
                            yOffset += 18;
                            if (slots % 3 == 0) {
                                xOffset += 18;
                                yOffset = 8;
                            }
                        } else {
                            if (slots == 6) {
                                xOffset = 77;
                            }
                            this.addSlot(new CurioSlot(this.player, stackHandler, i, identifier, xOffset, 62, stacksHandler.getRenders()));
                            slots++;
                            xOffset += 18;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void slotsChanged(@Nonnull IInventory inventoryIn) {
        WorkbenchContainer.slotChangedCraftingGrid(this.containerId, this.player.level, this.player, this.craftMatrix, this.craftResult);
    }

    @Override
    public void removed(@Nonnull PlayerEntity playerIn) {
        super.removed(playerIn);
        this.craftResult.clearContent();
        if (!playerIn.level.isClientSide) {
            this.clearContainer(playerIn, playerIn.level, this.craftMatrix);
        }
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity playerIn) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlotType entityequipmentslot = MobEntity.getEquipmentSlotForItem(itemstack);
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index < 5) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (entityequipmentslot.getType() == EquipmentSlotType.Group.ARMOR && !this.slots.get(8 - entityequipmentslot.getIndex()).hasItem()) {
                int i = 8 - entityequipmentslot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 46 && !CuriosApi.getCuriosHelper().getCurioTags(itemstack.getItem()).isEmpty()) {
                if (!this.moveItemStackTo(itemstack1, 46, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (entityequipmentslot == EquipmentSlotType.OFFHAND && !(this.slots.get(45)).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 36) {
                if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 45) {
                if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.drop(itemstack2, false);
            }
        }
        return itemstack;
    }

    @Nonnull
    @Override
    public RecipeBookCategory getRecipeBookType() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public void fillCraftSlotsStackedContents(RecipeItemHelper recipeItemHelper) {
        this.craftMatrix.fillStackedContents(recipeItemHelper);
    }

    @Override
    public void clearCraftingContent() {
        this.craftMatrix.clearContent();
        this.craftResult.clearContent();
    }

    @Override
    public boolean recipeMatches(IRecipe<? super CraftingInventory> iRecipe) {
        return iRecipe.matches(this.craftMatrix, this.player.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return this.craftMatrix.getWidth();
    }

    @Override
    public int getGridHeight() {
        return this.craftMatrix.getHeight();
    }

    @Override
    public int getSize() {
        return 5;
    }
}
