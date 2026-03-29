package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.mixin.mixins.common.accessor.AbstractContainerMenuAccessor;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.ClientGrabItemPacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.mojang.datafixers.util.Pair;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.menu.AccessoriesBasedSlot;
import io.wispforest.accessories.api.slot.SlotType;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.*;

/**
 * [CODE COPY] - {@link top.theillusivec4.curios.common.inventory.container.CuriosContainer}<br><br>
 * Heavily adapted to only set up Aether curio types.
 */
public class AccessoriesMenu extends InventoryMenu {
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
    public static final String[] AETHER_IDENTIFIERS = new String[] {
            "aether_pendant",
            "aether_cape",
            "aether_shield",
            "aether_ring",
            "aether_gloves",
            "aether_accessory"
    };
    public static final String[] AETHER_CURIOS_IDENTIFIERS = new String[] {
            "necklace",
            "cape", // Whilst accessories uses cape and back,
            "back", // Curios uses back and body.
            "ring",
            "hand",
            "charm"
    };

    public final Optional<AccessoriesCapability> curiosHandler;
    private final Player player;

    private final CraftingContainer craftMatrix = new TransientCraftingContainer(this, 2, 2);
    private final ResultContainer craftResult = new ResultContainer();

    public final boolean hasButton;
    protected boolean slotUpdateFlag;

