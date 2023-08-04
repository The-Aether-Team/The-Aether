package com.aetherteam.aether.mixin;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.common.util.LazyOptional;
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
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof CapeItem);
        if (slotResult.isPresent()) {
            String identifier = slotResult.get().slotContext().identifier();
            int id = slotResult.get().slotContext().index();
            LazyOptional<ICuriosItemHandler> itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player);
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
}
