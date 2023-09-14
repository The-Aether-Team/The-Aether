package com.aetherteam.aether.mixin;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.nio.file.Path;
import java.util.Optional;

public class AetherMixinHooks {
    /**
     * Checks whether a cape accessory is visible.
     * @param player The {@link AbstractClientPlayer} wearing the cape.
     * @return Whether the cape is visible, as a {@link Boolean}.
     * @see com.aetherteam.aether.mixin.mixins.client.AbstractClientPlayerMixin
     */
    public static boolean isCapeVisible(AbstractClientPlayer player) {
        Optional<SlotResult> slotResult = EquipmentUtil.findFirstCurio(player, (item) -> item.getItem() instanceof CapeItem);
        if (slotResult.isPresent()) {
            String identifier = slotResult.get().slotContext().identifier();
            int id = slotResult.get().slotContext().index();
            LazyOptional<ICuriosItemHandler> itemHandler = CuriosApi.getCuriosInventory(player);
            if (itemHandler.resolve().isPresent()) {
                Optional<ICurioStacksHandler> stacksHandler = itemHandler.resolve().get().getStacksHandler(identifier);
                if (stacksHandler.isPresent()) {
                    return stacksHandler.get().getRenders().get(id);
                }
            }
        }
        return false;
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

    public static String getIdentifierForItem(Mob mob, ItemStack stack) {
        if (AetherConfig.COMMON.use_curios_menu.get()) {
            TagKey<Item> glovesTag = TagKey.create(Registries.ITEM, new ResourceLocation(Curios.MODID, "hands"));
            TagKey<Item> pendantTag = TagKey.create(Registries.ITEM, new ResourceLocation(Curios.MODID, "necklace"));
            if (stack.is(glovesTag)) {
                return "hands";
            } else if (stack.is(pendantTag) && (mob.getType() == EntityType.PIGLIN || mob.getType() == EntityType.ZOMBIFIED_PIGLIN)) {
                return "necklace";
            }
        } else {
            if (stack.is(AetherTags.Items.AETHER_GLOVES)) {
                return "aether_gloves";
            } else if (stack.is(AetherTags.Items.AETHER_PENDANT) && (mob.getType() == EntityType.PIGLIN || mob.getType() == EntityType.ZOMBIFIED_PIGLIN)) {
                return "aether_pendant";
            }
        }
        return "";
    }

    public static ItemStack getItemByIdentifier(Mob mob, String identifier) {
        LazyOptional<ICuriosItemHandler> lazyHandler = CuriosApi.getCuriosInventory(mob);
        if (lazyHandler.isPresent() && lazyHandler.resolve().isPresent()) {
            ICuriosItemHandler handler = lazyHandler.resolve().get();
            Optional<SlotResult> optionalResult = handler.findCurio(identifier, 0);
            if (optionalResult.isPresent()) {
                return optionalResult.get().stack();
            }
        }
        return ItemStack.EMPTY;
    }

    public static void setItemByIdentifier(Mob mob, ItemStack itemStack, String identifier) {
        LazyOptional<ICuriosItemHandler> lazyHandler = CuriosApi.getCuriosInventory(mob);
        if (lazyHandler.isPresent() && lazyHandler.resolve().isPresent()) {
            ICuriosItemHandler handler = lazyHandler.resolve().get();
            handler.setEquippedCurio(identifier, 0, itemStack);
        }
    }
}