    public AccessoriesMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, true);
    }

    public AccessoriesMenu(int containerId, Inventory playerInventory, boolean hasButton) {
        super(playerInventory, playerInventory.player.level().isClientSide(), playerInventory.player);
        AbstractContainerMenuAccessor abstractContainerMenuAccessor = (AbstractContainerMenuAccessor) this;
        abstractContainerMenuAccessor.aether$setMenuType(AetherMenuTypes.ACCESSORIES.get());
        abstractContainerMenuAccessor.aether$setContainerId(containerId);
        abstractContainerMenuAccessor.aether$getRemoteSlots().clear();
        abstractContainerMenuAccessor.aether$getLastSlots().clear();
        this.slots.clear();
        this.player = playerInventory.player;
        this.curiosHandler = Optional.ofNullable(this.player.accessoriesCapability());
        this.hasButton = hasButton;

        this.addSlot(new ResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 28));

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.addSlot(new Slot(this.craftMatrix, j + i * 2, 116 + j * 18, 18 + i * 18));
            }
        }

        for (int k = 0; k < 4; ++k) {
            final EquipmentSlot equipmentSlotType = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(playerInventory, 36 + (3 - k), 59, 8 + k * 18) {
                @Override
                public void set(ItemStack stack) {
                    ItemStack itemStack = this.getItem();
                    super.set(stack);
                    AccessoriesMenu.this.player.onEquipItem(equipmentSlotType, itemStack, stack);
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return Mob.getEquipmentSlotForItem(stack) == equipmentSlotType;
                }

                @Override
                public boolean mayPickup(Player player) {
                    ItemStack itemStack = this.getItem();
                    return (itemStack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.mayPickup(player);
                }

                @Override
                @Environment(EnvType.CLIENT)
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

        this.addSlot(new Slot(playerInventory, 40, 116, 62) {
            @Override
            @Environment(EnvType.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        this.curiosHandler.ifPresent(curios -> {
            Map<String, AccessoriesContainer> curioMap = curios.getContainers();
            int slots = 0;
            int xOffset = 77;
            int yOffset = 8;
            for (String identifier : AetherConfig.COMMON.use_curios_menu.get() ? AETHER_CURIOS_IDENTIFIERS : AETHER_IDENTIFIERS) { // Creates the slots for all the Aether Accessory identifiers.
                AccessoriesContainer stacksHandler = curioMap.get(identifier);
                if(stacksHandler == null) continue;
                ExpandedSimpleContainer stackHandler = stacksHandler.getAccessories();
//                if (!stacksHandler.isVisible()) {
                    for (int i = 0; i < stacksHandler.getSize(); i++) {
                        if (!identifier.equals("aether_accessory") && !identifier.equals("charm")) {
                            this.addSlot(AccessoriesBasedSlot.of(this.player, stacksHandler.slotType(), i, xOffset, yOffset));
                            slots++;
                            yOffset += 18;
                            if (slots % 3 == 0) {
                                xOffset += 18;
                                yOffset = 8;
                            }
                        } else {
                            if (i == 0) {
                                xOffset = 77;
                            }
                            this.addSlot(AccessoriesBasedSlot.of(this.player, stacksHandler.slotType(), i, xOffset, 62));
                            slots++;
                            xOffset += 18;
                        }
                    }
//                }
            }
        });
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents contents) {
        this.craftMatrix.fillStackedContents(contents);
    }

    @Override
    public void clearCraftingContent() {
        this.craftMatrix.clearContent();
        this.craftResult.clearContent();
    }

    @Override
    public boolean recipeMatches(Recipe<? super CraftingContainer> recipe) {
        return recipe.matches(this.craftMatrix, this.player.level());
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

    @Override
    public void slotsChanged(Container container) {
        if (!this.player.level().isClientSide()) {
            ServerPlayer playerMP = (ServerPlayer) this.player;
            ItemStack itemStack = ItemStack.EMPTY;
            MinecraftServer server = this.player.level().getServer();

            if (server == null) {
                return;
            }
            Optional<CraftingRecipe> recipe = server.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, this.craftMatrix, this.player.level());

            if (recipe.isPresent()) {
                CraftingRecipe craftingRecipe = recipe.get();
                if (this.craftResult.setRecipeUsed(this.player.level(), playerMP, craftingRecipe)) {
                    itemStack = craftingRecipe.assemble(this.craftMatrix, this.player.level().registryAccess());
                }
            }
            this.craftResult.setItem(0, itemStack);
            this.setRemoteSlot(0, itemStack);
            playerMP.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, itemStack));
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.craftResult.clearContent();
        if (!player.level().isClientSide()) {
            this.clearContainer(player, this.craftMatrix);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
            Collection<SlotType> curioTags = AccessoriesAPI.getValidSlotTypes(player, itemStack);
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
            } else if (index < 46 && !curioTags.isEmpty() && !this.getEmptyCurioSlots(curioTags).isEmpty()) {
                for (int i : this.getEmptyCurioSlots(curioTags)) {
                    if (!this.moveItemStackTo(itemStack1, i, i + 1, false)) {
                        return ItemStack.EMPTY;
                    }
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
            slot.onTake(player, itemStack1);
            if (index == 0) {
                player.drop(itemStack1, false);
            }
        }
        return itemStack;
    }

    private Set<Integer> getEmptyCurioSlots(Collection<SlotType> slotData) {
        Set<Integer> slots = new HashSet<>();
        for (int slotIndex = 46; slotIndex < this.slots.size(); slotIndex++) {
            for (SlotType identifier : slotData) {
                if(this.slots.get(slotIndex) instanceof AccessoriesBasedSlot accessoriesBasedSlot && identifier.equals(accessoriesBasedSlot.accessoriesContainer.slotType())) {
                    slots.add(slotIndex); // Adds slot IDs agnostically to the slot identifiers themselves, so to work with Curios slot names in addition to Accessories, as well as with modified slot counts.
                }
            }
        }
        slots.removeIf(index -> this.slots.get(index).hasItem());
        return slots;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public void trinkets$updateTrinketSlots(boolean slotsChanged) { } // Prevents instantiation of InventoryMenu from refreshing Trinkets slots.

    public static void queueSlotRefresh(LivingEntity livingEntity, AccessoriesCapability capability, Map<AccessoriesContainer, Boolean> updatedContainers) {
        if (livingEntity instanceof ServerPlayer serverPlayer && serverPlayer.containerMenu instanceof AccessoriesMenu accessoriesMenu) {
            if (!updatedContainers.isEmpty() && updatedContainers.containsValue(true)) {
                accessoriesMenu.slotUpdateFlag = true;
            } else if (accessoriesMenu.slotUpdateFlag) { // After slot counts have updated, refresh the menu with the new slot layout. Will cause loss of mouse position.
                accessoriesMenu.slotUpdateFlag = false;
                ItemStack itemStack = accessoriesMenu.getCarried();
                accessoriesMenu.setCarried(ItemStack.EMPTY);
                serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, playerEntity) -> new AccessoriesMenu(id, inventory), Component.translatable("container.crafting")));
                serverPlayer.containerMenu.setCarried(itemStack);
                PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new ClientGrabItemPacket(itemStack), serverPlayer);
            }
        }
    }
}
