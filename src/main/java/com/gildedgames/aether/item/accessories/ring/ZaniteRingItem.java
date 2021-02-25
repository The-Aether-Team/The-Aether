package com.gildedgames.aether.item.accessories.ring;

import com.gildedgames.aether.item.accessories.AccessoryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber
public class ZaniteRingItem extends AccessoryItem {

    public ZaniteRingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if(livingEntity.ticksExisted % 400 == 0) {
            stack.damageItem(1, livingEntity, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, entity));
        }
    }
}
