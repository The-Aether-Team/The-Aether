package com.aetherteam.aether.mixin;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import com.aetherteam.aether.mixin.mixins.common.accessor.MinecraftServerAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.nio.file.Path;
import java.util.stream.StreamSupport;

public class AetherMixinHooks {
    private static final ResourceLocation SWUFF_CAPE_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/capes/swuff_accessory.png");

    /**
     * Checks whether a cape accessory is visible.
     *
     * @param livingEntity The {@link LivingEntity} wearing the cape.
     * @return Whether the cape is visible, as a {@link Boolean}.
     * @see com.aetherteam.aether.mixin.mixins.client.PlayerSkinMixin
     */
    public static ItemStack isCapeVisible(LivingEntity livingEntity) {
        AccessoriesCapability accessories = AccessoriesCapability.get(livingEntity);
        if (accessories != null) {
            AccessoriesContainer accessoriesContainer = accessories.getContainer(CapeItem.getStaticIdentifier());

            if (accessoriesContainer != null) {
                ExpandedSimpleContainer simpleAccessoriesContainer = accessoriesContainer.getAccessories();
                ExpandedSimpleContainer simpleCosmeticsContainer = accessoriesContainer.getCosmeticAccessories();

                Pair<Integer, ItemStack> stack = StreamSupport.stream(simpleAccessoriesContainer.spliterator(), true).findFirst().orElse(null);
                Pair<Integer, ItemStack> cosmeticStack = StreamSupport.stream(simpleCosmeticsContainer.spliterator(), true).findFirst().orElse(null);
                if (cosmeticStack != null && !cosmeticStack.getSecond().isEmpty() && Accessories.config().clientOptions.showCosmeticAccessories()) {
                    stack = cosmeticStack;
                }
                if (stack != null) {
                    if (accessoriesContainer.shouldRender(stack.getFirst())) {
                        return stack.getSecond();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Gets the cape texture from a {@link CapeItem}.
     *
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
     *
     * @param basePath The {@link Path} for the level directory.
     * @return Whether the level can be unlocked, as a {@link Boolean}.
     * @see com.aetherteam.aether.mixin.mixins.common.DirectoryLockMixin
     */
    public static boolean canUnlockLevel(Path basePath) {
        if (Minecraft.getInstance().screen != null && Minecraft.getInstance().screen instanceof SelectWorldScreen && Minecraft.getInstance().getSingleplayerServer() != null) {
            return basePath.getFileName().toString().equals(((MinecraftServerAccessor) Minecraft.getInstance().getSingleplayerServer()).aether$getStorageSource().getLevelId());
        }
        return false;
    }
}
