package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.registry.AetherContainerTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.resources.ResourceLocation;
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
import java.util.Optional;

public class AccessoriesContainer extends InventoryMenu
{
    private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[] {
            InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS,
            InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
            InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
            InventoryMenu.EMPTY_ARMOR_SLOT_HELMET
    };
    private static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EquipmentSlot[] {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
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
    private final Player player;

    private final CraftingContainer craftMatrix = new CraftingContainer(this, 2, 2);
    private final ResultContainer craftResult = new ResultContainer();

    public boolean hasButton;

    public AccessoriesContainer(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, true);
    }

    public AccessoriesContainer(int containerId, Inventory playerInventory, boolean hasButton) {
        super(playerInventory, playerInventory.player.level.isClientSide, playerInventory.player);
        this.menuType = AetherContainerTypes.ACCESSORIES.get();
        this.containerId = containerId;
        this.remoteSlots.clear();
        this.lastSlots.clear();
        this.slots.clear();
        this.player = playerInventory.player;
        this.curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(this.player);
        this.hasButton = hasButton;

        this.addSlot(new ResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 28));

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.addSlot(new Slot(this.craftMatrix, j + i * 2, 116 + j * 18, 18 + i * 18));
            }
        }

        for (int k = 0; k < 4; ++k) {
            final EquipmentSlot equipmentSlotType = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(playerInventory, 36 + (3 - k), 59, 8 + k * 18)
            {
                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(@Nonnull ItemStack stack) {
                    return stack.canEquip(equipmentSlotType, AccessoriesContainer.this.player);
                }

                @Override
                public boolean mayPickup(@Nonnull Player player) {
                    ItemStack itemStack = this.getItem();
                    return (itemStack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.mayPickup(player);
                }

                @Override
                @OnlyIn(Dist.CLIENT)
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES[equipmentSlotType.getIndex()]);
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
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
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
    public void slotsChanged(@Nonnull Container inventoryIn) {
        if (!this.player.level.isClientSide) {
            ServerPlayer playerMP = (ServerPlayer) this.player;
            ItemStack itemStack = ItemStack.EMPTY;
            MinecraftServer server = this.player.level.getServer();

            if (server == null) {
                return;
            }
            Optional<CraftingRecipe> recipe = server.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, this.craftMatrix, this.player.level);

            if (recipe.isPresent()) {
                CraftingRecipe craftingRecipe = recipe.get();
                if (this.craftResult.setRecipeUsed(this.player.level, playerMP, craftingRecipe)) {
                    itemStack = craftingRecipe.assemble(this.craftMatrix);
                }
            }
            this.craftResult.setItem(0, itemStack);
            this.setRemoteSlot(0, itemStack);
            playerMP.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, itemStack));
        }
    }

    @Override
    public void removed(@Nonnull Player playerIn) {
        super.removed(playerIn);
        this.craftResult.clearContent();
        if (!playerIn.level.isClientSide) {
            this.clearContainer(playerIn, this.craftMatrix);
        }
    }

    @Override
    public boolean stillValid(@Nonnull Player playerIn) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
            if (index == 0) {
                if (!this.moveItemStackTo(itemStack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index < 5) {
                if (!this.moveItemStackTo(itemStack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 9) {
                if (!this.moveItemStackTo(itemStack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentSlot.getIndex()).hasItem()) {
                int i = 8 - equipmentSlot.getIndex();
                if (!this.moveItemStackTo(itemStack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 46 && !CuriosApi.getCuriosHelper().getCurioTags(itemStack.getItem()).isEmpty()) {
                if (!this.moveItemStackTo(itemStack1, 46, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !(this.slots.get(45)).hasItem()) {
                if (!this.moveItemStackTo(itemStack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 36) {
                if (!this.moveItemStackTo(itemStack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 45) {
                if (!this.moveItemStackTo(itemStack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemStack1);

            if (index == 0) {
                playerIn.drop(itemStack1, false);
            }
        }
        return itemStack;
    }

    @Nonnull
    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public void fillCraftSlotsStackedContents(@Nonnull StackedContents itemHelper) {
        this.craftMatrix.fillStackedContents(itemHelper);
    }

    @Override
    public void clearCraftingContent() {
        this.craftMatrix.clearContent();
        this.craftResult.clearContent();
    }

    @Override
    public boolean recipeMatches(Recipe<? super CraftingContainer> recipe) {
        return recipe.matches(this.craftMatrix, this.player.level);
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
