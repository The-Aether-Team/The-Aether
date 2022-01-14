package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.item.accessories.abilities.FreezingAccessory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class IcePendantItem extends PendantItem implements FreezingAccessory
{
    public IcePendantItem(Properties properties) {
        super("ice_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_PENDANT, properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (!(livingEntity instanceof Player player) || (!player.getAbilities().flying && !player.isSpectator())) {
            int damage = this.freezeBlocks(livingEntity.level, livingEntity.blockPosition(), stack, 1.9f);
            stack.hurtAndBreak(damage / 3, livingEntity, wearer -> CuriosApi.getCuriosHelper().onBrokenCurio(slotContext));
        }
    }
}
