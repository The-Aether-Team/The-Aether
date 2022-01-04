package com.gildedgames.aether.common.item.accessories.ring;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.item.accessories.abilities.FreezingItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class IceRingItem extends RingItem implements FreezingItem
{
    public IceRingItem(Properties properties) {
        super(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_RING, properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player && (player.isCreative() || player.isSpectator())) return;

        int damage = this.freezeBlocks(livingEntity.level, livingEntity.blockPosition(), stack, 1.9f);
        stack.hurtAndBreak(damage / 3, livingEntity, entity -> CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, entity));
    }
}
