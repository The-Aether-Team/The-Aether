package com.aetherteam.aether.mixin;

import com.aetherteam.aether.item.accessories.cape.CapeItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public class AetherMixinHooks {
    public static boolean isCapeVisible(AbstractClientPlayer player) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof CapeItem);
        if (slotResult.isPresent()) {
            String identifier = slotResult.get().slotContext().identifier();
            int id = slotResult.get().slotContext().index();
            LazyOptional<ICuriosItemHandler> itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player);
            if (itemHandler.resolve().isPresent()) {
                Optional<ICurioStacksHandler> stacksHandler = itemHandler.resolve().get().getStacksHandler(identifier);
                return stacksHandler.get().getRenders().get(id);
            }
        }
        return false;
    }
}
