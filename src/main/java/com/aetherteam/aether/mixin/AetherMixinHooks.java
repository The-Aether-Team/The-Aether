package com.aetherteam.aether.mixin;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import io.wispforest.accessories.Accessories;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotType;
import io.wispforest.accessories.data.SlotTypeLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.nio.file.Path;
import java.util.Optional;

public class AetherMixinHooks {
    private static final ResourceLocation SWUFF_CAPE_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/accessory/capes/swuff_accessory.png");

    /**
     * Checks whether a cape accessory is visible.
     * @param livingEntity The {@link LivingEntity} wearing the cape.
     * @return Whether the cape is visible, as a {@link Boolean}.
     * @see com.aetherteam.aether.mixin.mixins.client.AbstractClientPlayerMixin
     */
    public static boolean isCapeVisible(LivingEntity livingEntity) {
        Optional<SlotEntryReference> slotResult = EquipmentUtil.findFirstCurio(livingEntity, (item) -> item.getItem() instanceof CapeItem);
        if (slotResult.isPresent()) {
            SlotType identifier = slotResult.get().reference().type();
            int id = slotResult.get().reference().slot();
            AccessoriesCapability itemHandler = livingEntity.accessoriesCapability();
            if (itemHandler != null) {
                AccessoriesContainer stacksHandler = itemHandler.getContainer(identifier);
                if (stacksHandler != null) {
                    return stacksHandler.shouldRender(id);
                }
            }
        }
        return false;
    }

    /**
     * Gets the cape texture from a {@link CapeItem}.
     * @param stack The {@link ItemStack}.
     * @return The {@link ResourceLocation} texture from the cape.
     */
    public static ResourceLocation getCapeTexture(ItemStack stack) {
        if (stack.getItem() instanceof CapeItem capeItem) {
            if (stack.getHoverName().getString().equalsIgnoreCase("swuff_'s cape")) { // Easter Egg cape texture.
                return SWUFF_CAPE_LOCATION;
            } else {
                return capeItem.getCapeTexture();
            }
        }
        return null;
    }

    /**
     * Checks whether the {@link SelectWorldScreen} is open and the level that the lock belongs to is the same one as the level loaded by the world preview.
     * @param basePath The {@link Path} for the level directory.
     * @return Whether the level can be unlocked, as a {@link Boolean}.
     * @see com.aetherteam.aether.mixin.mixins.common.DirectoryLockMixin
     */
    public static boolean canUnlockLevel(Path basePath) {
        if (Minecraft.getInstance().screen != null && Minecraft.getInstance().screen instanceof SelectWorldScreen) {
            return basePath.getFileName().toString().equals(WorldDisplayHelper.getLevelSummary().getLevelId());
        }
        return false;
    }

    /**
     * Whether an accessory can be equipped or replace an already equipped accessory.
     * @param mob The {@link Mob} to equip the accessory to.
     * @param candidate The {@link ItemStack} to try to equip.
     * @param existing The {@link ItemStack} already equipped.
     * @return Whether the accessory can be equipped or replaced, as a {@link Boolean}.
     */
    public static boolean canReplaceCurrentAccessory(Mob mob, ItemStack candidate, ItemStack existing) {
        if (EnchantmentHelper.hasBindingCurse(existing)) {
            return false;
        } else {
            if (candidate.getItem() instanceof GlovesItem candidateGloves) {
                if (!(existing.getItem() instanceof GlovesItem existingGloves)) {
                    return true;
                } else {
                    if (candidateGloves.getDamage() != existingGloves.getDamage()) {
                        return candidateGloves.getDamage() > existingGloves.getDamage();
                    } else {
                        return mob.canReplaceEqualItem(candidate, existing);
                    }
                }
            } else if (candidate.getItem() instanceof PendantItem) {
                if (!(existing.getItem() instanceof PendantItem)) {
                    return true;
                } else {
                    return mob.canReplaceEqualItem(candidate, existing);
                }
            }
        }
        return false;
    }

    /**
     * Gets the corresponding slot identifier for an accessory item.
     * @param livingEntity The {@link LivingEntity} to get the accessory from.
     * @param stack The accessory {@link ItemStack}.
     * @return The slot identifier {@link String}.
     */
    public static String getIdentifierForItem(LivingEntity livingEntity, ItemStack stack) {
        if (AetherConfig.COMMON.use_curios_menu.get()) {
            TagKey<Item> glovesTag = TagKey.create(Registries.ITEM, Accessories.of("hands"));
            TagKey<Item> pendantTag = TagKey.create(Registries.ITEM, Accessories.of("necklace"));
            if (stack.is(glovesTag)) {
                return "hands";
            } else if (stack.is(pendantTag) && (livingEntity.getType() == EntityType.PIGLIN || livingEntity.getType() == EntityType.ZOMBIFIED_PIGLIN)) {
                return "necklace";
            }
        } else {
            if (stack.is(AetherTags.Items.AETHER_GLOVES)) {
                return "aether_gloves";
            } else if (stack.is(AetherTags.Items.AETHER_PENDANT) && (livingEntity.getType() == EntityType.PIGLIN || livingEntity.getType() == EntityType.ZOMBIFIED_PIGLIN)) {
                return "aether_pendant";
            }
        }
        return "";
    }

    /**
     * Gets an accessory from an entity.
     * @param livingEntity The {@link LivingEntity} to get the accessory from.
     * @param identifier The {@link String} for the slot identifier.
     * @return The accessory {@link ItemStack} gotten from the entity.
     */
    public static ItemStack getItemByIdentifier(LivingEntity livingEntity, String identifier) {
        AccessoriesCapability handler = livingEntity.accessoriesCapability();
        SlotType type = SlotTypeLoader.getSlotType(livingEntity, identifier);
        if (handler != null) {
            AccessoriesContainer container = handler.getContainer(type);
            if (container != null) {
                return container.getAccessories().getItem(0);
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Equips an accessory to an entity.
     * @param livingEntity The {@link LivingEntity} to equip to.
     * @param itemStack The {@link ItemStack} to equip.
     * @param identifier The {@link String} for the slot identifier.
     */
    public static void setItemByIdentifier(LivingEntity livingEntity, ItemStack itemStack, String identifier) {
        AccessoriesCapability handler = livingEntity.accessoriesCapability();
        SlotType type = SlotTypeLoader.getSlotType(livingEntity, identifier);
        if (handler != null) {
            AccessoriesContainer container = handler.getContainer(type);
            if (container != null) {
                container.getAccessories().setItem(0, itemStack);
            }
        }
    }
}
