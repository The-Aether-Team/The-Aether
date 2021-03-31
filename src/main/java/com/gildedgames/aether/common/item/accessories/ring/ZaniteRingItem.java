package com.gildedgames.aether.common.item.accessories.ring;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.common.item.accessories.abilities.IZaniteAccessory;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

public class ZaniteRingItem extends AccessoryItem
{
    public ZaniteRingItem(Properties properties) {
        super(properties);
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