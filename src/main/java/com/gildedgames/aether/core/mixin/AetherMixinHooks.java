package com.gildedgames.aether.core.mixin;

import com.gildedgames.aether.common.item.accessories.cape.CapeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public class AetherMixinHooks {
    public static <T extends LivingEntity> ResourceLocation elytraLayerMixin(ItemStack stack, T entity) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findFirstCurio(entity, (item) -> item.getItem() instanceof CapeItem);
        if (slotResult.isPresent()) {
            String identifier = slotResult.get().slotContext().identifier();
            int id = slotResult.get().slotContext().index();
            LazyOptional<ICuriosItemHandler> itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
            if (itemHandler.resolve().isPresent()) {
                Optional<ICurioStacksHandler> stacksHandler = itemHandler.resolve().get().getStacksHandler(identifier);
                CapeItem cape = (CapeItem) slotResult.get().stack().getItem();
                boolean isCapeVisible = stacksHandler.get().getRenders().get(id);
                if (cape.getCapeTexture() != null && isCapeVisible) {
                    return cape.getCapeTexture();
                }
            }
        }
        return null;
    }
}
