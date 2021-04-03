package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class ZanitePendantItem extends PendantItem
{
    public ZanitePendantItem(Properties properties) {
        super("zanite_pendant", properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.getItem() == AetherItems.ZANITE_GEMSTONE.get();
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity.tickCount % 400 == 0) {
            stack.hurtAndBreak(1, livingEntity, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, entity));
        }
    }
}